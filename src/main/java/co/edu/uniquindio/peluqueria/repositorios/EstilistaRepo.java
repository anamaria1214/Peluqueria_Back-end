package co.edu.uniquindio.peluqueria.repositorios;

import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.peluqueria.model.documentos.Cita;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EstilistaRepo extends MongoRepository<Estilista, String> {
    List<Cita> findByEstilistaIdAndFechaHora(String estilistaId, LocalDateTime fechaHora);


}
