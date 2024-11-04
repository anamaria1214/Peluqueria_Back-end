package co.edu.uniquindio.peluqueria.controller;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.*;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.dto.MensajeDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CitaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cita")
public class CitaController {

    private final CitaServicio citaServicio;

    @PostMapping("/crear-cita")
    public ResponseEntity<MensajeDTO<VistaCreacionCitaDTO>> crearCita(@Valid @RequestBody CrearCitaDTO crearCitaDTO) throws Exception {
        VistaCreacionCitaDTO citaCreada = citaServicio.crearCita(crearCitaDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, citaCreada));
    }

        @DeleteMapping("/eliminar-cita/{id}")
    public ResponseEntity<MensajeDTO<String>> cancelar(@PathVariable String id) throws Exception {
        citaServicio.cancelarCita(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cita eliminada correctamente"));
    }

    @PutMapping("/editar-cita")
    public ResponseEntity<MensajeDTO<VistaEdicionCitaDTO>> editarCita(@Valid @RequestBody EditarCitaDTO editarCitaDTO) throws Exception {
        VistaEdicionCitaDTO citaEditada = citaServicio.editarCita(editarCitaDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, citaEditada));
    }

    @PutMapping("/asignarEstilista")
    public ResponseEntity<MensajeDTO<String>> asignarEstilista(@Valid @RequestBody AsignarEstilistaDTO asignarEstilistaDTO) throws Exception {
        citaServicio.asignarEstilista(asignarEstilistaDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Estilista asignado correctamente"));
    }

    @GetMapping("/encontrarCita")
    public ResponseEntity<MensajeDTO<Cita>> encontrarCita(@Valid @RequestBody EstilistaDisponiblesDTO encontrarCitaDTO) throws Exception {
        Cita cita = citaServicio.encontrarCita(encontrarCitaDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, cita));
    }

    @GetMapping("/listar-citas")
    public ResponseEntity<MensajeDTO<List<CitasDTO>>> listarCitas() throws Exception {
        List<CitasDTO> cita = citaServicio.listarCitas();
        return ResponseEntity.ok(new MensajeDTO<>(false, cita));
    }

    @GetMapping("/cita/obtener/{id}")
    public ResponseEntity<MensajeDTO<CitasDTO>> obtenerCita(@PathVariable String id) throws Exception {
        CitasDTO cita = citaServicio.buscarCita(id );
        return ResponseEntity.ok(new MensajeDTO<>(false, cita));
    }
}
