
CREATE INDEX idx_reservation_pending ON reservation (created_at) WHERE status = 'PENDING'