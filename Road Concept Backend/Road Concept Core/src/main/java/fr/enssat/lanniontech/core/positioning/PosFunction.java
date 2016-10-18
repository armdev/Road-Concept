package fr.enssat.lanniontech.core.positioning;

public class PosFunction {
    private double alat;
    private double blat;
    private double clat;

    private double alon;
    private double blon;
    private double clon;

    public PosFunction(Position P1, Position P2){
        double length = Position.length(P1,P2);

        blat = P1.lat;
        alat = (P2.lat - P1.lat) / length;
        blon = P1.lon;
        alon = (P2.lon - P1.lon) / length;

        clat = -alat;
        clon = alon;
    }

    @Deprecated
    public PosFunction(Position P1, Position P2, double length) {
        //calculate the parameters
        blat = P1.lat;
        alat = (P2.lat - P1.lat) / length;
        blon = P1.lon;
        alon = (P2.lon - P1.lon) / length;

        clat = -alat;
        clon = alon;

    }

    public Position get(double pos) {
        return get(pos, 0);
    }

    public Position get(double pos, double wpos) {
        double lat = alat * pos + blat + clat * wpos;
        double lon = alon * pos + blon + clon * wpos;
        return new Position(lat, lon);
    }

    public double[] getInterPos(PosFunction Pf, double myW, double PfW) {
        double[] M = new double[4];
        double[] R = new double[2];
        double k;
        double[] p = new double[2];

        // |M[0] M[1]|
        // |M[2] M[3]|

        M[0] = alat;
        M[1] = -Pf.getAlat();
        M[2] = alon;
        M[3] = -Pf.getAlon();

        //|R[0]|
        //|R[1]|

        R[0] = Pf.getBlat() - blat + PfW * Pf.getClat() - myW * clat;
        R[0] = Pf.getBlon() - blon + PfW * Pf.getClon() - myW * clon;

        if (M[0] == 0 || M[1] == 0 || M[2] == 0 || M[3] == 0) {
            if (M[0] == 0) {
                p[1] = (R[0] / M[1]);
                p[0] = (R[1] - (p[1] * M[3])) / M[2];
            } else if (M[1] == 0) {
                p[0] = R[0] / M[0];
                p[1] = (R[1] - (p[0] * M[2])) / M[3];
            } else if (M[2] == 0) {
                p[1] = R[1] / M[3];
                p[0] = (R[0] - (p[1]) * M[1]) / M[0];
            } else {
                p[0] = R[1] / M[2];
                p[1] = (R[0] - (p[0]) * M[0]) / M[1];
            }
        } else {
            k = M[0] / M[2];
            M[0] -= M[2] * k;
            M[1] -= M[3] * k;
            R[0] -= R[3] * k;

            p[1] = (R[0] / M[1]);
            p[0] = (R[1] - (p[1] * M[3])) / M[2];
        }

        return p;
    }

    /**
     * return a positive number if Pf cross from left side
     *
     * @param Pf
     * @return
     */
    public double det(PosFunction Pf){
        return alon*Pf.getAlat()-alat*Pf.getAlon();
    }

    public boolean cross(PosFunction Pf){
        return det(Pf)!=0;
    }

    public double getAlat() {
        return alat;
    }

    public double getBlat() {
        return blat;
    }

    public double getClat() {
        return clat;
    }

    public double getAlon() {
        return alon;
    }

    public double getBlon() {
        return blon;
    }

    public double getClon() {
        return clon;
    }
}
