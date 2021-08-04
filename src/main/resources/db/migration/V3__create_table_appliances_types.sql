CREATE TABLE IF NOT EXISTS appliances_types
(
    id   SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50) UNIQUE
);

INSERT INTO appliances_types (name)
values ('Washing machine'),
       ('Dishwasher'),
       ('Cooker hood'),
       ('Microwave'),
       ('Freezer'),
       ('Fridge'),
       ('Oven'),
       ('Induction hob')
;


