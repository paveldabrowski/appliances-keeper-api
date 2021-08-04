CREATE TABLE IF NOT EXISTS appliances (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    serial_number VARCHAR(50) NOT NULL UNIQUE,
    model_id BIGINT,
    brand_id INT,
    client_id BIGINT
);

ALTER TABLE appliances
    ADD CONSTRAINT appliance_brand FOREIGN KEY (brand_id)
        REFERENCES public.brands (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;

ALTER TABLE appliances
    ADD CONSTRAINT appliance_model FOREIGN KEY (model_id)
        REFERENCES models (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;

ALTER TABLE appliances
    ADD CONSTRAINT appliance_owner FOREIGN KEY (client_id)
        REFERENCES clients (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;

insert into appliances(serial_number, model_id, brand_id, client_id)
values
       ('21312-DC-213', 1, 1, 3),
       ('1234-MC123D', 2, 2, 2),
       ('GT-MC123D', 3, 3, 1),
       ('BK234-23-213', 4, 4, 4),
       ('MBA-2321GF-213', 5, 5, 5),
       ('TU14MX-SB34-554', 6, 6, 4),
       ('KLP-S1412-4234', 7, 7, 5),
       ('MAT-GFD-S124', 8, 8, 1),
       ('ZA324-DX-32432', 9, 9, 6),
       ('ZFS-D342-32432FD', 10, 10, 2)
;
