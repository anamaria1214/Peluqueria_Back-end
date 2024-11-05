package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.*;
import co.edu.uniquindio.peluqueria.model.documentos.*;
import co.edu.uniquindio.peluqueria.model.enums.EstadoCita;
import co.edu.uniquindio.peluqueria.repositorios.*;
import co.edu.uniquindio.peluqueria.servicios.interfaces.InventarioServicio;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioServicioImpl implements InventarioServicio {

    private final InventarioRepo inventarioRepo;
    private final CitaRepo citaRepo;
    private final ServicioRepo servicioRepo;
    private final EstilistaRepo estilistaRepo;
    private final ClienteRepo clienteRepo;

    public InventarioServicioImpl(InventarioRepo inventarioRepo, CitaRepo citaRepo, ServicioRepo servicioRepo, EstilistaRepo estilistaRepo, ClienteRepo clienteRepo) {
        this.inventarioRepo = inventarioRepo;
        this.citaRepo = citaRepo;
        this.servicioRepo = servicioRepo;
        this.estilistaRepo = estilistaRepo;
        this.clienteRepo = clienteRepo;
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
    public void agregarProducto(AgregarProductoStockDTO producto) {
        inventarioRepo.save(new Inventario(producto.nombre(), producto.cantidad(), producto.precio()));
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

    @Override
    public List<ReporteDTO> obtenerReporte(String fechaInicio, String fechaFin) {

        LocalDateTime fechaInicioConvertida = LocalDateTime.parse(fechaInicio + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime fechaFinConvertida = LocalDateTime.parse(fechaFin + "T23:59:59", DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        List<ReporteDTO> reporteDTOS = new ArrayList<>();

        List<Cita> citasFinalizadas = citaRepo.buscarCitasEnRango(fechaInicioConvertida, fechaFinConvertida);
        System.out.println(citasFinalizadas);
        for (Cita cita : citasFinalizadas){
            if (cita.getEstado() == EstadoCita.TERMINADA){

                Optional<Servicio> servicioOptional = servicioRepo.findById(cita.getIdServicio());
                Servicio servicio = servicioOptional.get();

                Optional<Estilista> estilistaOptional = estilistaRepo.findById(cita.getIdEstilista());
                Estilista estilista = estilistaOptional.get();

                Optional<Cliente> clienteOptional = clienteRepo.findById(cita.getIdCliente());
                Cliente cliente = clienteOptional.get();

                reporteDTOS.add(new ReporteDTO(cita.getId(), servicio.getNombreServicio(), estilista.getNombreEstilista(), cliente.getNombre(), cita.getFecha(), (float) servicio.getPrecio()));
            }
        }
        return reporteDTOS;
    }

    @Override
    public List<HistorialDTO> obtenerHistorial(String id) {
        List<Cita> citasID = citaRepo.findByIdCliente(id);
        List<HistorialDTO> historialDTOS = new ArrayList<>();
        for (Cita cita : citasID){
            Optional<Servicio> servicioOptional = servicioRepo.findById(cita.getIdServicio());
            Servicio servicio = servicioOptional.get();

            Optional<Estilista> estilistaOptional = estilistaRepo.findById(cita.getIdEstilista());
            Estilista estilista = estilistaOptional.get();

            Optional<Cliente> clienteOptional = clienteRepo.findById(cita.getIdCliente());
            Cliente cliente = clienteOptional.get();

            historialDTOS.add(new HistorialDTO(cita.getId(), servicio.getNombreServicio(), estilista.getNombreEstilista(), cita.getFecha(), (float) servicio.getPrecio()));
        }
        return historialDTOS;
    }

}
