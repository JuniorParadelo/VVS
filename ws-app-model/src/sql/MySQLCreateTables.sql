-- ----------------------------------------------------------------------------
-- Model
-------------------------------------------------------------------------------
-- # MySQL -u ws --password=ws ws
-- ---------- Table for validation queries from the connection pool -----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

-- -----------------------------------------------------------------------------
-- Drop tables. NOTE: before dropping a table (when re-executing the script),
-- the tables having columns acting as foreign keys of the table to be dropped,
-- must be dropped first (otherwise, the corresponding checks on those tables
-- could not be done).

DROP TABLE Booking;
DROP TABLE Offer;

-- --------------------------------- Offer ------------------------------------
CREATE TABLE Offer ( offerId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) COLLATE latin1_bin NOT NULL,
    description VARCHAR(1024) COLLATE latin1_bin NOT NULL,
    realPrice FLOAT NOT NULL,
    discountPrice FLOAT NOT NULL,
    commission FLOAT NOT NULL,
    bookingDate TIMESTAMP DEFAULT 0 NOT NULL,
    enjoymentDate TIMESTAMP DEFAULT 0,
    validOffer INT DEFAULT 0 NOT NULL,
    CONSTRAINT validRealPrice CHECK ( realPrice >= 0 AND realPrice <= 1000),
    CONSTRAINT validDiscountPrice CHECK ( discountPrice >= 0 AND discountPrice <= 1000),
	CONSTRAINT commission CHECK ( commission >= 0),
    CONSTRAINT validOffer CHECK ( validOffer >= 0 AND validOffer <= 1),
    CONSTRAINT OfferPK PRIMARY KEY(offerId) ) ENGINE = InnoDB;
    
-- --------------------------------- Booking ------------------------------------

CREATE TABLE Booking ( bookingId BIGINT NOT NULL AUTO_INCREMENT,
    offerId BIGINT NOT NULL,
    userId VARCHAR(40) COLLATE latin1_bin NOT NULL,
    enjoymentDate TIMESTAMP DEFAULT 0,
    bookingDate TIMESTAMP DEFAULT 0 NOT NULL,
    creditCardNumber VARCHAR(16),
    state ENUM('validBooking','nullbooking','invalidbooking'),
    code BIGINT NOT NULL,
    CONSTRAINT BookingPK PRIMARY KEY(bookingId),
    CONSTRAINT BookingOfferIdFK FOREIGN KEY(offerId)
        REFERENCES Offer(offerId) ON DELETE CASCADE ) ENGINE = InnoDB;
