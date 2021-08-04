CREATE TABLE IF NOT EXISTS commissions (
    id BIGSERIAL primary key not null,
    client_id BIGINT,
    appliance_id BIGINT,
    creation_date DATE,
    problem_description TEXT,
    advice_given BOOLEAN,
    technician_id INT,
    repair_date_id BIGINT,
    technician_report TEXT,
    solution_description TEXT,
    client_visited BOOLEAN,
    commission_status BOOLEAN
);

ALTER TABLE public.commissions
    ADD CONSTRAINT client_id FOREIGN KEY (client_id)
        REFERENCES public.clients (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;

ALTER TABLE public.commissions
    ADD CONSTRAINT appliances_id FOREIGN KEY (appliance_id)
        REFERENCES public.appliances (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;

ALTER TABLE public.commissions
    ADD CONSTRAINT technician_id FOREIGN KEY (technician_id)
        REFERENCES public.technicians (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;

ALTER TABLE public.commissions
    ADD CONSTRAINT available_date FOREIGN KEY (repair_date_id)
        REFERENCES public.technicians_terms (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;

ALTER TABLE technicians_terms
    ADD CONSTRAINT commission_id FOREIGN KEY (commission_id)
        REFERENCES public.commissions (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;



INSERT INTO commissions (client_id, appliance_id, creation_date, problem_description, advice_given, technician_id,
                         repair_date_id, technician_report, client_visited, commission_status) values
('1', 3, null, 'Washing machine not working', false, 2, null, null, false, false),
('2', 2, null, 'Dishwasher not working', false, 2, null, null, false, false),
('3', 1, null, 'Fridge not working', false, 2, null, null, false, false),
('4', 4, null, 'Washing machine not working', false, 3, null, null, false, false),
('5', 5, null, 'Microwave burned', false, 2, null, null, false, false)
;
