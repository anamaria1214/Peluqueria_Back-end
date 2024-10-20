package co.edu.uniquindio.peluqueria;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.*;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import co.edu.uniquindio.peluqueria.model.documentos.Servicio;
import co.edu.uniquindio.peluqueria.model.enums.EstadoCita;
import co.edu.uniquindio.peluqueria.repositorios.CitaRepo;
import co.edu.uniquindio.peluqueria.repositorios.EstilistaRepo;
import co.edu.uniquindio.peluqueria.repositorios.ServicioRepo;
import co.edu.uniquindio.peluqueria.servicios.implementacion.CitaServicioImpl;
import co.edu.uniquindio.peluqueria.servicios.interfaces.EstilistaServicio;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CitaTest {

    @Autowired
    private CitaRepo citaRepo;

    @Autowired
    private ServicioRepo servicioRepo;

    @Autowired
    private EstilistaRepo estilistaRepo;

    @Autowired
    private CitaServicioImpl citaServicio;

    @Autowired
    private EstilistaServicio estilistaServicio;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        // Inicializar el ObjectMapper
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Para manejar las fechas de LocalDateTime

        // Cargar los archivos JSON desde el directorio de recursos
        InputStream citasInputStream = getClass().getClassLoader().getResourceAsStream("citas.json");
        InputStream estilistasInputStream = getClass().getClassLoader().getResourceAsStream("estilistas.json");

        assertNotNull(citasInputStream, "Archivo citas.json no encontrado");
        assertNotNull(estilistasInputStream, "Archivo estilistas.json no encontrado");

        // Leer los archivos JSON y mapearlos a listas de objetos
        List<Cita> citas = objectMapper.readValue(citasInputStream, new TypeReference<List<Cita>>() {});
        List<Estilista> estilistas = objectMapper.readValue(estilistasInputStream, new TypeReference<List<Estilista>>() {});

        // Limpiar los repositorios antes de agregar nuevos registros
        citaRepo.deleteAll();
        estilistaRepo.deleteAll();

        // Guardar los datos cargados en los repositorios
        estilistaRepo.saveAll(estilistas);
        citaRepo.saveAll(citas);
    }

    @Test
    void testCrearCita() throws Exception {
        // Crear un servicio y guardarlo en el repo
        Servicio servicio = new Servicio();
        servicio.setNombreServicio("Corte de Cabello");
        servicio.setDescripcion("Corte para hombres");
        servicio.setPrecio(20000);
        servicio.setDuracionMinutos(30);
        servicioRepo.save(servicio);

        // Obtener un estilista por especialidad
        List<Estilista> estilistas = estilistaRepo.findByEspecialidad("Corte de Cabello");
        Estilista estilista = estilistas.get(0);

        // Crear el DTO para la cita
        CrearCitaDTO citaDTO = new CrearCitaDTO(
                UUID.randomUUID().toString(),
                servicio.getId(),
                estilista.getId(),
                LocalDateTime.now()
        );

        // Ejecutar el método crearCita
        VistaCreacionCitaDTO resultado = citaServicio.crearCita(citaDTO);

        // Verificar que la cita fue creada y guardada en el repositorio
        List<Cita> citasGuardadas = citaRepo.findByIdCliente(citaDTO.idCliente());
        assertFalse(citasGuardadas.isEmpty(), "La cita no fue guardada correctamente");

        // Comparar las fechas sin considerar nanosegundos
        LocalDateTime fechaEsperada = citasGuardadas.get(0).getFechaInicioCita().withNano(0);
        LocalDateTime fechaResultado = resultado.fechanicioCita().withNano(0);
        assertEquals(fechaEsperada, fechaResultado, "La fecha de inicio de la cita no coincide");
    }


    @Test
    void testAsignarEstilista() throws Exception {
        // Cargar estilista desde el archivo JSON
        Estilista estilista = estilistaRepo.findById("60d21b4667d0d8992e610c87").orElseThrow(() -> new Exception("Estilista no encontrado"));

        AsignarEstilistaDTO asignarEstilistaDTO = new AsignarEstilistaDTO(estilista.getId(), "60d21b4667d0d8992e610c86");

        // Ejecutar el método asignarEstilista
        citaServicio.asignarEstilista(asignarEstilistaDTO);

        // Verificar que el estilista ha sido asignado
        Cita citaActualizada = citaRepo.findByIdString("60d21b4667d0d8992e610c86");
        assertEquals(estilista.getId(), citaActualizada.getIdEstilista());
    }

    @Test
    void testCancelarCita() throws Exception {
        // Cargar una cita desde el archivo JSON
        Cita cita = citaRepo.findById("60d21b4667d0d8992e610c85").orElseThrow(() -> new Exception("Cita no encontrada"));

        // Crear el DTO para cancelar la cita
        EliminarCitaDTO eliminarCitaDTO = new EliminarCitaDTO(cita.getId());

        // Ejecutar el método cancelarCita
        citaServicio.cancelarCita(eliminarCitaDTO);

        // Verificar que la cita ha sido cancelada
        Cita citaCancelada = citaRepo.findById(cita.getId()).get();
        assertEquals(EstadoCita.CANCELADA, citaCancelada.getEstado());
    }
}
