package com.dmytrobilokha.wooning;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class HousingTimeline {

    private final SortedSet<HousingObject> housesByListingDate;
    private final SortedSet<HousingObject> housesByReservationDate;

    public HousingTimeline(Collection<HousingObject> housingCollection) {
        if (housingCollection.isEmpty()) {
            throw new IllegalArgumentException("Housing collection must be not empty");
        }
        housesByListingDate = new TreeSet<>(
                Comparator.comparing(HousingObject::getListingDate)
                        .thenComparingInt(HousingObject::getLineNumber)
        );
        housesByListingDate.addAll(housingCollection);
        housesByReservationDate = new TreeSet<>(
                Comparator.comparing(HousingObject::getReservationDate)
                        .thenComparingInt(HousingObject::getLineNumber)
        );
        housesByReservationDate.addAll(housingCollection);
    }

    public LocalDate getEarliestListingDate() {
        return housesByListingDate.first().getListingDate();
    }

    public LocalDate getLatestReservationDate() {
        return housesByReservationDate.last().getReservationDate();
    }

    public Set<HousingObject> getHousingAvailableAt(LocalDate availabilityDate) {
        var referenceHouseListed = new HousingObject();
        referenceHouseListed.setListingDate(availabilityDate);
        referenceHouseListed.setLineNumber(Integer.MAX_VALUE);
        var housesListed = housesByListingDate.headSet(referenceHouseListed);
        var referenceHouseReserved = new HousingObject();
        referenceHouseReserved.setReservationDate(availabilityDate);
        referenceHouseReserved.setLineNumber(Integer.MAX_VALUE);
        var housesNotReserved = housesByReservationDate.tailSet(referenceHouseReserved);
        return housesListed.stream()
                .filter(housesNotReserved::contains)
                .collect(Collectors.toUnmodifiableSet());
    }

    public Set<HousingObject> getHousingReservedInPeriod(LocalDate startDate, LocalDate endDate) {
        var startHouse = new HousingObject();
        startHouse.setReservationDate(startDate);
        startHouse.setLineNumber(Integer.MIN_VALUE);
        var endHouse = new HousingObject();
        endHouse.setReservationDate(endDate);
        endHouse.setLineNumber(Integer.MAX_VALUE);
        return Set.copyOf(housesByReservationDate.subSet(startHouse, endHouse));
    }

}
