package com.reasoningsystem.tourplanner.sangam.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BingDistanceMatrixResultDto {
    private int destinationIndex;
    private int originIndex;

    public int getDestinationIndex() {
        return destinationIndex;
    }

    public void setDestinationIndex(int destinationIndex) {
        this.destinationIndex = destinationIndex;
    }

    public int getOriginIndex() {
        return originIndex;
    }

    public void setOriginIndex(int originIndex) {
        this.originIndex = originIndex;
    }

    public Double getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(Double travelDistance) {
        this.travelDistance = travelDistance;
    }

    public Double getTravelDuration() {
        return travelDuration;
    }

    public void setTravelDuration(Double travelDuration) {
        this.travelDuration = travelDuration;
    }

    private Double travelDistance;
    private Double travelDuration;

    @Override
    public String toString(){
        String str="";
        str +=" o="+new Integer(originIndex).toString();
        str +=" d="+new Integer(destinationIndex).toString();
        str +=" dis="+travelDistance.toString();
        str +=" dur="+travelDuration.toString();
        return str;
    }
}
