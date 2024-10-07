package co.edu.uniquindio.peluqueria;

import co.edu.uniquindio.peluqueria.model.documentos.Servicio;
import co.edu.uniquindio.peluqueria.repositorios.ServicioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServicioTest {

    @Autowired
    private ServicioRepo servicioRepo;

    @Test
    public void crearServicioTest() {
        // Crear un nuevo servicio
        Servicio servicio = Servicio.builder()
                .nombreServicio("Corte de Cabello")
                .descripcion("Corte de cabello para hombres")
                .precio(15000)
                .duracionMinutos(30)
                .build();

        // Guardar el servicio en la base de datos
        Servicio servicioCreado = servicioRepo.save(servicio);

        // Verificar que el servicio ha sido creado correctamente
        Assertions.assertNotNull(servicioCreado);
        Assertions.assertEquals("Corte de Cabello", servicioCreado.getNombreServicio());
    }
}
