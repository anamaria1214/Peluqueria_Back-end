package co.edu.uniquindio.peluqueria;

import co.edu.uniquindio.peluqueria.dto.ClienteDTOs.CrearClienteDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cliente;
import co.edu.uniquindio.peluqueria.repositorios.ClienteRepo;
import co.edu.uniquindio.peluqueria.servicios.implementacion.ClienteServicioImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteTest {

    @Autowired
    private ClienteServicioImpl clienteServicio;

    @Autowired
    private ClienteRepo clienteRepo;

    @BeforeEach
    public void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Cargar el archivo JSON desde el directorio de recursos
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("clientes.json");
        assertNotNull(inputStream, "Archivo clientes.json no encontrado");

        List<Cliente> clientes = objectMapper.readValue(inputStream, new TypeReference<List<Cliente>>() {});

        clienteRepo.deleteAll(); // Limpiar el repositorio antes de agregar nuevos registros
        clienteRepo.saveAll(clientes);
    }

    @Test
    public void testCrearCliente() throws Exception {
        CrearClienteDTO nuevoClienteDTO = new CrearClienteDTO("60d21b4667d0d8992e610c95", "David", "1234567890", "nuevo_email@example.com", "password123");

        clienteServicio.registrarCliente(nuevoClienteDTO);

        Optional<Cliente> clienteOptional = clienteRepo.findById(nuevoClienteDTO.id());
        assertTrue(clienteOptional.isPresent(), "El cliente debería estar presente");
        assertEquals("David", clienteOptional.get().getNombre(), "El nombre debería coincidir");
    }

    @Test
    public void testBuscarClientePorId() {
        // ID que existe en el archivo clientes.json
        String clienteId = "60d21b4667d0d8992e610c85";

        // Ejecutar la búsqueda por ID
        Optional<Cliente> clienteOptional = clienteRepo.findById(clienteId);

        // Verificar que el cliente fue encontrado
        assertTrue(clienteOptional.isPresent(), "El cliente debería estar presente");
        assertEquals("Carlos", clienteOptional.get().getNombre(), "El nombre debería coincidir");
    }

    @Test
    public void testEditarCliente() {
        // ID que existe en el archivo clientes.json
        String clienteId = "60d21b4667d0d8992e610c85";
        Optional<Cliente> clienteOptional = clienteRepo.findById(clienteId);
        assertTrue(clienteOptional.isPresent(), "El cliente debería estar presente");

        Cliente cliente = clienteOptional.get();
        cliente.setNombre("Carlos Modificado");
        clienteRepo.save(cliente);

        Optional<Cliente> clienteModificado = clienteRepo.findById(clienteId);
        assertTrue(clienteModificado.isPresent(), "El cliente debería estar presente después de la modificación");
        assertEquals("Carlos Modificado", clienteModificado.get().getNombre(), "El nombre debería haber sido modificado");
    }

    @Test
    public void testEliminarCliente() {
        // ID que existe en el archivo clientes.json
        String clienteId = "60d21b4667d0d8992e610c85";
        Optional<Cliente> clienteOptional = clienteRepo.findById(clienteId);
        assertTrue(clienteOptional.isPresent(), "El cliente debería estar presente");

        clienteRepo.delete(clienteOptional.get());

        Optional<Cliente> clienteEliminado = clienteRepo.findById(clienteId);
        assertFalse(clienteEliminado.isPresent(), "El cliente no debería estar presente después de eliminarlo");
    }

    @Test
    public void testListarClientes() {
        List<Cliente> clientes = clienteRepo.findAll();
        assertEquals(5, clientes.size(), "El número de clientes debería ser 5");
    }

}
