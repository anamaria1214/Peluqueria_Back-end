package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.*;
import co.edu.uniquindio.peluqueria.dto.CuponDTOs.CrearCuponDTO;
import co.edu.uniquindio.peluqueria.dto.CuponDTOs.CuponDTO;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuponServicio {


    void actualizarCupon(@Valid CuponDTO cuponDTO) throws Exception;

    void redimirCupon(@Valid CuponDTO cuponDTO);

    List<CuponDTO> listarCupones();

    CuponDTO obtenerCupon(String id) throws Exception;

    void crearCupon(@Valid CrearCuponDTO cupon) throws Exception;

    void eliminarCupon(String id) throws Exception;
}
