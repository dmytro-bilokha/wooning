package com.dmytrobilokha.wooning;

import java.time.LocalDate;
import java.util.Objects;

public class HousingObject {

    private int lineNumber;
    private String address;
    private Type type;
    private int volume;
    private int livingArea;
    private int numberOfRooms;
    private int yearOfConstruction;
    private boolean hasElevator;
    private int askingPrice;
    private LocalDate listingDate;
    private LocalDate reservationDate;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(int livingArea) {
        this.livingArea = livingArea;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(int yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public boolean hasElevator() {
        return hasElevator;
    }

    public void setHasElevator(boolean hasElevator) {
        this.hasElevator = hasElevator;
    }

    public int getAskingPrice() {
        return askingPrice;
    }

    public void setAskingPrice(int askingPrice) {
        this.askingPrice = askingPrice;
    }

    public LocalDate getListingDate() {
        return listingDate;
    }

    public void setListingDate(LocalDate listingDate) {
        this.listingDate = listingDate;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HousingObject that = (HousingObject) o;
        return lineNumber == that.lineNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber);
    }

    @Override
    public String toString() {
        return "HousingObject{"
                + "lineNumber=" + lineNumber
                + ", address='" + address + '\''
                + ", type=" + type
                + ", volume=" + volume
                + ", livingArea=" + livingArea
                + ", numberOfRooms=" + numberOfRooms
                + ", yearOfConstruction=" + yearOfConstruction
                + ", hasElevator=" + hasElevator
                + ", askingPrice=" + askingPrice
                + ", listingDate=" + listingDate
                + ", reservationDate=" + reservationDate
                + '}';
    }

    public enum Type {
        HOUSE, TOWN_HOUSE, FLAT
    }

}
