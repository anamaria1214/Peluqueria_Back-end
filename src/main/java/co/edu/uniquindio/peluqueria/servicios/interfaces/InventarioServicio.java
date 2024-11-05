package co.edu.uniquindio.peluqueria.servicios.interfaces;

import co.edu.uniquindio.peluqueria.dto.*;
import co.edu.uniquindio.peluqueria.model.documentos.Inventario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventarioServicio {

    void actualizarCantidad(ProductoStockDTO productoStockDTO) throws  Exception;

    boolean verificarStockProducto(ProductoStockDTO productoStockDTO) throws  Exception;

    List<Inventario> listarProductos() throws  Exception;

    Inventario obtenerProductoPorId(String id) throws Exception;

    void agregarProducto(AgregarProductoStockDTO producto);

    void eliminarProducto(String id) throws Exception;

    List<HistorialDTO> obtenerHistorial(String id);

    List<ReporteDTO> obtenerReporte(String fechaInicio, String fechaFin);
}
