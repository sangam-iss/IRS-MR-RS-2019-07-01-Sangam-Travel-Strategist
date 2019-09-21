package com.reasoningsystem.tourplanner.sangam.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BingDistantMatrixDto {
    public List<LatLng> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<LatLng> destinations) {
        this.destinations = destinations;
    }

    public List<LatLng> getOrigins() {
        return origins;
    }

    public void setOrigins(List<LatLng> origins) {
        this.origins = origins;
    }

    public List<BingDistanceMatrixResultDto> getResults() {
        return results;
    }

    public void setResults(List<BingDistanceMatrixResultDto> results) {
        this.results = results;
    }

    private List<LatLng> destinations;
    private List<LatLng> origins;
    private List<BingDistanceMatrixResultDto> results;

    @Override
    public String toString(){
        String str = "";
        for(BingDistanceMatrixResultDto result: results){
            str+="\n"+result.toString();
        }
        return str;
    }
}
