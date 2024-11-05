package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.dto.EstilistasDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

public interface EstilistaServicio {

    boolean verificarDisponibilidadEstilista(EstilistaDisponiblesDTO estilistaDisponiblesDTO) throws Exception;

    List<Estilista> obtenerEstilistasDisponibles(LocalDateTime fechaHora) throws Exception;

    List<Estilista> obtenerEstilistas() throws Exception;

    Estilista encontrarEstilista(String idEstilista) throws Exception;

    // Método para guardar un estilista
    Estilista guardarEstilista(Estilista estilista) throws Exception;

    void eliminarEstilista(String id) throws Exception;

    EstilistasDTO obtenerEstilista(String id) throws  Exception;

    EstilistasDTO editarEstilista(@Valid EstilistasDTO estilistasDTO) throws Exception;
}
