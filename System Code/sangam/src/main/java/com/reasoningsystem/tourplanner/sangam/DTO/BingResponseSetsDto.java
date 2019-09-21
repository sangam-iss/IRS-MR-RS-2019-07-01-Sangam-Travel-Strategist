package com.reasoningsystem.tourplanner.sangam.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BingResponseSetsDto {
    private List<BingDistantMatrixDto> resources;
    private int estimatedTotal;

    public List<BingDistantMatrixDto> getResources() {
        return resources;
    }

    public void setResources(List<BingDistantMatrixDto> resources) {
        this.resources = resources;
    }

    public int getEstimatedTotal() {
        return estimatedTotal;
    }

    public void setEstimatedTotal(int estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }

    @Override
    public String toString(){
        String str = "estimated total = "+ new Integer(estimatedTotal).toString();
        for(BingDistantMatrixDto entry : resources) {
            str +="\n"+entry.toString();
        }
        return str;
    }
}
