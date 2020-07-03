DELIMITER $$

CREATE FUNCTION f_exists_audio_by_id(IN_AUDIO_ID INT) RETURNS tinyint(1)
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE audio_exist TINYINT;
	SELECT id INTO audio_exist FROM m_audio WHERE id = IN_AUDIO_ID;
	RETURN audio_exist;
END $$

CREATE FUNCTION f_exists_or_create_audio_by_name(IN_AUDIO_NAME VARCHAR(255)) RETURNS int
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE audio_id int;
	SELECT id INTO audio_id FROM m_audio WHERE name like IN_AUDIO_NAME;
    IF(audio_id IS NULL) THEN
		INSERT INTO m_audio(`name`) VALUES (IN_AUDIO_NAME);
		SELECT LAST_INSERT_ID() into audio_id;
	END IF;
	RETURN audio_id;
END $$


CREATE FUNCTION f_exists_quality_by_id(IN_QUALITY_ID INT) RETURNS tinyint(1)
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE quality_exist TINYINT;
	SELECT id INTO quality_exist FROM m_quality WHERE id = IN_QUALITY_ID;
	RETURN quality_exist;
END $$


CREATE FUNCTION f_exists_or_create_quality_by_name(IN_QUALITY_NAME VARCHAR(255), IN_QUALITY_DETAIL VARCHAR(255)) RETURNS int
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE quality_id int;
	SELECT id INTO quality_id FROM m_quality WHERE name like IN_QUALITY_NAME;
    IF(quality_id IS NULL) THEN
		INSERT INTO m_quality(`name`,`detail`) VALUES (IN_QUALITY_NAME, IN_QUALITY_DETAIL);
		SELECT LAST_INSERT_ID() into quality_id;
	END IF;
	RETURN quality_id;
END $$

CREATE FUNCTION f_exists_director_by_id(IN_DIRECTOR_ID INT) RETURNS tinyint(1)
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE director_exist TINYINT;
	SELECT id INTO director_exist FROM m_director WHERE id = IN_DIRECTOR_ID;
	RETURN director_exist;
END $$

CREATE FUNCTION f_exists_or_create_director_by_name(IN_DIRECTOR_NAME VARCHAR(255)) RETURNS int
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE director_id int;
	SELECT id INTO director_id FROM m_director WHERE name like IN_DIRECTOR_NAME;
    IF(director_id IS NULL) THEN
		INSERT INTO m_director(`name`) VALUES (IN_DIRECTOR_NAME);
		SELECT LAST_INSERT_ID() into director_id;
    END IF;
	RETURN director_id;
END $$


CREATE FUNCTION f_exists_genre_by_id(IN_GENRE_ID INT) RETURNS tinyint(1)
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE genre_exist TINYINT;
	SELECT id INTO genre_exist FROM m_genre WHERE id = IN_GENRE_ID;
	RETURN genre_exist;
END $$

CREATE FUNCTION f_exists_or_create_genre_by_name(IN_GENRE_NAME VARCHAR(255)) RETURNS int
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE genre_id int;
	SELECT id INTO genre_id FROM m_genre WHERE name like IN_GENRE_NAME;
    IF(genre_id IS NULL) THEN
		INSERT INTO m_genre(`name`) VALUES (IN_GENRE_NAME);
		SELECT LAST_INSERT_ID() into genre_id;
    END IF;
	RETURN genre_id;
END $$


CREATE PROCEDURE `insert_genre_with_movie`(
  IN IN_MOVIE_ID BIGINT,
  IN IN_GENRES_LIST VARCHAR(2000))
BEGIN
	DECLARE genre_with_id TEXT DEFAULT NULL;
	DECLARE genre_with_id_length INT DEFAULT NULL;
    DECLARE genre_id int;
    DECLARE genre_name VARCHAR(255);
    DECLARE final_genre_id int;
	iterator:
    LOOP
		IF LENGTH(TRIM(IN_GENRES_LIST)) = 0 OR IN_GENRES_LIST IS NULL THEN LEAVE iterator; END IF;

		SET genre_with_id = SUBSTRING_INDEX(IN_GENRES_LIST,',',1);
		SET genre_with_id_length = LENGTH(genre_with_id);
		SET IN_GENRES_LIST = INSERT(IN_GENRES_LIST,1,genre_with_id_length + 1,'');

        SET genre_id = CONVERT(SUBSTRING_INDEX(genre_with_id,'-',1),UNSIGNED INTEGER);
        SET genre_name = INSERT(genre_with_id,1,LENGTH(genre_id) + 1,'');

		IF(genre_id <> 0) THEN
			SELECT f_exists_genre_by_id(genre_id) into final_genre_id;
			IF(final_genre_id IS NULL and genre_name IS NOT NULL) THEN
				SELECT f_exists_or_create_genre_by_name(genre_name) into final_genre_id;
			ELSE
				SET final_genre_id = genre_id;
			END IF;
		ELSE
			SELECT f_exists_or_create_genre_by_name(genre_name) into final_genre_id;
		END IF;
        INSERT INTO movie_genre(`id_movie`,`id_genre`) VALUES (IN_MOVIE_ID,final_genre_id);

	END LOOP;
END $$

CREATE FUNCTION f_exists_cast_by_id(IN_CAST_ID INT) RETURNS tinyint(1)
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE cast_exist TINYINT;
	SELECT id INTO cast_exist FROM m_cast WHERE id = IN_CAST_ID;
	RETURN cast_exist;
END $$

CREATE FUNCTION f_exists_or_create_cast_by_name(IN_CAST_NAME VARCHAR(255)) RETURNS int
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE cast_id int;
	SELECT id INTO cast_id FROM m_cast WHERE name like IN_CAST_NAME;
    IF(cast_id IS NULL) THEN
		INSERT INTO m_cast(`name`) VALUES (IN_CAST_NAME);
		SELECT LAST_INSERT_ID() into cast_id;
    END IF;
	RETURN cast_id;
END $$

CREATE PROCEDURE `insert_cast_with_movie`(
  IN IN_MOVIE_ID BIGINT,
  IN IN_CAST_LIST VARCHAR(2000))
BEGIN
	DECLARE cast_with_id TEXT DEFAULT NULL;
	DECLARE cast_with_id_length INT DEFAULT NULL;
    DECLARE cast_id int;
    DECLARE cast_name VARCHAR(255);
    DECLARE final_cast_id int;
	iterator:
    LOOP
		IF LENGTH(TRIM(IN_CAST_LIST)) = 0 OR IN_CAST_LIST IS NULL THEN LEAVE iterator; END IF;

		SET cast_with_id = SUBSTRING_INDEX(IN_CAST_LIST,',',1);
		SET cast_with_id_length = LENGTH(cast_with_id);
		SET IN_CAST_LIST = INSERT(IN_CAST_LIST,1,cast_with_id_length + 1,'');

        SET cast_id = CONVERT(SUBSTRING_INDEX(cast_with_id,'-',1),UNSIGNED INTEGER);
        SET cast_name = INSERT(cast_with_id,1,LENGTH(cast_id) + 1,'');

		IF(cast_id <> 0) THEN
			SELECT f_exists_cast_by_id(cast_id) into final_cast_id;
			IF(final_cast_id IS NULL and cast_name IS NOT NULL) THEN
				SELECT f_exists_or_create_cast_by_name(cast_name) into final_cast_id;
			ELSE
				SET final_cast_id = cast_id;
			END IF;
		ELSE
			SELECT f_exists_or_create_cast_by_name(cast_name) into final_cast_id;
		END IF;
        INSERT INTO movie_cast(`id_movie`,`id_cast`) VALUES (IN_MOVIE_ID,final_cast_id);

	END LOOP;
END $$


CREATE PROCEDURE `register_movie`(
  IN IN_NAME VARCHAR(255),
  IN IN_YEAR INT,
  IN IN_DESCRIPTION VARCHAR(2000),
  IN IN_AUDIO_ID INTEGER,
  IN IN_AUDIO_NAME VARCHAR(255),
  IN IN_QUALITY_ID INT,
  IN IN_QUALITY_NAME VARCHAR(255),
  IN IN_QUALITY_DETAIL VARCHAR(255),
  IN IN_IMDB_RATE DOUBLE,
  IN IN_DIRECTOR_ID INT,
  IN IN_DIRECTOR_NAME VARCHAR(255),
  IN IN_IS_FREE BOOLEAN,
  IN IN_GENRES VARCHAR(2000),
  IN IN_CAST VARCHAR(2000))
BEGIN
	DECLARE audio_id INT;
	DECLARE quality_id INT;
	DECLARE director_id INT;
	DECLARE movie_id BIGINT;

    IF(IN_AUDIO_ID IS NOT NULL) THEN
		SELECT f_exists_audio_by_id(IN_AUDIO_ID) into audio_id;
        IF(audio_id IS NULL and IN_AUDIO_NAME IS NOT NULL) THEN
			SELECT f_exists_or_create_audio_by_name(IN_AUDIO_ID) into audio_id;
		ELSE
			SET audio_id = IN_AUDIO_ID;
        END IF;
	ELSE
		SELECT f_exists_or_create_audio_by_name(IN_AUDIO_NAME) into audio_id;
    END IF;

    IF(IN_QUALITY_ID IS NOT NULL) THEN
		SELECT f_exists_quality_by_id(IN_QUALITY_ID) into quality_id;
        IF(quality_id IS NULL and IN_QUALITY_NAME IS NOT NULL) THEN
			SELECT f_exists_or_create_quality_by_name(IN_QUALITY_NAME, IN_QUALITY_DETAIL) into quality_id;
		ELSE
			SET quality_id = IN_QUALITY_ID;
		END IF;
	ELSE
		SELECT f_exists_or_create_quality_by_name(IN_QUALITY_NAME, IN_QUALITY_DETAIL) into quality_id;
    END IF;

    IF(IN_DIRECTOR_ID IS NOT null) THEN
		 SELECT f_exists_director_by_id(IN_DIRECTOR_ID) into director_id;
         IF(director_id IS NULL and IN_DIRECTOR_NAME IS NOT NULL) THEN
			SELECT f_exists_or_create_director_by_name(IN_DIRECTOR_NAME) into director_id;
		 ELSE
			SET director_id = IN_DIRECTOR_ID;
         END IF;
	ELSE
		SELECT f_exists_or_create_director_by_name(IN_DIRECTOR_NAME) into director_id;
    END IF;

	INSERT INTO movie(`name`,`year`,`description`,`audio_id`,`quality_id`,`imdb_rate`,`director_id`,`free`) VALUES (IN_NAME,IN_YEAR,IN_DESCRIPTION,audio_id,quality_id,IN_IMDB_RATE,director_id,IN_IS_FREE);
	SELECT LAST_INSERT_ID() into movie_id;

	CALL insert_genre_with_movie(movie_id,IN_GENRES);

    CALL insert_cast_with_movie(movie_id,IN_CAST);

END $$


CREATE PROCEDURE `getMoviesList`()
BEGIN

	SELECT m.name,m.year,m.description,m.free, group_concat(concat(g.id,"-",g.name))as 'genres'
	FROM movie m
	JOIN movie_genre mg
	JOIN m_genre g on mg.id_genre = g.id
	WHERE mg.id_movie = m.id
	group by m.id;

END $$


CREATE PROCEDURE `getMovieDetail`(IN IN_MOVIE_ID BIGINT)
BEGIN

	SELECT m.id as 'movie_id',m.name as 'movie_name',m.year,m.description,a.id as 'audio_id',a.name as 'audio_name',q.id as 'quality_id', q.name as 'quality_name',q.detail as 'quality_detail', m.imdb_rate,d.id as 'director_id',d.name as 'director_name',m.free, g.genres as 'genres', c.cast as 'casts'
	FROM movie m
	JOIN m_audio a on m.audio_id = a.id
	JOIN m_quality q on m.quality_id = q.id
	JOIN m_director d on m.director_id = d.id
	JOIN (SELECT group_concat(g.id,'-',g.name) as 'genres' from movie_genre mg
	JOIN m_genre g on mg.id_genre = g.id
	WHERE mg.id_movie = IN_MOVIE_ID) g
	JOIN (SELECT group_concat(c.id,'-',c.name) as 'cast' from movie_cast mc
	JOIN m_cast c on mc.id_cast = c.id
	WHERE mc.id_movie = IN_MOVIE_ID) c
	WHERE m.id = IN_MOVIE_ID;

END $$