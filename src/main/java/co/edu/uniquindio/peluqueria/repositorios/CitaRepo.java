package co.edu.uniquindio.peluqueria.repositorios;

import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepo extends MongoRepository<Cita, String> {
        @Query("{ 'idEstilista': ?0, 'fecha': ?1 }")
        Cita findByEstilistaIdAndFechaHora(String idEstilista, LocalDateTime fecha);

    }
