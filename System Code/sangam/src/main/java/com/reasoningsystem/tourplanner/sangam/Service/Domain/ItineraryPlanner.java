package com.reasoningsystem.tourplanner.sangam.Service.Domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoftdouble.HardSoftDoubleScore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@PlanningSolution
public class ItineraryPlanner {
    private List<Days> daysOfTour;
    private List<Integer> slots;
    private List<Location> locations;
    private Map<String,Long> distanceMap;
    private Map<String,Long> durationMap;
    private List<Slot> slotsForTour;
    private HardMediumSoftScore score;

    public Map<String, Long> getDurationMap() {
        return durationMap;
    }

    public void setDurationMap(Map<String, Long> durationMap) {
        this.durationMap = durationMap;
    }

    @PlanningEntityCollectionProperty
    public List<Slot> getSlotsForTour() {
        return this.slotsForTour;
    }

    public void setSlotsForTour(List<Slot> slotsForTour) {
        this.slotsForTour = slotsForTour;
    }

    @ValueRangeProvider(id = "slotsAvailable")
    @ProblemFactCollectionProperty
    public List<Integer> getSlots() {
        return slots;
    }

    public void setSlots(List<Integer> slots) {
        this.slots = slots;
    }

    @PlanningScore
    public HardMediumSoftScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftScore score) {
        this.score = score;
    }

    @ValueRangeProvider(id = "dayRange")
    @ProblemFactCollectionProperty
    public List<Days> getDaysOfTour() {
        return daysOfTour;
    }

    public void setDaysOfTour(List<Days> daysOfTour) {
        this.daysOfTour = daysOfTour;
    }

    @ValueRangeProvider(id = "locations")
    @ProblemFactCollectionProperty
    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Map<String, Long> getDistanceMap() {
        return distanceMap;
    }

    public void setDistanceMap(Map<String, Long> distanceMap) {
        this.distanceMap = distanceMap;
    }

    public Collection<? extends Object> getProblemFacts() {
        List<Object> facts = new ArrayList<Object>();
        facts.addAll(daysOfTour);
        facts.addAll(slots);
        facts.addAll(locations);
        return facts;
    }

}
