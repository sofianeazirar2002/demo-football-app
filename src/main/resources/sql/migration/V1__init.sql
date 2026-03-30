CREATE TABLE "user" (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        username VARCHAR(255),
                        nickname VARCHAR(255),
                        email VARCHAR(255),
                        logo_path VARCHAR(255),
                        password_hash VARCHAR(255)
);

CREATE TABLE competition (
                        id INT PRIMARY KEY,
                        name VARCHAR(255),
                        logo_png VARCHAR(255)
);

CREATE TABLE club (
                        id INT PRIMARY KEY,
                        name VARCHAR(255),
                        logo_url VARCHAR(255),
                        competition_id INT,
                        FOREIGN KEY (competition_id) REFERENCES competition(id)
);

CREATE TABLE "match" (
                        id INT PRIMARY KEY,
                        home_team_id INT,
                        away_team_id INT,
                        kick_off_time TIMESTAMP,
                        home_score INT,
                        away_score INT,
                        competition_id INT,
                        status INT,
                        FOREIGN KEY (home_team_id) REFERENCES club(id),
                        FOREIGN KEY (away_team_id) REFERENCES club(id),
                        FOREIGN KEY (competition_id) REFERENCES competition(id)
);

CREATE TABLE pronostic_group (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255),
                        join_code VARCHAR(255),
                        created TIMESTAMP,
                        owner_id INT,
                        competition_id INT,
                        FOREIGN KEY (competition_id) REFERENCES competition(id),
                        FOREIGN KEY (owner_id) REFERENCES "user"(id)
);

CREATE TABLE pronostic_group_user (
                        id INT,
                        user_id INT,
                        total_score INT,
                        pronostic_group_id INT,
                        PRIMARY KEY (id, user_id),
                        FOREIGN KEY (pronostic_group_id) REFERENCES pronostic_group(id),
                        FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE prediction (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        group_user_id INT,
                        match_id INT,
                        predicted_home_score INT,
                        predicted_away_score INT,
                        points_awarded INT,
                        FOREIGN KEY (group_user_id) REFERENCES "pronostic_group_user"(id),
                        FOREIGN KEY (match_id) REFERENCES "match"(id)
);

