package com.example.kallyruan.eldermap.NavigationPkg;

public class SingleStepNavigation {
    private String move;
    private String direction;
    private String transportationMethod;
    private int period;
    private String unit;

    SingleStepNavigation(String move, String direction, String method, int period, String unit){
        this.move = move;
        this.direction = direction;
        this.transportationMethod = method;
        this.period = period;
        this.unit = unit;
    }

    public String getMove(){
        return move;
    }

    public String getDirection(){
        return direction;
    }

    public String getTransportationMethod(){
        return transportationMethod;
    }

    public int getPeriod(){
        return period;
    }

    public String getUnit(){
        return unit;
    }
}
