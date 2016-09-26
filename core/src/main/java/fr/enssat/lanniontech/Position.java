package fr.enssat.lanniontech;
import java.lang.Math;

public class Position {
    protected double lat;
    protected double lon;

    Position(double lat,double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public static double length(Position A, Position B) {
        double dY = A.lat-B.lat;
        double dX = (A.lon-B.lon)*Math.cos(((A.lat+B.lat)/2)*Math.PI/180);
        return Others.round(Math.sqrt(dX*dX+dY*dY)*1000*40000/360,1);
    }

    public String toString(){
        return "Longitude: " + lon + " , Latitude: " + lat;
    }
}
