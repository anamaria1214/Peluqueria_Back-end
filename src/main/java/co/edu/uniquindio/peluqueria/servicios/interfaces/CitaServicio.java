package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaServicio {

    void asignarEstilista(AsignarEstilistaDTO asignarEstilistaDTO) throws Exception;

    Cita encontrarCita(String idCita) throws Exception;

}
