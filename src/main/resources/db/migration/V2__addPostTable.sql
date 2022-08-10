CREATE TABLE posts
(
    id                   BIGINT       NOT NULL,
    created_at           datetime     NULL,
    updated_at           datetime     NULL,
    `description`        VARCHAR(255) NULL,
    referenced_recipe_id BIGINT       NULL,
    post_publisher_id    BIGINT       NULL,
    CONSTRAINT pk_posts PRIMARY KEY (id)
);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_POSTPUBLISHER FOREIGN KEY (post_publisher_id) REFERENCES app_users (id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_REFERENCEDRECIPE FOREIGN KEY (referenced_recipe_id) REFERENCES recipes (id);