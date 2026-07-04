CREATE TABLE propiedades (
                             id_propiedad  BIGSERIAL       PRIMARY KEY,
                             titulo        VARCHAR(200)    NOT NULL,
                             descripcion   VARCHAR(500)    NOT NULL,
                             ubicacion     VARCHAR(150)    NOT NULL,
                             precio        DECIMAL(12, 2)  NOT NULL,
                             id_anfitrion  BIGINT          NOT NULL,
                             tipo          VARCHAR(50)     NOT NULL,
                             disponible    BOOLEAN         NOT NULL DEFAULT FALSE
);