-- Insert a user into the User table
INSERT INTO users (username, password, description, email) VALUES ('john_doe', '$2a$12$Qojr8RNsVtHBusFDzkulHeVzrSQ54FECssaCfp2daWOmVy/GgsO0q', 'An avid gamer', 'test@gmail.com');
INSERT INTO users (username, password, description, email) VALUES ('henk', '$2a$12$Qojr8RNsVtHBusFDzkulHeVzrSQ54FECssaCfp2daWOmVy/GgsO0q', 'normaalgebruiker', 'normaal@gmail.com');

-- Insert into publishers table
INSERT INTO publisher (id, name, description, creation_date) VALUES (1000, 'FromSoft', 'From Software', '1999-05-05' );
INSERT INTO publisher (id, name, description, creation_date) VALUES (1001, '2K', '2K Interactive', '2005-11-07' );
INSERT INTO publisher (id, name, description, creation_date) VALUES (1002, 'Bioware', 'Bioware Studios', '2014-5-20' );


INSERT INTO genre (id, name, description) VALUES (1000, 'Puzzle','Lekker puzzelen');
INSERT INTO genre (id, name, description) VALUES (1001, 'Simulation','Extreem min-maxxen');
INSERT INTO genre (id, name, description) VALUES (1002, 'Adventure','Hard knallen');

INSERT INTO games (id, name, publisher_id, likes) VALUES (1000, 'Tetris', 1001, 0);
INSERT INTO games (id, name, publisher_id, likes) VALUES (1001, 'Dark Souls', 1000, 0);
INSERT INTO games (id, name, publisher_id, likes) VALUES (1002, 'Mass Effect', 1002, 0);

INSERT INTO game_genres (game_id, genre_id) VALUES (1000, 1000);
INSERT INTO game_genres (game_id, genre_id) VALUES (1001, 1001);
INSERT INTO game_genres (game_id, genre_id) VALUES (1002, 1002);

INSERT INTO game_favorites(user_id, game_id) values ('john_doe',1000);

INSERT INTO authorities(username, authority) VALUES ('john_doe', 'ROLE_ADMIN');
INSERT INTO authorities(username, authority) VALUES ('henk', 'ROLE_NORMAL_USER');