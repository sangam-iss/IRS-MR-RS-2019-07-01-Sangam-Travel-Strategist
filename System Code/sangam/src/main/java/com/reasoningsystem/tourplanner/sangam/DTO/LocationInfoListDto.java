package com.reasoningsystem.tourplanner.sangam.DTO;

import java.util.List;

public class LocationInfoListDto {
    private List<LocationInfoDto> locations;

    public List<LocationInfoDto> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationInfoDto> locations) {
        this.locations = locations;
    }
}
