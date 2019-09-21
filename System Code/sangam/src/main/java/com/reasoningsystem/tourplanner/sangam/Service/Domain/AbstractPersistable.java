package com.reasoningsystem.tourplanner.sangam.Service.Domain;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import java.io.Serializable;

public class AbstractPersistable implements Serializable, Comparable<AbstractPersistable> {

    protected Long id;

    protected AbstractPersistable() {
    }

    protected AbstractPersistable(long id) {
        this.id = id;
    }

    @PlanningId
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(AbstractPersistable other) {
        return new CompareToBuilder().append(getClass().getName(), other.getClass().getName()).append(id, other.id)
                .toComparison();
    }

    @Override
    public String toString() {
        return getClass().getName().replaceAll(".*\\.", "") + "-" + id;
    }
}