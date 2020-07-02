DELIMITER $$

CREATE FUNCTION f_exists_audio_by_id(IN_AUDIO_ID INT) RETURNS tinyint(1)
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE audio_exist TINYINT;
	SELECT COUNT(*) INTO audio_exist FROM m_audio WHERE id = IN_AUDIO_ID;
	RETURN audio_exist;
END $$

CREATE FUNCTION f_exists_or_create_audio_by_name(IN_AUDIO_NAME VARCHAR(255)) RETURNS int
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE audio_id int;
	SELECT id INTO audio_id FROM m_audio WHERE name like IN_AUDIO_NAME;
    IF(audio_id IS NULL) THEN
		SELECT id into audio_id from m_audio WHERE name LIKE IN_AUDIO_NAME;
        IF(audio_id IS NULL)THEN
			INSERT INTO m_audio(`name`) VALUES (IN_AUDIO_NAME);
			SELECT LAST_INSERT_ID() into audio_id;
		 END IF;
    END IF;
	RETURN audio_id;
END $$


CREATE FUNCTION f_exists_quality_by_id(IN_QUALITY_ID INT) RETURNS tinyint(1)
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE quality_exist TINYINT;
	SELECT COUNT(*) INTO quality_exist FROM m_quality WHERE id = IN_QUALITY_ID;
	RETURN quality_exist;
END $$


CREATE FUNCTION f_exists_or_create_quality_by_name(IN_QUALITY_NAME VARCHAR(255), IN_QUALITY_DETAIL VARCHAR(255)) RETURNS int
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE quality_id int;
	SELECT id INTO quality_id FROM m_quality WHERE name like IN_QUALITY_NAME;
    IF(quality_id IS NULL) THEN
		SELECT id into quality_id from m_quality WHERE name LIKE IN_QUALITY_NAME;
        IF(quality_id IS NULL)THEN
			INSERT INTO m_quality(`name`,`detail`) VALUES (IN_QUALITY_NAME, IN_QUALITY_DETAIL);
			SELECT LAST_INSERT_ID() into quality_id;
		 END IF;
    END IF;
	RETURN quality_id;
END $$

CREATE FUNCTION f_exists_director_by_id(IN_DIRECTOR_ID INT) RETURNS tinyint(1)
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE director_exist TINYINT;
	SELECT COUNT(*) INTO director_exist FROM m_director WHERE id = IN_DIRECTOR_ID;
	RETURN director_exist;
END $$

CREATE FUNCTION f_exists_or_create_director_by_name(IN_DIRECTOR_NAME VARCHAR(255)) RETURNS int
READS SQL DATA
DETERMINISTIC
BEGIN
	DECLARE director_id int;
	SELECT id INTO director_id FROM m_director WHERE name like IN_DIRECTOR_NAME;
    IF(director_id IS NULL) THEN
		SELECT id into director_id from m_director WHERE name LIKE IN_DIRECTOR_NAME;
        IF(director_id IS NULL)THEN
			INSERT INTO m_director(`name`) VALUES (IN_DIRECTOR_NAME);
			SELECT LAST_INSERT_ID() into director_id;
		 END IF;
    END IF;
	RETURN director_id;
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
  IN IN_IS_FREE BOOLEAN)
BEGIN
	DECLARE audio_id INT;
	DECLARE quality_id INT;
	DECLARE director_id INT;

    IF(IN_AUDIO_ID <> null) THEN
		 SELECT f_exists_audio_by_id(IN_AUDIO_ID) into audio_id;
         IF(audio_id = 0 and IN_AUDIO_NAME <> null) THEN
			SELECT f_exists_or_create_audio_by_name(IN_AUDIO_ID) into audio_id;
         END IF;
	ELSE
		SELECT f_exists_or_create_audio_by_name(IN_AUDIO_NAME) into audio_id;
    END IF;

    IF(IN_QUALITY_ID <> null) THEN
		 SELECT f_exists_quality_by_id(IN_QUALITY_ID) into quality_id;
         IF(quality_id = 0 and IN_QUALITY_NAME <> null) THEN
			SELECT f_exists_or_create_quality_by_name(IN_QUALITY_NAME, IN_QUALITY_DETAIL) into quality_id;
         END IF;
	ELSE
		SELECT f_exists_or_create_quality_by_name(IN_QUALITY_NAME, IN_QUALITY_DETAIL) into quality_id;
    END IF;

    IF(IN_DIRECTOR_ID <> null) THEN
		 SELECT f_exists_director_by_id(IN_DIRECTOR_ID) into director_id;
         IF(director_id = 0 and IN_DIRECTOR_NAME <> null) THEN
			SELECT f_exists_or_create_director_by_name(IN_DIRECTOR_NAME) into director_id;
         END IF;
	ELSE
		SELECT f_exists_or_create_director_by_name(IN_DIRECTOR_NAME) into director_id;
    END IF;

	INSERT INTO movie(`name`,`year`,`description`,`audio_id`,`quality_id`,`imdb_rate`,`director_id`,`free`) VALUES (IN_NAME,IN_YEAR,IN_DESCRIPTION,audio_id,quality_id,IN_IMDB_RATE,director_id,IN_IS_FREE);
	SELECT LAST_INSERT_ID();
END $$
