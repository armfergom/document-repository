-- Creation of schemas

CREATE SCHEMA authentication;
COMMENT ON SCHEMA common IS 'Schema for holding user tables.';
ALTER SCHEMA authentication OWNER TO postgres;

CREATE TABLE authentication.users (
    username character varying(50) NOT NULL,
    password character varying(60) NOT NULL,
    enabled boolean NOT NULL,
    PRIMARY KEY(username)
    );

CREATE TABLE authentication.user_roles (
    username varchar(50) NOT NULL,
    role varchar(50) NOT NULL,
    PRIMARY KEY(username, role),
    FOREIGN KEY (username) REFERENCES authentication.users(username)
);

INSERT INTO authentication.users VALUES ('Arun', '$2a$10$BwyjwGRWc4gMk2Y1e2jzie.FVYrfgxV0.aHgdU1VM6E.Rf0ZYoaWa', true);
INSERT INTO authentication.users VALUES ('Jeremy', '$2a$10$EHmzwTcEFS1IUZ.hhsMw.uZvG2uwH7fOS1nh/fcIiAvmXg3LwdVP.', true);
INSERT INTO authentication.users VALUES ('Jing', '$2a$10$twiIh66bjFBWBYZPWOrc1uS/KRCdT61Z5wFdpJGdeHwY2HeCZ.J.a', true);

INSERT INTO authentication.user_roles VALUES ('Arun', 'ROLE_ADMIN');
INSERT INTO authentication.user_roles VALUES ('Jeremy', 'ROLE_USER');
INSERT INTO authentication.user_roles VALUES ('Jing', 'ROLE_USER');