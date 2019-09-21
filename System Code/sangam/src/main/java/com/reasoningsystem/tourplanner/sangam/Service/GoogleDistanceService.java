package com.reasoningsystem.tourplanner.sangam.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import com.reasoningsystem.tourplanner.sangam.Service.Domain.Location;
import com.reasoningsystem.tourplanner.sangam.Service.Domain.Slot;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class GoogleDistanceService {
    String apiKey = "AIzaSyCh5wq1ylFCB7cwyuTw_lyyEaSL-wT7WRk";
    String googleEmbedUrl = "https://www.google.com/maps/embed/v1/";

    public List<Map<String,Long>> getDistanceMatrix(List<Location> locations) throws InterruptedException, ApiException, IOException {
        List<LatLng> places = new ArrayList<>();
        Map<String,Long> distanceMap = new HashMap<>();
        Map<String,Long> durationMap = new HashMap<>();

        for( Location location : locations){
            places.add(new LatLng(location.getLatitude(),location.getLongitude()));
        }

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(this.apiKey)
                .build();

        DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(context);
        request.origins(places.toArray(new LatLng[places.size()]));
        request.destinations(places.toArray(new LatLng[places.size()]));
        DistanceMatrix distanceMatrix = request.await();
        System.out.println(distanceMatrix.toString());

        for(int i=0;i<locations.size();i++){
            DistanceMatrixElement[] distanceRow = distanceMatrix.rows[i].elements;
            for(int j=0;j<locations.size();j++){
                distanceMap.put(locations.get(i).getName()+locations.get(j).getName(),
                        distanceRow[j].distance.inMeters);
                durationMap.put(locations.get(i).getName()+locations.get(j).getName(),
                        distanceRow[j].duration.inSeconds);
            }
        }
        return Arrays.asList(distanceMap,durationMap);
    }

    public String getGoogleMapEmbedString(List<Slot> slots) {
        String map_uri;
        if(slots.size()<2){
            map_uri = googleEmbedUrl+"place?key="+apiKey+"&q=";
            map_uri += slots.get(0).getLocation().getLatitude().toString()+",";
            map_uri += slots.get(0).getLocation().getLongitude().toString();
            return map_uri;
        } else {
            map_uri = googleEmbedUrl+"directions?key="+apiKey;
            String origins = "origin=";
            origins+= slots.get(0).getLocation().getLatitude().toString()+",";
            origins+= slots.get(0).getLocation().getLongitude().toString();

            String destination = "destination=";
            destination+=slots.get(slots.size()-1).getLocation().getLatitude().toString()+",";
            destination+= slots.get(slots.size()-1).getLocation().getLongitude().toString();

            String wayFind = "";
            int index = 1;
            int flag = 0;
            while(index < slots.size()-1) {
                if(flag==0){
                    wayFind+="waypoints=";
                    flag = 1;
                } else {
                    wayFind+="|";
                }
                wayFind+=slots.get(index).getLocation().getLatitude().toString()+",";
                wayFind+= slots.get(index).getLocation().getLongitude().toString();
                index+=1;
            }
            map_uri+="&"+origins;
            if(wayFind != ""){
                map_uri+="&"+wayFind;
            }
            map_uri+="&"+destination;
            return map_uri;
        }
    }
}
