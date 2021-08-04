CREATE TABLE IF NOT EXISTS models (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(40),
  brand_id INT,
  appliance_type_id INT,
  description TEXT
);

ALTER TABLE models
    ADD CONSTRAINT brand_id FOREIGN KEY (brand_id)
        REFERENCES public.brands (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;

ALTER TABLE models
    ADD CONSTRAINT appliance_type FOREIGN KEY (appliance_type_id)
        REFERENCES appliances_types (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT;

INSERT INTO models(name, brand_id, appliance_type_id) values
('MX-400', 1, 4),
('SOLARIS', 2, 3),
('T-1000', 3, 2),
('T-800', 4, 4),
('BT-213-0SAAS', 5, 1),
('BPS-213', 6, 2 ),
('GHT-213-43', 7, 3),
('PRETOR', 8, 6),
('PRIME', 9, 7),
('MT0-14', 10, 2),
('FX-5200', 3, 4),
('GTX1060', 4, 5),
('CHAMPION', 6, 6)
;
