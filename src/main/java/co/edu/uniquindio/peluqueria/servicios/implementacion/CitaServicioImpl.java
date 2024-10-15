package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.*;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import co.edu.uniquindio.peluqueria.model.documentos.Servicio;
import co.edu.uniquindio.peluqueria.model.enums.EstadoCita;
import co.edu.uniquindio.peluqueria.repositorios.CitaRepo;
import co.edu.uniquindio.peluqueria.repositorios.ServicioRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CitaServicio;
import co.edu.uniquindio.peluqueria.servicios.interfaces.EstilistaServicio;
import jakarta.validation.constraints.NotBlank;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServicioImpl implements CitaServicio {

    // Formato para fecha: dd/MM/yy
    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yy");

    // Formato para hora: HH:mm:ss
    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final CitaRepo citaRepo;
    private final ServicioRepo servicioRepo;
    private final EstilistaServicio estilistaServicio;

    public CitaServicioImpl(CitaRepo citaRepo, ServicioRepo servicioRepo,@Lazy EstilistaServicio estilistaServicio) {
        this.citaRepo = citaRepo;
        this.servicioRepo = servicioRepo;
        this.estilistaServicio = estilistaServicio;
    }

    @Override
    public void asignarEstilista(AsignarEstilistaDTO asignarEstilistaDTO) throws Exception {
        Cita cita = citaRepo.findById(asignarEstilistaDTO.idCita())
                .orElseThrow(() -> new Exception("Cita no encontrada"));

        Estilista estilista = estilistaServicio.encontrarEstilista(asignarEstilistaDTO.idEstilista());
        cita.setIdEstilista(asignarEstilistaDTO.idEstilista());
        citaRepo.save(cita);
    }

    @Override
    public Cita encontrarCita(EstilistaDisponiblesDTO encontrarCitaDTO) throws Exception {
        return citaRepo.findByEstilistaIdAndFechaHora(encontrarCitaDTO.idEstilista(), encontrarCitaDTO.fechaHora())
                .orElseThrow(() -> new Exception("No se encontró la cita"));
    }


    @Override
    public VistaCreacionCitaDTO crearCita(CrearCitaDTO cita) throws Exception {

        if (cita.idCliente() == null) {
            throw new Exception("El id del cliente es obligatorio");
        }

        if (cita.idServicio() == null) {
            throw new Exception("La seleccion del servicio es obligatoria");
        }

        if (cita.idEstilista() == null) {
            throw new Exception("La seleccion del estilista es obligatoria");
        }

        if (cita.fechaInicioCita() == null) {
            throw new Exception("La seleccion la fecha y hora de la cita es obligatoria");
        }

        Optional<Servicio> servicioCita = servicioRepo.buscarServicioPorId(cita.idServicio());
        int duracionServicio = servicioCita.get().getDuracionMinutos();

        Cita nuevaCita = Cita.builder()
                .idCliente(cita.idCliente())
                .idServicio(cita.idServicio())
                .idEstilista(cita.idEstilista())
                .fecha(LocalDateTime.parse(cita.fechaInicioCita().format(formatoFecha)))
                .fechaInicioCita(LocalDateTime.parse(cita.fechaInicioCita().format(formatoHora)))
                .fechaFinCita(LocalDateTime.parse(cita.fechaInicioCita().plusMinutes(duracionServicio).format(formatoHora)))
                .estado(EstadoCita.PENDIENTE).build();

        citaRepo.save(nuevaCita);

        return new VistaCreacionCitaDTO(nuevaCita.getIdCliente(),
                nuevaCita.getIdServicio(),
                nuevaCita.getIdEstilista(),
                nuevaCita.getFechaInicioCita(),
                nuevaCita.getFechaFinCita());
    }

    @Override
    public VistaEdicionCitaDTO editarCita(EditarCitaDTO cita) throws Exception{

        if (cita.idCita() == null) {
            throw new Exception("El id de la cita es obligatorio");
        }

        if (cita.idServicio() == null) {
            throw new Exception("La seleccion del servicio es obligatoria");
        }

        if (cita.idEstilista() == null) {
            throw new Exception("La seleccion del estilista es obligatoria");
        }

        if (cita.fechanicioCita() == null) {
            throw new Exception("La seleccion la fecha y hora de la cita es obligatoria");
        }

        if (verificarDisponibilidad(cita.fechanicioCita(), cita.idServicio())) {
            throw new Exception("La fecha y hora seleccionada no estan disponibles");
        }

        Optional<Servicio> servicioCita = servicioRepo.buscarServicioPorId(cita.idServicio());

        int duracionServicio = servicioCita.get().getDuracionMinutos();

        Optional<Cita> citaRep = citaRepo.findById(cita.idCita());

        Cita citaEdicion = citaRep.get();

        citaEdicion.setIdServicio(cita.idServicio());
        citaEdicion.setIdEstilista(cita.idEstilista());
        citaEdicion.setFecha(LocalDateTime.parse(cita.fechanicioCita().format(formatoFecha)));
        citaEdicion.setFechaInicioCita(LocalDateTime.parse(cita.fechanicioCita().format(formatoHora)));
        citaEdicion.setFechaFinCita(LocalDateTime.parse(cita.fechanicioCita().plusMinutes(duracionServicio).format(formatoHora)));
        citaEdicion.setEstado(EstadoCita.PENDIENTE);

        citaRepo.save(citaEdicion);

        return new VistaEdicionCitaDTO(
                citaEdicion.getId(),
                citaEdicion.getIdCliente(),
                citaEdicion.getIdServicio(),
                citaEdicion.getIdEstilista(),
                citaEdicion.getFechaInicioCita(),
                citaEdicion.getFechaFinCita());
    }

    private boolean verificarDisponibilidad(@NotBlank(message = "La fecha de la cita es obligatoria") LocalDateTime fecha, @NotBlank(message = "El id del Servicio es obligatorio") String servicio) {

        Optional<Servicio> servicioOptional = servicioRepo.buscarServicioPorId(servicio);

        LocalDateTime nuevaFechaFin = fecha.plusMinutes(servicioOptional.get().getDuracionMinutos());

        List<Cita> cita = citaRepo.buscarCitasEnRango(fecha, nuevaFechaFin);

        if (cita.isEmpty()) {
            return false;
        }

        return true;

    }

    @Override
    public void cancelarCita(EliminarCitaDTO citaEliminar) throws Exception{

        if (citaEliminar.idCita() == null) {
            throw new Exception("La id de la cita es obligatoria para elmininarla");
        }

        Optional<Cita> cita = citaRepo.findById(citaEliminar.idCita());
        Cita citaEliminado = cita.get();
        citaEliminado.setEstado(EstadoCita.CANCELADA);
    }

}
