CREATE TABLE movie_description (
    id INT(8) NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    score varchar(5) NOT NULL,
    peoples varchar(10) NOT NULL,
    describ varchar(255) NOT NULL,
    pic varchar(255) NOT NULL,
    PRIMARY KEY(id)
)

CREATE TABLE movie_users (
    id INT(8) NOT NULL AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    age INT(3) NOT NULL,
    gender TINYINT NOT NULL,
    PRIMARY KEY(id)
)

CREATE TABLE movie_details (
    id INT(8) NOT NULL AUTO_INCREMENT,
    m_id INT(8) NOT NULL,
    director varchar(100) NOT NULL,
    actor varchar(100) NOT NULL,
    plot text NOT NULL,
    comments text NOT NULL,
    PRIMARY KEY(id),
    UNIQUE KEY(m_id)
)