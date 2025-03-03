
-- -----------------------------------------------------
-- Data for table `paymybuddy`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `paymybuddy`;
INSERT INTO `paymybuddy`.`users` (`user_id`, `username`, `email`, `password`, `solde`) VALUES (1, 'alice', 'alice@mail.fr', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 10000);
INSERT INTO `paymybuddy`.`users` (`user_id`, `username`, `email`, `password`, `solde`) VALUES (2, 'bob', 'bob@mail.fr', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 20000);
INSERT INTO `paymybuddy`.`users` (`user_id`, `username`, `email`, `password`, `solde`) VALUES (3, 'charlie', 'charlie@mail.fr', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 1000);
INSERT INTO `paymybuddy`.`users` (`user_id`, `username`, `email`, `password`, `solde`) VALUES (4, 'emma', 'emma@mail.fr', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 5000);
INSERT INTO `paymybuddy`.`users` (`user_id`, `username`, `email`, `password`, `solde`) VALUES (5, 'david', 'david@mail.fr', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 4500);

COMMIT;


-- -----------------------------------------------------
-- Data for table `paymybuddy`.`transactions`
-- -----------------------------------------------------
START TRANSACTION;
USE `paymybuddy`;
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Café', 1);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Chocolat', 2);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Biscuit', 3);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Pain', 4);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Croissant', 5);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Jus d''orange', 6);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Lait', 7);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Yaourt', 8);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Miel', 9);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Confiture', 10);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Beurre', 11);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Farine', 12);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Sucre', 13);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Sel', 14);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Poivre', 15);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Huile', 16);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Vinaigre', 17);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Pâtes', 18);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Riz', 19);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Lentilles', 20);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Haricots', 21);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Pois chiches', 22);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Viande', 23);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Poulet', 24);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Poisson', 25);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Œufs', 26);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Tomates', 27);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Salade', 28);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Carottes', 29);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (1, 2, 'Pommes', 30);

INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES ( 3, 1, 'Concert', 123.45);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES ( 2, 1, 'Diner', 10000.00);

INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES ( 2, 4, 'Cinéma', 54.89);
INSERT INTO `paymybuddy`.`transactions` (`user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES ( 4, 2, 'Restaurant', 410.75);

COMMIT;


-- -----------------------------------------------------
-- Data for table `paymybuddy`.`user_relations`
-- -----------------------------------------------------
START TRANSACTION;
USE `paymybuddy`;
INSERT INTO `paymybuddy`.`user_relations` (`user_id`, `friend_id`) VALUES (1, 2);
INSERT INTO `paymybuddy`.`user_relations` (`user_id`, `friend_id`) VALUES (1, 3);
INSERT INTO `paymybuddy`.`user_relations` (`user_id`, `friend_id`) VALUES (2, 4);

COMMIT;

