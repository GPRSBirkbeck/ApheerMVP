package com.example.apheermvp.models;

public class FormerLocation {
    //this class represents the POJO for the friends that are actually displayed in the ratesListAdapter
    private String former_city_name;
    private String dates_in_former_city;
    private Integer cityImage;
    private String locationId;


    //constructor
    public FormerLocation(String former_city_name, String dates_in_former_city, Integer cityImage, String locationId) {
        this.former_city_name = former_city_name;
        this.dates_in_former_city = dates_in_former_city;
        this.cityImage = cityImage;
        this.locationId = locationId;
    }

    //getters and setters below
    public void setFormer_city_name(String former_city_name) {
        this.former_city_name = former_city_name;
    }

    public void setDates_in_former_city(String dates_in_former_city) {
        this.dates_in_former_city = dates_in_former_city;
    }

    public void setCityImage(Integer cityImage) {
        this.cityImage = cityImage;
    }

    public String getFormer_city_name() {
        return former_city_name;
    }

    public String getDates_in_former_city() {
        return dates_in_former_city;
    }

    public Integer getCityImage() {
        return cityImage;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
