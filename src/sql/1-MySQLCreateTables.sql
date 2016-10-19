-- Indexes for primary keys have been explicitly created.

-- ---------- Table for validation queries from the connection pool. ----------

DROP TABLE PingTable;
DROP TABLE Bet;
DROP TABLE UserProfile;
DROP TABLE OptionB;
DROP TABLE BetType;
DROP TABLE Event;
DROP TABLE Category;



CREATE TABLE PingTable (foo CHAR(1));

-- ------------------------------ Category -------------------------------------------------
	
	
CREATE TABLE Category (
	categoryId BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(15) COLLATE latin1_bin NOT NULL,
	CONSTRAINT Category_PK PRIMARY KEY (categoryId),
	CONSTRAINT UNIQUE (name)) 
	ENGINE = InnoDB;
	
-- -------------------------------------------------------------------------------------------------
	

CREATE TABLE Event (
	eventId BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) COLLATE latin1_bin NOT NULL,
	date TIMESTAMP DEFAULT 0 NOT NULL,
	category BIGINT NOT NULL,
	CONSTRAINT Event_PK PRIMARY KEY (eventId),
	CONSTRAINT Category_FK FOREIGN KEY(category) REFERENCES Category(categoryId) ON DELETE CASCADE
	)
	ENGINE = InnoDB;
	
-- -------------------------------------------------------------------------------------------------


CREATE TABLE BetType (
	betTypeId BIGINT NOT NULL AUTO_INCREMENT,
	question VARCHAR(50) COLLATE latin1_bin NOT NULL,
	eventId BIGINT NOT NULL,
	unica BOOLEAN NOT NULL,
	CONSTRAINT BetType_PK PRIMARY KEY (betTypeId),
	CONSTRAINT EventOfFK FOREIGN KEY(eventId) REFERENCES Event(eventId) ON DELETE CASCADE
	)
	ENGINE = InnoDB;

-- ------------------------------------------------------------------------------------------

CREATE TABLE OptionB (
	optionId BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(15) COLLATE latin1_bin NOT NULL,
	coute FLOAT NOT NULL,
	win BOOLEAN,
	betTypeId BIGINT NOT NULL,
	version BIGINT,
	CONSTRAINT Option_PK PRIMARY KEY (optionId),
	CONSTRAINT BetTypeof_Pk FOREIGN KEY (betTypeId) REFERENCES BetType(betTypeId) ON DELETE CASCADE
	)
	ENGINE = InnoDB;
	
-- ------------------------------ UserProfile ----------------------------------


CREATE TABLE UserProfile (
    usrId BIGINT NOT NULL AUTO_INCREMENT,
    loginName VARCHAR(30) COLLATE latin1_bin NOT NULL,
    enPassword VARCHAR(13) NOT NULL, 
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(40) NOT NULL, email VARCHAR(60) NOT NULL,
    CONSTRAINT UserProfile_PK PRIMARY KEY (usrId),
    CONSTRAINT LoginNameUniqueKey UNIQUE (loginName)) 
    ENGINE = InnoDB;

CREATE INDEX UserProfileIndexByLoginName ON UserProfile (loginName);

-- ------------------------------- Bet -------------------------------------------


CREATE TABLE Bet (
	betId BIGINT NOT NULL AUTO_INCREMENT,
	betAmount FLOAT NOT NULL,
	betDate TIMESTAMP DEFAULT 0 NOT NULL,
	optionId BIGINT NOT NULL,
	userId BIGINT NOT NULL,
	CONSTRAINT Bet_PK PRIMARY KEY (betId),
	CONSTRAINT OptionOfFK FOREIGN KEY(optionId) REFERENCES OptionB(optionId) ON DELETE CASCADE,
	CONSTRAINT UserProfileOfFK FOREIGN KEY(userId) REFERENCES UserProfile(usrId) ON DELETE CASCADE
	)
	ENGINE = InnoDB;
	

-- -------------------------------------------------------------------------------------------------

	
	
