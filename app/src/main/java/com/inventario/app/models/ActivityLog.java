package com.inventario.app.models;

public class ActivityLog {
    private String id;
    private String userId;
    private String userName;
    private String action;
    private String details;
    private long timestamp;

    public ActivityLog() {}

    public ActivityLog(String userId, String userName, String action, String details) {
        this.userId = userId;
        this.userName = userName;
        this.action = action;
        this.details = details;
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
