package co.edu.uniquindio.peluqueria.repositorios;

import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepo extends MongoRepository<Cita, String> {
}
