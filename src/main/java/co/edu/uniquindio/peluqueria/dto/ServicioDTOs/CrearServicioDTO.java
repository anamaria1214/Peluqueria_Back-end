package co.edu.uniquindio.peluqueria.dto.ServicioDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record CrearServicioDTO(
        @NotBlank(message = "El nombre del servicio es obligatorio")
        @Length(max = 50, message = "El nombre del servicio no puede tener más de 50 caracteres")
        String nombreServicio,

        @NotBlank(message = "La descripción es obligatoria")
        @Length(max = 255, message = "La descripción no puede tener más de 255 caracteres")
        String descripcion,

        @Positive(message = "El precio debe ser positivo")
        double precio,

        @Positive(message = "La duración debe ser positiva")
        int duracionMinutos
) {}

