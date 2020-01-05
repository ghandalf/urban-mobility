CREATE TABLE Agencies (
    id uuid not null primary key,
    agency_id VARCHAR(128),
    name VARCHAR(128) NOT NULL,
    url VARCHAR(256) NOT NULL,
    timezone VARCHAR(4) NOT NULL,
    language VARCHAR(8),
    phone VARCHAR(32),
    fareurl VARCHAR(256),
    email VARCHAR(128)
);
