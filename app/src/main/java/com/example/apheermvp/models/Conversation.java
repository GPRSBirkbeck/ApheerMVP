package com.example.apheermvp.models;

public class Conversation {
    private String friendName;
    private Integer friendImage;
    private String messageContent;
    private String friendId;


    public Conversation(String friendName, String friendId, String messageContent) {
        this.friendName = friendName;
        this.friendId = friendId;
        this.messageContent = messageContent;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Integer getFriendImage() {
        return friendImage;
    }

    public void setFriendImage(Integer friendImage) {
        this.friendImage = friendImage;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
