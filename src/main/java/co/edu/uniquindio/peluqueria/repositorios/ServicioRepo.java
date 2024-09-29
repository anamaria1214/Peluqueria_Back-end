package co.edu.uniquindio.peluqueria.repositorios;

import co.edu.uniquindio.peluqueria.model.documentos.Servicio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepo extends MongoRepository<Servicio, String> {
}
