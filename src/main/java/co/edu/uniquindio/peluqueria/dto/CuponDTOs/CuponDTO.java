package co.edu.uniquindio.peluqueria.dto.CuponDTOs;

import co.edu.uniquindio.peluqueria.model.enums.EstadoCupon;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record CuponDTO(
        @NotBlank(message = "El código del cupón es obligatorio")String id,
        @Length(max = 10, message = "El código del cupón no debe exceder los 10 caracteres") String codigo,

        @Positive(message = "El descuento debe ser un número positivo")
        float descuento,

        @NotNull(message = "El estado del cupón es obligatorio")
        EstadoCupon estado) {
}
