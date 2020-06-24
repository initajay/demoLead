create table lead
(
   id integer not null UNIQUE,
   first_name varchar(255) not null,
   last_name varchar(255) not null,
   mobile bigint not null UNIQUE,
   email varchar(255) not null UNIQUE,
   /*location_type ENUM('Country','City','Zip') not null,*/
   location_type varchar(8) not null UNIQUE,
   location_string varchar(255) not null ,
   /*status ENUM('Created','Contacted') not null,*/
   status varchar(8) not null UNIQUE,
   communication varchar(255) default null,
   primary key(id)
);