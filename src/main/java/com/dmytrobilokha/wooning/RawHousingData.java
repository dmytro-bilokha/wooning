package com.dmytrobilokha.wooning;

import com.opencsv.bean.CsvBindByName;

public class RawHousingData {

    @CsvBindByName(column = "Address")
    private String address;
    @CsvBindByName(column = "Type")
    private String type;
    @CsvBindByName(column = "Volume, m3")
    private String volume;
    @CsvBindByName(column = "Living Area, m2")
    private String livingArea;
    @CsvBindByName(column = "Total rooms")
    private String numberOfRooms;
    @CsvBindByName(column = "Year of construction")
    private String yearOfConstruction;
    @CsvBindByName(column = "Has elevator")
    private String elevatorPresence;
    @CsvBindByName(column = "Asking price, EUR")
    private String askingPrice;
    @CsvBindByName(column = "Listed since")
    private String listingDate;
    @CsvBindByName(column = "Reserved since")
    private String reservationDate;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(String livingArea) {
        this.livingArea = livingArea;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(String yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public String getElevatorPresence() {
        return elevatorPresence;
    }

    public void setElevatorPresence(String elevatorPresence) {
        this.elevatorPresence = elevatorPresence;
    }

    public String getAskingPrice() {
        return askingPrice;
    }

    public void setAskingPrice(String askingPrice) {
        this.askingPrice = askingPrice;
    }

    public String getListingDate() {
        return listingDate;
    }

    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public String toString() {
        return "RawHousingData{"
                + "address='" + address + '\''
                + ", type='" + type + '\''
                + ", volume='" + volume + '\''
                + ", livingArea='" + livingArea + '\''
                + ", numberOfRooms='" + numberOfRooms + '\''
                + ", yearOfConstruction='" + yearOfConstruction + '\''
                + ", elevatorPresence='" + elevatorPresence + '\''
                + ", askingPrice='" + askingPrice + '\''
                + ", listingDate='" + listingDate + '\''
                + ", reservationDate='" + reservationDate + '\''
                + '}';
    }

}
