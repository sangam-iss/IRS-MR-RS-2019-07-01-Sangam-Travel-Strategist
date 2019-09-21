package com.reasoningsystem.tourplanner.sangam.Service.Domain;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import java.util.*;
import java.util.stream.Collectors;

class SortbySlot implements Comparator<Slot>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Slot a, Slot b)
    {
        return a.getSlot() - b.getSlot();
    }
}

public class ItineraryPlannerScoreCalculator implements EasyScoreCalculator<ItineraryPlanner> {

    @Override
    public Score calculateScore(ItineraryPlanner itineraryPlanner) {
        int hardScore=0;
        int softScore=0;
        int mediumScore=0;
        int locationFilled=0;
        float totalRating = 0;

        Set<String> bookedSlots = new HashSet<>();
        Map<Integer,Integer> dayDuration = new HashMap<>();
        Map<String,Long> distanceMap = itineraryPlanner.getDistanceMap();

        for(Days days : itineraryPlanner.getDaysOfTour()){
            dayDuration.put(days.getIndex(),0);
        }

        for(Slot slots : itineraryPlanner.getSlotsForTour()){
            if(slots.getDay() == null || slots.getSlot() == null) {
                continue;
            } else {
                locationFilled +=1;
            }
            String slotIndex = slots.getDay().getIndex()+":"+slots.getSlot();
            if(bookedSlots.contains(slotIndex)){
                hardScore += -2;
            } else {
                bookedSlots.add(slotIndex);
            }
            int duration = dayDuration.get(slots.getDay().getIndex()) + slots.getLocation().getDuration() + 2;
            if(duration > 12) {
                hardScore += -1;
            }
            totalRating += slots.getLocation().getRating();
            dayDuration.replace(slots.getDay().getIndex(),duration);
        }
        if(locationFilled == 0){
            hardScore += -5;
        }
        mediumScore -= (itineraryPlanner.getSlotsForTour().size() - locationFilled)*2;
        mediumScore += (totalRating / locationFilled)*2;

        Map<Integer,List<Slot>> daysGroup = itineraryPlanner.getSlotsForTour().stream()
                .filter(x -> x.getDay()!=null && x.getSlot() != null)
                .collect(Collectors.groupingBy(x -> x.getDay().getIndex()));

        for(Map.Entry<Integer,List<Slot>> days : daysGroup.entrySet()){
            Collections.sort(days.getValue(), new SortbySlot());
            List<Slot> slotsForDay = days.getValue();
            if(days.getValue().size() > 1){
                for(int i=1; i< days.getValue().size();i++) {
                    String source = slotsForDay.get(i-1).getLocation().getName();
                    String destination = slotsForDay.get(i).getLocation().getName();
                    softScore -= distanceMap.getOrDefault(source+destination,0L);
                }
            }
            mediumScore += dayDuration.get(days.getKey())*2;
        }


        return HardMediumSoftScore.of(hardScore, mediumScore,softScore);
    }
}
