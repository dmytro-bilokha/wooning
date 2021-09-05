package com.dmytrobilokha.wooning;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.math3.stat.StatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ReportJob {

    private static final Logger LOG = LoggerFactory.getLogger(ReportJob.class);

    private ReportJob() {
        //No instantiation
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            LOG.error("CSV file name is a required argument");
            System.exit(1);
        }
        var fileName = args[0];
        List<RawHousingData> rawLines = null;
        try {
            rawLines = new CsvToBeanBuilder<RawHousingData>(Files.newBufferedReader(Paths.get(fileName)))
                    .withType(RawHousingData.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            LOG.error("Failed to open the file '{}'", fileName, e);
            System.exit(2);
        }
        var massHousingTimeline = new HousingTimeline(
                ParsingUtil.parseHousingData(rawLines)
                        .stream()
                        .filter(FilteringUtil::isMassHousing)
                        .collect(Collectors.toList())
        );
        var startDate = massHousingTimeline.getEarliestListingDate()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
        var endDate = massHousingTimeline.getLatestReservationDate();
        Set<HousingObject> previousWeekListedHouses = Collections.emptySet();
        System.out.println("analysisDate\tremainingHousingCount\tweekHousingCount"
                + "\tthreeMonthReservedCount\tmedianDaysAtMarket\tmedianPrice");
        for (var analysisDate = startDate;
             !analysisDate.isAfter(endDate); analysisDate = analysisDate.plusWeeks(1L)) {
            var thisWeekListedHouses = massHousingTimeline.getHousingAvailableAt(analysisDate);
            var newlyListedHousesCount = 0;
            for (var housingObject : thisWeekListedHouses) {
                if (!previousWeekListedHouses.contains(housingObject)) {
                    newlyListedHousesCount++;
                }
            }
            var alreadyListedHousesCount = thisWeekListedHouses.size() - newlyListedHousesCount;
            var threeMonthHouses = massHousingTimeline.getHousingReservedInPeriod(
                    analysisDate.minusMonths(3L), analysisDate);
            //Output should be in Gnuplot-friendly format
            System.out.println(
                    analysisDate + "\t" + alreadyListedHousesCount + "\t" + thisWeekListedHouses.size()
                            + "\t" + threeMonthHouses.size()
                            + "\t" + getMedianDaysAtMarket(threeMonthHouses)
                            + "\t" + getMedianPrice(threeMonthHouses)
            );
            previousWeekListedHouses = thisWeekListedHouses;
        }
    }

    private static double getMedianPrice(Set<HousingObject> housingObjects) {
        return StatUtils.percentile(housingObjects.stream()
                .mapToDouble(HousingObject::getAskingPrice)
                .toArray(), 50.0);
    }

    private static double getMedianDaysAtMarket(Set<HousingObject> housingObjects) {
        return StatUtils.percentile(housingObjects.stream()
                .mapToDouble(h -> ChronoUnit.DAYS.between(h.getListingDate(), h.getReservationDate()))
                .toArray(), 50.0);
    }

}
