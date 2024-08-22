create table product
(
    product_id   bigint generated by default as identity,
    product_name varchar(255) unique,
    product_price decimal(5,2),
    product_info varchar(255),
    product_quantity int,
    movedAt datetime,
    movement_type enam(INCOME, OUTCOME)

    primary key (product_id)
);