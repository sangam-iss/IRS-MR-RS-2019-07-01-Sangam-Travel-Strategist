package com.reasoningsystem.tourplanner.sangam.Service.Domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Slot {

    private Days day;
    private Location location;
    private Integer slot;

    @PlanningVariable(nullable = true, valueRangeProviderRefs = {"dayRange"})
    public Days getDay() {
        return day;
    }

    public void setDay(Days day) {
        this.day = day;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @PlanningVariable(nullable = true,valueRangeProviderRefs = {"slotsAvailable"})
    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

}
