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

Insert into task_types (screen_title) VALUES ('ChoiceMultiple'), ('ChoiceSingle'), ('Guess'), ('WrittenCommunication')
;

CREATE TABLE tasks 
(
	  id          SERIAL    NOT NULL PRIMARY KEY
	, type_id     SMALLINT  NOT NULL
	, creator_id  INTEGER   NOT NULL 
	, description TEXT      NOT NULL
	, created_at  TIMESTAMP NOT NULL
	, FOREIGN KEY (type_id) REFERENCES task_types (id)
	    ON DELETE RESTRICT
	    ON UPDATE RESTRICT
	, FOREIGN KEY (creator_id) REFERENCES members (id)
	    ON DELETE RESTRICT
	    ON UPDATE RESTRICT
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
RETURNS task AS
$$
INSERT INTO tasks (id, type_id, description, creator_id, created_at)
VALUES (DEFAULT, arg_type_id, arg_description, arg_member_id, NOW( ))
RETURNING id, type_id, description
;
$$
LANGUAGE SQL
STRICT
;

CREATE FUNCTION task_update (arg_member_id INTEGER, arg_task_id INTEGER, arg_description TEXT)
RETURNS task AS
$$
UPDATE tasks 
SET description = arg_description
WHERE id = arg_task_id
;
SELECT * FROM view_tasks
where id = arg_task_id
;
$$
LANGUAGE SQL
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

CREATE FUNCTION option_create (arg_task_id INTEGER, arg_message TEXT, arg_reward INTEGER)
RETURNS option 
AS
$$
INSERT INTO options (task_id, id, message, reward)
VALUES(arg_task_id, DEFAULT, arg_message, arg_reward) 
RETURNING task_id, id, message, reward
;
$$
LANGUAGE SQL
STRICT
;

CREATE FUNCTION option_update_message (arg_id BIGINT, arg_message TEXT)
RETURNS option 
AS
$$
UPDATE options 
SET message = arg_message 
WHERE id = arg_id
;
SELECT * FROM view_options 
WHERE id = arg_id
;
$$
LANGUAGE SQL
STRICT
;

CREATE FUNCTION option_update_reward (arg_id BIGINT, arg_reward INTEGER)
RETURNS option 
AS
$$
UPDATE options 
SET reward = arg_reward 
WHERE id = arg_id
;
SELECT * FROM view_options 
WHERE id = arg_id
;
$$
LANGUAGE SQL
STRICT
;

/* Pool */

CREATE TABLE pools 
(
	  id         SERIAL       NOT NULL PRIMARY KEY
	, title      VARCHAR(100) NOT NULL UNIQUE
	, creator_id INTEGER      NOT NULL
	, created_at TIMESTAMP    NOT NULL
	, FOREIGN KEY (creator_id) REFERENCES members (id)
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
ORDER BY RANDOM()
;

CREATE FUNCTION pool_create (arg_member_id INTEGER, arg_title VARCHAR(100))
RETURNS pool AS 
$$
INSERT INTO pools (id, title, creator_id, created_at)
VALUES (DEFAULT, arg_title, arg_member_id, NOW( ))
RETURNING id, title 
;
$$
LANGUAGE SQL 
STRICT 
;

CREATE FUNCTION pool_update (arg_pool_id INTEGER, arg_title VARCHAR(100))
RETURNS pool 
AS
$$
UPDATE pools 
SET title = arg_title 
WHERE id = arg_pool_id
;
SELECT * FROM view_pools 
WHERE id = arg_pool_id
;
$$
LANGUAGE SQL 
STRICT
;

CREATE FUNCTION pool_update_task_add (arg_pool_id INTEGER, arg_task_id INTEGER)
RETURNS pool 
AS
$$
INSERT INTO pool_tasks (pool_id, task_id)
VALUES (arg_pool_id, arg_task_id)
;
SELECT * FROM view_pools 
WHERE id = arg_pool_id
;
$$
LANGUAGE SQL 
STRICT
;

CREATE FUNCTION pool_update_task_remove (arg_pool_id INTEGER, arg_task_id INTEGER)
RETURNS pool 
AS
$$
DELETE FROM pool_tasks
WHERE CONCAT(pool_id, task_id) = CONCAT(arg_pool_id, arg_task_id)
;
SELECT * FROM view_pools 
WHERE id = arg_pool_id
;
$$
LANGUAGE SQL 
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
RETURNS exam AS
$$
INSERT INTO exams (id, title, creator_id, created_at)
VALUES (DEFAULT, arg_title, arg_member_id, NOW( ))
RETURNING id, title
;
$$
LANGUAGE SQL
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

CREATE FUNCTION exam_update_pool_add (arg_exam_id INTEGER, arg_pool_id INTEGER, arg_quantity INTEGER)
RETURNS exam AS
$$
DELETE FROM exam_pools 
WHERE CONCAT(exam_id, pool_id) = CONCAT(arg_exam_id, arg_pool_id)
;
INSERT INTO exam_pools (exam_id, pool_id, quantity)
VALUES (arg_exam_id, arg_pool_id, arg_quantity)
;
SELECT * 
FROM view_exams 
WHERE id = arg_exam_id
; 
$$
LANGUAGE SQL 
STRICT
;

CREATE FUNCTION exam_update_pool_remove (arg_exam_id INTEGER, arg_pool_id INTEGER)
RETURNS exam AS
$$
DELETE FROM exam_pools 
WHERE CONCAT(exam_id, pool_id) = CONCAT(arg_exam_id, arg_pool_id)
;
SELECT * 
FROM view_exams 
WHERE id = arg_exam_id
; 
$$
LANGUAGE SQL 
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
RETURNS assessment 
AS
$$
INSERT INTO assessments (id, student_id, exam_id, taken_at)
VALUES (DEFAULT, arg_student_id, arg_exam_id, NOW( ))
RETURNING id, student_id, exam_id
;
$$
LANGUAGE SQL
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
RETURNS answer AS 
$$
INSERT INTO answers (id, assessment_id, task_id, input, provided_at)
VALUES (DEFAULT, arg_assessment_id, arg_task_id, arg_input, NOW( ))
RETURNING id, assessment_id, task_id, input
;
$$
LANGUAGE SQL 
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