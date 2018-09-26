package com.example.kallyruan.eldermap.NavigationPkg;

public class CoorDist {

    private double dist;
    private double userLat;
    private double userLon;
    private double destLat;
    private double destLon;

    CoorDist(double userLat, double userLon, double destLat, double destLon) {
        this.userLat = userLat;
        this.userLon = userLon;
        this.destLat = destLat;
        this.destLon = destLon;

        double R = 6378.137; // Radius of earth in KM
        double dLat = destLat * Math.PI / 180 - userLat * Math.PI / 180;
        double dLon = destLon * Math.PI / 180 - userLon * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(userLat * Math.PI / 180) * Math.cos(destLat * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        this.dist = R * c;

    }

    public double getDist() {
        return this.dist;
    }

}
