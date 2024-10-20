package co.edu.uniquindio.peluqueria.repositorios;

import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.enums.EstadoCita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepo extends MongoRepository<Cita, String> {

    @Query("{ 'idEstilista': ?0, 'fecha': ?1 }")
    Optional<Cita> findByEstilistaIdAndFechaHora(String idEstilista, LocalDateTime fecha);

    @Query("{ $or: [ " +
            " { 'fechaInicioCita': { $lte: ?1 }, 'fechaFinCita': { $gte: ?1 } }, " +  // Nueva fechaInicio dentro de una cita existente
            " { 'fechaInicioCita': { $lte: ?2 }, 'fechaFinCita': { $gte: ?2 } }, " +  // Nueva fechaFin dentro de una cita existente
            " { 'fechaInicioCita': { $gte: ?1 }, 'fechaFinCita': { $lte: ?2 } } " +   // Cita existente dentro del nuevo rango
            " ] }")
    List<Cita> buscarCitasEnRango(LocalDateTime nuevaFechaInicio, LocalDateTime nuevaFechaFin);

    @Query("{ 'idCliente': ?0 }")
    List<Cita> findByIdCliente(String idCliente);

    @Query("{ 'idEstilista': ?0 }")
    List<Cita> findByIdEstilista(String idEstilista);

    @Query("{ 'idServicio': ?0 }")
    List<Cita> findByIdServicio(String idServicio);

    @Query("{ 'estado': ?0 }")
    List<Cita> findByEstado(EstadoCita estado);

    @Query("{ 'fechaInicioCita': { $gte: ?0, $lte: ?1 } }")
    List<Cita> findByFechaInicioCitaBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'fechaFinCita': { $gte: ?0, $lte: ?1 } }")
    List<Cita> findByFechaFinCitaBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ _id : ?0 }")
    Cita findByIdString(String s);
}
