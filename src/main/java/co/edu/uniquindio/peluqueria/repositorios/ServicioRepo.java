package co.edu.uniquindio.peluqueria.repositorios;

import co.edu.uniquindio.peluqueria.model.documentos.Servicio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ServicioRepo extends MongoRepository<Servicio, String> {

    @Query("{ 'nombreServicio' : ?0 }")
    Optional<Servicio> buscarServicioPorNombre(String nombreServicio);

    @Query("{ 'id' : ?0 }")
    Optional<Servicio> buscarServicioPorId(String id);

    @Query("{}")
    List<Servicio> obtenerTodosLosServicios(); // Consulta todos los servicios disponibles
}

