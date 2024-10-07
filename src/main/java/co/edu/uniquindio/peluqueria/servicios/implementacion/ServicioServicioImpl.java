package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.model.documentos.Servicio;
import co.edu.uniquindio.peluqueria.repositorios.ServicioRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ServicioServicio;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ServicioServicio;
import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.CrearServicioDTO;
import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.EditarServicioDTO;
import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.ItemServicioDTO;
import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.InformacionServicioDTO;
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

    @Autowired
    private ServicioRepo servicioRepo;

    @Override
    public String crearServicio(CrearServicioDTO crearServicioDTO) throws Exception {
        // Verificar si ya existe un servicio con el mismo nombre
        if (servicioRepo.buscarServicioPorNombre(crearServicioDTO.nombreServicio()).isPresent()) {
            throw new Exception("El nombre del servicio ya está registrado");
        }

        // Crear un nuevo servicio
        Servicio nuevoServicio = new Servicio();
        nuevoServicio.setId(UUID.randomUUID().toString()); // Genera un ID único
        nuevoServicio.setNombreServicio(crearServicioDTO.nombreServicio());
        nuevoServicio.setDescripcion(crearServicioDTO.descripcion());
        nuevoServicio.setPrecio(crearServicioDTO.precio());
        nuevoServicio.setDuracionMinutos(crearServicioDTO.duracionMinutos());

        servicioRepo.save(nuevoServicio); // Guardar en MongoDB
        return nuevoServicio.getId();
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
                        servicio.getPrecio()))
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