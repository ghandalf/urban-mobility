// Connect as system/oracle

// create tablespace
CREATE TABLESPACE tutorial_perm_01
  DATAFILE 'tutorial_perm_01.dat'
    SIZE 20M
    REUSE
    AUTOEXTEND ON NEXT 10M MAXSIZE unlimited;
    
// TABLESPACE TUTORIAL_PERM_01 created.

// Create schema user/pwd for our database
create user tutori identified by tutori
 DEFAULT TABLESPACE tutorial_perm_01
  QUOTA unlimited on tutorial_perm_01;
  
// User TUTORI created.

// Grant access
GRANT CREATE SESSION TO tutori WITH ADMIN OPTION; // Grant succeeded.

GRANT UNLIMITED TABLESPACE TO tutori;
GRANT CREATE TABLE to tutori;
GRANT CREATE VIEW to tutori;
GRANT CREATE CLUSTER to tutori;
GRANT CREATE SEQUENCE to tutori;
GRANT CREATE PROCEDURE to tutori;
GRANT CREATE TRIGGER to tutori;
GRANT CREATE TYPE to tutori;
GRANT CREATE OPERATOR to tutori;
GRANT CREATE INDEXTYPE to tutori;
GRANT dba to tutori;
GRANT CREATE database LINK to tutori;
GRANT imp_full_database to tutori;

GRANT CONNECT TO tutori;
GRANT CONNECT, RESOURCE, DBA TO tutori;
