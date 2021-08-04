CREATE TABLE IF NOT EXISTS technicians_calendar (
                                                    id BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
                                                    date date,
                                                    technician_id INT
);

ALTER TABLE technicians_calendar
    ADD CONSTRAINT technician_id FOREIGN KEY (technician_id)
        REFERENCES public.technicians (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;
