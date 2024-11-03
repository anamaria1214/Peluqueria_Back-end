package co.edu.uniquindio.peluqueria.dto.TokenDTOs;

public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
}
