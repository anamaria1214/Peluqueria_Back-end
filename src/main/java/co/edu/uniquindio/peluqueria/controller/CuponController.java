package co.edu.uniquindio.peluqueria.controller;

import co.edu.uniquindio.peluqueria.dto.*;
import co.edu.uniquindio.peluqueria.dto.CuponDTOs.CrearCuponDTO;
import co.edu.uniquindio.peluqueria.dto.CuponDTOs.CuponDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cupon;
import co.edu.uniquindio.peluqueria.model.documentos.Inventario;
import co.edu.uniquindio.peluqueria.servicios.implementacion.CuponServicioImpl;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CuponServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cupon")
public class CuponController {

    private final CuponServicio cuponServicio;

    @PutMapping("/actualizar-cupon")
    public ResponseEntity<MensajeDTO<String>> actualizarCupon(@Valid @RequestBody CuponDTO cuponDTO) throws Exception {
        cuponServicio.actualizarCupon(cuponDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Producto actualizado"));
    }

    @PutMapping("/redimir-cupon")
    public ResponseEntity<MensajeDTO<String>> redimirCupon(@Valid @RequestBody CuponDTO cuponDTO) throws Exception {
        cuponServicio.redimirCupon(cuponDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Producto actualizado"));
    }

    @GetMapping("/listarCupones")
    public ResponseEntity<MensajeDTO<List<CuponDTO>>> listarCupones() throws  Exception{
        List<CuponDTO> cupones= cuponServicio.listarCupones();
        return ResponseEntity.ok(new MensajeDTO<>(false,cupones));
    }

    @GetMapping("/obtenerCupon/{id}")
    public ResponseEntity<MensajeDTO<CuponDTO>> obtenerProductoPorId(@PathVariable String id) throws Exception{
        CuponDTO cupon= cuponServicio.obtenerCupon(id);
        return ResponseEntity.ok(new MensajeDTO<>(false,cupon));
    }

    @PostMapping("/crear-cupon")
    public ResponseEntity<MensajeDTO<String>> crearCupon(@Valid @RequestBody CrearCuponDTO cupon) throws Exception {
        cuponServicio.crearCupon(cupon);
        return ResponseEntity.ok(new MensajeDTO<>(false,"Producto agregado correctamente"));
    }

    @DeleteMapping("/eliminarCupon/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarCupon(@PathVariable String id) throws Exception{
        cuponServicio.eliminarCupon(id);
        return ResponseEntity.ok(new MensajeDTO<>(false,"Producto eliminado correctamente"));
    }

}
