SET SQL_SAFE_UPDATES = 0;

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
('user1', "https://vibeur.s3.us-west-1.amazonaws.com/iStock-1232014586.jpg" , '$2a$12$UdGkEgH6zB/Ws7fRs6DlZO1xgtjczIc7a6yCs43qANeB2zsE20cRK'),
('user2', null , '$2a$12$UdGkEgH6zB/Ws7fRs6DlZO1xgtjczIc7a6yCs43qANeB2zsE20cRK'),
('user3', null , '$2a$12$UdGkEgH6zB/Ws7fRs6DlZO1xgtjczIc7a6yCs43qANeB2zsE20cRK'),
('user4', null , 'password4'),
('user5', null , 'password5'),
('user6', null , 'password6'),
('user7', null , 'password7'),
('user8', null , 'password8'),
('user9', null , 'password9'),
('user10', null , 'password10'),
('user11', null , 'password11'),
('user12', null , 'password12'),
('user13', null , 'password13'),
('user14', null , 'password14'),
('user15', null , 'password15'),
('user16', null , 'password16'),
('user17', null , 'password17'),
('user18', null , 'password18'),
('user19', null , 'password19'),
('user20', null , 'password20'),
('user21', null , 'password21'),
('user22', null , 'password22'),
('user23', null , 'password23'),
('user24', null , 'password24'),
('user25', null , 'password25'),
('user26', null , 'password26'),
('user27', null , 'password27'),
('user28', null , 'password28'),
('user29', null , 'password29'),
('user30', null , 'password30');

insert into vibe(title, `description`, imageUrl, songUrl, dateUploaded, user_id, mood_id) values
('Feeling excited!', 'Today is a exciting day I am ready for some change!', 'https://vibeur.s3.us-west-1.amazonaws.com/images.jfif', 'https://vibeur.s3.us-west-1.amazonaws.com/Good+Kid+-+No+Time+To+Explain+(Official+Lyric+Video).mp3', '2025-03-17-10-17-52', 1, 5),
('Depresso', 'Feeling a bit down today, just want a cup of coffee', 'https://vibeur.s3.us-west-1.amazonaws.com/MotleyFool-TMOT-6ce98652-steaming-coffee-cup.webp', 'https://vibeur.s3.us-west-1.amazonaws.com/John+Mayer+-+Slow+Dancing+In+A+Burning+Room+(Lyrics).mp3', '2025-03-16-14-24-55', 2, 2),
('Fed Up and Fired Up', "I'm at my limit. Tired of biting my tongue, tired of holding back. Today, I'm letting it out — no filters, no apologies.", 'https://vibeur.s3.us-west-1.amazonaws.com/furious-frustated-businessman-hitting-computer-600nw-200466014.webp', 'https://vibeur.s3.us-west-1.amazonaws.com/Rage+against+the+machine+-+Killing+in+the+name+Lyrics.mp3', '2025-03-15-22-33-51', 3, 3);

insert into `comment`(content, dateCreated, isEdited, user_id, vibe_id) values
('This is a great vibe!', '2025-03-17-10-17-52', false, 2, 1),
('I love this song!', '2025-03-17-10-17-52', false, 3, 1),
('I feel you', '2025-03-16-14-24-55', false, 1, 2),
('I hope you feel better soon', '2025-03-16-14-24-55', false, 3, 2),
('I feel the same way tired of holding back', '2025-03-15-22-33-51', false, 1, 3),
('PREACH', '2025-03-15-22-33-51', false, 2, 3);

insert into `like`(user_id, vibe_id) values

(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 3),
(17, 3),
(18, 3),
(19, 3),
(20, 3),
(21, 2),
(22, 2),
(23, 2),
(24, 2),
(25, 2),
(26, 2),
(27, 2),
(28, 2),
(29, 2),
(30, 2);





SET SQL_SAFE_UPDATES = 1;
