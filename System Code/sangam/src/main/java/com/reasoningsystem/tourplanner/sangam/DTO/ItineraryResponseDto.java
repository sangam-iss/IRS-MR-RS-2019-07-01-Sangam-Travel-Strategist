package com.reasoningsystem.tourplanner.sangam.DTO;

import java.util.List;

public class ItineraryResponseDto {
    private int day;

    public String getGoogleString() {
        return googleString;
    }

    public void setGoogleString(String googleString) {
        this.googleString = googleString;
    }

    private String googleString;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<ItinerarySlotResponseDto> getSlots() {
        return slots;
    }

    public void setSlots(List<ItinerarySlotResponseDto> slots) {
        this.slots = slots;
    }

    private List<ItinerarySlotResponseDto> slots;

}
