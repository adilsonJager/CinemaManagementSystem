CREATE TABLE reservation_item (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    reservation_id INT NOT NULL REFERENCES reservation(id) ON DELETE CASCADE ,
    seat_id INT NOT NULL REFERENCES seat(id),
    showtime_id INT NOT NULL REFERENCES showtime(id),

    UNIQUE (seat_id, showtime_id)
);


ALTER TABLE reservation DROP CONSTRAINT IF EXISTS reservation_showtime_id_seat_id_key;
ALTER TABLE reservation DROP CONSTRAINT IF EXISTS reservation_seat_id_fkey;
ALTER TABLE reservation DROP COLUMN seat_id;