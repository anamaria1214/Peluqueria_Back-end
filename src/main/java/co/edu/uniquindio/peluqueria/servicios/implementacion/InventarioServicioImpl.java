package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.ProductoStockDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Inventario;
import co.edu.uniquindio.peluqueria.repositorios.InventarioRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.InventarioServicio;

import java.util.List;

public class InventarioServicioImpl implements InventarioServicio {

    private final InventarioRepo inventarioRepo;

    public InventarioServicioImpl(InventarioRepo inventarioRepo) {
        this.inventarioRepo = inventarioRepo;
    }

    @Override
    public void reducirCantidadProducto(ProductoStockDTO productoStockDTO) {

    }

    @Override
    public void aumentarCantidadProducto(ProductoStockDTO productoStockDTO) {

    }

    @Override
    public boolean verificarStockProducto(ProductoStockDTO productoStockDTO) {
        return false;
    }

    @Override
    public List<Inventario> listarProductos() {
        return null;
    }
}
