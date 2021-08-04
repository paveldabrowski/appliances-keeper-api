CREATE TABLE IF NOT EXISTS models_pictures (
    id bigserial not null primary key unique,
    url text,
    model_id bigint
);

