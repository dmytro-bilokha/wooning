package com.dmytrobilokha.wooning;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
        ParsingUtil.parseHousingData(rawLines)
                .forEach(ho -> System.out.println(ho.toString()));
    }

}
