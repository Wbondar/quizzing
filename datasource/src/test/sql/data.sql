START TRANSACTION
;
CREATE FUNCTION test ( ) RETURNS void AS
$$
DECLARE
	var_member_id INTEGER;
	var_exam_id INTEGER;
	var_pool_id INTEGER;
	var_task_id INTEGER;
	var_task_type_selection_single_id SMALLINT := 2;
BEGIN
	SELECT id FROM member_create('test', 'test') INTO var_member_id;
	SELECT id FROM pool_create (var_member_id, 'test') INTO var_pool_id;
	SELECT id FROM exam_create (var_member_id, 'test') INTO var_exam_id;
	PERFORM * FROM exam_update_pool_add (var_member_id, var_exam_id, var_pool_id, 10000);
	SELECT id FROM task_create (var_member_id, var_task_type_selection_single_id, '2 + 2 = ?') INTO var_task_id;
	PERFORM * FROM option_create (var_member_id, var_task_id, '1', 0);
	PERFORM * FROM option_create (var_member_id, var_task_id, '2', 0);
	PERFORM * FROM option_create (var_member_id, var_task_id, '3', 0);
	PERFORM * FROM option_create (var_member_id, var_task_id, '4', 1);
	PERFORM * FROM pool_update_task_add (var_member_id, var_pool_id, var_task_id);
END
;
$$
LANGUAGE 'plpgsql'
;
SELECT * FROM test ( );
DROP FUNCTION test ( );
COMMIT
;