package com.reasoningsystem.tourplanner.sangam.DTO;

import java.util.List;

public class BingDistanceRequestDto {

    private List<LatLng> origins;
    private List<LatLng> destinations;

    public List<LatLng> getOrigins() {
        return origins;
    }

    public void setOrigins(List<LatLng> origins) {
        this.origins = origins;
    }

    public List<LatLng> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<LatLng> destinations) {
        this.destinations = destinations;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    private String travelMode;
}
