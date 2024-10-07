package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.ProductoStockDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Inventario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventarioServicio {

    void actualizarCantidad(ProductoStockDTO productoStockDTO) throws  Exception;

    boolean verificarStockProducto(ProductoStockDTO productoStockDTO) throws  Exception;

    List<Inventario> listarProductos() throws  Exception;

    Inventario obtenerProductoPorId(String id) throws Exception;

    void agregarProducto(Inventario producto);

    void eliminarProducto(String id) throws Exception;
}
