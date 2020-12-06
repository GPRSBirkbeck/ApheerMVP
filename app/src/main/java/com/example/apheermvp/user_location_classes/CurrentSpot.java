package com.example.apheermvp.user_location_classes;

public class CurrentSpot{
        String location;
        Double timeArrived;

        public CurrentSpot(String location, Double timeArrived) {
            this.location = location;
            this.timeArrived = timeArrived;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Double getTimeArrived() {
            return timeArrived;
        }

        public void setTimeArrived(Double timeArrived) {
            this.timeArrived = timeArrived;
        }
}
