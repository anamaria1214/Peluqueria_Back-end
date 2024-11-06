package co.edu.uniquindio.peluqueria.dto.CitaDTO;

import jakarta.validation.constraints.NotNull;

public record CitaUpdateDTO(
        @NotNull(message = "El id de la cita es obligatoria")  String idCita
    ) {
}
