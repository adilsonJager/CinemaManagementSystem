CREATE TABLE movie (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR (150) NOT NULL,
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
    seat_column INT NOT NULL,
    seat_row INT NOT NULL,

    FOREIGN KEY (room_id) REFERENCES room(id),
    UNIQUE (room_id, seat_row, seat_column)
);

CREATE TABLE showtime (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    movie_id INT NOT NULL,
    room_id INT NOT NULL,
    date_time TIMESTAMP NOT NULL,

    FOREIGN KEY (movie_id) REFERENCES movie(id),
    FOREIGN KEY (room_id) REFERENCES room(id)
);

CREATE TABLE users (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(150),
    email VARCHAR(200)
);

CREATE TABLE reservation(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    users_id INT,
    showtime_id INT NOT NULL,
    seat_id INT NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',

    FOREIGN KEY (users_id) REFERENCES users(id),
    FOREIGN KEY (showtime_id) REFERENCES showtime(id),
    FOREIGN KEY (seat_id) REFERENCES seat(id),

    UNIQUE (showtime_id, seat_id)
    );