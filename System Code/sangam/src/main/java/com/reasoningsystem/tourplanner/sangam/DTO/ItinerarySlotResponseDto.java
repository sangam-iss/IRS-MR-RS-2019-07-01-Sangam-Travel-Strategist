package com.reasoningsystem.tourplanner.sangam.DTO;

import com.reasoningsystem.tourplanner.sangam.Service.Domain.Location;

public class ItinerarySlotResponseDto implements Comparable<ItinerarySlotResponseDto>{
    private int slot;
    private Location location;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int compareTo(ItinerarySlotResponseDto slot) {
        return this.getSlot() - slot.getSlot();
    }
}
