CREATE TABLE IF NOT EXISTS clients
(
    id           bigserial primary key not null,
    name         varchar(100),
    last_name    varchar(100),
    nip          character varying(12),
    regon        character varying(9),
    type         int,
    street       varchar(100),
    building     varchar(10),
    apartment    varchar(10),
    zip_code     varchar(7),
    city         varchar(100),
    phone_number varchar(100),
    email        varchar(50),
    description  text

);

insert into clients(name, last_name, nip, regon, type, street, building, apartment, zip_code, city, phone_number, email)
VALUES ('Aneta', 'Dąbrowska', '1001101012', '102020208', '0', 'Kołobrzeska', '54', '65', '12-143', 'Gdańsk',
        '758 344 433', 'ret@wp.pl'),
       ('Paweł', 'Dąbrowski', '1001101015', '102020201', '0', 'Grunwaldzka', '54', '65', '12-143', 'Gdańsk',
        '755 344 433', 'example@com.pl'),
       ('Artur', 'Kowalski', '1001101014', '102020202', '0', 'Mickiewicza', '54', '65', '12-143', 'Gdańsk',
        '754 344 433', 't23@wp.pl'),
       ('Aleksander', 'Dąbrowski', '1001101013', '102020203', '0', 'Podleśna', '54', '65', '12-143', 'Elbląg',
        '358 344 433', 'pocztatest@wp.pl'),
       ('Piotr', 'Dębowski', '1001101017', '102020204', '0', 'Kanta', '54', '65', '12-143', 'Lublin', '458 344 433',
        'test2@wp.pl'),
       ('Firma Handlowo Usługowa', '', '5832234130', '', '1', 'ul. Orłowicza', '51 C', '', '80-537', 'Olsztyn',
        '934 213 231', 'ftew@gmail.com')
;

ALTER TABLE clients
    ADD CONSTRAINT phone UNIQUE (phone_number);