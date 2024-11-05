package co.edu.uniquindio.peluqueria.repositorios;

import co.edu.uniquindio.peluqueria.model.documentos.Cupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuponRepo extends MongoRepository<Cupon, String> {

    @Query("{ 'codigo' : ?0 }")
    Optional<Cupon> buscarPorCodigo(String nombreServicio);

    @Query("{ 'id' : ?0 }")
    Optional<Cupon> buscarCuponPorId(String id);

    @Query("{}")
    List<Cupon> cupones();

}

