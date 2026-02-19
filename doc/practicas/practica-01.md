# Práctica 1 - DDD
El objetivo de esta sesión es aprender a modelar el dominio del caso práctico que se nos ha planteado.

Lo primero que tendremos que hacer es determinar cuáles son los conceptos principales, consensuando el lenguaje que vamos a utilizar para referirnos a ellos. Seguidamente, los categorizaremos como Agregados, Value Objects y Agregado raíz. También extraeremos los casos de uso y, por último, llevaremos nuestro modelo a código.

## Hito 1. Modelar el dominio
<details>
  <summary>Ver solución propuesta</summary>
  
  <img src="umufly-dominio.png">
</details>

## Hito 2. Crear un proyecto Spring Boot
Para elaborar este caso práctico vamos a utilizar Spring, ya que vamos a desarrollar un API de frontoffice que consumirá y proporcionará información al backoffice (MuchoVuelo).

Spring encaja muy bien con arquitecturas limpias, permitiendo generar APIs ligeras, desacopladas y fáciles de mantener. Aún así, los principios que aplicaremos en esta práctica pueden utilizarse igualmente en otros entornos o frameworks.

### Spring Boot Starter

Lo primero que tenemos que hacer es abrir FundeWeb e ir a `File > New > Other`.  
En el asistente que se abre, buscamos **Spring Starter Project** y pulsamos `Next >`.

Introducimos los siguientes datos:
```
Name: umufly-api
Java Version: 17
Group: es.um.atica.umufly
Artifact: umufly-api
Description: API que ofrece a estudiantes UM la posibilidad de reservar vuelos
Package: es.um.atica.umufly
```
En la siguiente pantalla, nos aseguramos de seleccionar la versión **4.X.X** de Spring Boot.

Para empezar, añadimos las siguientes dependencias:

- Spring Web
- Spring Boot DevTools

## Hito 3. Crear las clases del dominio
Aún no hemos explicado qué es la arquitectura hexagonal, pero vamos a organizar las clases que añadamos de la siguiente manera:

Como estamos bajo el contexto `vuelos`, todo lo que esté relacionado con este contexto se encontrará bajo el paquete `es.um.atica.umufly.vuelos`.

Las clases del dominio que añadiremos estarán bajo `es.um.atica.umufly.vuelos.domain.model`.

Del mismo modo, si necesitásemos añadir excepciones propias del dominio, las crearemos bajo `es.um.atica.umufly.vuelos.domain.exception`.

### Value Objects
Como ya hemos visto en el apartado de teoría, los Value Objects son pequeñas unidades autovalidadas que utilizaremos para dar forma a nuestros agregados.

Además, son inmutables: si tuviésemos que modificar algo de un Value Object, crearíamos uno nuevo y descartaríamos el anterior.

Un Value Object no se identifica por su identidad, sino por sus atributos. Dos Value Objects con los mismos valores son equivalentes.

Java 16+ incluye un tipo especial que encaja perfectamente con esta filosofía: los *records*. 

Los records generan automáticamente:
- constructor
- métodos de acceso (`atributo()`)
- equals()
- hashCode()
- toString()

Además, sus componentes son implícitamente `private` y `final`, y la clase es `final`. Esto los convierte en una excelente opción para representar Value Objects.

El siguiente record:
``` java
public record DocumentoIdentidad(String tipo, String numero) {}
```

Sería equivalente a:
``` java
public final class DocumentoIdentidad {

    private final String tipo;
    private final String numero;

    public DocumentoIdentidad(String tipo, String numero) {
        this.tipo = tipo;
        this.numero = numero;
    }

    public String tipo() { return tipo; }
    public String numero() { return numero; }

    // equals, hashCode y toString generados automáticamente
}
```

<details>
  <summary>DocumentoIdentidad.java</summary>
  
  ``` java
package es.um.atica.umufly.vuelos.domain.model;

public record DocumentoIdentidad(TipoDocumento tipo, String identificador) {

	private static final String PATRON_NIE = "[XYZ]\\d{7}[A-Z]";
	private static final String PATRON_NIF = "\\d{8}[A-Z]";
	private static final String LETRAS_CONTROL_NIE_NIF = "TRWAGMYFPDXBNJZSQVHLCKE";

	public DocumentoIdentidad {
		if ( tipo == null ) {
			throw new IllegalArgumentException( "El tipo del documento de identidad no puede ser nulo" );
		}
		if ( identificador == null || identificador.isBlank() ) {
			throw new IllegalArgumentException( "El número del documento de identidad no puede ser nulo" );
		}

		String normalizado = identificador.trim().toUpperCase();

		if ( TipoDocumento.NIF.equals( tipo ) && !isValidNIF( normalizado ) ) {
			throw new IllegalArgumentException( "El número del documento no es un NIF válido" );
		}
		if ( TipoDocumento.NIE.equals( tipo ) && !isValidNIE( normalizado ) ) {
			throw new IllegalArgumentException( "El número del documento no es un NIE válido" );
		}

		identificador = normalizado;
	}

	private static boolean isValidNIF( String nif ) {
		if ( !nif.matches( PATRON_NIF ) ) {
			return false;
		}
		int numero = Integer.parseInt( nif.substring( 0, 8 ) );
		return isValidNumber( numero, nif.charAt( 8 ) );
	}

	private static boolean isValidNIE( String nie ) {
		if ( !nie.matches( PATRON_NIE ) ) {
			return false;
		}
		int prefijo = switch ( nie.charAt( 0 ) ) {
			case 'X' -> 0;
			case 'Y' -> 1;
			case 'Z' -> 2;
			default -> -1;
		};
		if ( prefijo < 0 ) {
			return false;
		}

		int numero = Integer.parseInt( prefijo + nie.substring( 1, 8 ) );
		return isValidNumber( numero, nie.charAt( 8 ) );
	}

	private static boolean isValidNumber( int numero, char letra ) {
		char letraCorrecta = LETRAS_CONTROL_NIE_NIF.charAt( numero % 23 );
		return letra == letraCorrecta;
	}
}
  ```
</details>

### Agregados
Pasamos ahora a trasladar a código los agregados. Un Agregado es un objeto del dominio con identidad única y ciclo de vida propio. Puede cambiar su estado a lo largo del tiempo y es responsable de encapsular comportamiento y reglas de negocio.

Tendrán un constructor privado y expondrán un método estático `of` que aplicará la lógica de negocio que proceda y lanzará una excepción cuando se incumpla el invariante.

``` java
public class Vuelo {
	private UUID id;
	...

	private Vuelo( UUID id, Itinerario itinerario, TipoVuelo tipo, EstadoVuelo estado, Avion avion ) {
		...
	}

	public static Vuelo of( UUID id, Itinerario itinerario, TipoVuelo tipo, EstadoVuelo estado, Avion avion ) {
		if ( id == null ) {
			throw new IllegalArgumentException( "El id del vuelo no puede ser nulo" );
		}
  ...
}
```

### Agregado raíz y excepciones de dominio
El punto de entrada a nuestro dominio es el agregado raíz. Cualquier operación que implique modificar su estado debe pasar siempre por él, ya que es el responsable de garantizar la coherencia del modelo.

Por este motivo:

- No se modifica su estado directamente.
- Se invoca comportamiento.
- Si se incumple una regla, se lanza una excepción del dominio.

Por ejemplo:
``` java
/**
 * Método para crear una reserva de vuelo. Las restricciones que se aplicarán para crear una reserva de vuelo son las
 * siguientes:
 * <ol>
 * <li>No se puede reservar un vuelo CANCELADO o COMPLETADO.</li>
 * <li>No se puede reservar un vuelo si el pasajero ya tiene una reserva en ese vuelo.</li>
 * <li>No se puede reservar un vuelo si no quedan plazas en el avión.</li>
 * </ol>
 */
public static ReservaVuelo solicitarReserva(
        DocumentoIdentidad identificadorTitular,
        Pasajero pasajero,
        Vuelo vuelo,
        ClaseAsientoReserva clase,
        LocalDateTime fechaReserva,
        int numeroReservasPasajeroEnVuelo,
        int numeroPlazasDisponiblesEnAvion
) {
    if (numeroReservasPasajeroEnVuelo >= MAX_RESERVAS_POR_PASAJERO_EN_VUELO) {
        throw new LimiteReservasPorPasajeroEnVueloSuperadoException(
                "Sólo puede haber una reserva por pasajero en un vuelo"
        );
    }
    if (EstadoVuelo.CANCELADO.equals(vuelo.getEstado())
            || EstadoVuelo.COMPLETADO.equals(vuelo.getEstado())) {
        throw new VueloNoReservableException(
                "El vuelo se encuentra completado o cancelado"
        );
    }
    if (vuelo.isIniciado(fechaReserva)) {
        throw new VueloIniciadoException("El vuelo se encuentra iniciado");
    }
    if (numeroPlazasDisponiblesEnAvion <= 0) {
        throw new VueloSinPlazasException(
                "No hay plazas disponibles en el avión"
        );
    }

    return of(
            UUID.randomUUID(),
            identificadorTitular,
            pasajero,
            vuelo,
            clase,
            fechaReserva,
            EstadoReserva.PENDIENTE
    );
}


```



