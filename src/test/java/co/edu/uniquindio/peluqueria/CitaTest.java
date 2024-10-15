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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CitaTest {

    @Autowired
    private CitaRepo citaRepo;

    @Autowired
    private ServicioRepo servicioRepo;

    @Autowired
    private EstilistaServicio estilistaServicio;

    @Autowired
    private CitaServicioImpl citaServicio;

    @Autowired
    private EstilistaRepo estilistaRepo;

    @BeforeEach
    public void setUp() {
        // Limpiar el repositorio antes de cada prueba
        citaRepo.deleteAll();
        servicioRepo.deleteAll();
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

        List<Estilista> estilistaOptional = estilistaRepo.findByEspecialidad("Corte de Cabello");

        Estilista estilista = estilistaOptional.get(0);

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
        List<Cita> citaGuardada = citaRepo.findByIdCliente(citaDTO.idCliente());
        assertTrue(citaGuardada.isEmpty());
        assertEquals(resultado.fechanicioCita(), citaGuardada.get(0).getFechaInicioCita());
    }

    @Test
    void testAsignarEstilista() throws Exception {
        // Crear un estilista y una cita
        Estilista estilista = new Estilista();
        estilista.setId(UUID.randomUUID().toString());
        estilista.setNombreEstilista("Estilista 1");

        Cita cita = new Cita();
        cita.setIdCliente(UUID.randomUUID().toString());
        cita.setIdEstilista(null);
        citaRepo.save(cita);

        // Mockear el servicio estilista para que devuelva el estilista
        when(estilistaServicio.encontrarEstilista(anyString())).thenReturn(estilista);

        // Crear el DTO para asignar estilista
        AsignarEstilistaDTO asignarEstilistaDTO = new AsignarEstilistaDTO(
                estilista.getId(),
                cita.getId()
                );

        // Ejecutar el método asignarEstilista
        citaServicio.asignarEstilista(asignarEstilistaDTO);

        // Verificar que el estilista fue asignado correctamente
        Cita citaActualizada = citaRepo.findById(cita.getId()).get();
        assertEquals(estilista.getId(), citaActualizada.getIdEstilista());
    }

    @Test
    void testEditarCita() throws Exception {
        // Crear un servicio y una cita
        Servicio servicio = new Servicio();
        servicio.setNombreServicio("Corte de Cabello");
        servicio.setDescripcion("Corte para hombres");
        servicio.setPrecio(20000);
        servicio.setDuracionMinutos(30);
        servicioRepo.save(servicio);

        Cita cita = new Cita();
        cita.setIdCliente(UUID.randomUUID().toString());
        cita.setIdServicio(servicio.getId());
        cita.setFecha(LocalDateTime.now());
        citaRepo.save(cita);

        // Crear el DTO de edición
        EditarCitaDTO editarCitaDTO = new EditarCitaDTO(
                cita.getId(),
                UUID.randomUUID().toString(),
                servicio.getId(),
                LocalDateTime.now());

        // Ejecutar el método editarCita
        VistaEdicionCitaDTO resultado = citaServicio.editarCita(editarCitaDTO);

        // Verificar que la cita ha sido editada correctamente
        assertEquals(editarCitaDTO.idCita(), resultado.idCita());
    }

    @Test
    void testCancelarCita() throws Exception {
        // Crear una cita de prueba
        Cita cita = new Cita();
        cita.setIdCliente(UUID.randomUUID().toString());
        cita.setIdServicio(UUID.randomUUID().toString());
        cita.setFecha(LocalDateTime.now());
        cita.setEstado(EstadoCita.PENDIENTE);
        citaRepo.save(cita);

        // Crear el DTO para eliminar la cita
        EliminarCitaDTO eliminarCitaDTO = new EliminarCitaDTO(cita.getId());

        // Ejecutar el método cancelarCita
        citaServicio.cancelarCita(eliminarCitaDTO);

        // Verificar que la cita ha sido cancelada
        Cita citaCancelada = citaRepo.findById(cita.getId()).orElseThrow(() -> new Exception("Cita no encontrada"));
        assertEquals(EstadoCita.CANCELADA, citaCancelada.getEstado(), "La cita no fue cancelada correctamente");
    }
}
