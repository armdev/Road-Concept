package fr.enssat.lanniontech.core.roadElements;

import fr.enssat.lanniontech.core.positioning.PosFunction;
import fr.enssat.lanniontech.core.positioning.Position;
import fr.enssat.lanniontech.core.positioning.Trajectory;

import java.util.Map;

public class RoadSection {
    private Position A;
    private Position B;
    private Map<Integer, Trajectory> trajectoryMapA;
    private Map<Integer, Trajectory> trajectoryMapB;
    private double length;


    private Lane laneA; //this lane is on rigth side in A -> B scenario
    private Lane laneB; //this lane is on left side in A -> B scenario
    private PosFunction function;
    private Road myRoad;


    public RoadSection(Position A, Position B) {
        this(A,B,null);
    }

    public RoadSection(Position A, Position B,Road myRoad){
        this.A = A;
        this.B = B;
        length = Position.length(A, B);
        laneA = new Lane(this, length, null);
        laneB = new Lane(this, length, null);
        function = new PosFunction(A, B, length);
        this.myRoad = myRoad;
        trajectoryMapA = null;
        trajectoryMapB = null;
    }

    public Position getPosition(Lane myLane, double pos) {
        if (myLane == laneA) {
            return function.get(pos);
        } else {
            return function.get(length - pos);
        }
    }

    /**
     * @param myLane
     * @param pos
     * @param widthPos position of the vehicle from the left side of the road
     * @return
     */
    public Position getPosition(Lane myLane, double pos, double widthPos) {
        if (myLane == laneA) {
            return function.get(pos, widthPos);
        } else {
            return function.get(length - pos, -widthPos);
        }
    }

    public Lane getRigthLane(Position P) {
        if (P == A) {
            return laneA;
        } else {
            return laneB;
        }
    }

    public Lane getLeftLane(Position P) {
        if (P == B) {
            return laneA;
        } else {
            return laneB;
        }
    }

    public Lane getLaneA() {
        return laneA;
    }

    public Lane getLaneB() {
        return laneB;
    }

    public double getLength() {
        return length;
    }

    public Position getA() {
        return A;
    }

    public Position getB() {
        return B;
    }

    public PosFunction getFunction() {
        return function;
    }

    public double getWPos(Lane lane, double width) {
        if (lane == laneA) {
            return width / 2;
        } else {
            return -width / 2;
        }
    }

    public Road getMyRoad() {
        return myRoad;
    }

    public void setMyRoad(Road myRoad) {
        this.myRoad = myRoad;
    }
}
