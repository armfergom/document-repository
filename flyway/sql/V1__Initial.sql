-- Creation of schemas

CREATE SCHEMA common;
COMMENT ON SCHEMA common IS 'Schema for holding common tables.';
ALTER SCHEMA common OWNER TO postgres;

CREATE SCHEMA degree;
COMMENT ON SCHEMA degree IS 'Schema for holding all degree related tables.';
ALTER SCHEMA degree OWNER TO postgres;

CREATE SCHEMA document;
COMMENT ON SCHEMA document IS 'Schema for holding document information.';
ALTER SCHEMA document OWNER TO postgres;

CREATE SCHEMA registration;
COMMENT ON SCHEMA registration IS 'Schema for holding registration information.';
ALTER SCHEMA registration OWNER TO postgres;

CREATE SCHEMA subject;
COMMENT ON SCHEMA subject IS 'Schema for holding subject related information.';
ALTER SCHEMA subject OWNER TO postgres;

-- SEQUENCES

CREATE SEQUENCE common.periods__period_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE degree.degrees__degree_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE degree.degree__years_degree_year_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE degree.groups__groups_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE subject.subjects__subject_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE subject.degree_year_in_period_subjects__degree_year_in_period_subject_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE registration.students__student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE document.subjectInPeriodDocuments__subject_in_period_document_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- common schema tables

CREATE TABLE common.periods (
    period_id bigint DEFAULT nextval('common.periods__period_id_seq'::regclass) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    PRIMARY KEY(period_id)
);

-- degree schema tables

CREATE TABLE degree.degrees (
    degree_id bigint DEFAULT nextval('degree.degrees__degree_id_seq'::regclass) NOT NULL,
    name character varying(100) NOT NULL,
    PRIMARY KEY(degree_id)
);


CREATE TABLE degree.degree_years (
    degree_year_id bigint DEFAULT nextval('degree.degree__years_degree_year_id_seq'::regclass) NOT NULL,
    degree_id bigint NOT NULL,
    year integer NOT NULL,
    PRIMARY KEY(degree_year_id),
    FOREIGN KEY (degree_id) REFERENCES degree.degrees(degree_id)
);


CREATE TABLE degree.groups (
    group_id bigint DEFAULT nextval('degree.groups__groups_group_id_seq'::regclass) NOT NULL,
    name character varying(100) NOT NULL,
    PRIMARY KEY(group_id)

);

-- subject schema tables

CREATE TABLE subject.subjects (
    subject_id bigint DEFAULT nextval('subject.subjects__subject_id_seq'::regclass) NOT NULL,
    name character varying(200) NOT NULL,
    PRIMARY KEY(subject_id)
);

CREATE TABLE subject.degree_year_in_period_subjects (
	degree_year_in_period_subject_id bigint DEFAULT nextval('subject.degree_year_in_period_subjects__degree_year_in_period_subject_id_seq'::regclass) NOT NULL,
    degree_year_id bigint NOT NULL, 
    period_id bigint NOT NULL, 
    subject_id bigint NOT NULL, 
    PRIMARY KEY(degree_year_in_period_subject_id),
    FOREIGN KEY (degree_year_id) REFERENCES degree.degree_years(degree_year_id),
    FOREIGN KEY (period_id) REFERENCES common.periods(period_id),
    FOREIGN KEY (subject_id) REFERENCES subject.subjects(subject_id)
);

-- registration schema tables

CREATE TABLE registration.students (
    student_id bigint DEFAULT nextval('registration.students__student_id_seq'::regclass) NOT NULL,
    passport_id character varying(100) NOT NULL,
    name character varying(100) NOT NULL,
    surname character varying(100) NOT NULL,
    email character varying(50) NOT NULL,
    phone_number integer,
    PRIMARY KEY(student_id)
);

CREATE TABLE registration.registrations (
    student_id bigint NOT NULL,
    degree_year_id bigint NOT NULL,
    period_id bigint NOT NULL, 
    group_id bigint NOT NULL, 
    PRIMARY KEY(student_id, degree_year_id, period_id, group_id),
    FOREIGN KEY (student_id) REFERENCES registration.students(student_id),
    FOREIGN KEY (degree_year_id) REFERENCES degree.degree_years(degree_year_id),
    FOREIGN KEY (period_id) REFERENCES common.periods(period_id),
    FOREIGN KEY (group_id) REFERENCES degree.groups(group_id)
);


-- document schema tables

CREATE TABLE document.subject_in_period_documents (
	subject_in_period_document_id bigint DEFAULT nextval('document.subjectInPeriodDocuments__subject_in_period_document_id_seq'::regclass) NOT NULL,
    degree_year_in_period_subject_id bigint NOT NULL, 
    document_id bigint NOT NULL, -- Artificial FK to JackRabbit DB
    PRIMARY KEY(subject_in_period_document_id),
    FOREIGN KEY (degree_year_in_period_subject_id) REFERENCES subject.degree_year_in_period_subjects(degree_year_in_period_subject_id)
);
