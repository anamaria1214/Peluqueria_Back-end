package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.repositorios.EstilistaRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.EstilistaServicio;

public class EstilistaServicioImpl implements EstilistaServicio {

    private final EstilistaRepo estilistaRepo;

    public EstilistaServicioImpl(EstilistaRepo estilistaRepo) {
        this.estilistaRepo = estilistaRepo;
    }
}
