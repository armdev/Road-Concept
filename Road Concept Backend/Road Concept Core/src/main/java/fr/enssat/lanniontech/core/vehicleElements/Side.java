package fr.enssat.lanniontech.core.vehicleElements;


import fr.enssat.lanniontech.core.positioning.Position;
import fr.enssat.lanniontech.core.roadElements.Lane;
import fr.enssat.lanniontech.core.trajectory.Trajectory;
import fr.enssat.lanniontech.core.trajectory.informations.TrajectoryInformator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Side {

    private static final Logger LOGGER = LoggerFactory.getLogger(Side.class);

    private Trajectory myTrajectory;

    private double pos;
    private Vehicle myVehicle;
    private UUID myRoad;
    private UUID nextRoad;
    private int pathStep;
    private double length;

    public Side(double pos, Vehicle myVehicle, Lane myLane, double length) {
        this.myVehicle = myVehicle;
        this.pos = pos;
        this.length = length;
        myTrajectory = myLane.getInsertTrajectory();
        myTrajectory.getIn(this);
        pathStep = 1;
        myRoad = null;
        nextRoad = null;
        if (myVehicle != null) {
            myRoad = myVehicle.getPathStep(0);
            nextRoad = myVehicle.getPathStep(pathStep);
        }
    }

    public double getMaxSpeed() {
        return myTrajectory.getSpeed();
    }

    public double getPos() {
        return pos;
    }

    public Vehicle getMyVehicle() {
        return myVehicle;
    }

    @Deprecated
    public void move(double distance) {
        double pos = myTrajectory.getPos(this.pos + distance);
        if (this.pos > pos) {
            myTrajectory.getOut(this);
            myTrajectory = myTrajectory.getNext().getDestination();
            myTrajectory.getIn(this);
        }
        this.pos = pos;
    }

    public void moveOnPath(double distance) {
        double hypPos = pos + distance;
        double pos = myTrajectory.getPos(this.pos + distance);
        if (hypPos > pos) {
            this.pos = pos;
            myTrajectory.getOut(this);
            myTrajectory = myTrajectory.getNext(nextRoad).getDestination();
            if (myTrajectory.getRoadId() != myRoad) {
                myRoad = myTrajectory.getRoadId();
                if (nextRoad != myRoad) {
                    LOGGER.error(nextRoad + " != " + myRoad);
                }
                nextRoad = myVehicle.getPathStep(++pathStep);
                //LOGGER.debug("trajectory changed, now on road : " + myRoad);
                //LOGGER.debug("is on step : " + pathStep);
            }
            myTrajectory.getIn(this);
        } else {
            this.pos = pos;
        }
    }

    public Position getGPS() {
        return myTrajectory.getGPS(pos);
    }

    public UUID getNextRoad() {
        return nextRoad;
    }

    public void removeFromRoad() {
        myTrajectory.getOut(this);
    }

    public double getLength() {
        return length;
    }

    public TrajectoryInformator getInformations(double distanceOut) {
        return myTrajectory.getInformations(this, distanceOut);
    }

    public Trajectory getMyTrajectory() {
        return myTrajectory;
    }

    public void resetPath() {
        pathStep = 1;
        myRoad = myVehicle.getPathStep(0);
        nextRoad = myVehicle.getPathStep(pathStep);
    }
}
