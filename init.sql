CREATE SCHEMA DETECTORS;

CREATE TABLE DETECTORS.TEMPERATURE(
                          ID SERIAL PRIMARY KEY,
                          VALUE FLOAT(20) NOT NULL,
                          MEASURE CHAR(20) NOT NULL,
                          DATE_TIME TIMESTAMP NOT NULL
);

CREATE TABLE DETECTORS.HUMIDITY(
                       ID SERIAL PRIMARY KEY,
                       VALUE FLOAT(20) NOT NULL,
                       MEASURE CHAR(20) NOT NULL,
                       DATE_TIME TIMESTAMP NOT NULL
);

CREATE TABLE DETECTORS.PRESSURE(
                       ID SERIAL PRIMARY KEY,
                       VALUE FLOAT(20) NOT NULL,
                       MEASURE CHAR(20) NOT NULL,
                       DATE_TIME TIMESTAMP NOT NULL
);

CREATE TABLE DETECTORS.SPEED(
                    ID SERIAL PRIMARY KEY,
                    VALUE FLOAT(20) NOT NULL,
                    MEASURE CHAR(20) NOT NULL,
                    DATE_TIME TIMESTAMP NOT NULL
)