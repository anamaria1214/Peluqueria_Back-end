package co.edu.uniquindio.peluqueria.dto.ClienteDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditarClienteDTO(
        @NotBlank(message = "El ID del cliente es obligatorio")
        String id,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(min = 10, max = 10, message = "El número de teléfono debe tener 10 dígitos")
        String telefono,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Debe ingresar un correo válido")
        String correo
) {}

