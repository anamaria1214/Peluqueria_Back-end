package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.*;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CitaServicio {

    void asignarEstilista(AsignarEstilistaDTO asignarEstilistaDTO) throws Exception;

    Cita encontrarCita(EstilistaDisponiblesDTO encontrarCitaDTO) throws Exception;

    VistaCreacionCitaDTO crearCita(CrearCitaDTO crearCitaDTO) throws Exception;

    VistaEdicionCitaDTO editarCita(EditarCitaDTO crearCitaDTO) throws Exception;

    void cancelarCita(String cita) throws Exception;

    List<CitasDTO> listarCitas();

    CitasDTO buscarCita(String cita) throws Exception;

    void updateEstado(@Valid CitaUpdateDTO updateDTO) throws Exception;
}
