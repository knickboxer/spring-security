CREATE TABLE IF NOT EXISTS users (
    id bigint  auto_increment,
    username VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    enabled BOOLEAN,
    PRIMARY KEY (id)
);
CREATE TABLE  IF NOT EXISTS authorities (
    id bigint  auto_increment,
    username VARCHAR(128) NOT NULL,
    authority VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);


insert into users (id, username, password, enabled) values (1, 'john', '$2a$10$38JiOp7JCL0BJ742z222fuCFRIfKNKwMdsqkB4tH1rPoUjfiHdhgm', 1);
insert into authorities (id, username, authority) values (1, 'john', 'read');
insert into authorities (id, username, authority) values (2, 'john', 'write');
