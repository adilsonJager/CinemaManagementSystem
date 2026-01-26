CREATE TABLE movie (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR (100) NOT NULL,
    time INT NOT NULL
);

CREATE TABLE room (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(1) NOT NULL UNIQUE,
    capacity INT NOT NULL
);

CREATE TABLE seat (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    room_id INT NOT NULL,
    column INT NOT NULL,
    row INT NOT NULL,

    FOREIGN KEY (room_id) REFERENCES room(id),
    UNIQUE (room_id, row, column)
);

CREATE TABLE session (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    movie_id INT NOT NULL,
    room_id INT NOT NULL,
    date_time TIMESTAMP NOT NULL,

    FOREIGN KEY (movie_id) REFERENCES movie(id),
    FOREIGN KEY (room_id) REFERENCES room(id)
);

CREATE TABLE users (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULl

);

CREATE TABLE reservation(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    session_id INT NOT NULL,
    seat_id INT NOT NULL,
    status VARCHAR(20) DEFAULT 'RESERVADO',

    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (session_id) REFERENCES session(id),
    FOREIGN KEY (seat_id) REFERENCES seat(id),

    UNIQUE (session_id, seat_id)
    );