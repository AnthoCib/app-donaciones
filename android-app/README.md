# Red de Donación Inteligente de Alimentos - Android

Aplicación móvil Kotlin + Jetpack Compose alineada a los controllers reales del backend Spring Boot del repositorio.

## Arquitectura
- `core`: red, sesión DataStore, UI compartida.
- `data`: DTOs, Retrofit service y repository.
- `domain`: enums de dominio compartidos.
- `presentation`: pantallas y ViewModels por rol.
- `navigation`: navegación Compose por rol.
- `di`: contenedor manual de dependencias.

## Endpoints usados
La app no usa endpoints inventados. Se adaptó a rutas reales del backend, por ejemplo:
- `POST /api/auth/login`
- `POST /api/auth/registro`
- `GET /api/public/publicaciones`
- `POST /api/donante/publicaciones`
- `GET /api/donante/publicaciones`
- `GET /api/donante/solicitudes`
- `GET /api/receptor/solicitudes`
- `GET /api/admin/dashboard/resumen`
- `GET /api/admin/usuarios`
- `GET /api/admin/publicaciones`

La base URL por defecto es `http://10.0.2.2:8080/` para emulador Android contra backend local.
