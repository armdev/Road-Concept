package fr.enssat.lanniontech.api.entities.geojson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.enssat.lanniontech.api.exceptions.RoadConceptUnexpectedException;

import java.util.Arrays;
import java.util.List;

public class Polygon extends Geometry<List<Coordinates>> {

    @JsonIgnore
    public List<Coordinates> getExteriorRing() {
        assertExteriorRing();
        return getCoordinates().get(0);
    }

    public void setExteriorRing(List<Coordinates> points) {
        getCoordinates().add(0, points);
    }

    private void assertExteriorRing() {
        if (getCoordinates().isEmpty()) {
            throw new RoadConceptUnexpectedException("No exterior ring definied");
        }
    }

    @JsonIgnore
    public List<List<Coordinates>> getInteriorRings() {
        assertExteriorRing();
        return getCoordinates().subList(1, getCoordinates().size());
    }

    public List<Coordinates> getInteriorRing(int index) {
        assertExteriorRing();
        return getCoordinates().get(1 + index);
    }

    public void addInteriorRing(List<Coordinates> points) {
        assertExteriorRing();
        getCoordinates().add(points);
    }

    public void addInteriorRing(Coordinates... points) {
        assertExteriorRing();
        getCoordinates().add(Arrays.asList(points));
    }

    @Override
    public String toString() {
        return "Polygon{} " + super.toString();
    }
}
