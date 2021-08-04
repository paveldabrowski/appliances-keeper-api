CREATE TABLE IF NOT EXISTS technicians_terms (
    id BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
    hour INT4,
    date DATE,
    technician_working_day_id BIGINT,
    commission_id BIGINT,
    is_available BOOLEAN DEFAULT TRUE
);

ALTER TABLE technicians_terms
    ADD CONSTRAINT technician_working_day_id FOREIGN KEY (technician_working_day_id)
        REFERENCES public.technicians_calendar (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;


