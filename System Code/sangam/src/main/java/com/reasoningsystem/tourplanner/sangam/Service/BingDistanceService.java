package com.reasoningsystem.tourplanner.sangam.Service;

import com.reasoningsystem.tourplanner.sangam.DTO.*;
import com.reasoningsystem.tourplanner.sangam.Service.Domain.Location;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BingDistanceService {
    private String apiKey = "Aico2rk7yMVX5PPHUiY42tx4ul3cqpCEbFl7ukWsOg4oUYnVwhh1MqwB-ncCci-L";
    private String bingUrl = "https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix";

    public List<Map<String,Long>> getDistanceMatrix(List<Location> locations) throws InterruptedException, IOException {
        Map<String,Long> distanceMap = new HashMap<>();
        Map<String,Long> durationMap = new HashMap<>();

        BingDistanceRequestDto bingDistanceRequestDto = new BingDistanceRequestDto();
        bingDistanceRequestDto.setTravelMode("driving");
        List<LatLng> origins = locations.stream().limit(50).map(x -> new LatLng(x.getLatitude(),x.getLongitude()))
                .collect(Collectors.toList());
        bingDistanceRequestDto.setOrigins(origins);
        List<LatLng> destinations = locations.stream().limit(50).map(x -> new LatLng(x.getLatitude(),x.getLongitude()))
                .collect(Collectors.toList());
        bingDistanceRequestDto.setDestinations(destinations);
        BingDistantMatrixDto resources = bingApi(bingDistanceRequestDto);
        for(BingDistanceMatrixResultDto resource : resources.getResults()) {
            distanceMap.put(locations.get(resource.getOriginIndex())
                            .getName()+locations.get(resource.getDestinationIndex()).getName(),
                    resource.getTravelDistance().longValue());
            durationMap.put(locations.get(resource.getOriginIndex())
                            .getName()+locations.get(resource.getDestinationIndex()).getName(),
                    resource.getTravelDuration().longValue());
        }
        return Arrays.asList(distanceMap,durationMap);
    }

    private BingDistantMatrixDto bingApi(BingDistanceRequestDto bingDistanceRequestDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<BingDistanceRequestDto> request = new HttpEntity<>(bingDistanceRequestDto);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(bingUrl).queryParam("key",apiKey);

        ResponseEntity<BingResponseDto> responseEntity = restTemplate.postForEntity(builder.toUriString(),request,BingResponseDto.class);
        if (responseEntity.getStatusCode().isError()){
            System.out.println("Error response from Bing Server");
            System.out.println(responseEntity.toString());
            return null;
        }
        BingResponseDto bingResponseDto = responseEntity.getBody();
        System.out.println(bingResponseDto.getResourceSets().get(0).toString());
        return bingResponseDto.getResourceSets().get(0).getResources().get(0);
    }
}
