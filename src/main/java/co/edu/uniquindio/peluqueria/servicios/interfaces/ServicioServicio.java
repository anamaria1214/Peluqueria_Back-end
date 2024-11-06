package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.*;

import java.util.List;

public interface ServicioServicio {

    VistaCreacionServicioDTO crearServicio(CrearServicioDTO crearServicioDTO) throws Exception;

    String editarServicio(EditarServicioDTO editarServicioDTO) throws Exception;

    String eliminarServicio(String id) throws Exception;

    List<ItemServicioDTO> obtenerServicios() throws Exception;

    InformacionServicioDTO obtenerInformacionServicio(String id) throws Exception;
}

