package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.repositorios.ClienteRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ClienteServicio;

public class ClienteServicioImpl implements ClienteServicio {

    private final ClienteRepo clienteRepo;

    public ClienteServicioImpl(ClienteRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
    }
}
