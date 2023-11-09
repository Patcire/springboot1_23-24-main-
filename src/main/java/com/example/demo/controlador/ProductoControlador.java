
package com.example.demo.controlador;

import com.example.demo.error.exceptions.NotFoundException;
import com.example.demo.error.exceptions.NotFoundProduct;
import com.example.demo.error.exceptions.ProductExist;
import com.example.demo.repos.ProductoRepositorio;
import com.example.demo.repos.UsuarioRepositorio;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.modelo.Producto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/producto")
public class ProductoControlador {
    private final ProductoRepositorio productoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public ProductoControlador(ProductoRepositorio productoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.productoRepositorio = productoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }


    @GetMapping("/")
    public ResponseEntity<List<Producto>> getProductos() {
        List<Producto> lista_productos = productoRepositorio.findAll();
        if (lista_productos.isEmpty()) {
            throw new NotFoundProduct("None product was found");
        }
        else {
            return ResponseEntity.ok(lista_productos);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional> getProductoById(@PathVariable Long id) {
        Optional<Producto> producto = productoRepositorio.findById(id);
        if (producto.isEmpty()){
            throw new NotFoundException("Don't exist a producto with id: " + id);
        }
        else {
            return ResponseEntity.ok(producto);
        }
    }

    // Si existe un artículo con el mismo nombre, lo evitaremos
    @PostMapping("/")
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody Producto producto) {
        Producto sameProduct = productoRepositorio.findByName(producto.getName());
        if (sameProduct!=null){
            productoRepositorio.save(producto);
            return  ResponseEntity.ok(producto);
        }
        else{
            // Si ya existe lanazamos la excepción personalizada que he creado
            throw  new ProductExist();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(productoRepositorio.findById(id).map(existingProducto -> {
                    existingProducto.setName(producto.getName());
                    existingProducto.setPrice(producto.getPrice());
                    return productoRepositorio.save(existingProducto);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Producto not found with id " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        return productoRepositorio.findById(id)
                .map(producto -> {
                    productoRepositorio.delete(producto);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Producto not found with id " + id));
    }

    // Crear productos asociados a cliente
    @PostMapping("/{id}/productos")
    public ResponseEntity<Producto> addProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return productoRepositorio.findById(id)
                .map(usuario -> {
                    producto.setUsuario(usuario.getUsuario());
                    return  ResponseEntity.ok(productoRepositorio.save(producto));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + id));
    }

    @PutMapping("/{id}/productos/{productoId}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @PathVariable Long productoId, @Valid @RequestBody Producto productoRequest) {
        if(!usuarioRepositorio.existsById(id)) {
            throw new ResourceNotFoundException("Usuario not found with id " + id);
        }
        return ResponseEntity.ok(productoRepositorio.findById(productoId)
                .map(producto -> {
                    producto.setName(productoRequest.getName());
                    producto.setPrice(productoRequest.getPrice());
                    return productoRepositorio.save(producto);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Producto not found with id " + productoId)));
    }
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Producto>> getProductosByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(productoRepositorio.findByUsuarioId(usuarioId));
    }

}
