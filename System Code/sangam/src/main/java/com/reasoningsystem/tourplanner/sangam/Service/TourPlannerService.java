package com.reasoningsystem.tourplanner.sangam.Service;

import com.reasoningsystem.tourplanner.sangam.DTO.ItineraryResponseDto;
import com.reasoningsystem.tourplanner.sangam.DTO.ItinerarySlotResponseDto;
import com.reasoningsystem.tourplanner.sangam.DTO.LocationInfoDto;
import com.reasoningsystem.tourplanner.sangam.Service.Domain.Days;
import com.reasoningsystem.tourplanner.sangam.Service.Domain.ItineraryPlanner;
import com.reasoningsystem.tourplanner.sangam.Service.Domain.Location;
import com.reasoningsystem.tourplanner.sangam.Service.Domain.Slot;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TourPlannerService {

    @Autowired
    BingDistanceService bingDistanceService;

    @Autowired
    GoogleDistanceService googleDistanceService;

    public List<ItineraryResponseDto> getItinerary(int nDay, List<LocationInfoDto> locations) throws Exception{
        List<ItineraryResponseDto> responseListDto = new ArrayList<>();
        List<Slot> initalEntity = new ArrayList<>();

        locations.sort((m1,m2) ->{
                return (int)((m1.getUserRating()*10)-(m2.getUserRating()*10));
            });
        List<Location> locationsInfo = locations.stream().limit(50).map(x -> x.convertToLocation()).collect(Collectors.toList());

        List<Map<String,Long>> distanceMatrix = bingDistanceService.getDistanceMatrix(locationsInfo);
        Map<String,Long> distanceMap = distanceMatrix.get(0);
        Map<String,Long> durationMap = distanceMatrix.get(1);
        List<Days> days = new ArrayList<>();
        for(int i = 0; i < nDay; i++){
            Days day = new Days();
            day.setIndex(i);
            days.add(day);
        }
        for(Location initialSlots :locationsInfo){
            Slot slot = new Slot();
            slot.setLocation(initialSlots);
            initalEntity.add(slot);
        }

        SolverFactory<ItineraryPlanner> solverFactory = SolverFactory.createFromXmlResource("ItineraryPlannerSolverConfig.xml");
        Solver<ItineraryPlanner> solver = solverFactory.buildSolver();

        ItineraryPlanner itineraryPlanner = new ItineraryPlanner();
        itineraryPlanner.setDistanceMap(distanceMap);
        itineraryPlanner.setLocations(locationsInfo);
        itineraryPlanner.setDaysOfTour(days);
        itineraryPlanner.setSlots(Arrays.asList(1,2,3,4,5));
        itineraryPlanner.setDurationMap(durationMap);
        itineraryPlanner.setSlotsForTour(initalEntity);
        itineraryPlanner = solver.solve(itineraryPlanner);
        System.out.println(itineraryPlanner.getScore().toString());
        for(Slot slot : itineraryPlanner.getSlotsForTour()){
            if(slot.getDay() == null || slot.getSlot() == null){
                continue;
            }
            System.out.println(slot.getLocation().getName()+ " - " + slot.getDay().getIndex() + " - " + slot.getSlot());
        }
        List<Slot> slots = itineraryPlanner.getSlotsForTour();
        Map<Integer, List<Slot>> daysGroup = slots.stream()
                .filter(slot -> slot.getDay()!= null && slot.getSlot()!=null)
                .collect(Collectors.groupingBy(slot -> slot.getDay().getIndex()));
        for(Map.Entry<Integer,List<Slot>> entry : daysGroup.entrySet()){
            ItineraryResponseDto responseDto = new ItineraryResponseDto();
            responseDto.setDay(entry.getKey());
            List<ItinerarySlotResponseDto> slotResponseListDto = new ArrayList<>();
            for( Slot slot : entry.getValue()) {
                ItinerarySlotResponseDto slotResponseDto = new ItinerarySlotResponseDto();
                slotResponseDto.setSlot(slot.getSlot());
                slotResponseDto.setLocation(slot.getLocation());
                slotResponseListDto.add(slotResponseDto);
            }
            Collections.sort(slotResponseListDto);
            responseDto.setSlots(slotResponseListDto);
            responseDto.setGoogleString(googleDistanceService.getGoogleMapEmbedString(entry.getValue()));
            responseListDto.add(responseDto);
        }
        return responseListDto;
    }

    public List<ItineraryResponseDto> getBudgetItinerary(int nDay, List<LocationInfoDto> locations) throws Exception{
        List<ItineraryResponseDto> responseListDto = new ArrayList<>();
        List<Slot> initalEntity = new ArrayList<>();

        locations.sort((m1,m2) ->{
            return (int)(m1.getPrice()-m2.getPrice());
        });

        List<Location> locationsInfo = locations.stream().limit(50).map(x -> x.convertToLocation()).collect(Collectors.toList());
        List<Map<String,Long>> distanceMatrix = bingDistanceService.getDistanceMatrix(locationsInfo);
        Map<String,Long> distanceMap = distanceMatrix.get(0);
        Map<String,Long> durationMap = distanceMatrix.get(1);
        List<Days> days = new ArrayList<>();
        for(int i = 0; i < nDay; i++){
            Days day = new Days();
            day.setIndex(i);
            days.add(day);
        }
        for(Location initialSlots :locationsInfo){
            Slot slot = new Slot();
            slot.setLocation(initialSlots);
            initalEntity.add(slot);
        }

        SolverFactory<ItineraryPlanner> solverFactory = SolverFactory.createFromXmlResource("BudgetItineraryPlannerSolverConfig.xml");
        Solver<ItineraryPlanner> solver = solverFactory.buildSolver();

        ItineraryPlanner itineraryPlanner = new ItineraryPlanner();
        itineraryPlanner.setDistanceMap(distanceMap);
        itineraryPlanner.setLocations(locationsInfo);
        itineraryPlanner.setDaysOfTour(days);
        itineraryPlanner.setSlots(Arrays.asList(1,2,3,4,5));
        itineraryPlanner.setDurationMap(durationMap);
        itineraryPlanner.setSlotsForTour(initalEntity);
        itineraryPlanner = solver.solve(itineraryPlanner);
        System.out.println(itineraryPlanner.getScore().toString());
        for(Slot slot : itineraryPlanner.getSlotsForTour()){
            if(slot.getDay() == null || slot.getSlot() == null){
                continue;
            }
            System.out.println(slot.getLocation().getName()+ " - " + slot.getDay().getIndex() + " - " + slot.getSlot());
        }
        List<Slot> slots = itineraryPlanner.getSlotsForTour();
        Map<Integer, List<Slot>> daysGroup = slots.stream()
                .filter(slot -> slot.getDay()!= null && slot.getSlot()!=null)
                .collect(Collectors.groupingBy(slot -> slot.getDay().getIndex()));
        for(Map.Entry<Integer,List<Slot>> entry : daysGroup.entrySet()){
            ItineraryResponseDto responseDto = new ItineraryResponseDto();
            responseDto.setDay(entry.getKey());
            List<ItinerarySlotResponseDto> slotResponseListDto = new ArrayList<>();
            for( Slot slot : entry.getValue()) {
                ItinerarySlotResponseDto slotResponseDto = new ItinerarySlotResponseDto();
                slotResponseDto.setSlot(slot.getSlot());
                slotResponseDto.setLocation(slot.getLocation());
                slotResponseListDto.add(slotResponseDto);
            }
            Collections.sort(slotResponseListDto);
            responseDto.setSlots(slotResponseListDto);
            responseDto.setGoogleString(googleDistanceService.getGoogleMapEmbedString(entry.getValue()));
            responseListDto.add(responseDto);
        }
        return responseListDto;
    }
}
