CREATE USER projecttwo IDENTIFIED BY p4ssw0rd;

GRANT CONNECT, RESOURCE TO projecttwo;
GRANT DBA TO projecttwo WITH ADMIN OPTION;

CREATE OR REPLACE FUNCTION GET_HASH(USERNAME VARCHAR2, PASSWORD VARCHAR2) RETURN VARCHAR2
IS
EXTRA VARCHAR2(10) := 'SALT';
BEGIN
  RETURN TO_CHAR(DBMS_OBFUSCATION_TOOLKIT.MD5(
  INPUT => UTL_I18N.STRING_TO_RAW(DATA => USERNAME || PASSWORD || EXTRA)));
END;
/

CREATE OR REPLACE TRIGGER add_user
BEFORE INSERT ON users
for each row
begin
    select get_hash(:new.username,:new.password) into :new.password from dual;
end;
/

CREATE OR REPLACE TRIGGER update_user
BEFORE update ON users
for each row
declare
pragma autonomous_transaction;
temp varchar(500);
begin
select password into temp from users where username = :new.username;
if :new.password!= temp then
    select get_hash(:new.username,:new.password) into :new.password from dual;
end if;
end;
/


create or replace trigger add_post
before insert on post
for each row
begin
    select LOCALTIMESTAMP into :new.timestamp from dual;
end;
/

commit;

rollback;

select * from post;
update post set timestamp=localtimestamp;

update post set user_fk=2 where postid=61;

select* from users;

select * from users_post;

select * from post_users;

update users set picture = 'https://archive-media-1.nyafuu.org/bant/thumb/1520/87/1520878324175s.jpg';

update users set password = 'pwd';

ALTER TABLE post add timestamp TIMESTAMP;

ALTER TABLE post_users ADD CONSTRAINT like_table UNIQUE (post_postid,likes_userid);

delete from post_users;