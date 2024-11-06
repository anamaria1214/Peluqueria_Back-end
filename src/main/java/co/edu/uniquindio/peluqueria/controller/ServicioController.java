package co.edu.uniquindio.peluqueria.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.*;
import co.edu.uniquindio.peluqueria.dto.MensajeDTO;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ServicioServicio;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/servicio")
public class ServicioController {

    private final ServicioServicio servicioServicio;

    @PostMapping("/crear-servicio")
    public ResponseEntity<MensajeDTO<VistaCreacionServicioDTO>> crearServicio(@Valid @RequestBody CrearServicioDTO servicio) throws Exception{
        VistaCreacionServicioDTO servicioDTO = servicioServicio.crearServicio(servicio);
        return ResponseEntity.ok(new MensajeDTO<>(false, servicioDTO));
    }

    @PutMapping("/editar-servicio")
    public ResponseEntity<MensajeDTO<String>> editarServicio(@Valid @RequestBody EditarServicioDTO servicio) throws Exception{
        servicioServicio.editarServicio(servicio);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Servicio editado exitosamente"));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarServicio(@PathVariable String id) throws Exception{
        servicioServicio.eliminarServicio(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Servicio eliminado exitosamente"));
    }

    @GetMapping("/listar-todo/{id}")
    public ResponseEntity<MensajeDTO<InformacionServicioDTO>> obtenerInformacionServicio(@PathVariable String id) throws Exception{
        InformacionServicioDTO info = servicioServicio.obtenerInformacionServicio(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, info));
    }
    //listarServicios() throws Exception

    @GetMapping("/obtener")
    public ResponseEntity<MensajeDTO<List<ItemServicioDTO>>> listarServicios() throws Exception {
        List<ItemServicioDTO> lista = servicioServicio.obtenerServicios();
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }
}

