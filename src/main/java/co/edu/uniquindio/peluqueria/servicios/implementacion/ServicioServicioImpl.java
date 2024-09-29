package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.repositorios.ServicioRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ServicioServicio;

public class ServicioServicioImpl implements ServicioServicio {

    private final ServicioRepo servicioRepo;

    public ServicioServicioImpl(ServicioRepo servicioRepo) {
        this.servicioRepo = servicioRepo;
    }
}
