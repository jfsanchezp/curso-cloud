# UMUFly

## Enunciado del caso práctico
El director general de una conocida aerolínea ha contactado con el departamento de informática de la Universidad de Murcia para la realización de una aplicación para gestionar los vuelos y las reservas de esa aerolínea. Dado que tienen cierta urgencia, su equipo se encargará del frontend, mientras que la Universidad de Murcia desarrollará el backend al que dicho frontal enviará las peticiones. 

El público objetivo de esta aplicación serán los usuarios que dispongan de una credencial activa en la Universidad de Murcia. 
Nos han indicado que el software debe seguir las últimas tendencias en arquitecturas desacopladas, aplicando Domain Driven Design y utilizando una arquitectura hexagonal. También nos piden garantizar unos niveles mínimos de calidad en el API. 

La Universidad de Murcia, para lanzar el proyecto, mantuvo una reunión con un experto del dominio, que trasladó lo siguiente: 

> A ver, os cuento cómo trabajamos. En MuchoVuelo.com damos a los pasajeros la opción de reservar un vuelo sin tener que pagar en el momento. La idea es que, cuando entren en la aplicación, vean un listado con todos los vuelos disponibles y la información que solemos mostrar: el identificador del vuelo, la fecha y hora de salida, la hora prevista de llegada, si es nacional o internacional, de qué aeropuerto sale, a qué aeropuerto va, el estado del vuelo (programado, completado, retrasado o cancelado) y, por supuesto, la capacidad del avión.
>
> Cuando el pasajero elige un vuelo disponible, es decir, que no esté completado ni cancelado, puede iniciar el proceso de reserva. En ese momento, lo único que necesitamos que nos facilite son algunos datos para completar la reserva: nombre, apellidos, documento de identidad, correo electrónico y nacionalidad del pasajero, y la clase del asiento que quiere reservar, que puede ser económica, business o primera.
> 
> Aunque estos sean los datos que pedimos al usuario, la reserva también debe incluir la fecha en la que se realiza y el vuelo concreto que ha seleccionado.
>
> Cuando se completa la reserva, habrá que asignarle un identificador único para que pueda consultarla más adelante. La aplicación debe permitir ver un listado con todas las reservas que ha hecho un pasajero y, además, indicar en el listado de vuelos cuáles ya tiene reservados. Eso sí, un pasajero no puede tener dos reservas en el mismo vuelo.
>
> Somos bastante flexibles con las cancelaciones, así que puede anular la reserva en cualquier momento antes de que salga el vuelo.
>
> Y un detalle más: tenemos un servicio de parking. Todos los pasajeros con reservas de vuelos que aún no se han realizado tienen un 75 % de descuento. El parking puede ser de corta duración, que cuesta dos céntimos por minuto, o de larga duración, que cuesta siete euros al día. Se reserva igual que un vuelo, indicando el tipo de estacionamiento, las fechas de inicio y fin, y los datos del pasajero. Y, por supuesto, también puede cancelarlo sin coste alguno.” 

Inicialmente, podremos conectar nuestro API directamente a su base de datos para avanzar en el desarrollo. Sin embargo, el departamento informático de la aerolínea está preparando un API propio para la gestión de las reservas de vuelo. En cuanto esté disponible, tendremos que utilizar ese API y dejar de acceder directamente a la base de datos. 
