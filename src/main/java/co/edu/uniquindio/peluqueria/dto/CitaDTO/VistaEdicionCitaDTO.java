package co.edu.uniquindio.peluqueria.dto.CitaDTO;

import java.time.LocalDateTime;

public record VistaEdicionCitaDTO(
        String idCita,
        String idCliente,
        String idServicio,
        String idEstilista,
        LocalDateTime fechanicioCita,
        LocalDateTime fechaFinCita) {
}
