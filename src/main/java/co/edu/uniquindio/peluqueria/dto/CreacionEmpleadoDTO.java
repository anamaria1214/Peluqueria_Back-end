package co.edu.uniquindio.peluqueria.dto;

import jakarta.validation.constraints.NotNull;

public record CreacionEmpleadoDTO(
        @NotNull(message = "El id del Cliente es obligatoria") String nombre,
        @NotNull(message = "El id del Servicio es obligatorio") String especialidad
) {
}
