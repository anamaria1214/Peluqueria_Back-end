package co.edu.uniquindio.peluqueria.dto.CitaDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record EditarCitaDTO(
        @NotBlank(message = "El id de la Cita es obligatoria") String idCita,
        @NotBlank(message = "El id del Servicio es obligatorio") String idServicio,
        @NotBlank(message = "El id del Estilista es obligatorio") String idEstilista,
        @NotBlank(message = "La fecha de la cita es obligatoria") LocalDateTime fechanicioCita

) {
}
