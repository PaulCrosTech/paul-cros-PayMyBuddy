
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
INSERT INTO `paymybuddy`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 1, 2, 'Cadeau', 12);
INSERT INTO `paymybuddy`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 3, 1, 'Concert', 31);
INSERT INTO `paymybuddy`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 2, 4, 'Cinéma', 24);
INSERT INTO `paymybuddy`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 4, 2, 'Restaurant', 42);
INSERT INTO `paymybuddy`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 1, 2, 'Café', 12.01);
INSERT INTO `paymybuddy`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 1, 2, 'Transport', 12.02);
INSERT INTO `paymybuddy`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 2, 1, 'Diner', 21);

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

