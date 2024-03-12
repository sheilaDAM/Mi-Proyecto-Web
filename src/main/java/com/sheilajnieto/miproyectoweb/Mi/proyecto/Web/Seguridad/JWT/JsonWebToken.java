package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Seguridad.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//clase para generar los métodos necesarios para generar, firmar y extraer información del token como el nombre de usuario y la fecha de expiración
//Esta clase valida que el token no haya expirado y que el usuario que hace la petición sea el mismo que userDetails, que es una clase de Spring Security que contiene la información del usuario que se ha logeado
//tras esta clase necesitaremos un filtro que intercepte las peticiones y compruebe si el token es válido (será la clase FiltroJWT)
@Service
public class JsonWebToken {

    /*
    --------- TEORÍA ---------
    Un token es una cadena de caracteres que se utiliza para autenticar a un usuario.

    Cuando nos logeamos en una aplicación mediante una petición http a /login, el servidor nos devuelve un token que nos identifica.
    El token que recibe el usuario está firmado por el servidor con una clave secreta, que es la que se utiliza para firmar el token y validar que el token es válido.

    Por cada petición que hagamos al servidor, adjuntaremos el token recibido en la cabecera de la petición, y el servidor verificará que el token es válido
    y que el usuario tiene permisos para acceder a la información que está solicitando.


    PARTES DE UN TOKEN
    Un token JWT está compuesto por tres partes:
    -HEADER o Encabezado: Presenta 2 valores, contiene el tipo de token ("typ") y el algoritmo de encriptación ("alg").
    -PAYLOAD, Carga útil, *Claims (solicitudes): contiene la información que queremos transmitir, como el usuario, la fecha de expiración, etc.
    -SIGNATURE o Firma: contiene la firma del token, que es la combinación del encabezado, la carga útil y la clave secreta.
     verifica que el usuario es quien dice ser. Vamos a tener una clave secreta que nos permitirá firmar el token.
     Esta clave secreta la genera el servidor y es la que se utiliza para firmar el token y validar que el token es válido.

     *CLAIM: piezas de información sobre un usuario que se encuentran empaquetadas y firmadas con un token de seguridad

     En el contexto JWT, claimsResolver se utiliza como una interfaz funcional que toma un objeto Claims (reclamaciones, que son unidades de información
     dentro del token que contienen declaraciones sobre una entidad, como un usuario, y metadatos adicionales)
     y devuelve un resultado de tipo T, que es un tipo genérico que representará el tipo de dato que se desea extraer del token.
     En términos generales, esta función se utiliza para extraer información específica
     de la información que posee el token JWT. Cuando se quiera extraer,p.e., el nombre, se llamará a la función que aplica el claimsResolver, devolviendo un tipo T,
     en T será donde se guarde el nombre del usuario.
     Se utiliza en el contexto de decodificar y verificar un token JWT. Cuando se decodifica un token, se obtienen las reclamaciones (información)
     asociadas con ese token. Luego, claimsResolver se aplica a esas reclamaciones para extraer información.

     */

    private String claveSecreta = "springboot"; //creamos esta clave para poder firmar el token. La parte del token signature se firma con esta clave.

    /*
    Método para extraer el nombre de usuario del cuerpo del token.
   Utiliza el método extraerCuerpoToken pasando la función Claims::getSubject como claimsResolver.
   Esto se debe a que el nombre de usuario generalmente se almacena en el campo "subject" de las reclamaciones (claims) del token.
   y gracias a la función claimsResolver, se puede extraer específicamente el nombre de usuario del token.
    */
    public String extraerNombreUsuario(String token) {
        return extraerCuerpoToken(token, Claims::getSubject); //aquí se pueden extraer datos expecíficos,

        // extraemos el nombre de usuario del token
    }

    //Método para extraer la fecha de expiración del cuerpo del token (hace lo mismo que el método anterior, pero con la fecha de expiración)
    public Date extraerFechaExpiracion(String token) {
        return extraerCuerpoToken(token, Claims::getExpiration); //extraemos la fecha de expiración del token
    }

    //Método para extraer el cuerpo del token una vez que se han extraído las reclamaciones del token (claims). A éstas se les aplica la función claimsResolver
    //que lo que hace es que se le pasan las claims obtenidas a claimsResolver, y éste devuelve un objeto de tipo T, que es un tipo genérico que representará
    // el tipo de dato que se desea extraer del token cuando deseemos obtener un dato específico.
    public <T> T extraerCuerpoToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraerClaims(token); //para extraer todos las reclamaciones, es decir, todos los datos del token y guardarlos en la variable claims
        return claimsResolver.apply(claims); //claimsResolver se aplica a las reclamaciones del token, permitiendo extraer información específica de manera más flexible
    }

    //Método para extraer las reclamaciones del token, es decir, la información que contiene el token
    //Este método se utiliza en el método extraerCuerpoToken, para guardar el cuerpo del token en la variable claims
    public Claims extraerClaims(String token) {
        return Jwts.parser().setSigningKey(claveSecreta).parseClaimsJws(token).getBody(); //le asignamos la clave secreta (de manera que se firma el token) y le decimos que nos devuelva el cuerpo del token
    }

    //Método para verificar si el token es válido
    private Boolean esTokenExpirado(String token) {
        final Date fechaExpiracion = extraerFechaExpiracion(token); //extraemos la fecha de expiración del token
        return fechaExpiracion.before(new Date()); //devuelve true si la fecha de expiración es anterior a la fecha actual, es decir, si el token ha expirado
    }

    //Método para generar el token
    public String generarToken(String nombreUsuario, String rol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol); //añadimos el rol al token
        return crearToken(claims, nombreUsuario); //creamos el token con las reclamaciones y el nombre de usuario
    }

    private String crearToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) //añadimos las reclamaciones al token
                .setSubject(subject) //añadimos el nombre de usuario al token
                .setIssuedAt(new Date(System.currentTimeMillis())) //añadimos la fecha de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 60 * 10)) //añadimos la fecha de expiración del token, que será 10 horas después de la fecha de emisión del token (en milisegundos)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, claveSecreta).compact(); //firmamos el token con la clave secreta que encriptamos con el algoritmo HS256 y lo devolvemos. Por tanto este token va a estar encriptado y firmado con una clave secreta
    }

    //Método para validar el token (comprobar si el token es válido) y verificar si el usuario que se ha logeado es el mismo que el que está en el token
    public Boolean validarToken(String token, UserDetails detallesUsuario) {
        final String nombreUsuario = extraerNombreUsuario(token); //extraemos el nombre de usuario del token
        return (nombreUsuario.equals(detallesUsuario.getUsername()) && !esTokenExpirado(token)); //devuelve true si el nombre de usuario del token es igual al nombre de usuario del usuario que se ha logeado y si el token no ha expirado
    }

}

/*
Explicación Método --> public Claims extraerClaims(String token)

    Jwts.parser(): Este método estático de la clase Jwts crea un nuevo objeto JwtParser. Este objeto se utiliza para analizar (parsear) y verificar tokens JWT.

    setSigningKey(claveSecreta): Este método establece la clave secreta que se utilizará para verificar la firma del token. En otras palabras, se le proporciona la misma clave secreta que se utilizó para firmar el token originalmente.

    parseClaimsJws(token): Este método toma el token JWT como argumento y lo analiza. Además de analizar el token, también verifica su firma utilizando la clave secreta proporcionada. Si la firma no es válida, se lanzará una excepción.

    getBody(): Este método obtiene las reclamaciones (claims) del token analizado y verificado. Las "reclamaciones" son la información almacenada en el token, como el nombre de usuario, la fecha de expiración, etc. En este caso, estamos obteniendo el cuerpo del token.

En resumen, la línea completa se encarga de analizar y verificar el token JWT, y luego extrae las reclamaciones (claims) del cuerpo del token verificado.
 */
