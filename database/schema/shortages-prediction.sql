--liquibase formatted sql

--changeset michaluk.michal:1.init
CREATE SCHEMA shortages_prediction;

CREATE TABLE shortages_prediction.shortage (
    id serial NOT NULL PRIMARY KEY,
    version bigint NOT NULL,
    ref_no character varying(64) NOT NULL,
    shortages json NOT NULL,
    UNIQUE(ref_no)
);

CREATE TABLE shortages_prediction.stock_forecast (
    ref_no character varying(64) NOT NULL PRIMARY KEY
);

--changeset michaluk.michal:2.rename.shortages.column
ALTER TABLE shortages_prediction.shortage RENAME shortages TO shortage;
