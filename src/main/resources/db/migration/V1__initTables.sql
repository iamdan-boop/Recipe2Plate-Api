CREATE TABLE app_user_seq
(
    next_val BIGINT NULL
);

INSERT INTO app_user_seq VALUES(1);

CREATE TABLE app_users
(
    id         BIGINT       NOT NULL,
    created_at datetime     NULL,
    updated_at datetime     NULL,
    email      VARCHAR(255) NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    role_id    BIGINT       NULL,
    CONSTRAINT PK_APP_USERS PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id            BIGINT       NOT NULL,
    created_at    datetime     NULL,
    updated_at    datetime     NULL,
    category_name VARCHAR(255) NULL,
    CONSTRAINT PK_CATEGORIES PRIMARY KEY (id)
);

CREATE TABLE category_id_seq
(
    next_val BIGINT NULL
);

INSERT INTO category_id_seq VALUES(1);


CREATE TABLE ingredient_id_seq
(
    next_val BIGINT NULL
);

INSERT INTO ingredient_id_seq VALUES(1);

CREATE TABLE ingredients
(
    id              BIGINT       NOT NULL,
    created_at      datetime     NULL,
    updated_at      datetime     NULL,
    ingredient_name VARCHAR(255) NULL,
    CONSTRAINT PK_INGREDIENTS PRIMARY KEY (id)
);

CREATE TABLE instruction_id_seq
(
    next_val BIGINT NULL
);

INSERT INTO instruction_id_seq VALUES(1);

CREATE TABLE instructions
(
    id          BIGINT       NOT NULL,
    created_at  datetime     NULL,
    updated_at  datetime     NULL,
    image_url   VARCHAR(255) NULL,
    instruction VARCHAR(255) NULL,
    name        VARCHAR(255) NULL,
    recipe_id   BIGINT       NULL,
    CONSTRAINT PK_INSTRUCTIONS PRIMARY KEY (id)
);

CREATE TABLE recipe_id_seq
(
    next_val BIGINT NULL
);

INSERT INTO recipe_id_seq VALUES(1);

CREATE TABLE recipes
(
    id                BIGINT       NOT NULL,
    created_at        datetime     NULL,
    updated_at        datetime     NULL,
    `description`     VARCHAR(255) NULL,
    preview_image_url VARCHAR(255) NULL,
    recipe_name       VARCHAR(255) NULL,
    preview_video_url VARCHAR(255) NULL,
    publisher_id      BIGINT       NULL,
    CONSTRAINT PK_RECIPES PRIMARY KEY (id)
);

CREATE TABLE recipes_categories
(
    recipe_id     BIGINT NOT NULL,
    categories_id BIGINT NOT NULL,
    CONSTRAINT PK_RECIPES_CATEGORIES PRIMARY KEY (recipe_id, categories_id)
);

CREATE TABLE recipes_ingredients
(
    recipe_id      BIGINT NOT NULL,
    ingredients_id BIGINT NOT NULL,
    CONSTRAINT PK_RECIPES_INGREDIENTS PRIMARY KEY (recipe_id, ingredients_id)
);

CREATE TABLE role_id_seq
(
    next_val BIGINT NULL
);

INSERT INTO role_id_seq VALUES(1);

CREATE TABLE roles
(
    id         BIGINT       NOT NULL,
    created_at datetime     NULL,
    updated_at datetime     NULL,
    role_name  VARCHAR(255) NULL,
    CONSTRAINT PK_ROLES PRIMARY KEY (id)
);

INSERT INTO roles VALUES (1, NOW(), NOW(), 'ROLE_ADMIN');
INSERT INTO roles VALUES (2, NOW(), NOW(), 'ROLE_USER');

CREATE TABLE verification_id_seq
(
    next_val BIGINT NULL
);

INSERT INTO verification_id_seq VALUES (1);

CREATE TABLE verification_otps
(
    id          BIGINT   NOT NULL,
    created_at  datetime NULL,
    updated_at  datetime NULL,
    expires_at  datetime NULL,
    otp_code    INT      NULL,
    app_user_id BIGINT   NULL,
    CONSTRAINT PK_VERIFICATION_OTPS PRIMARY KEY (id)
);

CREATE INDEX FK5nou8vg8kk82e874c4e4q0bjj ON instructions (recipe_id);

CREATE INDEX FK7sipqsfa0da9aj10gga1iqnk1 ON app_users (role_id);

CREATE INDEX FKb6kr6qj8bgfhl2i3vqud4pb3h ON recipes (publisher_id);

CREATE INDEX FKdk6mq08g58fbaiowwusou268u ON verification_otps (app_user_id);

CREATE INDEX FKjc727h9gok1rmbkaid6bl9ig2 ON recipes_categories (categories_id);

CREATE INDEX FKlvjx0n917c1o5h1f4uyswgpx7 ON recipes_ingredients (ingredients_id);


ALTER TABLE instructions
    ADD CONSTRAINT FK5nou8vg8kk82e874c4e4q0bjj FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE app_users
    ADD CONSTRAINT FK7sipqsfa0da9aj10gga1iqnk1 FOREIGN KEY (role_id) REFERENCES roles (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE recipes
    ADD CONSTRAINT FKb6kr6qj8bgfhl2i3vqud4pb3h FOREIGN KEY (publisher_id) REFERENCES app_users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE verification_otps
    ADD CONSTRAINT FKdk6mq08g58fbaiowwusou268u FOREIGN KEY (app_user_id) REFERENCES app_users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE recipes_categories
    ADD CONSTRAINT FKjc727h9gok1rmbkaid6bl9ig2 FOREIGN KEY (categories_id) REFERENCES categories (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE recipes_ingredients
    ADD CONSTRAINT FKk6ck14h7wqd90hbryml2g6fqk FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE recipes_ingredients
    ADD CONSTRAINT FKlvjx0n917c1o5h1f4uyswgpx7 FOREIGN KEY (ingredients_id) REFERENCES ingredients (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE recipes_categories
    ADD CONSTRAINT FKt07dq3i5lppow9ralidp0bfh1 FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON UPDATE RESTRICT ON DELETE RESTRICT;