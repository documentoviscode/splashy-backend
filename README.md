# SplashyTV REST API

Java JDK: **17**

### Config: 
- default application port: `8080`
- All endpoints will be available at: `/api/v1/`
- Swagger UI available at `/api/v1/swagger-ui/index.html`
- H2 console available at `/api/v1/h2-console`

### Purpose of packages:
- `bootstrap` - create db objects that you want to be present after application startup
- `config` - project's config
- `controllers` - controllers
- `data` - this package is for objects that represent user's request bodies and rest responses
- `domain` - package for database classes
- `exceptions` - custom exceptions
- `repositories` - repositories
- `services` - services
