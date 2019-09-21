package com.reasoningsystem.tourplanner.sangam.Controller;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.reasoningsystem.tourplanner.sangam.DTO.ItineraryRequestDto;
import com.reasoningsystem.tourplanner.sangam.DTO.ItineraryResponseDto;
import com.reasoningsystem.tourplanner.sangam.DTO.LocationInfoListDto;
import com.reasoningsystem.tourplanner.sangam.Service.Domain.Location;
import com.reasoningsystem.tourplanner.sangam.Service.GoogleDistanceService;
import com.reasoningsystem.tourplanner.sangam.Service.TourPlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/tour")
public class TourPlannerController {
    @Autowired
    GoogleDistanceService googleDistanceService;

    @Autowired
    TourPlannerService tourPlannerService;

    @PostMapping(value = "/planItinerary")
    public ResponseEntity<List<ItineraryResponseDto>> planItinerary(@RequestBody ItineraryRequestDto locations) throws Exception {

        if(locations == null || locations.getLocations() == null || locations.getLocations().isEmpty()) {
            return new ResponseEntity<List<ItineraryResponseDto>>(HttpStatus.BAD_REQUEST);
        }
        List<ItineraryResponseDto> responseDto = tourPlannerService.getItinerary(locations.getDays(),locations.getLocations());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping(value = "/planBudgetItinerary")
    public ResponseEntity<List<ItineraryResponseDto>> planBudgetItinerary(@RequestBody ItineraryRequestDto locations) throws Exception {

        if(locations == null || locations.getLocations() == null || locations.getLocations().isEmpty()) {
            return new ResponseEntity<List<ItineraryResponseDto>>(HttpStatus.BAD_REQUEST);
        }
        List<ItineraryResponseDto> responseDto = tourPlannerService.getBudgetItinerary(locations.getDays(),locations.getLocations());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/getGoogleDistanceExample")
    public String getDistanceMatrix() throws Exception{
        System.out.println("Example Google App");
        List<Location> locations = new ArrayList<Location>();
        Location location1 = new Location();
        location1.setLatitude(1.2545);
        location1.setLongitude(103.8137);
        location1.setName("location1");
        locations.add(location1);
        Location location2 = new Location();
        location2.setLatitude(1.2588);
        location2.setLongitude(103.8187);
        location2.setName("location2");
        locations.add(location2);
        Location location3 = new Location();
        location3.setLatitude(1.2540);
        location3.setLongitude(103.8238);
        location3.setName("location3");
        locations.add(location3);
        List<Map<String,Long>> distanceMatrix = googleDistanceService.getDistanceMatrix(locations);
        String data = "";
        for (Map.Entry<String,Long> entry:distanceMatrix.get(0).entrySet()){
            data = data + entry.getKey()+" - "+entry.getValue().toString()+"\n";
        }
        System.out.println(data);
        data = "";
        for (Map.Entry<String,Long> entry:distanceMatrix.get(1).entrySet()){
            data = data + entry.getKey()+" - "+entry.getValue().toString()+"\n";
        }
        System.out.println(data);
        return data;
    }

}

