-- Insert a user into the User table
INSERT INTO users (username, password, description, email) VALUES ('john_doe', 'password123', 'An avid gamer', 'test@gmail.com');
-- Insert into publishers table
INSERT INTO publisher (id, name, description, creation_date) VALUES (1, 'Publisher Name', 'test', '1999-05-05' );

INSERT INTO genre (id, name, description) VALUES (1, 'Puzzle','test2');

INSERT INTO games (id, name, publisher_id, likes) VALUES (1, 'Tetris', 1, 0);

INSERT INTO game_genres (game_id, genre_id) VALUES (1, 1);

INSERT INTO game_favorites(user_id, game_id) values ('john_doe',1);