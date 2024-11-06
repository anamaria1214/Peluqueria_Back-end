package co.edu.uniquindio.peluqueria.dto.CitaDTO;

import co.edu.uniquindio.peluqueria.model.enums.EstadoCita;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CitasDTO(

        @NotNull(message = "El id del Cliente es obligatoria") String idCita,
        @NotNull(message = "El id del Servicio es obligatorio") String servicio,
        @NotNull(message = "El id del Estilista es obligatorio") String estilista,
        @NotNull(message = "El id del cliente es obligatorio") String cliente,
        @NotNull(message = "La fecha de la cita es obligatoria")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Asegúrate de que el patrón coincide con el formato de entrada
        LocalDateTime fechaInicioCita,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss") // Asegúrate de que el patrón coincide con el formato de entrada
        LocalDateTime horaCita,
        EstadoCita estado

) {
}
