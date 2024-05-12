package com.example.vizilabda;

public class dataItem {
    private String teamName;
    private int point;


    public dataItem(String teamName, int point) {
        this.teamName = teamName;
        this.point = point;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getPoint() {
        return point;
    }
}
