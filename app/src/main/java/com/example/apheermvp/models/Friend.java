package com.example.apheermvp.models;

public class Friend {
    //this class represents the POJO for the friends that are actually displayed in the ratesListAdapter
    private String friendName;
    private String friend_location;
    private Integer friendImage;
    private String userId;

    //constructor
    public Friend(String friendName, String friend_location, Integer friendImage, String userId) {
        this.friendName = friendName;
        this.friend_location = friend_location;
        this.friendImage = friendImage;
        this.userId = userId;
    }

    //getters and setters below
    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public void setFriend_location(String friend_location) {
        this.friend_location = friend_location;
    }

    public void setFriendImage(Integer friendImage) {
        this.friendImage = friendImage;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getFriend_location() {
        return friend_location;
    }

    public Integer getFriendImage() {
        return friendImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
