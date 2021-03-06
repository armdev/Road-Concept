import fr.enssat.lanniontech.core.managers.RoadManager;
import fr.enssat.lanniontech.core.positioning.PosFunction;
import fr.enssat.lanniontech.core.positioning.Position;
import fr.enssat.lanniontech.core.roadElements.roads.Road;
import fr.enssat.lanniontech.core.roadElements.roadSections.RoadSection;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by 4r3 on 04/10/16.
 */
public class TestPosFunction {
    @Test
    public void testParallelRoads1() {
        RoadManager RM = new RoadManager();
        Position A = new Position(0, 0);
        Position B = new Position(0, 1);
        Position C = new Position(0, 2);

        Road R1 = RM.addRoadSectionToRoad(A, B, UUID.randomUUID(),50,false);
        Road R2 = RM.addRoadSectionToRoad(B, C, UUID.randomUUID(),50,false);

        RoadSection RS1 = R1.get(0);
        RoadSection RS2 = R2.get(0);

        PosFunction Pf1 = RS1.getFunction();
        PosFunction Pf2 = RS2.getFunction();

        Assert.assertFalse(Pf1.cross(Pf2));
        Assert.assertFalse(Pf2.cross(Pf1));

        Assert.assertEquals(Pf1.det(Pf2), 0, 0.001);
    }

    @Test
    public void testParallelRoads2() {
        RoadManager RM = new RoadManager();
        Position A = new Position(0, 0);
        Position B = new Position(0, 1);
        Position C = new Position(0, -1);

        Road R1 = RM.addRoadSectionToRoad(A, B, UUID.randomUUID(),50,false);
        Road R2 = RM.addRoadSectionToRoad(B, C, UUID.randomUUID(),50,false);

        RoadSection RS1 = R1.get(0);
        RoadSection RS2 = R2.get(0);
        PosFunction Pf1 = RS1.getFunction();
        PosFunction Pf2 = RS2.getFunction();

        Assert.assertFalse(Pf1.cross(Pf2));
        Assert.assertFalse(Pf2.cross(Pf1));

        Assert.assertTrue(Pf1.det(Pf2) == 0);
    }

    @Test
    public void testNotParallelRoads1() {
        RoadManager RM = new RoadManager();
        Position A = new Position(0, 0);
        Position B = new Position(0, 1);
        Position C = new Position(1, 1);

        Road R1 = RM.addRoadSectionToRoad(A, B, UUID.randomUUID(),50,false);
        Road R2 = RM.addRoadSectionToRoad(B, C, UUID.randomUUID(),50,false);

        RoadSection RS1 = R1.get(0);
        RoadSection RS2 = R2.get(0);

        PosFunction Pf1 = RS1.getFunction();
        PosFunction Pf2 = RS2.getFunction();

        Assert.assertTrue(Pf1.cross(Pf2));
        Assert.assertTrue(Pf2.cross(Pf1));

        Assert.assertTrue(Pf1.det(Pf2) < 0);
        Assert.assertTrue(Pf2.det(Pf1) > 0);
    }

    @Test
    public void testNotParallelRoads2() {
        RoadManager RM = new RoadManager();
        Position A = new Position(0, 0);
        Position B = new Position(0, 1);
        Position C = new Position(-1, 1);

        Road R1 = RM.addRoadSectionToRoad(A, B, UUID.randomUUID(),50,false);
        Road R2 = RM.addRoadSectionToRoad(B, C, UUID.randomUUID(),50,false);

        RoadSection RS1 = R1.get(0);
        RoadSection RS2 = R2.get(0);

        PosFunction Pf1 = RS1.getFunction();
        PosFunction Pf2 = RS2.getFunction();

        Assert.assertTrue(Pf1.cross(Pf2));
        Assert.assertTrue(Pf2.cross(Pf1));

        Assert.assertTrue(Pf1.det(Pf2) > 0);
        Assert.assertTrue(Pf2.det(Pf1) < 0);
    }

    @Test
    public void testInterValues() {
        Position I = new Position(0, 0);
        Position A = new Position(-1, -1);
        Position B = new Position(-1, 1);


        PosFunction Pf1 = new PosFunction(I, A);
        PosFunction Pf2 = new PosFunction(I, B);

        //check for Ws=0

        double[] Ps = Pf1.getInterPos(Pf2, 0, 0);

        Assert.assertEquals(0, Ps[0], 0.00000001);
        Assert.assertEquals(Ps[0], Ps[1], 0.00000001);

        Assert.assertEquals(Pf1.get(Ps[0], 0), Pf2.get(Ps[1], 0));

        Ps = Pf2.getInterPos(Pf1, 0, 0);

        Assert.assertEquals(0, Ps[0], 0.00000001);
        Assert.assertEquals(Ps[0], Ps[1], 0.00000001);

        Assert.assertEquals(Pf1.get(Ps[1], 0), Pf2.get(Ps[0], 0));

        //check for Ws!=0

        Ps = Pf1.getInterPos(Pf2, 1, -1);

        Assert.assertEquals(1, Ps[0], 0.00000001);
        Assert.assertEquals(Ps[1], Ps[0], 0.00000001);

        Assert.assertEquals(Pf1.get(Ps[0], 1), Pf2.get(Ps[1], -1));

        Ps = Pf1.getInterPos(Pf2, -1, 1);

        Assert.assertEquals(-1, Ps[0], 0.00000001);
        Assert.assertEquals(Ps[0], Ps[1], 0.00000001);

        Assert.assertEquals(Pf1.get(Ps[0], -1), Pf2.get(Ps[1], 1));

        Ps = Pf1.getInterPos(Pf2, 0, 1);

        Assert.assertEquals(-1, Ps[0], 0.00000001);
        Assert.assertEquals(0, Ps[1], 0.00000001);

        Assert.assertEquals(Pf1.get(Ps[0], 0), Pf2.get(Ps[1], 1));

    }
}
