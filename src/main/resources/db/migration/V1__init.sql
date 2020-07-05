CREATE TABLE m_genre (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE m_audio (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE m_quality (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  detail varchar(500) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE m_director (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE m_cast (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE movie(
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  year int not null,
  description varchar(2000) NOT NULL,
  audio_id int not null,
  quality_id int not null,
  imdb_rate double,
  director_id int not null,
  free boolean not null,
  PRIMARY KEY (id),
  FOREIGN KEY (audio_id) REFERENCES m_audio(id),
  FOREIGN KEY (quality_id) REFERENCES m_quality(id),
  FOREIGN KEY (director_id) REFERENCES m_quality(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE movie_genre(
	id_movie bigint NOT NULL,
    id_genre int NOT NULL,
    PRIMARY KEY (id_movie,id_genre),
    FOREIGN KEY (id_movie) REFERENCES movie(id),
    FOREIGN KEY (id_genre) REFERENCES m_genre(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE movie_cast(
	id_movie bigint NOT NULL,
    id_cast int NOT NULL,
    PRIMARY KEY (id_movie,id_cast),
    FOREIGN KEY (id_movie) REFERENCES movie(id),
    FOREIGN KEY (id_cast) REFERENCES m_cast(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
