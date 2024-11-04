package co.edu.uniquindio.peluqueria.dto.CitaDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CrearCitaDTO(

        @NotNull(message = "El id del Cliente es obligatoria") String idCliente,
        @NotNull(message = "El id del Servicio es obligatorio") String idServicio,
        @NotNull(message = "El id del Estilista es obligatorio") String idEstilista,
        @NotNull(message = "La fecha de la cita es obligatoria")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") // Asegúrate de que el patrón coincide con el formato de entrada
        LocalDateTime fechaInicioCita

) {
}
