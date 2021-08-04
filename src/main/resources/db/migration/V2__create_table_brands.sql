CREATE TABLE IF NOT EXISTS brands (
    id SERIAL PRIMARY KEY NOT NULL,
    name varchar(50)
);

INSERT INTO brands(name) values
('Samsung'),
('LG'),
('Whirlpool'),
('Hotpoint-Ariston'),
('Bosh'),
('Amica'),
('Akpo'),
('Philips'),
('Electrolux'),
('Beko'),
('Indesit')
;