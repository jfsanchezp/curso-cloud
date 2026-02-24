# Práctica 3 - Arquitectura hexagonal II
Hasta ahora hemos completado un caso de uso: consultar el listado de vuelos.

Antes de continuar, vamos a repasar el flujo que ha seguido la petición a través de las capas que componen nuestra Arquitectura Hexagonal, para entender bien qué papel juega cada elemento y cómo se relacionan entre sí.

[comment]: <> (Imagen generada con PlantUML)
[![](https://img.plantuml.biz/plantuml/svg/TL9VJy8m47zVikzmsPF6G8mVm307KLx8KeGdNyxr9DNHpklDI9JlRhi1Wf6NvjhVtrmwye8ixRfE4p5I4-JytXgXIbf6UA1FN3cBDGoWSDQtjI1eWcLBjVDm1OeyyKTygw54Zw_4BatInByPso3Z6ZXOeLq5vwyq0L2kjRfmR8dXvZfDjiVSogqRPydAN3P1S916nmC6MPy-iRfnnigJlSWrDeBQCQZ9_14cLXWrNcQ07gQpgFvjaULDKviAnJYRnKwnrP7vFVa88VSf2qy5UkgiYaLaHtw89Nx1YlPb53NE6t6y2NNAbbXS7ryk__1t6_ETxhnCzK7J6Gm6jrqoXBExz9RxY-exRV_TKKdiZHUo5NKx8kxSy-K9zwJIEKd8qIZu79O1sLqympyOepZVuNVZM21Vmd3utLVCrMvMEoJMZRG0xCSq6P7LuVVz0000)](https://editor.plantuml.com/uml/TL9VJy8m47zVikzmsPF6G8mVm307KLx8KeGdNyxr9DNHpklDI9JlRhi1Wf6NvjhVtrmwye8ixRfE4p5I4-JytXgXIbf6UA1FN3cBDGoWSDQtjI1eWcLBjVDm1OeyyKTygw54Zw_4BatInByPso3Z6ZXOeLq5vwyq0L2kjRfmR8dXvZfDjiVSogqRPydAN3P1S916nmC6MPy-iRfnnigJlSWrDeBQCQZ9_14cLXWrNcQ07gQpgFvjaULDKviAnJYRnKwnrP7vFVa88VSf2qy5UkgiYaLaHtw89Nx1YlPb53NE6t6y2NNAbbXS7ryk__1t6_ETxhnCzK7J6Gm6jrqoXBExz9RxY-exRV_TKKdiZHUo5NKx8kxSy-K9zwJIEKd8qIZu79O1sLqympyOepZVuNVZM21Vmd3utLVCrMvMEoJMZRG0xCSq6P7LuVVz0000)

| Orden | Capa implicada | Descripción del flujo |
| --- | --- | --- |
| 1 | Adapter | Se inicia el flujo desde la capa REST, que adapta la petición HTTP y delega en el caso de uso. |
| 2 | Application | El caso de uso llama al puerto `VuelosRepository` para recuperar el listado de vuelos paginado. |
| 3 | Adapter | El adaptador de persistencia recupera los datos de base de datos y los transforma en objetos de dominio (`Vuelo`). |
| 4 | Application | El caso de uso llama al puerto `ReservasVueloRepository` para recuperar, dado un `DocumentoIdentidad` y un listado de vuelos, el identificador de las reservas correspondientes. |
| 5 | Adapter | El adaptador de persistencia consulta la base de datos y devuelve la información solicitada. |
| 6 | Application | El caso de uso compone la respuesta combinando vuelos y reservas. |
| 7 | Adapter | En la capa REST, el resultado se transforma en DTO y se añaden los enlaces HATEOAS correspondientes. |

Podemos observar que todo el flujo ocurre dentro de nuestra aplicación. Leemos únicamente de nuestro propio esquema de base de datos.

Hasta ahora, todo sucede dentro de los límites de nuestro sistema.

Ahora vamos a ver qué ocurre cuando necesitamos comunicarnos con otro sistema, externo y ajeno a nuestro dominio.

## Hito 1. Crear y persistir una reserva de vuelo 
Otro de los casos de uso que se nos plantea en el enunciado de la práctica es la posibilidad de reservar un vuelo. Las reglas de negocio que implican este caso de uso son las siguientes:
- Un pasajero no puede reservar dos veces un mismo vuelo.
- Sólo se pueden reservar vuelos que no estén iniciados, completados ni cancelados.
- Debe existir disponibilidad de plazas en el avión.

### Paso 1. Crear el caso de uso
Como en el caso anterior, lo primero que tendremos que definir es el caso de uso. Lo depositaremos en el siguiente paquete:

`es.um.atica.umufly.vuelos.application.usecase.reservas`

Y tendrá la siguiente estructura:
``` java
@Component
public class GestionarReservasUseCase {

	public ReservaVuelo creaReservaVuelo(
			DocumentoIdentidad documentoIdentidadTitular,
			UUID idVuelo,
			ClaseAsientoReserva claseAsiento,
			Pasajero pasajero ) {
		// 1. Recuperar el vuelo
		// 2. Recuperar el número de reservas del pasajero en el vuelo
		// 3. Recuperar el número de plazas disponibles en el avión
		// 4. Crear la reserva (Dominio) y persistirla (Puerto)
		// 5. Devolvemos la reserva de vuelo
	}
}
```
### Paso 2. Definir los puertos
En base a lo que debe hacer el caso de uso, debemos definir los puertos necesarios para que los adaptadores se encarguen de acceder a la información y persistirla.

El caso de uso no contiene la lógica de negocio, sino que recopila los datos necesarios para que el Dominio pueda decidir si una reserva es válida o no.

En nuestro caso necesitamos:
1. Recuperar un vuelo concreto a partir de un identificador.
2. Recuperar el número de reservas de vuelo para un pasajero en un vuelo concreto.
3. Recuperar el número de plazas disponibles en el avión para un vuelo concreto.
4. Persistir una reserva de vuelo.

Vamos a empezar por el puerto `VuelosRepository`. Si revisamos el listado anterior, vemos que tendremos que modificar la interfaz para añadir dos métodos nuevos:

``` java
public interface VuelosRepository {
  ... 

  /**
  * Método que recuperará el vuelo en base a su identificador.
  * Lanzará una excepción en caso de no encontrar ningún vuelo.
  */
	Vuelo findVuelo( UUID idVuelo );

  /**
  * Método que, dado un vuelo, devolverá el número de plazas disponibles.
  * Se le pasa el objeto Vuelo ya recuperado para evitar consultas adicionales,
  * ya que contiene la información del avión asociado.
  */
	int plazasDisponiblesEnVuelo( Vuelo vuelo );
}
```

También necesitamos ampliar el puerto para las reservas de vuelo:

``` java
public interface ReservasVueloRepository {
  ...

	/**
   * Método que cuenta las reservas de vuelo que tiene un pasajero
   * en un vuelo concreto.
	 */
	int countReservasByIdVueloAndPasajero( UUID idVuelo, Pasajero pasajero );

	/**
   * Método que persiste una reserva de vuelo.
	 */
	void persistirReserva( ReservaVuelo reservaVuelo );
}
```
Una vez tenemos todos los puertos definidos, podemos completar el caso de uso:

``` java
@Component
public class GestionarReservasUseCase {

	private final VuelosRepository vuelosRepository;
	private final ReservasVueloRepository reservasVueloRepository;

	public GestionarReservasUseCase( VuelosRepository vuelosRepository, ReservasVueloRepository reservasVueloRepository ) {
		this.vuelosRepository = vuelosRepository;
		this.reservasVueloRepository = reservasVueloRepository;
	}

	public ReservaVuelo creaReservaVuelo(
			DocumentoIdentidad documentoIdentidadTitular,
			UUID idVuelo,
			ClaseAsientoReserva claseAsiento,
			Pasajero pasajero ) {

		// 1. Recuperar el vuelo
		Vuelo vuelo = vuelosRepository.findVuelo( idVuelo );

		// 2. Recuperar el número de reservas del pasajero en el vuelo
		int reservasPasajeroEnVuelo = reservasVueloRepository.countReservasByIdVueloAndPasajero( idVuelo, pasajero );

		// 3. Recuperar el número de plazas disponibles en el avión
		int plazasDisponibles = vuelosRepository.plazasDisponiblesEnVuelo( vuelo );

		// 4. Crear la reserva (Dominio)
		ReservaVuelo reservaVuelo = ReservaVuelo.solicitarReserva(
				documentoIdentidadTitular,
				pasajero,
				vuelo,
				claseAsiento,
				LocalDateTime.now(),
				reservasPasajeroEnVuelo,
				plazasDisponibles );

		// 5. Persistir la reserva (Puerto)
		reservasVueloRepository.persistirReserva( reservaVuelo );

		return reservaVuelo;
	}
}
```
#### Mejora opcional. Desacoplar la gestión del tiempo
Si quisiésemos hacer el código más fácil de testear, no deberíamos utilizar directamente `LocalDateTime.now()`, ya que introduce una dependencia implícita del sistema.

En su lugar, podemos inyectar un `java.time.Clock`, lo que nos permitirá controlar el momento temporal en los tests.
``` java
@Configuration
public class ClockConfig {

	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}
}
```

### Paso 3. Modificar el adaptador VuelosPersistenceAdapter
Ahora tendremos que implementar los métodos que hemos definido en el puerto `VuelosRepository`. Empezaremos por el que recupera el vuelo.

#### `Vuelo findVuelo( UUID idVuelo )`
Este método deberá recuperar el vuelo desde el repositorio JPA y transformarlo en un objeto del dominio. Si no encuentra ningún resultado, deberá lanzar una excepción.
``` java
@Override
public Vuelo findVuelo( UUID idVuelo ) {
	return jpaVueloRepository
			.findById( idVuelo.toString() )
			.map( JpaPersistenceMapper::vueloToModel )
			.orElseThrow( () -> new IllegalStateException( "Vuelo no encontrado" ) );
}
```
Como mejora, este método no debería lanzar una excepción genérica como `IllegalStateException`, sino una excepción propia del dominio, por ejemplo `VueloNoEncontradoException`.

#### `int plazasDisponiblesEnVuelo( Vuelo vuelo )`
Este método deberá obtener el número de pasajeros actualmente asociados al vuelo y calcular las plazas disponibles en función de la capacidad del avión.

Para ello, disponemos de la vista `VW_ALL_VUELO_PASAJERO`, que devuelve el listado de pasajeros para un vuelo concreto.

Añadimos un método en nuestro repositorio JPA que haga uso de dicha vista. En este caso utilizaremos una query nativa:

``` java
public interface JpaVueloRepository extends JpaRepository<VueloExtViewEntity, String> {

	@Query(
		value = "SELECT COUNT(1) FROM FORMACION_TICARUM.VW_ALL_VUELO_PASAJERO WHERE ID_VUELO = :idVuelo",
		nativeQuery = true
	)
	int countPasajerosByIdVuelo( @Param( "idVuelo" ) String idVuelo );
}
```

El método para obtener las plazas disponibles en el avión quedaría así:
``` java
	@Override
	public int plazasDisponiblesEnVuelo( Vuelo vuelo ) {
		int ocupadas = jpaVueloRepository.countPasajerosByIdVuelo( vuelo.getId().toString() );
		return Math.max( vuelo.getAvion().capacidad() - ocupadas, 0 );
	}
```
Este método no aplica reglas de negocio, sino que devuelve un dato derivado que el Dominio utilizará para decidir si la reserva es posible.

### Paso 4. Modificar el adaptador ReservasVueloPersistenceAdapter
Lo siguiente que tendríamos que hacer es crear los métodos necesarios en `ReservasVueloPersistenceAdapter`.
#### `int countReservasByIdVueloAndPasajero( UUID idVuelo, Pasajero pasajero )`
Para contar las reservas que tiene un pasajero en un vuelo concreto, utilizaremos también la vista VW_ALL_VUELO_PASAJERO.

En este caso definiremos una query nativa en el repositorio JPA JpaReservaVueloRepository:
``` java
@Query( value = """
		SELECT COUNT(1)
		FROM FORMACION_TICARUM.VW_ALL_VUELO_PASAJERO
		WHERE ID_VUELO = :idVuelo
		  AND TIPO_DOCUMENTO_PASAJERO = :tipoDocumento
		  AND NUMERO_DOCUMENTO_PASAJERO = :numeroDocumento
		""",
		nativeQuery = true )
int countReservasByIdVueloAndPasajero(
	@Param( "idVuelo" ) String idVuelo,
	@Param( "tipoDocumento" ) String tipoDocumento,
	@Param( "numeroDocumento" ) String numeroDocumento );
```
El método del adaptador quedaría de la siguiente forma:
``` java
@Override
public int countReservasByIdVueloAndPasajero( UUID idVuelo, Pasajero pasajero ) {
	return jpaReservaVueloRepository.countReservasByIdVueloAndPasajero(
			idVuelo.toString(),
			JpaPersistenceMapper.tipoDocumentoToEntity(
					pasajero.getIdentificador().tipo() ).toString(),
			pasajero.getIdentificador().identificador() );
}
```

#### `void persistirReserva( ReservaVuelo reservaVuelo )`
Este método convertirá el objeto del dominio `ReservaVuelo` a entidad JPA y almacenará la reserva en base de datos.
``` java
	@Override
	public void persistirReserva( ReservaVuelo reservaVuelo ) {
		LocalDateTime fechaActual = LocalDateTime.now();
		jpaReservaVueloRepository.save( JpaPersistenceMapper.reservaVueloToEntity( reservaVuelo, fechaActual, fechaActual ) );
	}
```
Es importante destacar que:
- El Dominio (ReservaVuelo) no conoce nada de JPA ni de cómo se persisten los datos.
- Toda la transformación a Entidad JPA se realiza en el adaptador (en este caso, mediante `JpaPersistenceMapper`).
Por tanto, tendremos que implementar el método que construye una Entidad JPA a partir de una reserva del Dominio (`reservaVueloToEntity(...)`).

### Paso 5. Crear el adaptador REST ReservasVueloEndpoint
Los pasos para crear el adaptador REST son los mismos que vimos en la sesión anterior:
1. Crear los DTOs necesarios.
2. Añadir al mapper los métodos de conversión.
3. Crear el ModelAssembler.
4. Crear el controlador REST.

Adicionalmente, añadiremos validaciones a los DTOs de la capa REST para controlar que no nos envíen información errónea en el body de la petición.

Para que Spring valide la entrada, tendremos que:
- Anotar el parámetro del body con @Valid.
- Incluir anotaciones de validación (@NotNull, @NotBlank, @Email, etc.) en los DTOs.

``` java
@PostMapping( "/private/v2.0/reservas-vuelo" )
public ReservaVueloDTO creaReserva(
		@RequestHeader( name = "UMU-Usuario", required = true ) String usuario,
		@RequestBody @Valid ReservaVueloDTO nuevaReservaVuelo ) {

	return reservasModelAssembler.toModel(
			gestionarReservasUseCase.creaReservaVuelo(
					authService.parseUserHeader( usuario ),
					nuevaReservaVuelo.getVuelo().getId(),
					ClaseAsientoReserva.valueOf( nuevaReservaVuelo.getClaseAsiento().toString() ),
					ApiRestV2Mapper.pasajeroToModel( nuevaReservaVuelo.getPasajero() ) ) );
}
```

Vamos a ver ahora un ejemplo de DTO con las anotaciones relativas a las validaciones:
``` java
@JsonInclude( content = Include.NON_NULL )
public class PasajeroDTO {

	@Valid
	@NotNull
	private DocumentoIdentidadDTO documentoIdentidad;

	@NotBlank
	@Size( max = 100 )
	private String nombre;

	@NotBlank
	@Size( max = 100 )
	private String primerApellido;

	@Size( max = 100 )
	private String segundoApellido;

	@Email
	@NotNull
	private String correoElectronico;

	@NotBlank
	@Size( max = 300 )
	private String nacionalidad;

  // getters y setters
}
```
Cuando sepamos que un campo no debe recibirse en la entrada (por ejemplo, un id generado por el sistema), lo marcaremos como solo lectura usando:

`@JsonProperty( access = JsonProperty.Access.READ_ONLY )`

Así, el campo podrá aparecer en la respuesta, pero no se aceptará como parte del body de entrada.

## Hito 2. Formalizar una reserva de vuelo
Vale, hemos registrado la reserva de vuelo en nuestro sistema. Pero… ¿se lo hemos trasladado a MuchoVuelo? No.

Hasta ahora únicamente hemos persistido la información en nuestro propio esquema de base de datos.

El siguiente paso será precisamente ese: trasladar la reserva que acabamos de crear al sistema de backoffice (MuchoVuelo) para formalizarla también allí.
### Frontoffice vs Backoffice 
Todo lo que hemos hecho hasta este momento ha ocurrido dentro de los límites de nuestra aplicación. Hemos trabajado con nuestro Dominio, nuestra base de datos y nuestros propios puertos.

MuchoVuelo es otro sistema independiente. Tiene su propio Dominio, su propia base de datos y aplica sus propias reglas. No comparte entidades con nosotros ni conoce nuestra estructura interna.

Cuando persistimos una reserva en el frontoffice, estamos guardando nuestra versión del estado. Pero eso no significa que la reserva exista para el backoffice. Para que quede formalizada, debemos comunicársela y dejar que sea ese sistema el que la procese según sus reglas.

### Paso 1. Modificar el caso de uso
Lo primero que tendrémos que hacer es modificar el caso de uso para establecer la intención de formalizar la reserva.
``` java
public ReservaVuelo creaReservaVuelo(
		DocumentoIdentidad documentoIdentidadTitular,
		UUID idVuelo,
		ClaseAsientoReserva claseAsiento,
		Pasajero pasajero ) {

	...
	
	// 6. Llamamos al backoffice para formalizar la reserva
	// 7. Actualizamos nuestro estado interno
	return reservaVuelo;
}
```
### Paso 2. Definir los puertos
Ahora tenemos que definir los puertos que vamos a necesitar para formalizar la reserva de vuelo.

En primer lugar, necesitaremos un puerto de salida que represente la comunicación con el backoffice (MuchoVuelo). Este puerto define el contrato, pero no su implementación.

``` java
public interface FormalizacionReservasVueloPort {

	/**
	 * Método que formalizará una reserva de vuelo en el backoffice.
	 * Devuelve el identificador de la reserva formalizada.
	 */
	UUID formalizarReservaVuelo( ReservaVuelo reservaVuelo );
}
```

Una vez formalizada la reserva en MuchoVuelo, también tendremos que reflejar ese cambio en nuestro propio sistema. Para ello, ampliamos el puerto de persistencia ReservasVueloRepository con un método que permita guardar la información de formalización:

``` java
public interface ReservasVueloRepository {

	...

	/**
	 * Método que persiste la formalización de la reserva de vuelo.
	 */
	void persistirFormalizacionReserva( UUID idReserva, UUID idReservaFormalizada );
}
```
El caso de uso quedaría así:
``` java
public ReservaVuelo creaReservaVuelo(
		DocumentoIdentidad documentoIdentidadTitular,
		UUID idVuelo,
		ClaseAsientoReserva claseAsiento,
		Pasajero pasajero ) {

	...
	
	// 6. Llamamos al backoffice para formalizar la reserva
	UUID idReservaFormalizada =
			formalizacionReservasVueloPort.formalizarReservaVuelo( reservaVuelo );

	// 7. Actualizamos nuestro estado interno
	reservaVuelo.formalizarReserva();
	reservasVueloRepository.persistirFormalizacionReserva(
			reservaVuelo.getId(),
			idReservaFormalizada );

	return reservaVuelo;
}
```

### Paso 3. Crear el adaptador FormalizacionReservasVueloAdapter
Una vez hemos definido el puerto, tendremos que crear el adaptador que llame a los servicios REST de MuchoVuelo para formalizar la reserva. Lo crearemos bajo el siguiente paquete:

`es.um.atica.umufly.vuelos.adaptors.providers`

``` java 
@Component
public class FormalizacionReservasVueloAdapter implements FormalizacionReservasVueloPort {

	@Override
	public UUID formalizarReservaVuelo( ReservaVuelo reservaVuelo ) {
    // 1. Traducir ReservaVuelo a DTO y llamar al cliente
	}
}
```
#### Cliente de servicio REST de MuchoVuelo
Ya tenemos definido el puerto, así que ahora necesitamos un adaptador que lo implemente. Como MuchoVuelo expone un API REST, construiremos un cliente HTTP que encapsule la llamada.

Para poder utilizar `RestClient`, necesitaremos añadir en el pom.xml la dependencia correspondiente:
``` xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-restclient</artifactId>
</dependency>
```

Dentro de `providers` crearemos un paquete específico para todo lo relacionado con MuchoVuelo. En él ubicaremos los DTOs, los mappers y el cliente encargado de invocar los endpoints.

`es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo`

De esta forma, todo lo específico del backoffice quedará aislado fuera del núcleo de nuestra aplicación.

Para establecer la configuración del cliente, crearemos una clase que recupere los valores de las propiedades que definen la URL en la que se encontrará el API de MuchoVuelo. De este modo, evitamos hardcodear rutas en el código y podremos cambiar el entorno (desarrollo, preproducción, producción) únicamente modificando la configuración.

``` java
@Configuration
public class MuchoVueloClientConfig {

	@Value( "${umufly.vuelos.providers.muchovuelo.base-url}" )
	private String baseUrl;

	@Value( "${umufly.vuelos.providers.muchovuelo.base-path}" )
	private String basePath;

	@Bean
	public RestClient muchoVueloRestClient( RestClient.Builder builder ) {
		return builder
				.baseUrl(
						UriComponentsBuilder.fromUriString( baseUrl )
								.path( basePath )
								.build()
								.toUri() )
				.build();
	}
}
```
Estas propiedades se definirán en el `application.properties`:
``` properties
### URL servicios MuchoVuelo
umufly.vuelos.providers.muchovuelo.base-url=https://daastest.um.es
umufly.vuelos.providers.muchovuelo.base-path=/ords/zeus/formacion_ticarum/muchovuelo-api/rest/
```
A continuación, deberemos definir los DTOs que enviaremos al API de MuchoVuelo en base a la documentación que nos hubiesen pasado en `es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.dto`. 

NOTA: La documentación se encuentra en la colección postman del API MuchoVuelo. Este ejemplo nos sirve para construir nuestros DTOs de salida hacia MuchoVuelo.
<details>
  <summary>JSON de ejemplo</summary>

  ``` JSON
  {
    "id_vuelo": "a1b2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d",
    "clase_asiento_reserva": "E",
    "pasajeros": [
      {
        "tipo_documento": "N",
        "numero_documento": "12345678Z",
        "nombre": "JUAN",
        "primer_apellido": "MARTÍNEZ",
        "segundo_apellido": "FERNÁNDEZ",
        "email": "juan.martinez@ejemplo.com",
        "nacionalidad": "ESP"
      }
    ]
  }
  ```
</details>

Seguidamente, tendremos que crear el mapper que convierta nuestro objeto del dominio en el DTO que le enviemos a MuchoVuelo. Lo depositaremos en `es.um.atica.umufly.vuelos.adaptors.providers.muchovuelo.mapper` y lo llamaremos `MuchoVueloMapper`.

Por último, definiremos el cliente que llame al API de MuchoVuelo:
``` java
@Component
public class MuchoVueloClient {

	private static final String API_VERSION_1 = "/v1.0";
	private static final String API_PRIVATE = "/private";
	private static final String API_RECURSO_RESERVAS_VUELO = "/reservas-vuelo";
	private static final String API_HEADER_USUARIO = "X-Usuario";

	private static final String URI_RESERVAS_VUELO_V1 = API_VERSION_1 + API_PRIVATE + API_RECURSO_RESERVAS_VUELO;

	private final RestClient restClientMuchoVuelo;

	public MuchoVueloClient( @Qualifier( "muchoVueloRestClient" ) RestClient restClientMuchoVuelo ) {
		this.restClientMuchoVuelo = restClientMuchoVuelo;
	}

	public ReservaVueloDTO creaReservaVuelo( ReservaVueloDTO reservaVuelo ) {
		String headerUsuario = getHeaderUsuario(
				reservaVuelo.getTipoDocumentoTitular(),
				reservaVuelo.getNumeroDocumentoTitular() );

		try {
			return restClientMuchoVuelo
					.post()
					.uri( URI_RESERVAS_VUELO_V1 )
					.header( API_HEADER_USUARIO, headerUsuario )
					.body( reservaVuelo )
					.retrieve()
					.body( ReservaVueloDTO.class );
		} catch ( org.springframework.web.client.RestClientResponseException ex ) {
			throw new MuchoVueloClientException(
					"MuchoVueloAPI - Error " + ex.getStatusText() + ": " + ex.getResponseBodyAsString(),
					ex );
		}
	}

	private String getHeaderUsuario( TipoDocumentoDTO tipoDocumento, String identificador ) {
		return tipoDocumento.getCodigo() + ":" + identificador;
	}
}

```
Y ya podemos implementar el adaptador que formaliza la reserva, que será el encargado de traducir nuestro dominio al contrato del puerto:
``` java
@Component
public class FormalizacionReservasVueloAdapter implements FormalizacionReservasVueloPort {

	private final MuchoVueloClient muchoVueloClient;

	public FormalizacionReservasVueloAdapter( MuchoVueloClient muchoVueloClient ) {
		this.muchoVueloClient = muchoVueloClient;
	}

	@Override
	public UUID formalizarReservaVuelo( ReservaVuelo reservaVuelo ) {
		ReservaVueloDTO reservaVueloMuchoVuelo =
				muchoVueloClient.creaReservaVuelo( MuchoVueloMapper.reservaVueloToDTO( reservaVuelo ) );

		return reservaVueloMuchoVuelo.getId();
	}
}
```

### Paso 4. Modificar el adaptador ReservasVueloPersistenceAdapter
Para completar el caso de uso, necesitamos modificar el adaptador que implementa el puerto ReservasVueloRepository para persistir la información de formalización en la base de datos del frontoffice.

Este método:
- Recupera la reserva a partir de su identificador.
- Guarda el identificador de la reserva formalizada (backoffice).
- Cambia el estado a Activa.
- Actualiza las fechas de modificación y formalización.
``` java
@Override
public void persistirFormalizacionReserva( UUID idReserva, UUID idReservaFormalizada ) {
	LocalDateTime fechaActual = LocalDateTime.now();

	ReservaVueloEntity entidad = jpaReservaVueloRepository
			.findById( idReserva.toString() )
			.orElseThrow( () -> new IllegalStateException( "No se ha encontrado la reserva de vuelo" ) );

	entidad.setEstadoReserva( EstadoReservaVueloEnum.A );
	entidad.setFechaModificacion( fechaActual );
	entidad.setFechaFormalizacion( fechaActual );
	entidad.setIdReservaFormalizada( idReservaFormalizada.toString() );

	jpaReservaVueloRepository.save( entidad );
}
```
Como mejora (en la misma línea que comentamos antes), este método también debería lanzar una excepción propia del dominio en lugar de IllegalStateException.

## Resumen final
El flujo que sigue la petición para crear y formalizar una reserva de vuelo es el siguiente: 

[comment]: <> (Imagen generada con PlantUML)
[![](https://img.plantuml.biz/plantuml/svg/ZLF1Rfj04BqZyGyZJgagHrK-e0fWf_N12IArJxrCbt6w2koGsQLgslhVoox4O3ULSW1fcFVUlBUpN5k7uhgcZYEdNKsGobEd7IdN2S9d-eaFRB261Qoi4Xs-leSCrIFlzrfH7CMHVTIcHS46fEUXUUXbViSHGCcTgN8MGm9N7-BeCCSgRbesPDozU-u9HwfNFFOWAScI_C1THpNdR9nmNPDyDHTfXQt3YWNArVqNMFKjhF1T0cXXVUjL9ZT9shQrLkWqcyHxy-vcGpwHzIsKSPhTMihHKX3BjnxjdvdWaXlKNYiqu4HjRZaeBEys7dKOvchZIFQew8Yq0LfIorOxbkS-NT6HE8Q2n6hho2WzHYkACut0jMyZx_vXtx0qMEjVgFhy9tOA5ZTfJHiVRMH3c7mBYyN7buHZDKnvAR9P4GmSpbOy99ZSKbWjoMWvVUq-Z1vVvvJdfF-ddDDIVv7Yg4A7t_hbGvBvTqMmHCSsGBBbYMvQh67JgUyyxNgpy-dMjo6klpfnvEt0vUMVuM2EHJYBC7DiXYCT09xiPvNpOi3wQXWIHzTaglxV_Gi0)](https://editor.plantuml.com/uml/ZLF1Rfj04BqZyGyZJgagHrK-e0fWf_N12IArJxrCbt6w2koGsQLgslhVoox4O3ULSW1fcFVUlBUpN5k7uhgcZYEdNKsGobEd7IdN2S9d-eaFRB261Qoi4Xs-leSCrIFlzrfH7CMHVTIcHS46fEUXUUXbViSHGCcTgN8MGm9N7-BeCCSgRbesPDozU-u9HwfNFFOWAScI_C1THpNdR9nmNPDyDHTfXQt3YWNArVqNMFKjhF1T0cXXVUjL9ZT9shQrLkWqcyHxy-vcGpwHzIsKSPhTMihHKX3BjnxjdvdWaXlKNYiqu4HjRZaeBEys7dKOvchZIFQew8Yq0LfIorOxbkS-NT6HE8Q2n6hho2WzHYkACut0jMyZx_vXtx0qMEjVgFhy9tOA5ZTfJHiVRMH3c7mBYyN7buHZDKnvAR9P4GmSpbOy99ZSKbWjoMWvVUq-Z1vVvvJdfF-ddDDIVv7Yg4A7t_hbGvBvTqMmHCSsGBBbYMvQh67JgUyyxNgpy-dMjo6klpfnvEt0vUMVuM2EHJYBC7DiXYCT09xiPvNpOi3wQXWIHzTaglxV_Gi0)

| Orden | Capa implicada | Descripción del flujo                                                                                                             |
| ----- | -------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| 1     | Adapter        | La petición se inicia en la capa REST, que adapta la entrada HTTP y delega en el caso de uso.                                     |
| 2     | Application    | El caso de uso llama al puerto `VuelosRepository` para recuperar el vuelo.                                                        |
| 3     | Adapter        | El adaptador de persistencia consulta la base de datos y transforma la entidad en un objeto de dominio (`Vuelo`).                 |
| 4     | Application    | El caso de uso llama al puerto `ReservasVueloRepository` para recuperar el número de reservas del pasajero en ese vuelo.          |
| 5     | Adapter        | El adaptador de persistencia consulta la vista correspondiente y devuelve el número de reservas.                                  |
| 6     | Application    | El caso de uso llama al puerto `VuelosRepository` para recuperar el número de plazas disponibles en el vuelo.                     |
| 7     | Adapter        | El adaptador de persistencia consulta la vista correspondiente y devuelve el número de plazas disponibles.                        |
| 8     | Application    | El caso de uso invoca al Dominio para solicitar la creación de la reserva.                                                        |
| 9     | Domain         | El Dominio aplica las reglas de negocio y decide si la reserva puede crearse.                                                     |
| 10    | Application    | El caso de uso llama al puerto `ReservasVueloRepository` para persistir la reserva.                                               |
| 11    | Adapter        | El adaptador de persistencia guarda la reserva en estado **PENDIENTE** en el frontoffice.                                         |
| 12    | Application    | El caso de uso llama al puerto `FormalizacionReservasVueloPort` para formalizar la reserva en el backoffice.                      |
| 13    | Adapter        | El adaptador de MuchoVuelo invoca el API REST del backoffice y obtiene el identificador de la reserva formalizada.                |
| 14    | Application    | El caso de uso llama al puerto `ReservasVueloRepository` para persistir la formalización.                                         |
| 15    | Adapter        | El adaptador de persistencia actualiza la reserva en el frontoffice (estado **ACTIVA**, identificador de formalización y fechas). |
| 16    | Application    | El caso de uso devuelve la reserva de vuelo.                                                                                      |
| 17    | Adapter        | En la capa REST, el resultado se transforma en DTO y se añaden los enlaces HATEOAS correspondientes.                              |

Vemos cómo cada capa cumple una única responsabilidad. El Dominio decide, la Application orquesta y los Adaptadores se encargan de la infraestructura.

En ningún momento el Dominio conoce la base de datos ni el API de MuchoVuelo.
