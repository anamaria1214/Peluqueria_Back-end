package co.edu.uniquindio.peluqueria.dto;

public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
}
