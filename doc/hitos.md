1. Domain Driven Design (4 horas)
	1. Conceptos
	2. Tipos de elementos

Hito: Modelo completo tanto en papel como Java (solo dominio)
	Metodos con "funcionalidad" sin codigo con un "TODO"
	VO completo y validaciones
	- ¿record?

2. Arquitecturas hexagonales (8 horas)
	1. Diseño y estructuración
	2. Soluciones a problemas comunes

Hito: Proyecto hexagonal completo SIN eventos
	- Todo en base de datos directamente lecturas (sesion 1)
	- Persisto en BBDD y notifico via ORDS a otra BBDD (sesion 2)

3. CQRS: Command Query Responsability Segregation (4 horas)
1. Objetivo de CQRS
2. Tipos de elementos

- Hito: Separar el código entre lecturas y escrituras


4. Gestión de eventos (4 horas)
1. Eventos
2. CQRS asíncrono

- Hito: Modificar el código para que funcione con eventos
	- Solo eventos de escritura
	- Y valicaciones (eventos intencionados)
	- Comentar SSE


5. Testing (4 horas)

1. Test de integración
2. Test de carga

- Hito: Junit, Cucumber, VisualVM, JMeter


6. Gestión de la calidad (8 horas )

1. Análisis estático de código
2. Capa de seguridad en aplicaciones
3. Autenticación. Seguridad en los desarrollos
5. Versionado de proyectos
6. Documentación de APIs
7. CI/CD

- Hitos:
	- Validacion
	- Security de Springboot
	- Autenticacion y  autorizacion
	- Acceder solo a mis datos
	- Comprobar solo usuario umu
	- OpenAPI