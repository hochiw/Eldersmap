package com.example.kallyruan.eldermap.NavigationPkg;

public class CoorDist {

    public static double getDist(double userLat, double userLon, double destLat, double destLon) {
        double R = 6378.137; // Radius of earth in KM
        double dLat = destLat * Math.PI / 180 - userLat * Math.PI / 180;
        double dLon = destLon * Math.PI / 180 - userLon * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(userLat * Math.PI / 180) * Math.cos(destLat * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c * 1000;
    }

    public static double getAngle(double userLat, double userLon, double destLat, double destLon) {
        double y = Math.sin(destLon - userLon) * Math.cos(destLat);
        double x = Math.cos(userLat) * Math.sin(destLat) - Math.sin(userLat)
                * Math.cos(destLat) * Math.cos(destLon - userLon);

        double bearing = Math.atan2(y, x);

        bearing = Math.toDegrees(bearing);
        bearing = (bearing + 360) % 360;
        bearing = 360 - bearing;

        return bearing;
    }
}