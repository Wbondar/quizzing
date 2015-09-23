SELECT * FROM member_create('wbond', 'wbond');
SELECT * FROM pool_create (1, 'wbond');
SELECT * FROM exam_create (1, 'wbond');
SELECT * FROM exam_update_pool_add (1, 1, 10000);