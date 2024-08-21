create table movements.stock_movement (
    id           bigserial constraint pk_stock_movement_id primary key,
    product_id   bigint not null,
    product_name varchar(255) not null,
    quantity     int not null,
    price        decimal(19, 4) not null,
    moved_at     timestamp(3) without time zone not null,
    type         varchar(7) not null
);

create index idx_stock_movement_moved_at on movements.stock_movement(moved_at);

