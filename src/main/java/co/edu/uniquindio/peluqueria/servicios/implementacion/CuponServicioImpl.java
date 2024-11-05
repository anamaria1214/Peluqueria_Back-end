package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.*;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.*;
import co.edu.uniquindio.peluqueria.dto.CuponDTOs.CrearCuponDTO;
import co.edu.uniquindio.peluqueria.dto.CuponDTOs.CuponDTO;
import co.edu.uniquindio.peluqueria.model.documentos.*;
import co.edu.uniquindio.peluqueria.model.enums.EstadoCita;
import co.edu.uniquindio.peluqueria.model.enums.EstadoCupon;
import co.edu.uniquindio.peluqueria.repositorios.*;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CuponServicio;
import co.edu.uniquindio.peluqueria.servicios.interfaces.InventarioServicio;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuponServicioImpl implements CuponServicio {

    private final InventarioRepo inventarioRepo;
    private final CitaRepo citaRepo;
    private final ServicioRepo servicioRepo;
    private final EstilistaRepo estilistaRepo;
    private final ClienteRepo clienteRepo;
    private final CuponRepo cuponRepo;

    public CuponServicioImpl(InventarioRepo inventarioRepo, CitaRepo citaRepo, ServicioRepo servicioRepo, EstilistaRepo estilistaRepo, ClienteRepo clienteRepo, CuponRepo cuponRepo) {
        this.inventarioRepo = inventarioRepo;
        this.citaRepo = citaRepo;
        this.servicioRepo = servicioRepo;
        this.estilistaRepo = estilistaRepo;
        this.clienteRepo = clienteRepo;
        this.cuponRepo = cuponRepo;
    }

    @Override
    public void actualizarCupon(CuponDTO cuponDTO) throws Exception  {
        Optional<Cupon> cuponOptional = cuponRepo.buscarCuponPorId(cuponDTO.id());
        if ( cuponOptional.isPresent() ) {
            Cupon cuponNuevo = new Cupon(cuponDTO.id(), cuponDTO.estado(), cuponDTO.descuento(), cuponDTO.estado(),cuponDTO.codigo());
            cuponRepo.save(cuponNuevo);
        }else{
            throw new Exception("No se pudo actualizar el cupon");
        }
    }

    @Override
    public void redimirCupon(CuponDTO cuponDTO) {

    }

    @Override
    public List<CuponDTO> listarCupones() {
        List<Cupon> cupones = cuponRepo.cupones();
        List<CuponDTO> cuponDTOs = new ArrayList<>();
        for (Cupon cupon : cupones) {
            cuponDTOs.add(new CuponDTO(cupon.getId(), cupon.getCodigo(), cupon.getValor(), cupon.getEstadoCupon()));
        }
        return cuponDTOs;
    }

    @Override
    public CuponDTO obtenerCupon(String id) throws Exception {
        Optional<Cupon> cuponOptional = cuponRepo.buscarCuponPorId(id);
        if ( cuponOptional.isPresent() ) {
            Cupon cupon = cuponOptional.get();
            CuponDTO cuponDTO = new CuponDTO(cupon.getId(), cupon.getCodigo(), cupon.getValor(), cupon.getEstadoCupon());
            return  cuponDTO;
        }else{
            throw new Exception("No existe el codigo con ese id");
        }
    }

    @Override
    public void crearCupon(CrearCuponDTO cupon) throws Exception {
        if (cupon.codigo().isEmpty()) {
            throw new Exception("Es obligatorio el codigo");
        }
        if (cuponRepo.buscarPorCodigo(cupon.codigo()).isPresent()) {
            throw new Exception("Ya existe un cupon con ese codigo");
        }
        Cupon cuponNuevo = new Cupon(cupon.codigo(), EstadoCupon.DISPONIBLE, cupon.descuento());
        cuponRepo.save(cuponNuevo);
    }

    @Override
    public void eliminarCupon(String id) throws  Exception{
        Optional<Cupon> cuponOptional = cuponRepo.buscarCuponPorId(id);
        if ( cuponOptional.isPresent() ) {
            cuponRepo.delete(cuponOptional.get());
        }else{
            throw new Exception("No existe el codigo con ese id");
        }
    }
}
