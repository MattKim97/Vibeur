drop database if exists vibeur;
create database vibeur;
use vibeur;

create table `user`(
	user_id int primary key auto_increment,
	username varchar(255) unique,
    userImageUrl text,
	`password` varchar(255)
);

create table mood(
    mood_id int primary key auto_increment,
    mood_name varchar(255) unique
);

create table vibe(
    vibe_id int primary key auto_increment,
    title varchar(255),
    `description` text,
    imageUrl text,
    songUrl text,
    dateUploaded datetime,
    user_id int,
    mood_id int,
    foreign key (user_id) references `user`(user_id),
    foreign key (mood_id) references mood(mood_id)
);

create table `comment`(
    comment_id int primary key auto_increment,
    content text,
    dateCreated datetime,
    isEdited boolean,
    user_id int,
    vibe_id int,
    foreign key (user_id) references `user`(user_id),
    foreign key (vibe_id) references vibe(vibe_id) on delete cascade
);

create table `like`(
    like_id int primary key auto_increment,
    user_id int,
    vibe_id int,
    foreign key (user_id) references `user`(user_id),
    foreign key (vibe_id) references vibe(vibe_id) on delete cascade,
    constraint unique_like unique(user_id, vibe_id)
);

select * from vibe
