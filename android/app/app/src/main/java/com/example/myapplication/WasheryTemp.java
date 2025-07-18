package com.example.myapplication;

public class WasheryTemp {
    private String Washery_Rawcoal;
    private String Washery_Cleancoal;
    private String Washery_Midding;
    private String Washery_Slurry;
    private String Washery_Rejected;



    public WasheryTemp() {
    }

    public WasheryTemp(String washery_Rawcoal, String washery_Cleancoal, String washery_Midding, String washery_Slurry,String washery_Rejected) {
        Washery_Rawcoal = washery_Rawcoal;
        Washery_Cleancoal = washery_Cleancoal;
        Washery_Midding = washery_Midding;
        Washery_Slurry = washery_Slurry;
        Washery_Rejected = washery_Rejected;
    }

    public String getWashery_Rawcoal() {
        return Washery_Rawcoal;
    }

    public void setWashery_Rawcoal(String washery_Rawcoal) {
        Washery_Rawcoal = washery_Rawcoal;
    }

    public String getWashery_Cleancoal() {
        return Washery_Cleancoal;
    }

    public void setWashery_Cleancoal(String washery_Cleancoal) {
        Washery_Cleancoal = washery_Cleancoal;
    }

    public String getWashery_Midding() {
        return Washery_Midding;
    }

    public void setWashery_Midding(String washery_Midding) {
        Washery_Midding = washery_Midding;
    }

    public String getWashery_Slurry() {
        return Washery_Slurry;
    }

    public void setWashery_Slurry(String washery_Slurry) {
        Washery_Slurry = washery_Slurry;
    }

    public String getWashery_Rejected() {return Washery_Rejected;}

    public void setWashery_Rejected(String washery_Rejected) {Washery_Rejected = washery_Rejected;}
}
