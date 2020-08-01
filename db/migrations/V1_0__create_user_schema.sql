CREATE TABLE IF NOT EXISTS `users` (

    `id` int NOT NULL AUTO_INCREMENT ,
    `name` varchar(20),
    `email` varchar(50),
    PRIMARY KEY (id)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
CREATE TABLE IF NOT EXISTS `tasks` (

    `id` int NOT NULL AUTO_INCREMENT ,
    `todo` varchar(50),
    `userId` int,
    PRIMARY KEY (id)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;