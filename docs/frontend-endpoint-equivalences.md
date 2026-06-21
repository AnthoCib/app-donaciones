# Tabla de equivalencias Frontends ↔ Backend

Revisión basada en los controllers y DTOs reales del backend Spring Boot. Cuando un endpoint solicitado no existe, se marca como **Falta endpoint** para no inventar rutas en los frontends.

| Módulo | Endpoint real | Método | DTO Request | DTO Response | Rol | Pantalla |
|---|---|---:|---|---|---|---|
| Auth | `/api/auth/login` | POST | `LoginRequest` | `ApiResponse<AuthResponse>` | ADMIN, DONANTE, RECEPTOR | Login Admin / Login Mobile |
| Auth | `/api/auth/registro` | POST | `RegistroRequest` | `ApiResponse<AuthResponse>` | DONANTE, RECEPTOR | Registro Mobile |
| Auth sesión | **Falta endpoint `/api/auth/me`** | GET | - | - | ADMIN, DONANTE, RECEPTOR | Splash / restaurar sesión |
| Categorías | `/api/categorias` | GET | - | `ApiResponse<List<CategoriaResponse>>` | ADMIN | Categorías Admin |
| Categorías | `/api/categorias/activas` | GET | - | `ApiResponse<List<CategoriaResponse>>` | DONANTE, RECEPTOR | Formularios / filtros |
| Categorías | `/api/categorias` | POST | `CategoriaRequest` | `ApiResponse<CategoriaResponse>` | ADMIN | Crear categoría |
| Categorías | `/api/categorias/{id}` | PUT | `CategoriaRequest` | `ApiResponse<CategoriaResponse>` | ADMIN | Editar categoría |
| Categorías | `/api/categorias/{id}/estado` | PATCH | query `estado` | `ApiResponse<CategoriaResponse>` | ADMIN | Activar/desactivar categoría |
| Usuarios | `/api/admin/usuarios` | GET | - | `ApiResponse<List<UsuarioResponse>>` | ADMIN | Gestión usuarios |
| Usuarios | `/api/admin/usuarios/rol/{rol}` | GET | path `rol` | `ApiResponse<List<UsuarioResponse>>` | ADMIN | Filtro usuarios por rol |
| Usuarios | `/api/admin/usuarios/{id}` | GET | - | `ApiResponse<UsuarioResponse>` | ADMIN | Detalle usuario |
| Usuarios | `/api/admin/usuarios/{id}/estado` | PATCH | `CambiarEstadoRequest` | `ApiResponse<UsuarioResponse>` | ADMIN | Bloquear/activar usuario |
| Publicaciones públicas | `/api/public/publicaciones` | GET | query `distrito`, `categoria` | `ApiResponse<List<PublicacionResponse>>` | RECEPTOR | Alimentos disponibles |
| Publicaciones públicas | `/api/public/publicaciones/cercanas` | GET | query `latitud`, `longitud`, `radioKm` | `ApiResponse<List<PublicacionResponse>>` | RECEPTOR | Cercanía |
| Publicaciones públicas | `/api/public/publicaciones/{id}` | GET | - | `ApiResponse<PublicacionResponse>` | RECEPTOR | Detalle alimento |
| Publicaciones donante | `/api/donante/publicaciones` | POST | `PublicacionRequest` | `ApiResponse<PublicacionResponse>` | DONANTE | Publicar alimento |
| Publicaciones donante | `/api/donante/publicaciones` | GET | - | `ApiResponse<List<PublicacionResponse>>` | DONANTE | Mis publicaciones |
| Publicaciones donante | `/api/donante/publicaciones/{id}` | PUT | `PublicacionRequest` | `ApiResponse<PublicacionResponse>` | DONANTE | Editar publicación |
| Publicaciones donante | `/api/donante/publicaciones/{id}/confirmar-disponibilidad` | PATCH | - | `ApiResponse<PublicacionResponse>` | DONANTE | Confirmar disponibilidad |
| Publicaciones admin | `/api/admin/publicaciones` | GET | - | `ApiResponse<List<PublicacionResponse>>` | ADMIN | Gestión publicaciones |
| Publicaciones admin | `/api/admin/publicaciones/{id}/aprobar` | PATCH | - | `ApiResponse<PublicacionResponse>` | ADMIN | Aprobar publicación |
| Publicaciones admin | `/api/admin/publicaciones/{id}/bloquear` | PATCH | query `motivo` | `ApiResponse<PublicacionResponse>` | ADMIN | Bloquear publicación |
| Solicitudes receptor | `/api/receptor/solicitudes` | POST | `SolicitudRequest` | `ApiResponse<SolicitudResponse>` | RECEPTOR | Solicitar donación |
| Solicitudes receptor | `/api/receptor/solicitudes` | GET | - | `ApiResponse<List<SolicitudResponse>>` | RECEPTOR | Mis solicitudes |
| Solicitudes receptor | `/api/receptor/solicitudes/{id}/cancelar` | PATCH | - | `ApiResponse<SolicitudResponse>` | RECEPTOR | Cancelar solicitud |
| Solicitudes donante | `/api/donante/solicitudes` | GET | - | `ApiResponse<List<SolicitudResponse>>` | DONANTE | Solicitudes recibidas |
| Solicitudes donante | `/api/donante/solicitudes/{id}/aceptar` | PATCH | - | `ApiResponse<SolicitudResponse>` | DONANTE | Aceptar solicitud |
| Solicitudes donante | `/api/donante/solicitudes/{id}/rechazar` | PATCH | query `observacion` | `ApiResponse<SolicitudResponse>` | DONANTE | Rechazar solicitud |
| Entrega | `/api/solicitudes/{id}/confirmar-entrega` | PATCH | - | `ApiResponse<SolicitudResponse>` | DONANTE, RECEPTOR | Confirmar entrega/recepción |
| Dashboard | `/api/admin/dashboard/resumen` | GET | - | `ApiResponse<DashboardResumenResponse>` | ADMIN | Dashboard Admin |
| Reportes | `/api/admin/reportes` | GET | - | `ApiResponse<List<ReporteResponse>>` | ADMIN | Reportes |
| Reportes | `/api/reportes` | POST | `ReporteRequest` | `ApiResponse<ReporteResponse>` | DONANTE, RECEPTOR | Crear reporte |
| Historial | `/api/admin/historial/operaciones` | GET | - | `ApiResponse<List<HistorialOperacionResponse>>` | ADMIN | Historial Admin |
| Historial | `/api/historial/operaciones` | GET | - | `ApiResponse<List<HistorialOperacionResponse>>` | DONANTE, RECEPTOR | Historial móvil |
| Alertas | `/api/alertas` | GET | - | `ApiResponse<List<AlertaResponse>>` | DONANTE, RECEPTOR | Alertas móvil |
| Alertas Admin | **Falta endpoint admin para listar todas las alertas** | GET | - | - | ADMIN | Alertas Admin |
| Solicitudes Admin | **Falta endpoint admin para listar todas las solicitudes** | GET | - | - | ADMIN | Solicitudes Admin |
| Estadísticas gráficas | **Faltan endpoints agregados por mes/estado/distrito/categoría** | GET | - | - | ADMIN | Gráficos Dashboard |
