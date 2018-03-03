--liquibase formatted sql

--changeset michaluk.michal:1.init
CREATE SCHEMA production_planning;

CREATE TABLE production_planning.production_daily_output (
    id serial NOT NULL PRIMARY KEY,
    ref_no character varying(64) NOT NULL,
    "date" timestamp without time zone NOT NULL,
    output bigint NOT NULL,
    UNIQUE(ref_no, "date")
);

CREATE TABLE production_planning.production_output (
    id serial NOT NULL PRIMARY KEY,
    ref_no character varying(64) NOT NULL,
    "start" timestamp without time zone NOT NULL,
    "end" timestamp without time zone NOT NULL,
    duration bigint NOT NULL,
    parts_per_minute integer NOT NULL,
    total bigint NOT NULL
);
