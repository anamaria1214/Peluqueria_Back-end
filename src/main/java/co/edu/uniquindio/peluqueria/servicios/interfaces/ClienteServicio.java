package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.ClienteDTOs.CrearClienteDTO;
import co.edu.uniquindio.peluqueria.dto.ClienteDTOs.EditarClienteDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cliente;

import java.util.List;

public interface ClienteServicio {
    void registrarCliente(CrearClienteDTO crearClienteDTO) throws Exception;
    Cliente editarCliente(EditarClienteDTO editarClienteDTO) throws Exception;
    void eliminarCliente(String id) throws Exception;
    Cliente obtenerClientePorId(String id) throws Exception;
    List<Cliente> listarClientes() throws Exception;
}
