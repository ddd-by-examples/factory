--liquibase formatted sql

--changeset michaluk.michal:1.init
CREATE SCHEMA delivery_planning;

CREATE TABLE delivery_planning.delivery_planner_definition (
    ref_no character varying(64) NOT NULL PRIMARY KEY,
    definition json NOT NULL
);

CREATE TABLE delivery_planning.delivery_forecast (
    id serial NOT NULL PRIMARY KEY,
    ref_no character varying(64) NOT NULL,
    "time" timestamp without time zone NOT NULL,
    "date" timestamp without time zone NOT NULL,
    level bigint NOT NULL
);
