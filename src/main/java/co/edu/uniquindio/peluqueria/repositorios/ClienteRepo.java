package co.edu.uniquindio.peluqueria.repositorios;

import co.edu.uniquindio.peluqueria.model.documentos.Cliente;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepo extends MongoRepository<Cliente, String> {

    @Query("{ '_id' : ?0 }")
    Optional<Cliente> findById(ObjectId id);

    @Query("{ 'cliente.cedula' : ?0 }")
    Optional<Cliente> buscarCuentaPorCedula(String cedula);

    @Query("{ 'email' : ?0 }")
    Optional<Cliente> buscarCuentaPorCorreo(String correo);

    @Query(value = "{}", fields = "{ 'email' : 1 }")
    List<String> obtenerTodosLosCorreos();
}
