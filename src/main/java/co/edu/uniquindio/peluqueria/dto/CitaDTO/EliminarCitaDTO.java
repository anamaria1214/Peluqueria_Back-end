package co.edu.uniquindio.peluqueria.dto.CitaDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record EliminarCitaDTO(
        @NotBlank(message = "El id de la Cita es obligatoria") String idCita
) {
}
