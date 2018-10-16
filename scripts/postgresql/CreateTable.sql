CREATE TABLE books (
    id bigint not null primary key,
    title text,
    description text,
    shoppingId bigint
);


CREATE TABLE Shoppings (
    id bigint not null,
    bookId bigint not null
);