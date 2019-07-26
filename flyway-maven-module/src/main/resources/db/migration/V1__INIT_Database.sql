CREATE TABLE user
(
  id           int(11)      NOT NULL AUTO_INCREMENT,
  first        varchar(100) NOT NULL,
  last         varchar(100) NOT NULL,
  dateofbirth  DATE DEFAULT null,
  placeofbirth varchar(100) not null,
  PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into user (first, last, dateofbirth, placeofbirth)
values ('Dursun', 'KOC', STR_TO_DATE('02/10/1982', '%m/%d/%Y'), 'Erzincan');
insert into user (first, last, dateofbirth, placeofbirth)
values ('Durseeun', 'KeeOC', STR_TO_DATE('05/10/1982', '%m/%d/%Y'), 'Erzeeincan');

create table PERSON
(
  ID   int          not null,
  NAME varchar(100) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into PERSON (ID, NAME) values (1, 'Axel');
insert into PERSON (ID, NAME) values (2, 'Mr. Foo');
insert into PERSON (ID, NAME) values (3, 'Ms. Bar');

