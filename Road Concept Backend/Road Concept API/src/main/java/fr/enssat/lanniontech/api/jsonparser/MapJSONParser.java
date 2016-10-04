package fr.enssat.lanniontech.api.jsonparser;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.enssat.lanniontech.api.exceptions.JSONProcessingException;
import fr.enssat.lanniontech.api.jsonparser.entities.Map;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * JSON Parser for JSON object representing a map. The MAPJson objet is used to represent a map between the API and the appliation front end.
 */
public class MapJSONParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapJSONParser.class);

    /**
     * Jackson 2 Object Mapper
     */
    private static ObjectMapper MAPPER = new ObjectMapper();

    public static Map unmarshall(File json) throws JSONProcessingException {
        try {
            return MAPPER.readValue(json, Map.class);
        } catch (IOException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            throw new JSONProcessingException("file " + json.getName(), e);
        }
    }

    public static Map unmarshall(String json) throws JSONProcessingException {
        try {
            return MAPPER.readValue(json, Map.class);
        } catch (IOException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            throw new JSONProcessingException("string input", e);
        }
    }
}