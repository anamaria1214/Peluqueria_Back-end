package co.edu.uniquindio.peluqueria.repositorios;

import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import co.edu.uniquindio.peluqueria.model.documentos.Inventario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepo extends MongoRepository<Inventario, String> {
}
