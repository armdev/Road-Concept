package fr.enssat.lanniontech.core.roadElements.intersections;

import fr.enssat.lanniontech.core.positioning.Position;
import fr.enssat.lanniontech.core.trajectory.SimpleTrajectory;
import fr.enssat.lanniontech.core.trajectory.Trajectory;
import fr.enssat.lanniontech.core.trajectory.TrajectoryJunction;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoundAboutIO extends Intersection {

    private UUID id;

    public RoundAboutIO(Position P, UUID RoundAboutUUID) {
        super(P);
        id = RoundAboutUUID;
    }

    @Override
    public void assembleIntersection() {
        for (SimpleTrajectory source : incomingTrajectories) {
            //create the entry in the trajectories table
            Map<UUID, SimpleTrajectory> myTrajectories = new HashMap<>();
            for (SimpleTrajectory destination : outgoingTrajectories) {
                if (source.getRoadId() != destination.getRoadId() || source.getRoadId() == id) {
                    myTrajectories.put(destination.getRoadId(), destination);

                    TrajectoryJunction junction = TrajectoryJunction.computeJunction(source, destination);

                    source.addDestination(junction);
                    source.setDestIntersection(this);
                    destination.addSource(junction);
                    destination.setSourceIntersection(this);
                }
            }
            trajectories.put(source.getRoadId(), myTrajectories);
        }
    }

    @Override
    public boolean isValid() {
        boolean res1, res2;
        res1 = false;
        for (Trajectory t : outgoingTrajectories) {
            if (t.getRoadId() == id) {
                res1 = true;
            }
        }
        res2 = false;
        for (Trajectory t : incomingTrajectories) {
            if (t.getRoadId() == id) {
                res2 = true;
            }
        }

        return res1 & res2;
    }
}
