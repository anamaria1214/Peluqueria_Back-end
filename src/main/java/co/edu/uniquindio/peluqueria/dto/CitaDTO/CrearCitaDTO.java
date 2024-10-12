package co.edu.uniquindio.peluqueria.dto.CitaDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CrearCitaDTO(

        @NotBlank(message = "El id del Cliente es obligatoria") String idCliente,
        @NotBlank(message = "El id del Servicio es obligatorio") String idServicio,
        @NotBlank(message = "El id del Estilista es obligatorio") String idEstilista,
        @NotBlank(message = "La fecha de la cita es obligatoria") LocalDateTime fechaInicioCita

) {
}
