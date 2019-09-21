package com.reasoningsystem.tourplanner.sangam.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BingResponseDto {
    private String authenticationResultCode;
    private String statusCode;

    public String getAuthenticationResultCode() {
        return authenticationResultCode;
    }

    public void setAuthenticationResultCode(String authenticationResultCode) {
        this.authenticationResultCode = authenticationResultCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    private String statusDescription;

    private List<BingResponseSetsDto> resourceSets;

    public List<BingResponseSetsDto> getResourceSets() {
        return resourceSets;
    }

    public void setResourceSets(List<BingResponseSetsDto> resourceSets) {
        this.resourceSets = resourceSets;
    }
}
