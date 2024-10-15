package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.ProductoStockDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Inventario;
import co.edu.uniquindio.peluqueria.repositorios.InventarioRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.InventarioServicio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioServicioImpl implements InventarioServicio {

    private final InventarioRepo inventarioRepo;

    public InventarioServicioImpl(InventarioRepo inventarioRepo) {
        this.inventarioRepo = inventarioRepo;
    }

    @Override
    public void actualizarCantidad(ProductoStockDTO productoStockDTO) throws Exception {

        Optional<Inventario> productoExistente = inventarioRepo.findById(productoStockDTO.idProducto());
        if (productoExistente.isPresent()) {
            Inventario producto = productoExistente.get();
            producto.setCantidad(productoStockDTO.cantidad());
            producto.setPrecio(productoStockDTO.precio());
            inventarioRepo.save(producto);
        } else {
            throw new Exception("No se puede actualizar");
        }
    }
    @Override
    public boolean verificarStockProducto(ProductoStockDTO productoStockDTO) throws Exception {
        Optional<Inventario> productoOptional = inventarioRepo.findById(productoStockDTO.idProducto());

        if (productoOptional.isPresent()) {
            Inventario producto = productoOptional.get();
            if (producto.getCantidad() < 10) {
                return true;
            }
            return false;
        } else {
            throw new Exception("Producto no encontrado");
        }
    }

    @Override
    public List<Inventario> listarProductos() {
        return inventarioRepo.findAll();
    }

    @Override
    public Inventario obtenerProductoPorId(String id) throws Exception{
        Optional<Inventario> producto = inventarioRepo.findById(id);
        if (producto.isPresent()) {
            return producto.get();
        } else {
            throw new Exception("Producto no encontrado");
        }
    }

    @Override
    public void agregarProducto(Inventario producto) {
        inventarioRepo.save(producto);
    }

    @Override
    public void eliminarProducto(String id) throws Exception {
        Optional<Inventario> producto = inventarioRepo.findById(id);
        if (producto.isPresent()) {
            inventarioRepo.deleteById(id);
        } else {
            throw new Exception("Producto no encontrado");
        }
    }
}
