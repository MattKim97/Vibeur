drop database if exists vibeur_test;
create database vibeur_test;
use vibeur_test;


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


delimiter //
create procedure set_known_good_state()
begin

delete from `like`;
alter table `like` auto_increment = 1;
delete from `comment`;
alter table `comment` auto_increment = 1;
delete from vibe;
alter table vibe auto_increment = 1;
delete from `user`;
alter table `user` auto_increment = 1;
delete from mood;
alter table mood auto_increment = 1;

insert into mood(mood_name) values('happy');
insert into mood(mood_name) values('sad');
insert into mood(mood_name) values('angry');
insert into mood(mood_name) values('relaxed');
insert into mood(mood_name) values('excited');

insert into `user`(username, userImageUrl, `password`) values
('user1', "test" , 'password1'),
('user2', null , 'password2'),
('user3', null , 'password3');

insert into vibe(title, `description`, imageUrl, songUrl, dateUploaded, user_id, mood_id) values
('title1', 'description1', 'imageUrl1', 'songUrl1', '2021-01-01-10-10-10', 1, 1),
('title2', 'description2', 'imageUrl2', 'songUrl2', '2021-01-02-15-15-15', 2, 2),
('title3', 'description3', 'imageUrl3', 'songUrl3', '2021-01-03-20-20-20', 3, 3);

insert into `comment`(content, dateCreated, isEdited, user_id, vibe_id) values
('content1', '2021-01-01-01-01-01', false, 1, 1),
('content2', '2021-02-02-02-02-02', false, 2, 2),
('content3', '2021-03-03-03-03-03', false, 3, 3);

insert into `like`(user_id, vibe_id) values
(3, 1),
(1, 2),
(2, 3);
        
    
end//
delimiter ;
