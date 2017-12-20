--liquibase formatted sql

--changeset michaluk.michal:1.init

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE CAST (character varying AS json) WITH INOUT AS ASSIGNMENT;
