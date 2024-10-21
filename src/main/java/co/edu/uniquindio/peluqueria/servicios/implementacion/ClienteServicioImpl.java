package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.ClienteDTOs.CrearClienteDTO;
import co.edu.uniquindio.peluqueria.dto.ClienteDTOs.EditarClienteDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cliente;
import co.edu.uniquindio.peluqueria.repositorios.ClienteRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio {

    @Autowired
    private ClienteRepo clienteRepo;

    public void registrarCliente(CrearClienteDTO nuevoClienteDTO) throws Exception {
        // Verificar si el cliente ya existe por ID
        if (clienteRepo.existsById(nuevoClienteDTO.id())) {
            throw new Exception("El ID ya está registrado.");
        }

        // Continuar con el registro del cliente
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setId(nuevoClienteDTO.id());
        nuevoCliente.setNombre(nuevoClienteDTO.nombre());
        nuevoCliente.setTelefono(nuevoClienteDTO.telefono());
        nuevoCliente.setCorreo(nuevoClienteDTO.correo());
        nuevoCliente.setPassword(nuevoClienteDTO.password());

        clienteRepo.save(nuevoCliente);
    }


    @Override
    public Cliente editarCliente(EditarClienteDTO editarClienteDTO) throws Exception {
        Optional<Cliente> clienteOpt = clienteRepo.findById(editarClienteDTO.id());
        if (clienteOpt.isEmpty()) {
            throw new Exception("El cliente no existe.");
        }

        Cliente cliente = clienteOpt.get();
        cliente.setNombre(editarClienteDTO.nombre());
        cliente.setTelefono(editarClienteDTO.telefono());
        cliente.setCorreo(editarClienteDTO.correo());

        return clienteRepo.save(cliente);
    }

    @Override
    public void eliminarCliente(String id) throws Exception {
        if (!clienteRepo.existsById(id)) {
            throw new Exception("El cliente no existe.");
        }
        clienteRepo.deleteById(id);
    }

    @Override
    public Cliente obtenerClientePorId(String id) throws Exception {
        return clienteRepo.findById(id).orElseThrow(() -> new Exception("El cliente no fue encontrado."));
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }
}
