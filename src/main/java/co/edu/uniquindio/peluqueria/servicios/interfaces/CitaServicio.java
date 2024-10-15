package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.*;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import org.springframework.stereotype.Service;

@Service
public interface CitaServicio {

    void asignarEstilista(AsignarEstilistaDTO asignarEstilistaDTO) throws Exception;

    Cita encontrarCita(EstilistaDisponiblesDTO encontrarCitaDTO) throws Exception;

    VistaCreacionCitaDTO crearCita(CrearCitaDTO crearCitaDTO) throws Exception;

    VistaEdicionCitaDTO editarCita(EditarCitaDTO crearCitaDTO) throws Exception;

    void cancelarCita(EliminarCitaDTO crearCitaDTO) throws Exception;

}
