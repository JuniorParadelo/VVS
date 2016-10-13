-- ----------------------------------------------------------------------------
-- Ofertas Model
-------------------------------------------------------------------------------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));


DROP TABLE Reserva;
DROP TABLE Oferta;

CREATE TABLE Oferta (

	idOferta BIGINT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(255) COLLATE latin1_bin NOT NULL,
	descripcion VARCHAR(1024) COLLATE latin1_bin NOT NULL,
	fechaLimReserva TIMESTAMP DEFAULT 0 NOT NULL,
	fechaLimReclamacion TIMESTAMP DEFAULT 0 NOT NULL,
	precioReal FLOAT NOT NULL,
	precioDescontado FLOAT NOT NULL,
	comisionVenta FLOAT NOT NULL,
	invalida TINYINT NOT NULL,
	
	CONSTRAINT OfertaPK PRIMARY KEY(idOferta),
	CONSTRAINT precioRealValido CHECK ( precioReal >= 0 AND precioReal <= 500 ),
	CONSTRAINT precioDescontadoValido CHECK ( precioDescontado >= 0 AND precioDescontado <= 500 )

) ENGINE = InnoDB;


CREATE TABLE Reserva (

	codigo BIGINT NOT NULL AUTO_INCREMENT,
	email VARCHAR(125) COLLATE latin1_bin NOT NULL,
	tarjeta VARCHAR(17) NOT NULL,
	idOferta BIGINT NOT NULL,
	fechaReserva TIMESTAMP DEFAULT 0 NOT NULL,
	estado VARCHAR(10) NOT NULL,
	precioReserva FLOAT NOT NULL,

	CONSTRAINT ReservaPK PRIMARY KEY(codigo),
	CONSTRAINT ReservaOfertaIdFK FOREIGN KEY(idOferta) REFERENCES Oferta(idOferta) ON DELETE CASCADE

) ENGINE = InnoDB;


