# Panel Administrativo Angular

Panel Angular 17 para el rol ADMIN/ADMINISTRADOR conectado a los endpoints reales del backend Spring Boot.

## Ejecutar
```bash
cd admin-web
npm install
npm start
```

API por defecto: `http://localhost:8080` en `src/environments/environment.ts`.

## Endpoints faltantes detectados
- `GET /api/auth/me` para validar sesión por token.
- `GET /api/admin/solicitudes` para administración global de solicitudes.
- `GET /api/admin/alertas` para administración global de alertas.
- Endpoints agregados para gráficas del dashboard por mes/estado/distrito/categoría.
