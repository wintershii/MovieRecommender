package com.winter.model;

public class UserData {

    private String uuid;

    private String agent;

    private String mid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "uuid='" + uuid + '\'' +
                ", agent='" + agent + '\'' +
                ", mid='" + mid + '\'' +
                '}';
    }
}
