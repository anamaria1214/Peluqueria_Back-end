package co.edu.uniquindio.peluqueria.controller;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.dto.MensajeDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Inventario;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CitaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cita")
public class CitaController {

    private final CitaServicio citaServicio;

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
