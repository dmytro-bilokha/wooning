package com.dmytrobilokha.wooning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class ParsingUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ParsingUtil.class);
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uu");
    private static final DateTimeFormatter DISAPPEARED_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private static final Pattern DEFAULT_DATE_PATTERN = Pattern.compile("[0-3][0-9]/[0-1][0-9]/[0-9][0-9]");
    private static final Pattern DISAPPEARED_DATE_PATTERN =
            Pattern.compile(".*([0-3][0-9]/[0-1][0-9]/20[0-9][0-9]).*", Pattern.DOTALL);
    private static final int CSV_LINE_SHIFT = 2;

    private ParsingUtil() {
        //Utility class
    }

    public static List<HousingObject> parseHousingData(List<RawHousingData> housingDataItems) {
        var result = new ArrayList<HousingObject>();
        for (int i = 0; i < housingDataItems.size(); i++) {
            var housingData = housingDataItems.get(i);
            if (!shouldIgnore(housingData)) {
                try {
                    result.add(parse(housingData));
                } catch (RuntimeException e) {
                    LOG.error("Failed to parse {} in position {}", housingData, i + CSV_LINE_SHIFT, e);
                }
            }
        }
        return result;
    }

    private static boolean shouldIgnore(RawHousingData housingData) {
        return housingData.getAddress() == null || housingData.getAddress().isBlank()
                || "?".equals(housingData.getReservationDate().strip())
                || "Lost".equalsIgnoreCase(housingData.getReservationDate().strip());
    }

    private static HousingObject parse(RawHousingData housingData) {
        var housingObject = new HousingObject();
        housingObject.setAddress(housingData.getAddress());
        housingObject.setAskingPrice(Integer.parseInt(housingData.getAskingPrice()));
        housingObject.setHasElevator("1".equals(housingData.getElevatorPresence().strip()));
        housingObject.setLivingArea(Integer.parseInt(housingData.getLivingArea()));
        housingObject.setNumberOfRooms(Integer.parseInt(housingData.getNumberOfRooms()));
        housingObject.setYearOfConstruction(Integer.parseInt(housingData.getYearOfConstruction()));
        housingObject.setVolume(Integer.parseInt(housingData.getVolume()));
        HousingObject.Type housingType;
        switch (housingData.getType().strip()) {
            case "F":
                housingType = HousingObject.Type.FLAT;
                break;

            case "TH":
                housingType = HousingObject.Type.TOWN_HOUSE;
                break;

            case "H":
                housingType = HousingObject.Type.HOUSE;
                break;

            default:
                throw new IllegalArgumentException("Unable to parse '" + housingData.getType() + "' into housing type");
        }
        housingObject.setType(housingType);
        housingObject.setListingDate(LocalDate.parse(housingData.getListingDate(), DEFAULT_DATE_FORMATTER));
        var reservationDateString = housingData.getReservationDate().strip();
        if ("Available".equalsIgnoreCase(reservationDateString)) {
            housingObject.setReservationDate(LocalDate.now());
        } else if (DEFAULT_DATE_PATTERN.matcher(reservationDateString).matches()) {
            housingObject.setReservationDate(LocalDate.parse(reservationDateString, DEFAULT_DATE_FORMATTER));
        } else {
            var matcher = DISAPPEARED_DATE_PATTERN.matcher(reservationDateString);
            if (matcher.matches()) {
                var datePartString = matcher.group(1);
                housingObject.setReservationDate(LocalDate.parse(datePartString, DISAPPEARED_DATE_FORMATTER));
            } else {
                throw new IllegalArgumentException("Unrecognizable reservation date '" + reservationDateString + '\'');
            }
        }
        return housingObject;
    }

}
