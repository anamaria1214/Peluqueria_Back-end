package co.edu.uniquindio.peluqueria.repositorios;

import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstilistaRepo extends MongoRepository<Estilista, String> {

    // Buscar estilistas por nombre exacto
    @Query("{ 'nombreEstilista' : ?0 }")
    List<Estilista> findByNombreEstilista(String nombreEstilista);

    // Buscar estilistas por especialidad
    @Query("{ 'especialidad' : ?0 }")
    List<Estilista> findByEspecialidad(String especialidad);

    // Buscar estilistas que tengan un horario disponible en particular
    @Query("{ 'horariosDisponibles.horaInicio' : ?0 }")
    List<Estilista> findByHorarioDisponible(String horaInicio);

    // Buscar todos los estilistas ordenados por nombre de forma ascendente
    @Query("{}")
    List<Estilista> findAllByOrderByNombreEstilistaAsc();
}
