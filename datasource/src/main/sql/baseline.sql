START TRANSACTION
;

/* Member */

CREATE TABLE members
(
	  id            SERIAL       NOT NULL PRIMARY KEY
	, screen_name   VARCHAR(100) NOT NULL UNIQUE
	, password_hash TEXT         NOT NULL 
)
;

CREATE TYPE member AS
(
	  id            INTEGER
	, screen_name   VARCHAR(100)
	, password_hash TEXT 
)
;

CREATE FUNCTION member_create (arg_screen_name VARCHAR(100), arg_password_hash TEXT)
RETURNS member
AS
$$
INSERT INTO members (id, screen_name, password_hash)
VALUES (DEFAULT, arg_screen_name, arg_password_hash)
RETURNING id, screen_name, password_hash
;
$$
LANGUAGE SQL
STRICT
;

CREATE VIEW view_members AS 
SELECT id, screen_name FROM members
;

CREATE VIEW view_instructors AS 
SELECT * FROM view_members
;

CREATE VIEW view_students AS 
SELECT * FROM view_members
;

/* Task */

CREATE TABLE task_types
(
      id           SMALLSERIAL  NOT NULL PRIMARY KEY 
    , screen_title VARCHAR(100) NOT NULL UNIQUE
)
;

INSERT INTO task_types (screen_title) 
VALUES 
  ('ChoiceMultiple')
, ('ChoiceSingle')
, ('Guess')
, ('WrittenCommunication')
;

CREATE TABLE tasks 
(
	  id          SERIAL    NOT NULL PRIMARY KEY
	, type_id     SMALLINT  NOT NULL
	, description TEXT      NOT NULL
	, created_at  TIMESTAMP NOT NULL
	, FOREIGN KEY (type_id) REFERENCES task_types (id)
	    ON DELETE RESTRICT
	    ON UPDATE RESTRICT
)
;

CREATE TABLE task_owners
(
	  task_id   INTEGER NOT NULL 
	, member_id INTEGER NOT NULL 
	, PRIMARY KEY (task_id, member_id)
	, FOREIGN KEY (task_id) REFERENCES tasks (id)
	, FOREIGN KEY (member_id) REFERENCES members (id)
)
;

CREATE TYPE task AS
(
	  id          INTEGER
	, type_id     SMALLINT
	, description TEXT
)
;

CREATE VIEW view_tasks AS
SELECT id, type_id, description FROM tasks
;

CREATE FUNCTION task_create (arg_member_id INTEGER, arg_type_id SMALLINT, arg_description TEXT)
RETURNS SETOF task AS
$$
DECLARE
    arg_task_id INTEGER;
BEGIN
	INSERT INTO tasks (id, type_id, description, created_at)
	VALUES (DEFAULT, arg_type_id, arg_description, NOW( ))
	RETURNING (SELECT id INTO arg_task_id)
	;
	INSERT INTO task_owners (task_id, member_id)
	VALUES (arg_task_id, arg_member_id)
	;
	RETURN QUERY SELECT * 
	FROM view_tasks 
	WHERE id = arg_task_id 
	;
END
;
$$
LANGUAGE 'plpgsql'
STRICT
;

CREATE FUNCTION task_update (arg_member_id INTEGER, arg_task_id INTEGER, arg_description TEXT)
RETURNS SETOF task AS
$$
DECLARE
BEGIN
	UPDATE tasks 
	SET description = arg_description
	FROM task_owners
	WHERE CONCAT(task_owners.task_id, task_owners.member_id) = CONCAT(arg_task_id, arg_member_id)
	AND tasks.id = arg_task_id
	;
	RETURN QUERY SELECT * FROM view_tasks
	WHERE id = arg_task_id
	;
END
;
$$
LANGUAGE 'plpgsql'
STRICT
;

/* Option */

CREATE TABLE options 
(
	  task_id INTEGER   NOT NULL
	, id      BIGSERIAL NOT NULL
	, message TEXT      NOT NULL
	, reward  INTEGER  NOT NULL
	, PRIMARY KEY (id)
	, FOREIGN KEY (task_id) REFERENCES tasks (id)
)
;

CREATE TYPE option AS
(
	  task_id INTEGER 
	, id      BIGINT
	, message TEXT    
	, reward  INTEGER
)
;

CREATE VIEW view_options AS 
SELECT task_id, id, message, reward
FROM options
;

CREATE FUNCTION option_create (arg_member_id INTEGER, arg_task_id INTEGER, arg_message TEXT, arg_reward INTEGER)
RETURNS SETOF option 
AS
$$
DECLARE
	arg_option_id BIGINT;
BEGIN
	INSERT INTO options (task_id, id, message, reward)
	SELECT arg_task_id, nextval('options_id_seq'), arg_message, arg_reward 
	FROM tasks JOIN task_owners ON tasks.id = task_owners.task_id 
	WHERE CONCAT(task_owners.task_id, task_owners.member_id) = CONCAT(arg_task_id, arg_member_id)
	RETURNING (SELECT id INTO arg_option_id)
	;
	RETURN QUERY SELECT * 
	FROM view_options 
	WHERE id = arg_option_id
	;
END
;
$$
LANGUAGE 'plpgsql'
STRICT
;

CREATE FUNCTION option_update_message (arg_member_id INTEGER, arg_id BIGINT, arg_message TEXT)
RETURNS SETOF option 
AS
$$
DECLARE
BEGIN
	UPDATE options 
	SET message = arg_message 
	WHERE id = arg_id
	;
	RETURN QUERY SELECT * FROM view_options 
	WHERE id = arg_id
	;
END
;
$$
LANGUAGE 'plpgsql'
STRICT
;

CREATE FUNCTION option_update_reward (arg_member_id INTEGER, arg_id BIGINT, arg_reward INTEGER)
RETURNS SETOF option 
AS
$$
DECLARE
BEGIN
	UPDATE options 
	SET reward = arg_reward 
	WHERE id = arg_id
	;
	RETURN QUERY SELECT * FROM view_options 
	WHERE id = arg_id
	;
END
;
$$
LANGUAGE 'plpgsql'
STRICT
;

/* Pool */

CREATE TABLE pools 
(
	  id         SERIAL       NOT NULL PRIMARY KEY
	, title      VARCHAR(100) NOT NULL UNIQUE
	, created_at TIMESTAMP    NOT NULL
)
;

CREATE TABLE pool_owners 
(
	  pool_id   INTEGER NOT NULL
	, member_id INTEGER NOT NULL
	, PRIMARY KEY (pool_id, member_id)
	, FOREIGN KEY (pool_id) REFERENCES pools (id)
	, FOREIGN KEY (member_id) REFERENCES members (id)
)
;

CREATE TYPE pool AS
(
	  id    INTEGER
	, title VARCHAR(100)
)
;

CREATE VIEW view_pools AS
SELECT id, title FROM pools 
;

CREATE TABLE pool_tasks
(
	  pool_id INTEGER NOT NULL 
	, task_id INTEGER NOT NULL
	, PRIMARY KEY (pool_id, task_id)
	, FOREIGN KEY (pool_id) REFERENCES pools (id)
	, FOREIGN KEY (task_id) REFERENCES tasks (id)
)
;

CREATE VIEW view_pool_tasks AS 
SELECT pool_tasks.pool_id, view_tasks.* 
FROM view_tasks JOIN pool_tasks ON pool_tasks.task_id = view_tasks.id 
;

CREATE FUNCTION pool_create (arg_member_id INTEGER, arg_title VARCHAR(100))
RETURNS SETOF pool AS 
$$
DECLARE
	arg_pool_id INTEGER;
BEGIN
	INSERT INTO pools (id, title, created_at)
	VALUES (DEFAULT, arg_title, NOW( ))
	RETURNING (SELECT id INTO arg_pool_id)
	;
	INSERT INTO pool_owners (pool_id, member_id)
	VALUES (arg_pool_id, arg_member_id)
	;
	RETURN QUERY SELECT * 
	FROM view_pools 
	WHERE id = arg_pool_id
	;
END
;
$$
LANGUAGE 'plpgsql' 
STRICT 
;

CREATE FUNCTION pool_update (arg_member_id INTEGER, arg_pool_id INTEGER, arg_title VARCHAR(100))
RETURNS SETOF pool 
AS
$$
DECLARE 
BEGIN
	UPDATE pools 
	SET title = arg_title
	FROM pool_owners
	WHERE CONCAT(pool_owners.pool_id, pool_owners.member_id) = CONCAT(arg_pool_id, arg_member_id)
	AND pools.id = arg_pool_id
	;
	RETURN QUERY SELECT * FROM view_pools 
	WHERE id = arg_pool_id
	;
END 
;
$$
LANGUAGE 'plpgsql' 
STRICT
;

CREATE FUNCTION pool_update_task_add (arg_member_id INTEGER, arg_pool_id INTEGER, arg_task_id INTEGER)
RETURNS SETOF pool 
AS
$$
DECLARE
BEGIN
	PERFORM * FROM pool_update_task_remove (arg_member_id, arg_pool_id, arg_task_id)
	;
	INSERT INTO pool_tasks (pool_id, task_id)
	SELECT pool_owners.pool_id, arg_task_id 
	FROM pool_owners
	WHERE CONCAT(pool_owners.pool_id, pool_owners.member_id) = CONCAT(arg_pool_id, arg_member_id)
	LIMIT 1
	;
	RETURN QUERY SELECT * 
	FROM view_pools 
	WHERE id = arg_pool_id
	;
END
;
$$
LANGUAGE 'plpgsql' 
STRICT
;

CREATE FUNCTION pool_update_task_remove (arg_member_id INTEGER, arg_pool_id INTEGER, arg_task_id INTEGER)
RETURNS SETOF pool 
AS
$$
DECLARE
BEGIN
	PERFORM * 
	FROM pool_owners
	WHERE CONCAT(pool_owners.pool_id, pool_owners.member_id) = CONCAT(arg_pool_id, arg_member_id)
	;
	IF FOUND THEN 
		DELETE FROM pool_tasks
		WHERE CONCAT(pool_id, task_id) = CONCAT(arg_pool_id, arg_task_id)
		;
	END IF 
	;
	RETURN QUERY SELECT * FROM view_pools 
	WHERE id = arg_pool_id
	;
END
;
$$
LANGUAGE 'plpgsql' 
STRICT
;

/* Exam */

CREATE TABLE exams
(
	  id         SERIAL       NOT NULL PRIMARY KEY
	, title      VARCHAR(100) NOT NULL UNIQUE
	, creator_id INTEGER      NOT NULL
	, created_at TIMESTAMP    NOT NULL
	, FOREIGN KEY (creator_id) REFERENCES members (id)
)
;

CREATE TABLE exam_owners 
(
	  exam_id INTEGER NOT NULL 
	, member_id INTEGER NOT NULL
	, PRIMARY KEY (exam_id, member_id)
	, FOREIGN KEY (exam_id) REFERENCES exams (id)
	, FOREIGN KEY (member_id) REFERENCES members (id)
)
;

CREATE TYPE exam AS
(
	  id    INTEGER
	, title VARCHAR(100)
)
;

CREATE VIEW view_exams AS
SELECT id, title FROM exams
;

CREATE FUNCTION exam_create (arg_member_id INTEGER, arg_title VARCHAR(100))
RETURNS SETOF exam AS
$$
DECLARE
	var_exam_id INTEGER;
BEGIN
	INSERT INTO exams (id, title, creator_id, created_at)
	VALUES (DEFAULT, arg_title, arg_member_id, NOW( ))
	RETURNING (SELECT id INTO var_exam_id)
	;
	INSERT INTO exam_owners (exam_id, member_id)
	VALUES (var_exam_id, arg_member_id)
	;
	RETURN QUERY SELECT * FROM view_exams WHERE id = var_exam_id
	;
END
;
$$
LANGUAGE 'plpgsql'
STRICT
;

CREATE TABLE exam_pools 
(
	  exam_id  INTEGER  NOT NULL
	, pool_id  INTEGER  NOT NULL
	, quantity INTEGER NOT NULL
	, PRIMARY KEY (exam_id, pool_id)
	, FOREIGN KEY (exam_id) REFERENCES exams (id)
	, FOREIGN KEY (pool_id) REFERENCES pools (id)
)
;

CREATE VIEW view_exam_pools AS
SELECT exam_pools.exam_id, view_pools.*
FROM view_pools JOIN exam_pools ON view_pools.id = exam_pools.pool_id
;

CREATE FUNCTION exam_update_pool_add (arg_member_id INTEGER, arg_exam_id INTEGER, arg_pool_id INTEGER, arg_quantity INTEGER)
RETURNS SETOF exam AS
$$
DECLARE 
BEGIN
	SELECT *
	FROM exam_owners
	WHERE CONCAT(exam_owners.exam_id, exam_owners.member_id) = CONCAT(arg_exam_id, arg_member_id)
	;
	IF FOUND THEN
		DELETE FROM exam_pools 
		WHERE CONCAT(exam_id, pool_id) = CONCAT(arg_exam_id, arg_pool_id)
		;
		INSERT INTO exam_pools (exam_id, pool_id, quantity)
		VALUES (arg_exam_id, arg_pool_id, arg_quantity)
		;
	END IF 
	;
	RETURN QUERY SELECT * 
	FROM view_exams 
	WHERE id = arg_exam_id
	;
END
; 
$$
LANGUAGE 'plpgsql' 
STRICT
;

CREATE FUNCTION exam_update_pool_remove (arg_member_id INTEGER, arg_exam_id INTEGER, arg_pool_id INTEGER)
RETURNS SETOF exam AS
$$
DECLARE
BEGIN
	SELECT *
	FROM exam_owners
	WHERE CONCAT(exam_owners.exam_id, exam_owners.member_id) = CONCAT(arg_exam_id, arg_member_id)
	;
	IF FOUND THEN
		DELETE FROM exam_pools 
		WHERE CONCAT(exam_id, pool_id) = CONCAT(arg_exam_id, arg_pool_id)
		;
	END IF 
	;
	RETURN QUERY SELECT * 
	FROM view_exams 
	WHERE id = arg_exam_id
	; 
END
;
$$
LANGUAGE 'plpgsql'
STRICT
;

/* Assessment */

CREATE TABLE assessments
(
	  id          BIGSERIAL  NOT NULL PRIMARY KEY
	, exam_id     INTEGER    NOT NULL
	, student_id  INTEGER    NOT NULL
	, taken_at    TIMESTAMP  NOT NULL
	, finished_at TIMESTAMP     NULL
	, FOREIGN KEY (exam_id) REFERENCES exams (id)
	, FOREIGN KEY (student_id) REFERENCES members (id)
)
;

CREATE TYPE assessment AS
(
	  id         BIGINT
	, exam_id    INTEGER
	, student_id INTEGER
)
;

CREATE TABLE assessment_tasks
(
	  assessment_id BIGINT  NOT NULL
	, task_id       INTEGER NOT NULL
	, PRIMARY KEY (assessment_id, task_id)
	, FOREIGN KEY (assessment_id) REFERENCES assessments (id)
	, FOREIGN KEY (task_id) REFERENCES tasks (id)
)
;

CREATE VIEW view_finished_assessments AS
SELECT id, exam_id, student_id FROM assessments 
WHERE finished_at IS NOT NULL
;

CREATE VIEW view_ongoing_assessments AS
SELECT id, exam_id, student_id FROM assessments 
WHERE finished_at IS NULL
;

CREATE VIEW view_assessment_tasks AS
SELECT assessment_tasks.assessment_id, view_tasks.* 
FROM view_tasks JOIN assessment_tasks ON view_tasks.id = assessment_tasks.task_id
;

CREATE FUNCTION assessment_ongoing_create (arg_student_id INTEGER, arg_exam_id INTEGER)
RETURNS SETOF assessment 
AS
$$
DECLARE
	var_assessment_id BIGINT;
BEGIN
	INSERT INTO assessments (id, student_id, exam_id, taken_at)
	VALUES (DEFAULT, arg_student_id, arg_exam_id, NOW( ))
	RETURNING (SELECT id INTO var_assessment_id)
	;
	RETURN QUERY SELECT * FROM view_ongoing_assessments WHERE id = var_assessment_id;
END
;
$$
LANGUAGE 'plpgsql'
STRICT
;

/* Answer */

CREATE TABLE answers 
(
	  id            BIGSERIAL NOT NULL PRIMARY KEY
	, assessment_id BIGINT    NOT NULL 
	, task_id       INTEGER   NOT NULL
	, input         TEXT      NOT NULL
	, provided_at   TIMESTAMP NOT NULL
	, FOREIGN KEY (assessment_id) REFERENCES assessments (id)
	, FOREIGN KEY (task_id) REFERENCES tasks (id)
)
;

CREATE TYPE answer AS
(
	  id            BIGINT 
	, assessment_id BIGINT 
	, task_id       INTEGER 
	, input         TEXT
)
;

CREATE VIEW view_answers AS 
SELECT id, assessment_id, task_id, input FROM answers 
;

CREATE FUNCTION answer_create (arg_assessment_id BIGINT, arg_task_id INTEGER, arg_input TEXT)
RETURNS SETOF answer AS 
$$
DECLARE
	var_answer_id BIGINT;
BEGIN
	INSERT INTO answers (id, assessment_id, task_id, input, provided_at)
	VALUES (DEFAULT, arg_assessment_id, arg_task_id, arg_input, NOW( ))
	RETURNING (SELECT id INTO var_answer_id)
	;
	RETURN QUERY SELECT * FROM view_answers WHERE id = var_answer_id
	;
END
$$
LANGUAGE 'plpgsql' 
STRICT 
;

/* Score */

CREATE TABLE scores 
(
 	  id            BIGSERIAL NOT NULL PRIMARY KEY
	, assessment_id BIGINT    NOT NULL
	, task_id       INTEGER   NOT NULL
	, reward        INTEGER  NOT NULL
	, FOREIGN KEY (assessment_id) REFERENCES assessments (id)
	, UNIQUE (assessment_id, task_id)
)
;

CREATE TYPE score AS
(
	id BIGINT, assessment_id BIGINT, task_id INTEGER, reward INTEGER
)
;

CREATE VIEW view_scores AS 
SELECT id, assessment_id, task_id, reward FROM scores 
;
COMMIT;