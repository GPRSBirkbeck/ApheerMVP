package com.example.apheermvp.user_location_classes;

public class PreviousLocation{
        String location;
        Double timeArrived;
        Double timeLeft;

        public PreviousLocation(String location, Double timeArrived, Double timeLeft) {
            this.location = location;
            this.timeArrived = timeArrived;
            this.timeLeft = timeLeft;
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

        public Double getTimeLeft() {
            return timeLeft;
        }

        public void setTimeLeft(Double timeLeft) {
            this.timeLeft = timeLeft;
        }

}
