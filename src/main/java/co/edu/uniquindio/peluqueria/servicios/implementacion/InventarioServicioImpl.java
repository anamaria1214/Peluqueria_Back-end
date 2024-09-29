package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.repositorios.InventarioRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.InventarioServicio;

public class InventarioServicioImpl implements InventarioServicio {

    private final InventarioRepo inventarioRepo;

    public InventarioServicioImpl(InventarioRepo inventarioRepo) {
        this.inventarioRepo = inventarioRepo;
    }
}
