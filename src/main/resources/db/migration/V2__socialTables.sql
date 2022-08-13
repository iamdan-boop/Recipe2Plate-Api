
CREATE TABLE post_id_seq(
    next_val BIGINT NULL
);

INSERT INTO post_id_seq VALUES(1);

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


CREATE TABLE comment_id_seq(
    next_val BIGINT NULL
);

INSERT INTO comment_id_seq VALUES(1);

CREATE TABLE comments
(
    id            BIGINT       NOT NULL,
    created_at    datetime     NULL,
    updated_at    datetime     NULL,
    `comment`     VARCHAR(255) NULL,
    post_id       BIGINT       NULL,
    comment_by_id BIGINT       NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);


ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_POSTPUBLISHER FOREIGN KEY (post_publisher_id) REFERENCES app_users (id);

ALTER TABLE posts
    ADD CONSTRAINT FK_POSTS_ON_REFERENCEDRECIPE FOREIGN KEY (referenced_recipe_id) REFERENCES recipes (id);


ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_COMMENTBY FOREIGN KEY (comment_by_id) REFERENCES app_users (id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_POST FOREIGN KEY (post_id) REFERENCES posts (id);
