package com.example.eldermap.NavigationPkg;

public class CoorDist {

    /**
     * Algorithm to calculate the distance between the two coordinates
     * @param userLat Latitude of the user location
     * @param userLon Longitude of the user location
     * @param destLat Latitude of the destination
     * @param destLon Longitude of the destination
     * @return distance of between the two coordinates
     */
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
}
