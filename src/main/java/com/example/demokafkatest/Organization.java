package com.example.demokafkatest;

import java.util.Objects;

public class Organization {
    private String name;
    private String organizationType;

    public Organization() {}

    public Organization(String name, String organizationType) {
        this.name = name;
        this.organizationType = organizationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) && Objects.equals(organizationType, that.organizationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, organizationType);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", organizationType=" + organizationType +
                '}';
    }
}