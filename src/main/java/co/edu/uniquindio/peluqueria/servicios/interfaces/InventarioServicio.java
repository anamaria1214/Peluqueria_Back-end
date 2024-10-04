package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.ProductoStockDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Inventario;

import java.util.List;

public interface InventarioServicio {

    void reducirCantidadProducto(ProductoStockDTO productoStockDTO) throws  Exception;

    void aumentarCantidadProducto(ProductoStockDTO productoStockDTO) throws  Exception;

    boolean verificarStockProducto(ProductoStockDTO productoStockDTO) throws  Exception;

    List<Inventario> listarProductos() throws  Exception;
}
