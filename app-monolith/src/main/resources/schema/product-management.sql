--liquibase formatted sql

--changeset michaluk.michal:1.init
CREATE SCHEMA product_management;

CREATE TABLE product_management.product_description (
    ref_no character varying(64) NOT NULL PRIMARY KEY,
    description json NOT NULL
);
