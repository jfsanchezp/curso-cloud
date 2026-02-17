# Práctica 2 - Arquitectura hexagonal
El objetivo de esta práctica es definir la estructura que tendrá un proyecto con arquitectura hexagonal, así como el orden natural que seguiremos para definir los endpoints que expondremos para resolver los casos de uso.

## Hito 1. Definir los endpoints
En la sesión anterior extrajimos los siguientes casos de uso a partir del enunciado:

| Caso de uso | Reglas de negocio |
| --- | --- | 
| Obtener un listado de vuelos | - El listado será paginado. <br>- Para cada vuelo se debe indicar si el pasajero tiene una reserva activa sobre él. |
| Reservar un vuelo | - Un pasajero no puede reservar dos veces un mismo vuelo. <br>- Sólo se pueden reservar vuelos que no estén iniciados, completados ni cancelados. <br> - Debe existir disponibilidad de plazas en el avión. |
| Cancelar una reserva de vuelo | - La reserva se puede cancelar antes de que se inicie el vuelo. <br>- Sólo puede cancelarla el titular de la reserva. <br>- No puede cancelarse una reserva ya cancelada.|

Partiendo de esto, tendremos que determinar los endpoints REST que vamos a implementar en nuestra API. Es importante que todos ellos sigan un enfoque REST, orientado a recursos, utilizando los verbos principales de HTTP para aportar semántica a las operaciones que realizaremos sobre esos recursos.

Al definir los endpoints debemos tener en cuenta:

- Los recursos se representan mediante sustantivos (por ejemplo: vuelos, reservas).
- Las operaciones se expresan mediante los verbos HTTP (GET, POST, DELETE, etc.).
- Las URLs deben representar recursos, no acciones.

Revisemos los siguientes ejemplos:

| Verbo HTTP | URL | ¿Es correcto? | Justificación |
| --- | --- | --- | --- |
| GET | /reservarVuelo | ❌ No | - GET no debe usarse para ejecutar una acción. <br>- La URL describe una acción, no un recurso. |
| POST | /reservas-vuelo | ✅ Sí | - POST representa la creación de un recurso. <br>- La URL representa el recurso `reservas-vuelo`. |
| POST | /reservas-vuelo/cancelar | ❌ No | - POST no representa una cancelación (semánticamente encaja mejor DELETE). <br>- La URL introduce una acción ("cancelar") en lugar de un recurso. |
| DELETE | /reservas-vuelo/{idReserva} | ✅ Sí | - Estamos operando sobre el recurso `reservas-vuelo`. <br>- DELETE es semánticamente correcto para representar una cancelación (normalmente como borrado lógico). |

En este punto sólo definiremos los recursos y operaciones. Los detalles del contenido, validaciones y representación los abordaremos más adelante.

<details>
  <summary>Endpoints definidos</summary>

| Verbo HTTP | URL |
| --- | --- |
| GET | /private/v1.0/vuelos |
| POST | /private/v1.0/reservas-vuelo | 
| DELETE | /private/v1.0/reservas-vuelo/{idReserva} |

</details>

Con esto hemos definido la interfaz pública de nuestra aplicación. En los siguientes hitos modelaremos cómo estos endpoints se conectan con los casos de uso mediante la arquitectura hexagonal.

## Hito 2. Recuperar el listado de vuelos
<img height="400px" alt="hexagonal-diagrama" src="https://github.com/user-attachments/assets/110364a7-0db7-4461-894c-405398dbe2cf" />

Si miramos el diagrama de la arquitectura, podemos ver que en la capa externa se encuentran los adaptadores. Recordemos que en esta capa viven todas las implementaciones ligadas al framework de nuestra aplicación.

Podríamos pensar que, como la interfaz REST está en la capa más externa, deberíamos empezar a definir la operación que consulta el listado de vuelos por ahí. Sin embargo, en una arquitectura hexagonal el punto de partida no es el framework, sino el caso de uso.

### Paso 1. Definir el caso de uso
Los casos de uso de nuestra aplicación viven en la capa que se sitúa entre la infraestructura y el dominio. Por convención, nombraremos esta capa como:

`es.um.atica.umufly.vuelos.application`

Dentro de ella, los casos de uso estarán bajo `es.um.atica.umufly.vuelos.application.usecase` y cada caso de uso tendrá su propio paquete.

En nuestro caso, para obtener el listado de vuelos, crearemos el paquete:

`es.um.atica.umufly.vuelos.application.usecase.getvuelos`

Dentro de este paquete crearemos la clase `GetVuelosUseCase`, que contendrá la lógica del caso de uso.

``` java
@Component
public class GetVuelosUseCase {

	public Page<Vuelo> getVuelos(int pagina, int tamanioPagina ) {
    // 1. Obtenemos y devolveremos el listado de vuelos 
	}
}

```
Aunque intentemos ser agnósticos al framework en las capas internas, es imposible serlo del todo. Estamos trabajando con Spring Boot y tendremos que apoyarnos en ciertos elementos para poder desarrollar nuestra aplicación.

Por ejemplo, utilizaremos `@Component` para indicar que esta clase debe ser gestionada por el contenedor de Spring. Del mismo modo, utilizaremos `org.springframework.data.domain.Page` para representar el resultado paginado.

Estas decisiones son prácticas y nos permiten avanzar más rápido, aunque en un diseño completamente independiente del framework podríamos abstraer también estos elementos.

### Paso 2. Definir los puertos para nutrir el caso de uso
Ya hemos definido el caso de uso, pero ¿cómo obtiene la información de los vuelos?

La respuesta es sencilla: debe definir un puerto que le permita acceder a esos datos. Este puerto se encontrará en la capa de aplicación y será implementado posteriormente por la capa de adaptadores. La capa de aplicación declara qué necesita, pero no se preocupa de cómo ni de dónde se obtienen los datos.

Los puertos los definiremos en el siguiente paquete:

`es.um.atica.umufly.vuelos.application.port`

Quedaría algo como lo siguiente:
``` java
public interface VuelosRepository {
	Page<Vuelo> findVuelos( int pagina, int tamanioPagina );
}
```
Ahora tendríamos que actualizar el caso de uso para utilizar este puerto.
``` java
@Component
public class GetVuelosUseCase {

  private final VuelosRepository vuelosRepository;

	public GetVuelosUseCase( VuelosRepository vuelosRepository ) {
		this.vuelosRepository = vuelosRepository;
	}

	public Page<Vuelo> getVuelos(int pagina, int tamanioPagina ) {
    return vuelosRepository.findVuelos(pagina, tamanioPagina);
	}
}
```
Es importante señalar que estamos utilizando Spring Boot 4. En versiones anteriores era habitual utilizar la anotación `@Autowired` para inyectar las dependencias de un componente. Sin embargo, en versiones actuales de Spring no es necesario utilizar esta anotación cuando la clase tiene un único constructor, y se recomienda la inyección de dependencias por constructor.

La inyección por constructor hace explícitas las dependencias reales de la clase, favorece la inmutabilidad al poder declararlas como `final` y facilita la realización de pruebas. Además, si el número de dependencias crece en exceso, el propio constructor nos ayudará a detectar un posible problema de diseño.

### Paso 3. Implementar el puerto en la capa de adaptadores
Ahora debemos ir a la capa de adaptadores para implementar el puerto que hemos definido (`VuelosRepository`). Recordemos que, en arquitectura hexagonal, la capa de aplicación define la interfaz (puerto) y la infraestructura aporta la implementación concreta.

En este caso recuperaremos el listado de vuelos desde una vista de base de datos facilitada por la empresa MuchoVuelo. 

La vista `FORMACION_TICARUM.VWEXT_VUELO` tiene la siguiente estructura:

| Columna           | Tipo           | Nulo |
|-------------------|---------------|------|
| ID                | VARCHAR2(36)  | No   |
| FECHA_SALIDA      | TIMESTAMP(6)  | No   |
| FECHA_LLEGADA     | TIMESTAMP(6)  | No   |
| TIPO_VUELO        | VARCHAR2(2)   | No   |
| ORIGEN            | VARCHAR2(500) | No   |
| DESTINO           | VARCHAR2(500) | No   |
| ESTADO_VUELO      | VARCHAR2(2)   | No   |
| CAPACIDAD_AVION   | NUMBER(5)     | No   |

Para poder consultar esta vista, crearemos una Entidad JPA asociada a la vista y un repositorio que nos permita paginar los resultados.

#### Dependencias necesarias
Antes de definir la entidad JPA, debemos asegurarnos de que nuestro proyecto incluye las dependencias necesarias para trabajar con Spring Data JPA y Oracle.

Para conocer las dependencias oficiales que ofrece Spring Boot podemos utilizar Spring Initializr:

`https://start.spring.io`

Seleccionando la versión de Spring Boot y las dependencias necesarias (por ejemplo: Spring Data JPA), podremos consultar el `pom.xml` generado y comprobar el artefacto exacto que debemos añadir.

Las dependencias que necesitaremos son las siguientes:
``` xml
		<!-- Inicio: JPA + Oracle driver -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>com.oracle.database.jdbc</groupId>
			<artifactId>ojdbc11</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<!-- Fin: JPA + Oracle driver -->
```

Con esto ya tenemos lo necesario para implementar el puerto que obtendrá el listado de vuelos. A continuación crearemos la entidad JPA asociada a la vista y el repositorio Spring Data que nos permitirá paginar los resultados.

#### Crear entidad JPA
Al igual que ocurría con los elementos de la capa de aplicación, los elementos de la capa de adaptadores irán bajo el paquete:

`es.um.atica.umufly.vuelos.adaptors`

En este caso estamos creando un adaptador de persistencia, por lo que todo lo relacionado con él se ubicará bajo:

`es.um.atica.umufly.vuelos.adaptors.persistence`

Al tratarse de entidades JPA, las crearemos en:

`es.um.atica.umufly.vuelos.adaptors.persistence.jpa.entity`


``` java
@Entity
@Table( name = "VWEXT_VUELO", schema = "FORMACION_TICARUM" )
public class VueloExtViewEntity {

	@Id
	@NotNull
	@Column( name = "ID", nullable = false, length = 36 )
	private String id;

	@NotNull
	@Column( name = "ESTADO_VUELO", length = 2, nullable = false )
	@Enumerated( value = EnumType.STRING )
	private EstadoVueloEnum estadoVuelo;
	...
}
```

Vemos que en la anotación `@Column` hemos indicado `nullable = false`. Con ello reflejamos que la columna en base de datos no admite valores nulos.

En algunos casos también podríamos utilizar la anotación `@NotNull` para reforzar esta restricción a nivel de aplicación. La diferencia es que `nullable = false` actúa a nivel de base de datos (a través de JPA), mientras que `@NotNull` realiza una validación previa antes de que la entidad sea persistida.

En este caso, al tratarse de una vista de solo lectura que no vamos a modificar, sería suficiente con indicar `nullable = false`. Sin embargo, añadimos `@NotNull` por coherencia con el modelo y para mantener alineadas las restricciones de base de datos y aplicación.

En el caso del campo `ESTADO_VUELO`, en lugar de utilizar un `String`, hemos decidido mapearlo a un `enum` (`EstadoVueloEnum`).

La anotación `@Enumerated(EnumType.STRING)` indica a JPA que debe persistir el nombre del valor del enum como texto. De esta forma, el valor almacenado en base de datos será el mismo que el definido en el enum.

Es importante utilizar `EnumType.STRING` y no `EnumType.ORDINAL`, ya que este último almacena la posición del enum (0, 1, 2, ...), lo que puede provocar inconsistencias si en el futuro se reordenan los valores.

#### Crear repositorio JPA
Tenemos la entidad; ahora necesitamos una manera de recuperarla. Spring Data JPA nos facilita esta tarea proporcionando una infraestructura que permite consultar y gestionar entidades JPA sin necesidad de escribir implementaciones manuales.

Este repositorio se ubicará en el paquete:

`es.um.atica.umufly.vuelos.adaptors.persistence.jpa.repository`

Y tendrá la siguiente forma:

```java
public interface JpaVueloRepository extends JpaRepository<VueloExtViewEntity, String> {

}
```
En este caso no necesitamos definir ningún método adicional, ya que al extender de JpaRepository disponemos del método `findAll(Pageable pageable)`, que nos permite obtener resultados paginados.

Si necesitásemos definir consultas más específicas, tenemos varias opciones:
- Definir el método siguiendo la nomenclatura de Spring Data JPA. Si respetamos las convenciones descritas en la documentación oficial, Spring generará automáticamente la consulta a partir del nombre del método (https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html).
- Utilizar la anotación `@Query` para definir la consulta explícitamente, ya sea en JPQL o como consulta nativa.

Este repositorio pertenece a la capa de infraestructura y será utilizado únicamente por el adaptador que implementa el puerto definido en la capa de aplicación.

#### Crear adaptador para recuperar el listado de vuelos

Pasamos ahora a implementar el adaptador que dará soporte al puerto encargado de recuperar el listado de vuelos.

Lo ubicaremos en:

`es.um.atica.umufly.vuelos.adaptors.persistence.jpa`

Como hemos visto antes, el adaptador recibirá los parámetros de paginación y devolverá una respuesta paginada de objetos del dominio (`Vuelo`). Para ello, apoyándose en el repositorio JPA, deberá transformar las entidades JPA en objetos del dominio. ¿Cómo lo hará? Mediante un mapper.

Definiremos un mapper encargado de traducir un objeto de infraestructura (la entidad JPA) a un objeto del dominio, que es lo que consumirá la capa de aplicación. Esta únicamente conoce `VuelosRepository` y `Vuelo`; todo lo relacionado con JPA queda encapsulado dentro del adaptador.

El adaptador quedaría así:
``` java
@Component
public class VuelosPersistenceAdapter implements VuelosRepository {

	private final JpaVueloRepository jpaVueloRepository;

	public VuelosPersistenceAdapter( JpaVueloRepository jpaVueloRepository ) {
		this.jpaVueloRepository = jpaVueloRepository;
	}

	@Override
	public Page<Vuelo> findVuelos( int pagina, int tamanioPagina ) {
		return jpaVueloRepository.findAll( PageRequest.of( pagina, tamanioPagina ) ).map( VueloMapper::vueloEntityToModel );
	}
}
```

Ahora debemos definir el mapper encargado de traducir las entidades a objetos del dominio. En este caso lo ubicaremos junto al adaptador, bajo el paquete:

`es.um.atica.umufly.vuelos.adaptors.persistence.jpa.mapper`

Y el mapper quedaría:

``` java
public class VueloMapper {

	private VueloMapper() {
		throw new IllegalStateException( "Clase de conversión" );
	}

	public static Vuelo vueloEntityToModel( VueloExtViewEntity v ) {
        return Vuelo.of(
                UUID.fromString(v.getId()),
                new Itinerario(v.getFechaSalida(), v.getFechaLlegada(), v.getOrigen(), v.getDestino()),
                tipoVueloEntityToModel(v.getTipoVuelo()),
                estadoVueloEntityToModel(v.getEstadoVuelo()),
                new Avion(v.getCapacidadAvion())
        );
	}
  // resto de métodos
}
```

#### ¿Por qué falla al arrancar la aplicación?
Si en este punto lanzamos la aplicación, fallará. ¿Por qué? Aunque ya hemos definido las entidades y los repositorios, todavía no le hemos indicado a Spring cómo conectarse a la base de datos.

Para ello, configuraremos el origen de datos (DataSource) en el fichero `application.properties`.

Si seguimos la guía de configuración de DataSource de la documentación oficial de Spring Boot (https://docs.spring.io/spring-boot/reference/data/sql.html#data.sql.datasource.configuration), veremos que necesitamos indicar, como mínimo, estas propiedades:

``` properties
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@hydra-prescan.atica.um.es:1526/ZEUSTEST
spring.datasource.username=FORMACION_TICARUM
spring.datasource.password=
```
Con esto sería suficiente para arrancar y poder consultar la base de datos.

Opcionalmente, si queremos que Hibernate valide el mapeo de nuestras entidades con el esquema existente, podemos añadir:

``` properties
spring.jpa.hibernate.ddl-auto=validate
logging.level.org.hibernate.orm.boot=TRACE
```
La propiedad `spring.jpa.hibernate.ddl-auto=validate` indica a Hibernate que valide que las entidades coinciden con el esquema existente, sin modificarlo.

Con la línea `logging.level.org.hibernate.orm.boot=TRACE` aumentamos el nivel de detalle del log durante el arranque, lo que nos permite visualizar cómo Hibernate interpreta los mapeos de las entidades JPA.

### Paso 4. Implementar el adaptador de entrada y conectar con el caso de uso
Bien, ya tenemos el caso de uso y el adaptador de persistencia que recupera el listado de vuelos disponibles. Ahora necesitamos una manera de exponer esta información al exterior.

El encargado de esto será el adaptador de entrada: un controlador REST cuya responsabilidad será recibir una petición HTTP, invocar el caso de uso y devolver una respuesta.

Al tratarse de un adaptador, todos los elementos relacionados con él se ubicarán bajo el paquete:

`es.um.atica.umufly.vuelos.adaptors.api.rest` 

#### DTOs 
Lo primero que debemos hacer es definir los DTOs que devolverá nuestro controlador REST. Si recordamos el enunciado, se nos pedía la siguiente información de un vuelo: identificador, fecha y hora de salida y llegada, si es nacional o internacional, aeropuerto de origen, aeropuerto de destino, estado del vuelo y capacidad del avión.

En una arquitectura hexagonal, el adaptador REST no debe exponer directamente el modelo de dominio. Para ello definimos DTOs específicos que representan la información que queremos publicar a través de la API.

De esta forma evitamos acoplar nuestra API al modelo de dominio y mantenemos la independencia entre capas.

Lo ideal sería que la definición del API REST estuviera documentada mediante OpenAPI. Sin embargo, esto lo abordaremos en temas posteriores.

Estos objetos los crearemos bajo el paquete:

`es.um.atica.umufly.vuelos.adaptors.api.rest.dto`

``` java
public class VueloDTO {

	private UUID id;
	private ItinerarioDTO itinerario;
	private TipoVuelo tipo;
	private EstadoVuelo estado;
	private AvionDTO avion;

	// constructor y getters/setters
}
```

Si no añadimos ninguna configuración adicional, Jackson serializará automáticamente este objeto a JSON utilizando los nombres de las propiedades como claves.

En caso de necesitar personalizar el nombre de las propiedades o controlar su acceso (solo lectura, solo escritura, etc.), podemos utilizar la anotación `@JsonProperty`.

### Mappers
Ahora necesitamos una manera de que el controlador REST pueda traducir los objetos que devuelve el caso de uso a los DTOs con los que trabaja. Como vimos antes, en este adaptador también necesitaremos un mapper.

El mapper lo depositaremos en:

`es.um.atica.umufly.vuelos.adaptors.api.rest.mapper`

``` java
public class VueloMapper {

	private VueloMapper() {
		throw new IllegalStateException( "Clase de conversión" );
	}

	public static VueloDTO vueloToDTO( Vuelo vuelo ) {
		return new VueloDTO(
			// Conversión del objeto del dominio a DTO
		);
	}
}
```

#### Paginación con HATEOAS
Pero con esto no es suficiente: el caso de uso nos devuelve una respuesta paginada, y necesitamos mantener esa paginación en la respuesta REST.

Spring ofrece una manera sencilla de convertir un `Page<Vuelo>` en una representación paginada con HATEOAS, incluyendo enlaces de navegación (por ejemplo: siguiente, anterior, primera y última página).

HATEOAS es un principio de diseño REST en el que el servidor incluye enlaces en la respuesta, de forma que el cliente pueda navegar por la API sin construir manualmente las URLs.

Para ello, añadiremos al `pom.xml` la siguiente dependencia:
``` xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

Ahora tendremos que definir un `RepresentationModelAssembler` que se encargue de traducir un objeto del dominio en un DTO. Si el DTO debe incluir enlaces HATEOAS, se añadirán en este punto, evitando así sobrecargar el controlador REST.

``` java
@Component
public class VuelosModelAssembler implements RepresentationModelAssembler<Vuelo, VueloDTO> {

	@Override
	public VueloDTO toModel( Vuelo entity ) {
		return VueloMapper.vueloToDTO( entity );
	}

}
```
Para poder utilizar HATEOAS, `VueloDTO` deberá extender de `RepresentationModel<VueloDTO>`:

```
@Relation( collectionRelation = "vuelos", itemRelation = "vuelo" )
public class VueloDTO extends RepresentationModel<VueloDTO> {
	...
}
```

La anotación `@Relation` permite indicar el nombre que tendrá la colección y los elementos individuales en la representación generada.

Por último, en el controlador REST inyectaremos un `PagedResourcesAssembler<Vuelo>` para convertir la respuesta paginada del caso de uso en un `PagedModel<VueloDTO>`, manteniendo tanto los datos como los metadatos de paginación.

Con todos estos elementos ya podemos construir el controlador REST.

#### Controlador REST
Es importante remarcar que el controlador REST no contiene lógica de negocio. Únicamente realizará la llamada al caso de uso y adaptará la entrada y salida al protocolo HTTP.


``` java
@RestController
public class VuelosEndpoint {

    private final GetVuelosUseCase getVuelosUseCase;
    private final VuelosModelAssembler vuelosModelAssembler;
    private final PagedResourcesAssembler<Vuelo> pagedResourcesAssembler;

    public VuelosEndpoint(GetVuelosUseCase getVuelosUseCase,
                          VuelosModelAssembler vuelosModelAssembler,
                          PagedResourcesAssembler<Vuelo> pagedResourcesAssembler) {
        this.getVuelosUseCase = getVuelosUseCase;
        this.vuelosModelAssembler = vuelosModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/private/v1.0/vuelos")
    public PagedModel<VueloDTO> getVuelos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "25") int size) {

        return pagedResourcesAssembler.toModel(
                getVuelosUseCase.getVuelos(page, size),
                vuelosModelAssembler
        );
    }
}
```
Algunos detalles importantes:

- La anotación `@RestController` indica que esta clase es un componente de Spring encargado de gestionar peticiones HTTP y devolver respuestas en formato JSON.
- La anotación `@GetMapping` define el endpoint HTTP que se expondrá, indicando que responderá a peticiones GET en la ruta especificada.
- Dado que partes de la URL pueden reutilizarse en varios endpoints, es recomendable extraerlas a un fichero de constantes.

Si invocamos el endpoint, veremos que la respuesta incluye el listado de vuelos, metadatos de paginación y enlaces HATEOAS (self, next, prev, etc.).

Con esto completamos el flujo completo: petición HTTP > controlador > caso de uso > adaptador de persistencia > base de datos, manteniendo separadas las responsabilidades de cada capa.

## Hito 3. Recuperar el listado de vuelos, indicando los que tienen reserva

Hemos conseguido obtener el listado de vuelos; ya tenemos la funcionalidad mínima implementada. Sin embargo, eso no es exactamente lo que se nos pedía. En el enunciado del caso práctico se indica que, además del listado, debe reflejarse si el usuario tiene cada vuelo reservado.

Podríamos plantear varias alternativas:

- Delegar esta responsabilidad en el front. El cliente recuperaría el listado de vuelos y, posteriormente, llamaría a otro endpoint para obtener las reservas del usuario y realizar la combinación en el propio front.
- Adaptar el caso de uso para que devuelva la información enriquecida.

La primera alternativa desplaza la responsabilidad al cliente y obliga a realizar una composición artificial fuera del servidor. En cambio, la segunda opción es más coherente desde el punto de vista de arquitectura: el caso de uso es quien conoce el listado de vuelos y las reservas del usuario, por lo que es el lugar adecuado para combinar esta información.

### Paso 1. Modificación del caso de uso
Una vez hemos decidido que vamos a modificar el caso de uso, debemos decidir cómo devolveremos esta información. ¿El caso de uso seguirá devolviendo el objeto del dominio `Vuelo`? No.

El agregado `Vuelo` no contiene información sobre las reservas realizadas y no deberíamos modificarlo para incluir información contextual que no forma parte de su responsabilidad.

#### DTOs en capa de aplicación

En su lugar, crearemos un DTO en la capa de aplicación que contendrá la información que el adaptador REST necesita exponer.

Podríamos pensar que ese DTO, llamémosle `VueloAmpliadoDTO`, podría contener directamente los objetos del dominio `Vuelo` y `Reserva`. Sin embargo, esto generaría una fuga del dominio, ya que la capa de aplicación quedaría acoplada al modelo interno del dominio.

El DTO de aplicación no debe exponer objetos del dominio, sino únicamente los datos necesarios para representar la información enriquecida.

Si el usuario tiene una reserva sobre un vuelo, necesitaremos conocer cuál es. Aunque el caso de uso no debe preocuparse por HATEOAS ni por la construcción de enlaces, sí debe proporcionar la información necesaria para que el adaptador REST pueda generarlos.

Por ello, el DTO de aplicación incluirá, además de la información del vuelo, el identificador (`UUID`) de la reserva en caso de que exista.

Los DTOs de la capa de aplicación irán bajo el siguiente paquete:

`es.um.atica.umufly.vuelos.application.dto` 

Y quedará tal que así:

``` java
public class VueloAmpliadoDTO {

	private UUID idVuelo;
	private LocalDateTime fechaSalida;
	private LocalDateTime fechaLlegada;
	private String origen;
	private String destino;
	private String tipoVuelo;
	private String estadoVuelo;
	private Integer capacidadAvion;

	// Datos ampliados
	private UUID idReserva;

	// Constructor, getters y setters
}
```
En este DTO, `idReserva` será `null` cuando el usuario no tenga una reserva activa para ese vuelo.

#### Nuevo puerto para recuperar información de las reservas

El siguiente paso será ajustar la firma del caso de uso para recibir información de identificación del pasajero sobre el que se está lanzando la consulta.

``` java
	public Page<VueloAmpliadoDTO> getVuelos(DocumentoIdentidad documentoIdentidadPasajero, int pagina, int tamanioPagina ) {
		// 1. obtenemos el listado de vuelos paginados
		// 2. consultamos las reservas que tienee el usuario en esos vuelos
		// 3. convertimos el resultado y lo devolvemos
	}
```

Pero, ¿cómo obtenemos las reservas dado un listado de vuelos? ¿Creamos un nuevo método en el puerto `VuelosRepository`? No. Estamos tratando con reservas de vuelo, deberíamos crear un puerto nuevo para esto, ya que no es responsabilidad del vuelo saber las reservas de vuelo que tiene asociadas.

El caso de uso necesita ahora dos fuentes de información distintas: los vuelos y las reservas del pasajero. Por tanto, necesitaremos un nuevo puerto que permita acceder a esta información.

El nuevo puerto podría ser algo como lo siguiente:

``` java
public interface ReservasVueloRepository {

	/**
	 * Obtiene las reservas asociadas a un pasajero para un conjunto de vuelos.
	 *
	 * @param documentoIdentidadPasajero
	 *                                   documento de identidad del pasajero
	 * @param vueloIds
	 *                                   identificadores de los vuelos a consultar
	 * @return mapa cuya clave es el identificador del vuelo y cuyo valor es el identificador de la reserva asociada a dicho
	 *         vuelo; solo se incluyen los vuelos para los que el pasajero tiene una reserva activa
	 */
	Map<UUID, UUID> findReservasActivasPorVuelosYPasajero( DocumentoIdentidad documentoIdentidadPasajero, List<UUID> vueloIds );
}
```
Devolvemos un `Map<UUID, UUID>` en lugar de una lista de reservas. El caso de uso solo necesita saber si existe una reserva para cada vuelo y cuál es su identificador. Devolver más información generaría acoplamiento innecesario.

#### ¿Necesitaremos un mapper aquí también?
Sí. Igual que en los adaptadores, aquí necesitaremos transformar el resultado a un modelo de salida del caso de uso (el DTO de aplicación), que es el que consumirá el adaptador REST. Estará bajo el paquete `es.um.atica.umufly.vuelos.application.mapper`.

``` java
public class VueloAmpliadoMapper {

	private VueloMapper() {
		throw new IllegalStateException( "Clase de conversión" );
	}

	public static VueloAmpliadoDTO vueloModelToDTO( Vuelo v, UUID idReserva ) {
		// Conversión a vuelo ampliado
	}
}

```

En proyectos de mayor tamaño podría tener sentido organizar estos elementos por caso de uso. Para este curso los agruparemos bajo el paquete `application` para simplificar la estructura y centrarnos en los conceptos arquitectónicos.

#### Caso de uso completo

Ya tenemos definidos los puertos que necesita el caso de uso, ahora tenemos que adaptarlo para combinar la información y transformarla al nuevo DTO que hemos creado.

``` java
@Component
public class GetVuelosUseCase {

    private final VuelosRepository vuelosRepository;
    private final ReservasVueloRepository reservasVueloRepository;

    public GetVuelosUseCase(VuelosRepository vuelosRepository,
                            ReservasVueloRepository reservasVueloRepository) {
        this.vuelosRepository = vuelosRepository;
        this.reservasVueloRepository = reservasVueloRepository;
    }

    public Page<VueloAmpliadoDTO> getVuelos(DocumentoIdentidad documentoIdentidadPasajero,
                                           int pagina,
                                           int tamanioPagina) {

        Page<Vuelo> vuelos = vuelosRepository.findVuelos(pagina, tamanioPagina);

        if (documentoIdentidadPasajero == null) {
            return vuelos.map(v -> VueloAmpliadoMapper.toDto(v, null));
        }

        List<UUID> vueloIds = vuelos.getContent().stream().map(Vuelo::getId).toList();
        Map<UUID, UUID> reservaIdPorVueloId =
                reservasVueloRepository.findReservasActivasPorVuelosYPasajero(documentoIdentidadPasajero, vueloIds);

        return vuelos.map(v -> VueloAmpliadoMapper.vueloModelToDTO(v, reservaIdPorVueloId.get(v.getId())));
    }
}
```

La comprobación `if (documentoIdentidadPasajero == null)` permite mantener la retrocompatibilidad con la versión anterior del endpoint. 

Aunque abordaremos el versionado en detalle más adelante, es fundamental que la evolución de la API no rompa el comportamiento ya existente.

### Paso 2. Crear adaptador para el nuevo puerto

Ya tenemos el caso de uso adaptado a la nueva funcionalidad. El siguiente paso será implementar el adaptador que provea la información que necesita el caso de uso.

En el esquema de base de datos de nuestra API tenemos las siguientes tablas que almacenan información sobre las reservas de vuelo que se han solicitado y los pasajeros de las mismas.

Tabla: `FORMACION_TICARUM.RESERVA_VUELO`

| Columna                  | Tipo         | Nulo | Descripción                              |
| ------------------------ | ------------ | ---- | ---------------------------------------- |
| ID                       | VARCHAR2(36) | No   | Identificador de la reserva              |
| TIPO_DOCUMENTO_TITULAR   | VARCHAR2(2)  | No   | Tipo de documento del titular            |
| NUMERO_DOCUMENTO_TITULAR | VARCHAR2(15) | No   | Número de documento del titular          |
| ID_VUELO                 | VARCHAR2(36) | No   | Identificador del vuelo reservado        |
| CLASE_ASIENTO_RESERVA    | VARCHAR2(2)  | No   | Clase de asiento reservada               |
| FECHA_CREACION           | TIMESTAMP    | No   | Fecha de creación de la reserva          |
| FECHA_MODIFICACION       | TIMESTAMP    | No   | Fecha de última modificación             |
| ESTADO_RESERVA           | VARCHAR2(2)  | No   | Estado actual de la reserva              |

Tabla: `RESERVA_VUELO_PASAJERO`

| Columna          | Tipo          | Nulo | Descripción                              |
| ---------------- | ------------- | ---- | ---------------------------------------- |
| ID               | VARCHAR2(36)  | No   | Identificador del pasajero en la reserva |
| ID_RESERVA_VUELO | VARCHAR2(36)  | No   | Identificador de la reserva asociada     |
| TIPO_DOCUMENTO   | VARCHAR2(2)   | No   | Tipo de documento del pasajero           |
| NUMERO_DOCUMENTO | VARCHAR2(15)  | No   | Número de documento del pasajero         |
| NOMBRE           | VARCHAR2(100) | No   | Nombre del pasajero                      |
| PRIMER_APELLIDO  | VARCHAR2(100) | No   | Primer apellido                          |
| SEGUNDO_APELLIDO | VARCHAR2(100) | Sí   | Segundo apellido                         |
| EMAIL            | VARCHAR2(300) | No   | Correo electrónico                       |
| NACIONALIDAD     | VARCHAR2(300) | No   | Nacionalidad                             |


#### Crear entidades JPA
Lo primero que necesitaremos será crear las entidades JPA para poder recuperar la información de las reservas que tiene un pasajero dado un listado de vuelos.

En base de datos, se ha modelado de tal forma que una reserva de vuelo puede tener varios pasajeros, por lo que existe una relación 1:N entre `RESERVA_VUELO` y `RESERVA_VUELO_PASAJERO`.

Para reflejarla en JPA usaremos `@OneToMany` en `ReservaVueloEntity` y `@ManyToOne` en `ReservaVueloPasajeroEntity`.

En `ReservaVueloPasajeroEntity` tendremos:
``` java
@Entity
@Table( name = "RESERVA_VUELO_PASAJERO", schema = "FORMACION_TICARUM" )
public class ReservaVueloPasajeroEntity {

	...

	@NotNull
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "ID_RESERVA_VUELO", nullable = false )
	private ReservaVueloEntity reservaVuelo;

	...
}
```

Mientras que, en `ReservaVueloEntity` tendremos:
``` java
@Entity
@Table( name = "RESERVA_VUELO", schema = "FORMACION_TICARUM" )
public class ReservaVueloEntity {

	...
	@OneToMany( mappedBy = "reservaVuelo", fetch = FetchType.LAZY )
	private List<ReservaVueloPasajeroEntity> pasajeros;
	...
}
```
Indicamos `FetchType.LAZY` para evitar que, al consultar reservas, se cargue automáticamente la lista completa de pasajeros si no es necesaria.

#### Crear repositorio JPA
Ahora, al igual que sucedía en la primera parte de la práctica, debemos crear el repositorio JPA que nos permita recuperar las reservas que tiene un pasajero para un conjunto determinado de vuelos.

Los requisitos de la consulta que necesitamos son los siguientes:
- Filtrar por el tipo de documento de identidad del pasajero (`pasajeros.tipoDocumento`).
- Filtrar por el número de documento de identidad del pasajero (`pasajeros.numeroDocumento`).
- Filtrar por un listado de vuelos (`idVuelo IN (...)`).
- Filtrar por un listado de estados de la reserva (`estadoReserva IN (...)`).

Spring Data JPA nos permite definir esta consulta mediante una query derivada, es decir, construyendo el método siguiendo sus convenciones de nomenclatura.

El repositorio quedaría de la siguiente forma:
``` java
public interface JpaReservaVueloRepository extends JpaRepository<ReservaVueloEntity, String> {

	List<ReservaVueloEntity> findByPasajerosTipoDocumentoAndPasajerosNumeroDocumentoAndIdVueloInAndEstadoReservaIn(
			TipoDocumentoEnum tipoDocumento,
			String numeroDocumento,
			List<String> idsVuelo,
			List<EstadoReservaVueloEnum> estados
	);

}
```
Al arrancar la aplicación, Spring Data JPA validará la firma del método. Posteriormente, cuando este sea invocado, generará dinámicamente la consulta correspondiente y la traducirá a SQL específico de Oracle a través de Hibernate.

Como vimos anteriormente, si seguimos las convenciones de nomenclatura descritas en la documentación oficial (https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html), Spring Data JPA es capaz de construir automáticamente la consulta a partir del nombre del método, sin necesidad de definir explícitamente una anotación `@Query`.

#### Crear adaptador para recuperar el listado de reservas
Por último crearemos el adaptador del puerto que hemos definido antes para recuperar el listado de reservas de vuelo dado un listado de vuelos para un pasajero concreto.

``` java
@Component
public class ReservasVueloPersistenceAdapter implements ReservasVueloRepository {

	private final JpaReservaVueloRepository jpaReservaVueloRepository;

	public ReservasVueloPersistenceAdapter( JpaReservaVueloRepository jpaReservaVueloRepository ) {
		this.jpaReservaVueloRepository = jpaReservaVueloRepository;
	}

	public Map<UUID, UUID> findReservaIdByVueloIdAndPasajero(
			DocumentoIdentidad documentoIdentidadPasajero,
			List<UUID> vueloIds ) {
		if ( vueloIds.isEmpty() ) {
			return Collections.emptyMap();
		}

		List<ReservaVueloEntity> reservasVuelo =
				jpaReservaVueloRepository.findByPasajerosTipoDocumentoAndPasajerosNumeroDocumentoAndIdVueloInAndEstadoReservaIn(
						// Tipo de documento de identidad del pasajero convertido
						ReservaVueloMapper.tipoDocumentoEntityFromModel( documentoIdentidadPasajero.tipo() ),
						// Número de documento del pasajero
						documentoIdentidadPasajero.identificador(),
						// El listado de identificadores de vuelos
						vueloIds.stream().map( UUID::toString ).toList(),
						// Los estados "activos" son pendiente y activo
						Arrays.asList( EstadoReservaVueloEnum.P, EstadoReservaVueloEnum.A )
				);
		return reservasVuelo.stream()
				.collect( Collectors.toMap(
						r -> UUID.fromString( r.getIdVuelo() ),
						r -> UUID.fromString( r.getId() )
				) );
	}
}

```

### Paso 3. Adaptar y versionar el adaptador de entrada
Llegados a este punto, debemos volver al adaptador REST para que tenga en cuenta las modificaciones del caso de uso. Aquí se nos plantean dos alternativas:

- Modificar el endpoint ya expuesto para que devuelva la información ampliada.
- Versionar el API: mantener la v1 con el comportamiento actual y exponer una v2 que incluya si el pasajero tiene una reserva en cada vuelo.

Por norma general, siempre que un cambio no sea retrocompatible debemos versionar el endpoint. Es posible que algún cliente esté consumiendo la v1 y, si cambiamos la respuesta sin control, romperemos su integración.

Por ello, mantendremos `/private/v1.0/vuelos` tal y como está y añadiremos un nuevo endpoint `/private/v2.0/vuelos`.

#### Adaptaciones en v1
Aunque hemos versionado el endpoint, el caso de uso ya devuelve el modelo ampliado (`VueloAmpliadoDTO`). Para mantener la retrocompatibilidad, la versión 1 seguirá devolviendo la misma representación que antes, simplemente ignorando los nuevos campos.

Además, reorganizaremos la paquetería para separar claramente las versiones:

```
es.um.atica.umufly.vuelos.adaptors.api.rest.v1
es.um.atica.umufly.vuelos.adaptors.api.rest.v1.dto
es.um.atica.umufly.vuelos.adaptors.api.rest.v1.mapper
```
De esta forma, cada versión tendrá su propio controlador, DTOs y mappers.

#### Nueva versión del API
Seguiremos ahora los mismos pasos que hicimos para la creación de la versión 1 del endpoint de vuelos, pero depositando todos los elementos que necesitemos bajo:

`es.um.atica.umufly.vuelos.adaptors.api.rest.v2`.

En esta versión no necesitamos modificar los DTOs. La información sobre la reserva no se añadirá como un nuevo campo, sino como un enlace HATEOAS cuando exista.

Es decir, si el pasajero tiene una reserva sobre un vuelo, el vuelo incluirá un enlace adicional hacia el recurso `reserva-vuelo`. Si no la tiene, dicho enlace no aparecerá.

Podemos reutilizar los DTOs definidos en la versión 1.

A continuación definiremos el `RepresentationModelAssembler` encargado de transformar el `VueloAmpliadoDTO` que devuelve el caso de uso en el `VueloDTO` que expondrá la API.

``` java
@Component
public class VuelosModelAssemblerV2 implements RepresentationModelAssembler<VueloAmpliadoDTO, VueloDTO> {

	private final LinkService linkService;

	public VuelosModelAssemblerV2( LinkService linkService ) {
		this.linkService = linkService;
	}

	@Override
	public VueloDTO toModel( VueloAmpliadoDTO entity ) {

		VueloDTO vuelo = VueloMapper.vueloToDTO( entity );

		if ( entity.getIdReserva() != null ) {
			vuelo.add( linkReserva( entity.getIdReserva() ) );
		}

		return vuelo;
	}

	private Link linkReserva( UUID idReserva ) {

		String href = linkService.privateApiV2()
				.path( Constants.RESOURCE_RESERVAS_VUELO )
				.pathSegment( idReserva.toString() )
				.build()
				.toString();

		return Link.of( href ).withRel( "reserva-vuelo" );
	}
}
```
Observaciones importantes:
- El ModelAssembler es el lugar adecuado para construir enlaces HATEOAS.
- El caso de uso no se preocupa de enlaces ni de HTTP; únicamente devuelve los datos necesarios.
- El enlace solo se añade cuando `idReserva` no es null.

Para centralizar la construcción de URLs base utilizaremos un componente auxiliar. Este componente nos permite construir enlaces de forma consistente sin acoplar el ModelAssembler a detalles de la URL base.

<details>
	<summary>LinkService</summary>
	
``` java 
	package es.um.atica.umufly.vuelos.adaptors.api.rest;

	import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
	import org.springframework.stereotype.Component;
	import org.springframework.web.util.DefaultUriBuilderFactory;
	import org.springframework.web.util.UriBuilder;

	@Component
	public class LinkService {
	
		private UriBuilder base() {
			return new DefaultUriBuilderFactory( BasicLinkBuilder.linkToCurrentMapping().toString() ).builder();
		}
	
		public UriBuilder privateApiV2() {
			return base().path( Constants.PRIVATE_PREFIX ).path( Constants.API_VERSION_2 );
		}
	
	}
```
</details>

Por último, crearemos el controlador REST correspondiente a la versión 2 del API.

En esta versión necesitamos identificar al usuario para poder consultar sus reservas. Para ello solicitaremos su documento de identidad en una cabecera HTTP llamada UMU-Usuario.

En sesiones posteriores veremos mecanismos más seguros para autenticación y autorización.

``` java
@RestController
public class VuelosEndpointV2 {

	private final GetVuelosUseCase getVuelosUseCase;
	private final VuelosModelAssemblerV2 vuelosModelAssembler;
	private final PagedResourcesAssembler<VueloAmpliadoDTO> pagedResourcesAssembler;
	private final AuthService authService;

	public VuelosEndpointV2(
			GetVuelosUseCase getVuelosUseCase,
			VuelosModelAssemblerV2 vuelosModelAssembler,
			PagedResourcesAssembler<VueloAmpliadoDTO> pagedResourcesAssembler,
			AuthService authService ) {
		this.getVuelosUseCase = getVuelosUseCase;
		this.vuelosModelAssembler = vuelosModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.authService = authService;
	}

	@GetMapping( Constants.PRIVATE_PREFIX + Constants.API_VERSION_2 + Constants.RESOURCE_VUELOS )
	public PagedModel<VueloDTO> getVuelos(
			@RequestHeader( name = "UMU-Usuario", required = true ) String usuario,
			@RequestParam( name = "page", defaultValue = "0" ) int page,
			@RequestParam( name = "size", defaultValue = "25" ) int size ) {

		DocumentoIdentidad documento = authService.parseUserHeader( usuario );
		return pagedResourcesAssembler.toModel(
				getVuelosUseCase.getVuelos( documento, page, size ),
				vuelosModelAssembler
		);
	}
}
```

`AuthService` es un componente transforma la cabecera `UMU-Usuario` en un objeto de dominio `DocumentoIdentidad`.
<details>
	<summary>AuthService</summary>

``` java
	
	@Component
	public class AuthService {
	
		public DocumentoIdentidad parseUserHeader( String userHeader ) {
			if ( userHeader == null ) {
				throw new IllegalArgumentException( "La cabecera no tiene un formato correcto" );
			}
	
			String[] documentoIdentidad = userHeader.split( ":" );
			if ( documentoIdentidad.length != 2 ) {
				throw new IllegalArgumentException( "La cabecera no tiene un formato correcto" );
			}
	
			return new DocumentoIdentidad( TipoDocumento.valueOf( documentoIdentidad[0] ), documentoIdentidad[1] );
		}
	}
```
</details>

