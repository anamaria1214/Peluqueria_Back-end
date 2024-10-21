package co.edu.uniquindio.peluqueria.controller;

import co.edu.uniquindio.peluqueria.dto.ClienteDTOs.CrearClienteDTO;
import co.edu.uniquindio.peluqueria.dto.ClienteDTOs.EditarClienteDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cliente;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteServicio clienteServicio;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarCliente(@Validated @RequestBody CrearClienteDTO crearClienteDTO) {
        try {
            clienteServicio.registrarCliente(crearClienteDTO);
            return ResponseEntity.ok("Cliente registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<String> editarCliente(@Validated @RequestBody EditarClienteDTO editarClienteDTO) {
        try {
            clienteServicio.editarCliente(editarClienteDTO);
            return ResponseEntity.ok("Cliente editado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable String id) {
        try {
            clienteServicio.eliminarCliente(id);
            return ResponseEntity.ok("Cliente eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable String id) {
        try {
            Cliente cliente = clienteServicio.obtenerClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar")
    public List<Cliente> listarClientes() throws Exception {
        return clienteServicio.listarClientes();
    }
}

