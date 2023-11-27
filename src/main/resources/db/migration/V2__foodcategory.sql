create table food_categories
(
    id        bigint auto_increment
        primary key,
    food_type enum ('BUNGEOPPANG', 'DALGONA', 'EGGBREAD', 'FIRED', 'GIMBAP', 'HOTTEOK', 'KKOCHI', 'KUNGOGUMA', 'KUNOKSUSU', 'ODENG', 'SUNDAE', 'TACOYAKI', 'TANGHURU', 'TOAST', 'TTAKONGPPANG', 'TTEOKBOKKI', 'WAFFLE') not null,
    store_id  bigint                                                                                                                                                                                                    null,
    constraint fk_food_categories_store_id
        foreign key (store_id) references stores (id)
);