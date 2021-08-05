CREATE TABLE IF NOT EXISTS models_pictures (
    id bigserial not null primary key unique,
    ibm_key text,
    model_id bigint
);

