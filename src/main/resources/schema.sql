CREATE DATABASE IF NOT EXISTS donacion_alimentos_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE donacion_alimentos_db;

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombres VARCHAR(80) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(15),
    rol VARCHAR(20) NOT NULL,
    tipo_entidad VARCHAR(30) NOT NULL,
    distrito VARCHAR(80) NOT NULL,
    direccion VARCHAR(200),
    latitud DECIMAL(10,7),
    longitud DECIMAL(10,7),
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS categoria (
    id_categoria BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(80) NOT NULL UNIQUE,
    estado BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS publicacion (
    id_publicacion BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    id_donante BIGINT NOT NULL,
    id_categoria BIGINT NOT NULL,
    nombre_alimento VARCHAR(120) NOT NULL,
    descripcion VARCHAR(400),
    cantidad_disponible DECIMAL(10,2) NOT NULL,
    unidad_medida VARCHAR(20) NOT NULL,
    fecha_vencimiento DATETIME NOT NULL,
    imagen_url VARCHAR(500),
    distrito VARCHAR(80) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    latitud DECIMAL(10,7) NOT NULL,
    longitud DECIMAL(10,7) NOT NULL,
    estado VARCHAR(25) NOT NULL DEFAULT 'PENDIENTE',
    fecha_publicacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_aprobacion DATETIME,
    id_admin_aprobador BIGINT,
    motivo_observacion VARCHAR(300),
    CONSTRAINT fk_publicacion_donante FOREIGN KEY (id_donante) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_publicacion_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
    CONSTRAINT fk_publicacion_admin FOREIGN KEY (id_admin_aprobador) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS solicitud (
    id_solicitud BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    id_publicacion BIGINT NOT NULL,
    id_receptor BIGINT NOT NULL,
    motivo VARCHAR(300) NOT NULL,
    cantidad_solicitada DECIMAL(10,2) NOT NULL,
    personas_beneficiadas INT NOT NULL DEFAULT 1,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    fecha_solicitud DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_respuesta DATETIME,
    observacion_respuesta VARCHAR(300),
    CONSTRAINT fk_solicitud_publicacion FOREIGN KEY (id_publicacion) REFERENCES publicacion(id_publicacion),
    CONSTRAINT fk_solicitud_receptor FOREIGN KEY (id_receptor) REFERENCES usuario(id_usuario),
    CONSTRAINT uk_solicitud_unica UNIQUE (id_publicacion, id_receptor)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS entrega (
    id_entrega BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    id_solicitud BIGINT NOT NULL UNIQUE,
    fecha_programada DATETIME,
    fecha_entrega DATETIME,
    cantidad_entregada DECIMAL(10,2),
    personas_beneficiadas INT NOT NULL DEFAULT 1,
    confirmada_donante BOOLEAN NOT NULL DEFAULT FALSE,
    confirmada_receptor BOOLEAN NOT NULL DEFAULT FALSE,
    estado VARCHAR(25) NOT NULL DEFAULT 'PENDIENTE',
    observacion VARCHAR(300),
    CONSTRAINT fk_entrega_solicitud FOREIGN KEY (id_solicitud) REFERENCES solicitud(id_solicitud)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS alerta (
    id_alerta BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_publicacion BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    mensaje VARCHAR(300) NOT NULL,
    leida BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_alerta_publicacion FOREIGN KEY (id_publicacion) REFERENCES publicacion(id_publicacion) ON DELETE CASCADE,
    CONSTRAINT fk_alerta_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS reporte (
    id_reporte BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    id_usuario_reportante BIGINT NOT NULL,
    id_publicacion BIGINT,
    asunto VARCHAR(120) NOT NULL,
    descripcion VARCHAR(400) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    fecha_reporte DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_atencion DATETIME,
    id_admin BIGINT,
    respuesta_admin VARCHAR(400),
    CONSTRAINT fk_reporte_usuario FOREIGN KEY (id_usuario_reportante) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_reporte_publicacion FOREIGN KEY (id_publicacion) REFERENCES publicacion(id_publicacion),
    CONSTRAINT fk_reporte_admin FOREIGN KEY (id_admin) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

CREATE OR REPLACE VIEW vw_publicaciones_disponibles AS
SELECT p.id_publicacion, p.codigo, p.nombre_alimento, p.descripcion, p.cantidad_disponible,
       p.unidad_medida, p.fecha_vencimiento, p.imagen_url, p.distrito, p.direccion,
       p.latitud, p.longitud, p.estado, c.id_categoria, c.nombre AS categoria,
       u.id_usuario AS id_donante, CONCAT(u.nombres, ' ', u.apellidos) AS donante, u.tipo_entidad
FROM publicacion p
JOIN categoria c ON c.id_categoria = p.id_categoria
JOIN usuario u ON u.id_usuario = p.id_donante
WHERE p.estado = 'PUBLICADA' AND p.cantidad_disponible > 0
  AND p.fecha_vencimiento > CURRENT_TIMESTAMP AND u.estado = 'ACTIVO';

CREATE OR REPLACE VIEW vw_dashboard AS
SELECT
    (SELECT COUNT(*) FROM publicacion) AS total_publicaciones,
    (SELECT COUNT(*) FROM publicacion WHERE estado = 'PUBLICADA') AS publicaciones_disponibles,
    (SELECT COALESCE(SUM(cantidad_entregada), 0) FROM entrega WHERE estado = 'CONFIRMADA') AS cantidad_alimentos_entregados,
    (SELECT COALESCE(SUM(personas_beneficiadas), 0) FROM entrega WHERE estado = 'CONFIRMADA') AS personas_beneficiadas,
    (SELECT COUNT(DISTINCT p.distrito) FROM publicacion p JOIN solicitud s ON s.id_publicacion = p.id_publicacion JOIN entrega e ON e.id_solicitud = s.id_solicitud WHERE e.estado = 'CONFIRMADA') AS distritos_impactados,
    (SELECT COUNT(*) FROM usuario WHERE rol = 'DONANTE' AND estado = 'ACTIVO') AS donantes_activos,
    (SELECT COUNT(*) FROM solicitud) AS reservas_realizadas,
    (SELECT COUNT(*) FROM entrega WHERE estado = 'CONFIRMADA') AS entregas_confirmadas;

-- Alinea instalaciones existentes con el modelo oficial de la base enviada.
ALTER TABLE publicacion DROP COLUMN IF EXISTS peso_total_kg;
ALTER TABLE entrega DROP COLUMN IF EXISTS peso_entregado_kg;
ALTER TABLE entrega ADD COLUMN IF NOT EXISTS fecha_programada DATETIME AFTER id_solicitud;
