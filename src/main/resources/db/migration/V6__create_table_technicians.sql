CREATE TABLE IF NOT EXISTS technicians (
    id SERIAL primary key not null,
    name VARCHAR(100),
    last_name VARCHAR(100),
    phone_number VARCHAR(20)
);

INSERT INTO technicians (name, last_name, phone_number) VALUES
('Marcel', 'Sumirsky', '213 435 345'),
('Clark', 'Kent', '452 543 777'),
('Bruce', 'Wayne', '713 233 555'),
('Peter', 'Parker', '213 135 828'),
('John', 'Snow', '513 322 454'),
('John', 'Wick', '213 676 899'),
('Mick', 'St. John', '813 376 424')
;

