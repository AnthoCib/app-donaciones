# Android DONANTE / RECEPTOR - Red de Donación Inteligente de Alimentos

Aplicación móvil Kotlin + Jetpack Compose alineada a los controllers reales del backend Spring Boot.

## Arquitectura
- `core`: red, DataStore de sesión, utilidades y UI compartida.
- `data`: DTOs, Retrofit service y repository.
- `domain`: enums/modelos de dominio.
- `presentation`: pantallas y ViewModels por rol DONANTE/RECEPTOR.
- `navigation`: navegación Compose por rol.
- `di`: contenedor manual de dependencias.

## Roles
- DONANTE: permitido.
- RECEPTOR: permitido.
- ADMIN/ADMINISTRADOR: la app muestra el mensaje “El administrador debe usar el panel web” y permite cerrar sesión.

## Ejecutar
```bash
cd android-app
gradle :app:assembleDebug
```

La base URL por defecto es `http://10.0.2.2:8080/` para emulador Android contra backend local.

## Endpoints usados
La app no usa endpoints inventados. Se adaptó a rutas reales del backend:
- `POST /api/auth/login`
- `POST /api/auth/registro`
- `GET /api/public/publicaciones`
- `GET /api/public/publicaciones/cercanas`
- `POST /api/donante/publicaciones`
- `GET /api/donante/publicaciones`
- `PATCH /api/donante/publicaciones/{id}/confirmar-disponibilidad`
- `GET /api/donante/solicitudes`
- `POST /api/receptor/solicitudes`
- `GET /api/receptor/solicitudes`
- `PATCH /api/solicitudes/{id}/confirmar-entrega`
- `GET /api/historial/operaciones`
