package co.edu.uniquindio.peluqueria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record HistorialDTO(
        @NotNull (message = "El id de la cita es obligatorio")String idCita,
        @NotNull (message = "El id del servicio es obligatorio")String servicio,
        @NotNull(message = "El id del Estilista es obligatorio") String estilista,
        @NotNull(message = "La fecha de la cita es obligatoria")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Asegúrate de que el patrón coincide con el formato de entrada
        LocalDateTime fecha,
        float valor
)

{
}
