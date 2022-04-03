package com.example.demokafkatest;

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
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", organizationType=" + organizationType +
                '}';
    }
}