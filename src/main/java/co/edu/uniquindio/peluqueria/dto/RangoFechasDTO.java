package co.edu.uniquindio.peluqueria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RangoFechasDTO(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Asegúrate de que el patrón coincide con el formato de entrada
        LocalDateTime fechaInicio,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Asegúrate de que el patrón coincide con el formato de entrada
        LocalDateTime fechaFin
)

{
}
