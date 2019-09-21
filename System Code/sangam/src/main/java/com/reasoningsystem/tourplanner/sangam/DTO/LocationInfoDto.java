package com.reasoningsystem.tourplanner.sangam.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reasoningsystem.tourplanner.sangam.Service.Domain.Location;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationInfoDto {
    @JsonProperty("id")
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getHourRequired() {
        return hourRequired;
    }

    public void setHourRequired(Integer hourRequired) {
        this.hourRequired = hourRequired;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    @JsonProperty("name")
    String name;
    @JsonProperty("price")
    Double price;
    @JsonProperty("hours_required")
    Integer hourRequired;
    @JsonProperty("latitude")
    Double latitude;
    @JsonProperty("longitude")
    Double longitude;
    @JsonProperty("user_rating")
    Double userRating;

    public Location convertToLocation(){
        Location location = new Location();
        location.setName(this.name);
        location.setLongitude(this.longitude);
        location.setLatitude(this.latitude);
        location.setDuration(this.hourRequired);
        location.setRating(this.userRating.floatValue());
        location.setPrice(this.price);
        return location;
    }

}