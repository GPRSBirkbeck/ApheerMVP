package com.example.apheermvp.user_location_classes;

import java.util.ArrayList;

public class UserLocation {
    //TODO delete these classes
    double numberOfLocationsCounter;
    CurrentSpot currentSpot;
    ArrayList<PreviousLocation> PreviousLocations;

    public UserLocation(double numberOfLocationsCounter, CurrentSpot currentSpot, ArrayList<PreviousLocation> previousLocations) {
        this.numberOfLocationsCounter = numberOfLocationsCounter;
        this.currentSpot = currentSpot;
        this.PreviousLocations = previousLocations;
    }

    public UserLocation(double numberOfLocationsCounter, CurrentSpot currentSpot) {
        this.numberOfLocationsCounter = numberOfLocationsCounter;
        this.currentSpot = currentSpot;
        this.PreviousLocations = null;
    }

    public UserLocation(CurrentSpot currentSpot) {
        this.numberOfLocationsCounter = 0;
        this.currentSpot = currentSpot;
        this.PreviousLocations = null;

    }

    public double getNumberOfLocationsCounter() {
        return numberOfLocationsCounter;
    }

    public void setNumberOfLocationsCounter(double numberOfLocationsCounter) {
        this.numberOfLocationsCounter = numberOfLocationsCounter;
    }

    public CurrentSpot getCurrentSpot() {
        return currentSpot;
    }

    public void setCurrentSpot(CurrentSpot currentSpot) {
        this.currentSpot = currentSpot;
    }

    public ArrayList<PreviousLocation> getPreviousLocations() {
        return PreviousLocations;
    }

    public void setPreviousLocations(ArrayList<PreviousLocation> previousLocations) {
        PreviousLocations = previousLocations;
    }
}
