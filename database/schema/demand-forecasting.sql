--liquibase formatted sql

--changeset michaluk.michal:1.init
CREATE SCHEMA demand_forecasting;

CREATE TABLE demand_forecasting.product_demand (
    id serial NOT NULL PRIMARY KEY,
    version bigint NOT NULL,
    ref_no character varying(64) NOT NULL,
    UNIQUE(ref_no)
);

CREATE TABLE demand_forecasting."demand" (
    id serial NOT NULL PRIMARY KEY,
    ref_no character varying(64) NOT NULL,
    "date" timestamp without time zone NOT NULL,
    value json NOT NULL,
    UNIQUE(ref_no, "date")
);

CREATE TABLE demand_forecasting.current_demand (
    id serial NOT NULL PRIMARY KEY,
    ref_no character varying(64) NOT NULL,
    "date" timestamp without time zone NOT NULL,
    level bigint NOT NULL,
    "schema" character varying(64) NOT NULL,
    UNIQUE(ref_no, "date")
);

CREATE TABLE demand_forecasting.demand_adjustment (
    id serial NOT NULL PRIMARY KEY,
    customer_representative character varying(255) NOT NULL,
    note character varying(255) NOT NULL,
    adjustment json NOT NULL,
    clean_after timestamp without time zone
);

CREATE TABLE demand_forecasting.demand_review (
    id serial NOT NULL PRIMARY KEY,
    ref_no character varying(64) NOT NULL,
    "date" timestamp without time zone NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    review json NOT NULL,
    decision character varying(64),
    clean_after timestamp without time zone
);
