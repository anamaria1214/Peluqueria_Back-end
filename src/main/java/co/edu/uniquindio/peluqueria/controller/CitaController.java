package co.edu.uniquindio.peluqueria.controller;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.*;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.dto.MensajeDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CitaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cita")
public class CitaController {

    private final CitaServicio citaServicio;

    @PostMapping("/crear-cita")
    public ResponseEntity<MensajeDTO<VistaCreacionCitaDTO>> crearCita(CrearCitaDTO crearCitaDTO) throws Exception{
        VistaCreacionCitaDTO citaCreada = citaServicio.crearCita(crearCitaDTO);
        return  ResponseEntity.ok(new MensajeDTO<>(false, citaCreada));
    }

    @DeleteMapping("/eliminar-cita")
    public ResponseEntity<MensajeDTO<String>> cancelar(EliminarCitaDTO eliminarCitaDTO) throws Exception{
        citaServicio.cancelarCita(eliminarCitaDTO);
        return  ResponseEntity.ok(new MensajeDTO<>(false, "Cita eliminada correctamente"));
    }

    @PutMapping("/editar-cita")
    public ResponseEntity<MensajeDTO<VistaEdicionCitaDTO>> editarCita(EditarCitaDTO editarCitaDTO) throws Exception{
        VistaEdicionCitaDTO citaEditada = citaServicio.editarCita(editarCitaDTO);
        return  ResponseEntity.ok(new MensajeDTO<>(false, citaEditada));
    }

    @PutMapping("/asignarEstilista")
    public ResponseEntity<MensajeDTO<String>> asignarEstilista(AsignarEstilistaDTO asignarEstilistaDTO) throws Exception{
        citaServicio.asignarEstilista(asignarEstilistaDTO);
        return  ResponseEntity.ok(new MensajeDTO<>(false, "Estilista asignado correctamente"));
    }

    @GetMapping("/encontrarCita")
    public ResponseEntity<MensajeDTO<Cita>> encontrarCita(EstilistaDisponiblesDTO encontrarCitaDTO) throws Exception{
        Cita cita= citaServicio.encontrarCita(encontrarCitaDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, cita));
    }


}
