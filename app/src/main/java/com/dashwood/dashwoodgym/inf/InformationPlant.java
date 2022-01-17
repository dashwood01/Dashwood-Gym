package com.dashwood.dashwoodgym.inf;

public class InformationPlant {
    private int id,round;
    private String name, workTimeAsSec, restTimeAsSec;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkTimeAsSec() {
        return workTimeAsSec;
    }

    public void setWorkTimeAsSec(String workTimeAsSec) {
        this.workTimeAsSec = workTimeAsSec;
    }

    public String getRestTimeAsSec() {
        return restTimeAsSec;
    }

    public void setRestTimeAsSec(String restTimeAsSec) {
        this.restTimeAsSec = restTimeAsSec;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
