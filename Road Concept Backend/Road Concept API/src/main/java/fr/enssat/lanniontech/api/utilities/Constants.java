package fr.enssat.lanniontech.api.utilities;

public final class Constants {

    public static final ProjetEnvironment ENVIRONMENT = ProjetEnvironment.DEVELOPPMENT;
    public static final int HTTP_SERVER_PORT = 8080;
    public static final String SESSION_CURRENT_USER = "me";

    // =======
    // MONGODB
    // =======

    public static final String MONGODB_DATABASE_NAME = "RoadConcept";
    public static final String MONGODB_SERVER_URL = "localhost";
    public static final int MONGODB_SERVER_PORT = 27017;

    // ==========
    // POSTGRESQL
    // ==========

    public static final String POSTGRESQL_DATABASE_NAME = "roadconcept";
    public static final int POSTGRESQL_SERVER_PORT = 5432;
    public static final String POSTGRESQL_SERVER_HOST = "localhost";
    public static final int POSTGRESQL_MAX_CONNECTIONS = 10;

    // ERROR CODES
    // -----------
    public static final String POSTGRESQL_FOREIGN_KEY_VIOLATION = "23503";
    public static final String POSTGRESQL_UNIQUE_VIOLATION = "23505";
    public static final String POSTGRESQL_CHECK_VIOLATION = "23514";

    // ====================
    // SINGLETON MANAGEMENT
    // ====================

    private Constants() {
        // Prevent instantiation
    }

    public static Constants getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private final static Constants INSTANCE = new Constants();
    }

}