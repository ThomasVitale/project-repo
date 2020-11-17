CREATE SEQUENCE entity_sequence INCREMENT 50;

CREATE TABLE orders (
      id                  bigint NOT NULL,
      created_date        bigint NOT NULL,
      last_modified_date  bigint NOT NULL,
      version             integer NOT NULL,
      book_author         varchar(255),
      book_isbn           varchar(255) NOT NULL,
      book_price          float8,
      book_title          varchar(255),
      status              varchar(255) NOT NULL,
      PRIMARY KEY (id)
);