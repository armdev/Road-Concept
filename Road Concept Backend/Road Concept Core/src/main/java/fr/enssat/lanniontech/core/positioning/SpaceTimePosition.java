package fr.enssat.lanniontech.core.positioning;

import fr.enssat.lanniontech.core.Tools;
import fr.enssat.lanniontech.core.vehicleElements.VehicleType;

public final class SpaceTimePosition extends Position {

    private final int time;
    private final double angle;
    private final int vehicleId;
    private final VehicleType type;

    private SpaceTimePosition(double lon, double lat, int time, double angle, int vehicleId, VehicleType type) {
        super(lon, lat);

        this.vehicleId = vehicleId;
        this.time = time;
        this.angle = angle;
        this.type = type;
    }

    public static SpaceTimePosition getMean(Position A, Position B, int time, int vehicleId, VehicleType type) {

        double lon = (A.getLongitude() + B.getLongitude()) / 2;
        double lat = (A.getLatitude() + B.getLatitude()) / 2;

        double lonDir = A.getLongitude() - B.getLongitude();
        double latDir = A.getLatitude() - B.getLatitude();

        double angle = -Tools.getOrientedAngle(0, 1, lonDir, latDir);

        return new SpaceTimePosition(lon, lat, time, angle, vehicleId, type);
    }

    public int getTime() {
        return time;
    }

    public int getId() {
        return vehicleId;
    }

    public double getAngle() {
        return angle;
    }

    public VehicleType getType() {
        return type;
    }
}
