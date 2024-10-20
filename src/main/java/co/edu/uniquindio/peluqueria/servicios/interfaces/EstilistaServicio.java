package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface EstilistaServicio {

    boolean verificarDisponibilidadEstilista(EstilistaDisponiblesDTO estilistaDisponiblesDTO) throws Exception;

    List<Estilista> obtenerEstilistasDisponibles(LocalDateTime fechaHora) throws Exception;

    Estilista encontrarEstilista(String idEstilista) throws Exception;

    // Método para guardar un estilista
    Estilista guardarEstilista(Estilista estilista) throws Exception;
}
