INSERT INTO category (name) values ('test1');
INSERT INTO category (name) values ('test2');

INSERT INTO goods (name, description, price, category_id) values ('test1', 'test-test1', 123, 1);
INSERT INTO goods (name, description, price, category_id) values ('test2', 'test-test2', 234, 2);

INSERT INTO item_position (row, place, amount, goods_id) values (1, 2, 3, 1);
INSERT INTO item_position (row, place, amount, goods_id) values (2, 3, 4, 2);
