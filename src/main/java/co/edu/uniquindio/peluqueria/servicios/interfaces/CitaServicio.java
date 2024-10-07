package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface CitaServicio {

    void asignarEstilista(AsignarEstilistaDTO asignarEstilistaDTO) throws Exception;

    Cita encontrarCita(EstilistaDisponiblesDTO encontrarCitaDTO) throws Exception;

}
