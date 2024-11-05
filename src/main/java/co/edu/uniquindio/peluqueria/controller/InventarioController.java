package co.edu.uniquindio.peluqueria.controller;

import co.edu.uniquindio.peluqueria.dto.*;
import co.edu.uniquindio.peluqueria.model.documentos.Inventario;
import co.edu.uniquindio.peluqueria.servicios.interfaces.InventarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioServicio inventarioServicio;

    @PutMapping("/actualizarCantidad")
    public ResponseEntity<MensajeDTO<String>> actualizarCantidad(@Valid @RequestBody ProductoStockDTO productoStockDTO) throws Exception {
        inventarioServicio.actualizarCantidad(productoStockDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Producto actualizado"));
    }

    @GetMapping("/verificarStockProducto")
    public ResponseEntity<MensajeDTO<Boolean>> verificarStockProducto(@Valid @RequestBody ProductoStockDTO productoStockDTO) throws Exception {
        boolean respuesta= inventarioServicio.verificarStockProducto(productoStockDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false,respuesta));
    }

    @GetMapping("/listarProductos")
    public ResponseEntity<MensajeDTO<List<Inventario>>> listarProductos() throws  Exception{
        List<Inventario> inventarios= inventarioServicio.listarProductos();
        return ResponseEntity.ok(new MensajeDTO<>(false,inventarios));
    }

    @GetMapping("/obtenerProductoPorId")
    public ResponseEntity<MensajeDTO<Inventario>> obtenerProductoPorId(@PathVariable String id) throws Exception{
        Inventario inventario= inventarioServicio.obtenerProductoPorId(id);
        return ResponseEntity.ok(new MensajeDTO<>(false,inventario));
    }

    @PostMapping("/agregarProducto")
    public ResponseEntity<MensajeDTO<String>> agregarProducto(@Valid @RequestBody AgregarProductoStockDTO producto){
        inventarioServicio.agregarProducto(producto);
        return ResponseEntity.ok(new MensajeDTO<>(false,"Producto agregado correctamente"));
    }

    @DeleteMapping("/eliminarProducto/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarProducto(@PathVariable String id) throws Exception{
        inventarioServicio.eliminarProducto(id);
        return ResponseEntity.ok(new MensajeDTO<>(false,"Producto eliminado correctamente"));
    }

    @GetMapping("/generarReporte/{fechaInicio}/{fechaFin}")
    public ResponseEntity<MensajeDTO<List<ReporteDTO>>> obtenerProductoPorId(@PathVariable String fechaInicio,
                                                                             @PathVariable String fechaFin) throws Exception{
        List<ReporteDTO> reporteDTO= inventarioServicio.obtenerReporte(fechaInicio, fechaFin);
        return ResponseEntity.ok(new MensajeDTO<>(false,reporteDTO));
    }

        @GetMapping("/generarHistorial/{id}")
    public ResponseEntity<MensajeDTO<List<HistorialDTO>>> obtenerHistorial(@PathVariable String id) throws Exception{
        List<HistorialDTO> historialDTOS= inventarioServicio.obtenerHistorial(id);
        return ResponseEntity.ok(new MensajeDTO<>(false,historialDTOS));
    }
}
