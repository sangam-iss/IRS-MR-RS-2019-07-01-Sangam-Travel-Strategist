package com.reasoningsystem.tourplanner.sangam.DTO;

import java.util.List;

public class ItineraryRequestDto {
    int days;
    List<LocationInfoDto> locations;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public List<LocationInfoDto> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationInfoDto> locations) {
        this.locations = locations;
    }
}
