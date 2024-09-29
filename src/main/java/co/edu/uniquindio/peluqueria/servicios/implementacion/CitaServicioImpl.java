package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.repositorios.CitaRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CitaServicio;

public class CitaServicioImpl implements CitaServicio {

    private final CitaRepo citaRepo;

    public CitaServicioImpl(CitaRepo citaRepo) {
        this.citaRepo = citaRepo;
    }
}
