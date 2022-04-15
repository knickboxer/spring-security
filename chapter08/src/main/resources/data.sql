CREATE TABLE IF NOT EXISTS user (
    id bigint  auto_increment,
    username VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    enabled BOOLEAN,
    PRIMARY KEY (id)
);
CREATE TABLE  IF NOT EXISTS otp (
    id bigint  auto_increment,
    username VARCHAR(128) NOT NULL,
    otp VARCHAR(128) NOT NULL,
    issued TIMESTAMP,
    PRIMARY KEY (id)
);


insert into user (id, username, password, enabled) values (1, 'john', '12345', 1);
