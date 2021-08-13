CREATE TABLE IF NOT EXISTS models_images (
    id bigserial not null primary key unique,
    ibm_key text,
    model_id bigint
);

