# GKlyphon-Multipurpose-User

![Java](https://img.shields.io/badge/Java-22-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-green)
![License](https://img.shields.io/badge/License-MIT-yellow)
![Build](https://img.shields.io/badge/Build-Maven-red)

*GKlyphon* is a Spring Boot project that provides comprehensive user management with role-based authentication and authorization. It's designed to handle multi-role systems with flexible control over user access and permissions.

## Setup


### Prerequisites
Ensure you have the following installed:
- *Java 22*
- *Maven 3.x*
- PostgreSQL (or any configured database)
- Redis


### Environment Variables
The following environment variables should be configured for the application to work correctly:

| Variable           | Description                              |
|--------------------|------------------------------------------|
| PBKDF2_SECRET    | 256-bit secret key for PBKDF2 password encoding |


### PBKDF2 Secret Key Configuration

For security, you need to generate a *256-bit secret key* to be used with PBKDF2. You can create one using this tool:

- [GigaHosta CodeIgniter Encryption Key Generator](https://gigahosta.com/tools/codeigniter-encryption-key-generator)

After generating the key, set it as an environment variable:

```bash
export PBKDF2_SECRET=your-256-bit-key
```


### Accessing the API Documentation
To access the API documentation via Swagger, make sure to run the application with the dev profile:

```bash
./mvnw spring-boot:run -Dspring.profiles.active=dev
```


Once the application is running, you can view the Swagger documentation at:

```bash
http://{server}:{port}/swagger-ui/index.html
```

> **Note:** Ensure that you are running the application with the `dev` profile to access the Swagger documentation.


## License
This project is licensed under the  Apache 2.0 license - see the LICENSE file for detail
