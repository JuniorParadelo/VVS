DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));


DROP TABLE Reserva;
DROP TABLE Oferta;

-- --------------------------------- Oferta ------------------------------------
CREATE TABLE Oferta ( ofertaId BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) COLLATE latin1_bin NOT NULL,
    descripcion VARCHAR(255) COLLATE latin1_bin NOT NULL,
    fechaReservar TIMESTAMP DEFAULT 0 NOT NULL,
    fechaReclamar TIMESTAMP DEFAULT 0 NOT NULL,
    precioReal FLOAT NOT NULL,
    precioDescontado FLOAT NOT NULL,
    comision FLOAT NOT NULL,
    estado VARCHAR(255) COLLATE latin1_bin NOT NULL,
    CONSTRAINT OfertaPK PRIMARY KEY(ofertaId))
    
    ENGINE = InnoDB;

-- --------------------------------- Reserva ------------------------------------

CREATE TABLE Reserva ( reservaId BIGINT NOT NULL AUTO_INCREMENT,
    ofertaId BIGINT NOT NULL,
    email VARCHAR(255) COLLATE latin1_bin NOT NULL,
    tarjeta VARCHAR(16) NOT NULL,
    fechaDeReserva TIMESTAMP DEFAULT 0 NOT NULL,
    estado VARCHAR(255) COLLATE latin1_bin NOT NULL,
    codigo INT NOT NULL,
    precio FLOAT NOT NULL,
    CONSTRAINT ReservaPK PRIMARY KEY(reservaId),
    CONSTRAINT ReservaOfertaIdFK FOREIGN KEY(ofertaId)
        REFERENCES Oferta(ofertaId) ON DELETE CASCADE ) ENGINE = InnoDB;
