create table events
(
    event_id uuid
        constraint table_name_pk
            primary key,
    event_type varchar(15) not null,
    time timestamp not null,
    user_name varchar not null,
    ip varchar not null,
    dry_level double precision not null,
    threshold double precision not null,
    raw_value int not null
);

