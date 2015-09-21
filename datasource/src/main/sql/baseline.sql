CREATE TABLE members
(
	  id            BIGSERIAL    NOT NULL PRIMARY KEY
	, screen_name   VARCHAR(100) NOT NULL UNIQUE
	, password_hash TEXT         NOT NULL 
)
;

CREATE TYPE member AS
(
	  id            BIGINT
	, screen_name   VARCHAR(100)
	, password_hash TEXT 
)
;

CREATE FUNCTION member_create (screen_name VARCHAR(100), password_hash TEXT)
RETURNS member
AS
$$
INSERT INTO members (id, screen_name, password_hash)
VALUES (DEFAULT, screen_name, password_hash)
RETURNING id, screen_name, password_hash
;
$$
LANGUAGE SQL
STRICT
;

CREATE VIEW view_members AS 
SELECT id, screen_name FROM members;