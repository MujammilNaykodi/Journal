# Security Configuration Documentation

This document describes the security configuration for the Journal App application.

## Overview

The security configuration is managed by the `SpringScurity` class, which implements Spring Security configurations to protect sensitive endpoints and manage user authentication.

## Configuration Details

### Class: SpringScurity

Located in: `com.naykodi.journalApp.config.SpringScurity`

This configuration class sets up the security filter chain and password encoding for the application.

## Security Filter Chain

### Endpoints Protection

The application implements the following security rules:

#### Protected Endpoints (Requires Authentication)
```
/journal/**    - All journal entry endpoints
/users/**      - All user endpoints (except registration)
```

These endpoints require a valid authenticated user to access.

#### Public Endpoints (No Authentication Required)
```
All other endpoints
```

Including public endpoints for user registration and information access.

## Security Features

### 1. CSRF Protection
- **Status**: Disabled
- **Reason**: REST API applications typically use stateless authentication and don't require CSRF protection

### 2. Authorization Rules
```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/journal/**", "/users/**").authenticated()
    .anyRequest().permitAll()
)
```

- Journal and user endpoints require authentication
- All other requests are publicly accessible

### 3. HTTP Basic Authentication
- **Enabled**: Yes
- **Type**: HTTP Basic Authentication
- **Usage**: HTTP requests include `Authorization: Basic <base64(username:password)>` header

### 4. Password Encryption
- **Algorithm**: BCrypt
- **Strength**: Default (12 rounds)
- **Bean**: `PasswordEncoder`

Passwords are encrypted using BCrypt for secure storage in the database.

## Components

### UserDetailsImplementation
- **Location**: `com.naykodi.journalApp.service.UserDetailsImplementation`
- **Purpose**: Custom implementation of Spring Security's `UserDetailsService`
- **Responsibility**: 
  - Load user details from the database
  - Validate user credentials
  - Provide user authorities and roles

### PasswordEncoder Bean
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

- Returns a BCryptPasswordEncoder instance
- Used throughout the application for password encoding/validation
- Automatically injected into services that need password operations

## Security Filter Chain Configuration

The security filter chain is built using Spring Security's fluent API:

1. **CSRF**: Disabled for stateless API
2. **Authorization**: Protected endpoints require authentication
3. **HTTP Basic**: Enables HTTP Basic authentication
4. **User Details Service**: Uses custom `UserDetailsImplementation` for user loading

## How Authentication Works

1. Client sends a request with HTTP Basic credentials:
   ```
   Authorization: Basic base64(username:password)
   ```

2. Spring Security intercepts the request

3. `UserDetailsImplementation` loads the user from the database

4. Password is validated using BCryptPasswordEncoder

5. If valid, user is authenticated and request is processed

6. Protected resources (/journal/**, /users/**) are now accessible

7. Unprotected resources are accessible without authentication

## Configuration Class Annotations

| Annotation | Purpose |
|-----------|---------|
| `@Configuration` | Marks this as a Spring configuration class |
| `@Bean` | Registers methods as Spring beans |
| `@Autowired` | Injects the UserDetailsImplementation service |

## Best Practices Implemented

✅ Password encryption using BCrypt  
✅ HTTP Basic authentication for stateless APIs  
✅ Endpoint-based authorization rules  
✅ Separation of protected and public endpoints  
✅ CSRF protection appropriate to API type  

## Future Security Enhancements

Consider implementing:

- **JWT Authentication**: More secure than HTTP Basic for APIs
- **Role-Based Access Control (RBAC)**: Different user roles with specific permissions
- **API Key Authentication**: For service-to-service communication
- **OAuth 2.0**: For third-party integrations
- **HTTPS Enforcement**: Secure communication in production
- **Rate Limiting**: Prevent brute force attacks
- **Input Validation**: Sanitize user inputs to prevent injection attacks

## Configuration Properties

Additional security configurations can be added to `application.properties`:

```properties
# Security configuration examples
server.servlet.session.timeout=30m
spring.security.require-ssl=false  # Set to true in production
```

## Testing Security

When testing the application:

1. **Without authentication**:
   ```bash
   curl http://localhost:8080/journal
   # Returns 401 Unauthorized
   ```

2. **With HTTP Basic authentication**:
   ```bash
   curl -u username:password http://localhost:8080/journal
   # Returns journal entries if credentials are valid
   ```

## Troubleshooting

### 401 Unauthorized
- Ensure correct username and password
- Check if endpoint requires authentication
- Verify HTTP Basic header format

### 403 Forbidden
- User authenticated but lacks required permissions
- Check endpoint authorization rules

### Password Validation Fails
- BCrypt expects hashed passwords in database
- Ensure passwords are encoded before storing
- Use `passwordEncoder()` bean for encoding

## Related Files

- [SpringScurity.java](SpringScurity.java) - Main security configuration
- [UserDetailsImplementation.java](../service/UserDetailsImplementation.java) - User details service
- [UserService.java](../service/UserService.java) - User business logic
- [UserEntity.java](../Entity/UserEntity.java) - User data model

## References

- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [BCrypt Password Encoder](https://en.wikipedia.org/wiki/Bcrypt)
- [HTTP Basic Authentication](https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication)

---

**Last Updated**: February 2026  
**Spring Boot Version**: 4.0.2  
**Spring Security Version**: Included in Spring Boot Starter Security
