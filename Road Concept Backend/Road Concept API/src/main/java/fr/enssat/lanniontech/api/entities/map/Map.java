package fr.enssat.lanniontech.api.entities.map;

import fr.enssat.lanniontech.api.entities.geojson.FeatureCollection;

public class Map {

    private MapInfo infos;
    private FeatureCollection features;

    public MapInfo getInfos() {
        return infos;
    }

    public void setInfos(MapInfo infos) {
        this.infos = infos;
    }

    public FeatureCollection getFeatures() {
        return features;
    }

    public void setFeatures(FeatureCollection features) {
        this.features = features;
    }
}
