package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

//clase para generar los métodos necesarios para generar, validar, verificar y extraer información del token
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
     que contienen declaraciones sobre una entidad, como un usuario, y metadatos adicionales)
     y devuelve un resultado de tipo T. En términos generales, esta función se utiliza para extraer información específica
     de la información que posee el token JWT.
     Se utiliza en el contexto de decodificar y verificar un token JWT. Cuando se decodifica un token, se obtienen las reclamaciones (información)
     asociadas con ese token. Luego, claimsResolver se aplica a esas reclamaciones para extraer información.
     Un ejemplo práctico podría ser tener un claimsResolver que extrae el nombre de usuario del token JWT.
     */

    private String claveSecreta = "springboot"; //creamos esta clave para poder firmar el token. La parte del token signature se firma con esta clave.
    public String extraerNombreUsuario(String token) {
        return extraerCuerpoToken(token, Claims::getSubject); //extraemos el nombre de usuario del token
    }

    public Date extraerFechaExpiracion(String token) {
        return extraerCuerpoToken(token, Claims::getExpiration); //extraemos la fecha de expiración del token
    }

    public <T> T extraerCuerpoToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraerClaims(token); //para extraer todos los datos del token
        return claimsResolver.apply(claims);
    }

    public Claims extraerClaims(String token) {
        return Jwts.parser().setSigningKey(claveSecreta).parseClaimsJws(token).getBody(); //le asignamos la clave secreta (de manera que se firma el token) y le decimos que nos devuelva el cuerpo del token
    }
}
