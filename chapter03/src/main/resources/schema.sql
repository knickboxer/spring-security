CREATE TABLE USERS (
                       id bigint  auto_increment,
                       username VARCHAR(128) NOT NULL,
                       password VARCHAR(128) NOT NULL,
                       enabled BOOLEAN,
                       PRIMARY KEY (id)
);
CREATE TABLE AUTHORITIES (
                             id bigint  auto_increment,
                             username VARCHAR(128) NOT NULL,
                             authority VARCHAR(128) NOT NULL,
                             PRIMARY KEY (id)
);
