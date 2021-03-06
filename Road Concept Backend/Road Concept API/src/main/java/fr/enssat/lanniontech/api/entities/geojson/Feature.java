package fr.enssat.lanniontech.api.entities.geojson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonIgnoreProperties({"_id"}) // "_id" is auto set by MongoDB
public class Feature extends GeoJsonObject {

    @JsonProperty(access = Access.WRITE_ONLY)
    private UUID uuid = UUID.randomUUID();
    @JsonInclude(Include.NON_NULL)
    private Map<String, Object> properties = new HashMap<>(); //NOSONAR: Java HashMap can be serialized without problem
    @JsonInclude(Include.ALWAYS)
    private GeoJsonObject geometry;
    @JsonProperty(value = "id")
    private String openStreetMapID;

    @JsonIgnore
    public boolean isRoad() {
        return geometry instanceof LineString && getType() != FeatureType.ROUNDABOUT;
    }

    @JsonIgnore
    public boolean isRoundabout() {
        return geometry instanceof LineString && getType() == FeatureType.ROUNDABOUT;
    }

    @JsonIgnore
    public FeatureType getType() {
        FeatureType type;
        try {
            type = (FeatureType) getProperties().get("type"); // from osm
        } catch (ClassCastException e) { //NOSONAR
            type = FeatureType.forValue((Integer) getProperties().get("type")); // created by the user
        }
        return type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public GeoJsonObject getGeometry() {
        return geometry;
    }

    public void setGeometry(GeoJsonObject geometry) {
        this.geometry = geometry;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @JsonProperty(value = "id")
    public String getOpenStreetMapID() {
        return openStreetMapID;
    }

    @JsonProperty(value = "id")
    public void setOpenStreetMapID(String openStreetMapID) {
        this.openStreetMapID = openStreetMapID;
    }

    @Override
    public String toString() {
        return "Feature{properties=" + properties + ", geometry=" + geometry + ", uuid='" + uuid + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Feature feature = (Feature) o;
        return feature.getUuid().equals(getUuid());
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        result = 31 * result + (geometry != null ? geometry.hashCode() : 0);
        result = 31 * result + (openStreetMapID != null ? openStreetMapID.hashCode() : 0);
        return result;
    }
}
