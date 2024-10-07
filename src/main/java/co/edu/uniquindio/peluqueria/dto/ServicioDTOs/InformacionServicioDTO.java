package co.edu.uniquindio.peluqueria.dto.ServicioDTOs;

public record InformacionServicioDTO(
        String id,
        String nombreServicio,
        String descripcion,
        double precio,
        int duracionMinutos
) {}

