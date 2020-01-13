select * from dba_users where account_status = 'OPEN';

create user rmidev identified by  rmidev;

update dba_users set dba_users.DEFAULT_TABLESPACE="SYSTEM" where username="rmidev";
