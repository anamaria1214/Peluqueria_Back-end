package co.edu.uniquindio.peluqueria;

import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.*;
import co.edu.uniquindio.peluqueria.model.documentos.Servicio;
import co.edu.uniquindio.peluqueria.repositorios.ServicioRepo;
import co.edu.uniquindio.peluqueria.servicios.implementacion.ServicioServicioImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServicioTest {

    @Autowired
    private ServicioServicioImpl servicioServicio;

    @Autowired
    private ServicioRepo servicioRepo;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();

        // Cargar el archivo JSON desde el directorio de recursos
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("servicios.json");
        assertNotNull(inputStream, "Archivo servicios.json no encontrado");

        List<Servicio> servicios = objectMapper.readValue(inputStream, new TypeReference<List<Servicio>>() {});

        // Limpiar el repositorio antes de agregar nuevos registros
        servicioRepo.deleteAll();
        servicioRepo.saveAll(servicios);
    }

    @Test
    void testCrearServicio() throws Exception {
        CrearServicioDTO crearServicioDTO = new CrearServicioDTO(
                "Corte de Cabello Nuevo",
                "Corte para hombres y mujeres",
                20000,
                30
        );

        // Ejecutar el método para crear servicio
        String servicioId = String.valueOf(servicioServicio.crearServicio(crearServicioDTO));

        // Verificar que el servicio se ha creado correctamente
        assertNotNull(servicioId);
        Optional<Servicio> servicioExistente = servicioRepo.buscarServicioPorId(servicioId);
        assertTrue(servicioExistente.isPresent());
    }

    @Test
    void testEditarServicio() throws Exception {
        // Crear un servicio de prueba para editar
        String servicioId = servicioRepo.findAll().get(0).getId();

        EditarServicioDTO editarServicioDTO = new EditarServicioDTO(
                servicioId,
                "Corte de Cabello Editado",
                "Corte actualizado para todas las edades",
                25000,
                35
        );

        // Ejecutar el método para editar servicio
        String resultado = servicioServicio.editarServicio(editarServicioDTO);

        // Verificar que el servicio ha sido editado correctamente
        assertEquals("Servicio actualizado correctamente", resultado);
    }

    @Test
    void testEliminarServicio() throws Exception {
        // Crear un servicio de prueba para eliminar
        String servicioId = servicioRepo.findAll().get(0).getId();

        // Ejecutar el método para eliminar servicio
        String resultado = servicioServicio.eliminarServicio(servicioId);

        // Verificar que el servicio ha sido eliminado correctamente
        assertEquals("Servicio eliminado correctamente", resultado);
        assertFalse(servicioRepo.existsById(servicioId));
    }

    @Test
    void testObtenerServicios() throws Exception {
        List<ItemServicioDTO> servicios = servicioServicio.obtenerServicios();

        // Verificar que se están obteniendo los servicios correctamente
        assertNotNull(servicios);
        assertFalse(servicios.isEmpty());
    }

    @Test
    void testObtenerInformacionServicio() throws Exception {
        // Crear un servicio de prueba
        String servicioId = servicioRepo.findAll().get(0).getId();

        // Obtener la información del servicio
        InformacionServicioDTO servicioInfo = servicioServicio.obtenerInformacionServicio(servicioId);

        // Verificar que la información del servicio es correcta
        assertNotNull(servicioInfo);
        assertEquals(servicioId, servicioInfo.id());
        assertEquals("Corte de Cabello", servicioInfo.nombreServicio());
    }
}
