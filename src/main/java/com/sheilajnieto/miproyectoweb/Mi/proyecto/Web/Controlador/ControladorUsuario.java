package com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador;

import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Controlador.DTO.UsuarioDTO;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Entidades.Usuario;
import com.sheilajnieto.miproyectoweb.Mi.proyecto.Web.Servicios.Implementaciones.ServicioUsuarioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class ControladorUsuario {

    @Autowired
    private ServicioUsuarioImpl servicioUsuarioImpl;

    @GetMapping
    public String home() {
        return "redirect:/usuarios";
    }

    //endpoint para listar usuarios en la vista
    @GetMapping("/usuarios")
     public String listarUsuarios (Model model){
         List<Usuario> usuarios = servicioUsuarioImpl.listarUsuarios();
         model.addAttribute("usuarios", usuarios);
         return "usuarios";
    }

    //endpoint para mostrar el formulario para crear un  nuevo usuario
    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioParaGuardarUsuario(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Nuevo usuario/a");
        return "formularioNuevoUsuario";
    }

    //endpoint para guardar un nuevo usuario
    /*
    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(UsuarioDTO usuario, RedirectAttributes redirectAttributes) {
        try{
            servicioUsuarioImpl.guardar(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "El usuario se ha guardado con éxito.");
        }catch (Exception e) {
            redirectAttributes.addAttribute("mensaje", e.getMessage());
        }
        return "redirect:/usuarios";
    }

     */

    //endpoint para guardar usuario
    @PostMapping("/usuarios/guardar")
    public ResponseEntity<String> registrarUsuario(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return servicioUsuarioImpl.registrar(requestMap);
        }catch (Exception e) {
            e.getMessage();
        }
        return ResponseEntity.ok().body("Error al registrar usuario.");
    }

    //endpoint para mostrar el formulario para editar un usuario concreto seleccionado
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(Long id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Usuario usuario = servicioUsuarioImpl.obtenerUsuarioPorId(id);
            model.addAttribute("usuario", usuario);
            model.addAttribute("titulo", "Editar usuario/a");
            return "formulariNuevoUsuario";
        }catch (Exception e) {
            redirectAttributes.addAttribute("mensaje", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    //endpoint para eliminar un usuario concreto seleccionado
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(Long id, RedirectAttributes redirectAttributes) {
        try{
            servicioUsuarioImpl.eliminarUsuario(id);
            redirectAttributes.addFlashAttribute("mensaje", "El usuario se ha eliminado con éxito.");
        }catch (Exception e) {
            redirectAttributes.addAttribute("mensaje", e.getMessage());
        }
        return "redirect:/usuarios";
    }
}
