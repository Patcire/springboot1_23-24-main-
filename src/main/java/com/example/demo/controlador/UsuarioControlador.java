package com.example.demo.controlador;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.error.exceptions.NotFoundException;
import com.example.demo.modelo.Producto;
import com.example.demo.modelo.Usuario;
import com.example.demo.repos.UsuarioRepositorio;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioControlador {

    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioControlador(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // Ejemplo de como funcionaría la función getmapping con DTO para usuario
    @GetMapping("/")
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        List<UsuarioDTO> resultado = new ArrayList<>();
        for (Usuario usuario: usuarioRepositorio.findAll()) resultado.add(new UsuarioDTO(usuario));
        if (resultado.isEmpty()){
            throw new NotFoundException("No users were found");
        }
        else {
            return ResponseEntity.ok(resultado);
        }
    }

    // OPCIONAL - añade una petición GET para un usuario y sus productos
    //@GetMapping("/{id}/productos")
    //public  ResponseEntity<List<List<Producto>>> getProductsfromUser(@PathVariable Long idUser){
    //}

    // Add methods for POST, PUT, DELETE...
    @PostMapping("/")
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioRepositorio.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return usuarioRepositorio.findById(id)
                .map(existingUsuario -> {
                    existingUsuario.setName(usuario.getName());
                    existingUsuario.setEmail(usuario.getEmail());
                    return ResponseEntity.ok( usuarioRepositorio.save(existingUsuario));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado  con id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        return usuarioRepositorio.findById(id)
                .map(usuario -> {
                    usuarioRepositorio.delete(usuario);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + id));
    }


}
