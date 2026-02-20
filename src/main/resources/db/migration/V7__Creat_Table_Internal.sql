CREATE TABLE internal (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login varchar(250) UNIQUE NOT NULL,
    password varchar(250) NOT NULL,
    role varchar(250) not null
);