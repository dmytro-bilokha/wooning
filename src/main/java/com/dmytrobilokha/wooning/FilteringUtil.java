package com.dmytrobilokha.wooning;

public final class FilteringUtil {

    private FilteringUtil() {
        //Util class
    }

    //Filter represents the flats which are the most common in my city
    public static boolean isMassHousing(HousingObject housingObject) {
        return !housingObject.hasElevator()
                && housingObject.getType() == HousingObject.Type.FLAT
                && housingObject.getNumberOfRooms() == 3
                && housingObject.getYearOfConstruction() < 1975
                && housingObject.getYearOfConstruction() > 1955
                && housingObject.getLivingArea() < 70
                && housingObject.getLivingArea() > 57;
    }

}
