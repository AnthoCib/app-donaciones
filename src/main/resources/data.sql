INSERT IGNORE INTO categoria(id_categoria, nombre) VALUES
(1, 'Frutas y verduras'), (2, 'Panadería'), (3, 'Alimentos preparados'), (4, 'Lácteos'),
(5, 'Carnes'), (6, 'Cereales y menestras'), (7, 'Conservas'), (8, 'Bebidas'), (9, 'Otros');

INSERT IGNORE INTO usuario(id_usuario, nombres, apellidos, correo, password, telefono, rol, tipo_entidad, distrito, direccion, latitud, longitud, estado) VALUES
(1, 'Administrador', 'General', 'admin@demo.com', '$2a$12$/UyH1Rjrk./Qt2fQLcIEXutnTBUKoJ3jbbAKWo2pjHZoLF6Mw/GNe', '999999999', 'ADMINISTRADOR', 'PERSONA', 'Callao', 'Callao', -12.0566000, -77.1181000, 'ACTIVO'),
(2, 'Anthony', 'Cordero', 'donante@demo.com', '$2a$12$/UyH1Rjrk./Qt2fQLcIEXutnTBUKoJ3jbbAKWo2pjHZoLF6Mw/GNe', '999888777', 'DONANTE', 'PANADERIA', 'Ventanilla', 'Av. Principal 100', -11.8765000, -77.1278000, 'ACTIVO'),
(3, 'Maria', 'Lopez', 'receptor@demo.com', '$2a$12$/UyH1Rjrk./Qt2fQLcIEXutnTBUKoJ3jbbAKWo2pjHZoLF6Mw/GNe', '999333444', 'RECEPTOR', 'OLLA_COMUN', 'Ventanilla', 'Mz. A Lt. 10', -11.8790000, -77.1250000, 'ACTIVO');

INSERT IGNORE INTO publicacion(id_publicacion, codigo, id_donante, id_categoria, nombre_alimento, descripcion, cantidad_disponible, unidad_medida, fecha_vencimiento, imagen_url, distrito, direccion, latitud, longitud, estado, fecha_aprobacion, id_admin_aprobador) VALUES
(1, 'PUB-0001', 2, 2, 'Pan francés', 'Pan elaborado durante la mañana y en buen estado.', 50, 'UNIDAD', DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY), 'https://ejemplo.com/pan.jpg', 'Ventanilla', 'Av. Principal 100', -11.8765000, -77.1278000, 'DISPONIBLE', CURRENT_TIMESTAMP, 1);

INSERT IGNORE INTO solicitud(id_solicitud, codigo, id_publicacion, id_receptor, motivo, cantidad_solicitada, personas_beneficiadas, estado) VALUES
(1, 'SOL-0001', 1, 3, 'Alimento para familias atendidas por la olla común.', 20, 15, 'PENDIENTE');
