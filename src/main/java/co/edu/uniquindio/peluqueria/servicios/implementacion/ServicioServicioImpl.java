package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.*;
import co.edu.uniquindio.peluqueria.model.documentos.Servicio;
import co.edu.uniquindio.peluqueria.repositorios.ServicioRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ServicioServicio;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ServicioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioServicioImpl implements ServicioServicio {

    private final ServicioRepo servicioRepo;

    public ServicioServicioImpl(ServicioRepo servicioRepo) {
        this.servicioRepo = servicioRepo;
    }

    @Override
    public VistaCreacionServicioDTO crearServicio(CrearServicioDTO crearServicioDTO) throws Exception {
        // Verificar si ya existe un servicio con el mismo nombre
        if (crearServicioDTO.nombreServicio().isBlank()) {
            throw new Exception("El nombre es obligatorio");
        }
        if (servicioRepo.buscarServicioPorNombre(crearServicioDTO.nombreServicio()).isPresent()) {
            throw new Exception("El nombre del servicio ya está registrado");
        }
        if (crearServicioDTO.descripcion().isBlank()) {
            throw new Exception("la descripcion es obligatoria");
        }
        if (crearServicioDTO.precio() < 0) {
            throw new Exception("El precio es obligatorio y positivo");
        }

        // Crear un nuevo servicio
        Servicio nuevoServicio = new Servicio();
        nuevoServicio.setNombreServicio(crearServicioDTO.nombreServicio());
        nuevoServicio.setDescripcion(crearServicioDTO.descripcion());
        nuevoServicio.setPrecio(crearServicioDTO.precio());
        nuevoServicio.setDuracionMinutos(crearServicioDTO.duracionMinutos());
        nuevoServicio.setImagen("https://st3.depositphotos.com/1421381/35450/v/450/depositphotos_354506988-stock-illustration-bearded-man-faces-hipster-haircuts.jpg");
        servicioRepo.save(nuevoServicio);

        return new VistaCreacionServicioDTO(nuevoServicio.getNombreServicio(), nuevoServicio.getDescripcion(), nuevoServicio.getPrecio(), nuevoServicio.getDuracionMinutos());
    }

    @Override
    public String editarServicio(EditarServicioDTO editarServicioDTO) throws Exception {
        // Buscar el servicio existente por ID
        Optional<Servicio> servicioExistente = servicioRepo.buscarServicioPorId(editarServicioDTO.id());

        if (servicioExistente.isPresent()) {
            Servicio servicio = servicioExistente.get();

            // Actualizar los campos del servicio
            servicio.setNombreServicio(editarServicioDTO.nombreServicio());
            servicio.setDescripcion(editarServicioDTO.descripcion());
            servicio.setPrecio(editarServicioDTO.precio());
            servicio.setDuracionMinutos(editarServicioDTO.duracionMinutos());

            servicioRepo.save(servicio); // Guardar los cambios en MongoDB
            return "Servicio actualizado correctamente";
        } else {
            throw new Exception("Servicio no encontrado");
        }
    }

    @Override
    public String eliminarServicio(String id) throws Exception {
        // Verificar si el servicio existe antes de eliminarlo
        if (servicioRepo.existsById(id)) {
            servicioRepo.deleteById(id); // Eliminar servicio en MongoDB
            return "Servicio eliminado correctamente";
        } else {
            throw new Exception("Servicio no encontrado");
        }
    }

    @Override
    public List<ItemServicioDTO> obtenerServicios() throws Exception {
        // Obtener todos los servicios desde MongoDB
        List<Servicio> servicios = servicioRepo.obtenerTodosLosServicios();

        // Convertir la lista de Servicios en ItemServicioDTO
        return servicios.stream()
                .map(servicio -> new ItemServicioDTO(
                        servicio.getId(),
                        servicio.getNombreServicio(),
                        servicio.getPrecio(), servicio.getImagen()))
                .collect(Collectors.toList());
    }

    @Override
    public InformacionServicioDTO obtenerInformacionServicio(String id) throws Exception {
        // Buscar el servicio por ID
        Optional<Servicio> servicio = servicioRepo.buscarServicioPorId(id);

        if (servicio.isPresent()) {
            Servicio s = servicio.get();
            return new InformacionServicioDTO(
                    s.getId(),
                    s.getNombreServicio(),
                    s.getDescripcion(),
                    s.getPrecio(),
                    s.getDuracionMinutos());
        } else {
            throw new Exception("Servicio no encontrado");
        }
    }
}