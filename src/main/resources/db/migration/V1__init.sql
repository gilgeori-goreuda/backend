create database if not exists gilgeorigoreuda;

drop table if exists flyway_schema_history;

create table flyway_schema_history
(
    installed_rank int                                 not null
        primary key,
    version        varchar(50)                         null,
    description    varchar(200)                        not null,
    type           varchar(20)                         not null,
    script         varchar(1000)                       not null,
    checksum       int                                 null,
    installed_by   varchar(100)                        not null,
    installed_on   timestamp default CURRENT_TIMESTAMP not null,
    execution_time int                                 not null,
    success        tinyint(1)                          not null
);

create index flyway_schema_history_s_idx
    on flyway_schema_history (success);

create table hot_places
(
    id          bigint       not null
        primary key,
    created_at  datetime(6)  not null,
    modified_at datetime(6)  not null,
    place       varchar(100) not null
);

create table keywords
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  not null,
    modified_at datetime(6)  not null,
    keyword     varchar(100) not null
);

create table member_tokens
(
    member_id     bigint       not null
        primary key,
    access_token  varchar(255) not null,
    refresh_token varchar(255) not null
);

create table members
(
    id                bigint auto_increment
        primary key,
    created_at        datetime(6)  not null,
    modified_at       datetime(6)  not null,
    nickname          varchar(30)  not null,
    profile_image_url varchar(512) null,
    social_id         varchar(30)  not null,
    constraint UK_e6u9u9ypoc7oldnpxdjwcdx3
        unique (nickname),
    constraint idx_member_nickname
        unique (nickname),
    constraint idx_member_social_id
        unique (social_id)
);

create table member_active_info
(
    id                     bigint auto_increment
        primary key,
    exp                    int                                         not null,
    member_level           enum ('BEGINNER', 'EXPERT', 'INTERMEDIATE') null,
    total_visit_count      int                                         not null,
    total_walking_distance int                                         not null,
    member_id              bigint                                      null,
    constraint fk_member_active_info_member_id
        foreign key (member_id) references members (id)
);

create table stores
(
    id                            bigint auto_increment
        primary key,
    created_at                    datetime(6)                               not null,
    modified_at                   datetime(6)                               not null,
    average_rating                double                                    not null,
    business_date                 varchar(20)                               not null,
    close_time                    time(6)                                   null,
    detail_location               varchar(300)                              null,
    image_url                     varchar(512)                              null,
    is_blocked                    bit                                       not null,
    last_modified_member_nickname varchar(10)                               null,
    lat                           decimal(22, 16)                           not null,
    lng                           decimal(22, 16)                           not null,
    name                          varchar(100)                              not null,
    open_time                     time(6)                                   null,
    purchase_type                 enum ('ACCOUNT_TRANSFER', 'CARD', 'CASH') not null,
    store_type                    enum ('FOOD_STALL', 'FOOD_TRUCK')         not null,
    large_address                 varchar(100)                              not null,
    medium_address                varchar(100)                              not null,
    small_address                 varchar(100)                              not null,
    total_report_count            int                                       not null,
    total_visit_count             int                                       null,
    member_id                     bigint                                    null,
    constraint fk_stores_member_id
        foreign key (member_id) references members (id)
);

create table food_categories
(
    id        bigint auto_increment
        primary key,
    food_type enum ('BUNGEOPPANG', 'DALGONA', 'EGGBREAD', 'FIRED', 'GIMBAP', 'HOTTEOK', 'KKOCHI', 'KUNGOGUMA', 'KUNOKSUSU', 'ODENG', 'SUNDAE', 'TACOYAKI', 'TANGHURU', 'TOAST', 'TTAKONGPPANG', 'TTEOKBOKKI', 'WAFFLE') not null,
    store_id  bigint                                                                                                                                                                                                    null,
    constraint fk_food_categories_store_id
        foreign key (store_id) references stores (id)
);

create table reviews
(
    id            bigint auto_increment
        primary key,
    created_at    datetime(6)  not null,
    modified_at   datetime(6)  not null,
    content       varchar(300) null,
    hate_count    int          null,
    like_count    int          null,
    review_rating int          null,
    member_id     bigint       null,
    store_id      bigint       null,
    constraint fk_review_member_id
        foreign key (member_id) references members (id),
    constraint fk_review_store_id
        foreign key (store_id) references stores (id)
);

create table review_comments
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  not null,
    modified_at datetime(6)  not null,
    content     varchar(200) null,
    member_id   bigint       null,
    review_id   bigint       null,
    constraint fk_review_comment_member_id
        foreign key (member_id) references members (id),
    constraint fk_review_comment_review_id
        foreign key (review_id) references reviews (id)
);

create table review_images
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  not null,
    modified_at datetime(6)  not null,
    image_url   varchar(512) null,
    review_id   bigint       null,
    constraint fk_review_image_review_id
        foreign key (review_id) references reviews (id)
);

create table review_preferences
(
    id              bigint auto_increment
        primary key,
    created_at      datetime(6)                   not null,
    modified_at     datetime(6)                   not null,
    preference_type enum ('HATE', 'LIKE', 'NONE') not null,
    member_id       bigint                        null,
    review_id       bigint                        null,
    constraint fk_review_preferences_member_id
        foreign key (member_id) references members (id),
    constraint fk_review_preferences_review_id
        foreign key (review_id) references reviews (id)
);

create table store_preferences
(
    id              bigint auto_increment
        primary key,
    created_at      datetime(6)                                 not null,
    modified_at     datetime(6)                                 not null,
    preference_type enum ('NONE', 'NOT_PREFERRED', 'PREFERRED') not null,
    member_id       bigint                                      null,
    store_id        bigint                                      null,
    constraint fk_store_preferences_member_id
        foreign key (member_id) references members (id),
    constraint fk_store_preferences_store_id
        foreign key (store_id) references stores (id)
);

create table store_report_histories
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  not null,
    modified_at datetime(6)  not null,
    content     varchar(500) not null,
    member_id   bigint       null,
    store_id    bigint       null,
    constraint fk_store_report_histories_member_id
        foreign key (member_id) references members (id),
    constraint fk_store_report_histories_store_id
        foreign key (store_id) references stores (id)
);

create table store_visit_records
(
    id               bigint auto_increment
        primary key,
    created_at       datetime(6) not null,
    modified_at      datetime(6) not null,
    is_visited       bit         not null,
    walking_distance int         not null,
    member_id        bigint      null,
    store_id         bigint      null,
    constraint fk_store_visit_records_member_id
        foreign key (member_id) references members (id),
    constraint fk_store_visit_records_store_id
        foreign key (store_id) references stores (id)
);

create index idx_store_large_medium_address_lat_lng
    on stores (large_address, medium_address, lat, lng);



