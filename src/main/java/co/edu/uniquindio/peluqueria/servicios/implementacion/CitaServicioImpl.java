package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.*;
import co.edu.uniquindio.peluqueria.dto.EmailDTO;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Cliente;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import co.edu.uniquindio.peluqueria.model.documentos.Servicio;
import co.edu.uniquindio.peluqueria.model.enums.EstadoCita;
import co.edu.uniquindio.peluqueria.repositorios.CitaRepo;
import co.edu.uniquindio.peluqueria.repositorios.ClienteRepo;
import co.edu.uniquindio.peluqueria.repositorios.EstilistaRepo;
import co.edu.uniquindio.peluqueria.repositorios.ServicioRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CitaServicio;
import co.edu.uniquindio.peluqueria.servicios.interfaces.EstilistaServicio;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CitaServicioImpl implements CitaServicio {

    private final CitaRepo citaRepo;
    private final ServicioRepo servicioRepo;
    private final EstilistaServicio estilistaServicio;
    private final EstilistaRepo estilistaRepo;
    private final ClienteRepo clienteRepo;
    private final EmailServicioImpl emailServicio;

    public CitaServicioImpl(CitaRepo citaRepo, ServicioRepo servicioRepo, @Lazy EstilistaServicio estilistaServicio, EstilistaRepo estilistaRepo, ClienteRepo clienteRepo, EmailServicioImpl emailServicio) {
        this.citaRepo = citaRepo;
        this.servicioRepo = servicioRepo;
        this.estilistaServicio = estilistaServicio;
        this.estilistaRepo = estilistaRepo;
        this.clienteRepo = clienteRepo;
        this.emailServicio = emailServicio;
    }

    @Override
    public void asignarEstilista(AsignarEstilistaDTO asignarEstilistaDTO) throws Exception {
        Cita cita = citaRepo.findById(asignarEstilistaDTO.idCita())
                .orElseThrow(() -> new Exception("Cita no encontrada"));

        // Lógica para asignar el estilista
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
            throw new Exception("La selección del servicio es obligatoria");
        }

        if (cita.idEstilista() == null) {
            throw new Exception("La selección del estilista es obligatoria");
        }

        if (cita.fechaInicioCita() == null) {
            throw new Exception("La fecha y hora de la cita son obligatorias");
        }

        if (verificarDisponibilidad(cita.fechaInicioCita(), cita.idServicio())) {
            throw new Exception("La fecha y hora seleccionadas no están disponibles");
        }

        Optional<Servicio> servicioCita = servicioRepo.buscarServicioPorId(cita.idServicio());
        int duracionServicio = servicioCita.get().getDuracionMinutos();

        // Aquí ya no se formatea la fecha
        LocalDateTime fechaInicioCita = cita.fechaInicioCita();
        LocalDateTime fechaFinCita = fechaInicioCita.plusMinutes(duracionServicio);

        Cita nuevaCita = Cita.builder()
                .idCliente(cita.idCliente())
                .idServicio(cita.idServicio())
                .idEstilista(cita.idEstilista())
                .fecha(fechaInicioCita) // Guardamos la fecha de la cita tal como es
                .fechaInicioCita(fechaInicioCita)
                .fechaFinCita(fechaFinCita)
                .estado(EstadoCita.PENDIENTE)
                .build();
        Optional<Cliente> clienteOptional = clienteRepo.findById(cita.idCliente());
        Cliente cliente =  clienteOptional.get();
        citaRepo.save(nuevaCita);

        EmailDTO emailDTO = new EmailDTO("RESERVA EN BARBER-SHOP", "Hola, " +cliente.getNombre()
                + " este es el correo de confirmacion de la cita que reservaste en Barber-Shop", cliente.getEmail());

        emailServicio.enviarCorreo(emailDTO);

        return new VistaCreacionCitaDTO(
                nuevaCita.getIdCliente(),
                nuevaCita.getIdServicio(),
                nuevaCita.getIdEstilista(),
                nuevaCita.getFechaInicioCita(),
                nuevaCita.getFechaFinCita());
    }

    @Override
    public VistaEdicionCitaDTO editarCita(EditarCitaDTO cita) throws Exception {

        if (cita.idCita() == null) {
            throw new Exception("El id de la cita es obligatorio");
        }

        if (cita.idServicio() == null) {
            throw new Exception("La selección del servicio es obligatoria");
        }

        if (cita.idEstilista() == null) {
            throw new Exception("La selección del estilista es obligatoria");
        }

        if (cita.fechaInicioCita() == null) {
            throw new Exception("La fecha y hora de la cita son obligatorias");
        }

        if (verificarDisponibilidad(cita.fechaInicioCita(), cita.idServicio())) {
            throw new Exception("La fecha y hora seleccionadas no están disponibles");
        }

        Optional<Servicio> servicioCita = servicioRepo.buscarServicioPorId(cita.idServicio());

        int duracionServicio = servicioCita.get().getDuracionMinutos();

        Optional<Cita> citaRep = citaRepo.findById(cita.idCita());

        Cita citaEdicion = citaRep.get();

        citaEdicion.setIdServicio(cita.idServicio());
        citaEdicion.setIdEstilista(cita.idEstilista());
        citaEdicion.setFecha(cita.fechaInicioCita());
        citaEdicion.setFechaInicioCita(cita.fechaInicioCita());
        citaEdicion.setFechaFinCita(cita.fechaInicioCita    ().plusMinutes(duracionServicio));
        citaEdicion.setEstado(EstadoCita.PENDIENTE);

        citaRepo.save(citaEdicion);

        Optional<Cliente> clienteOptional = clienteRepo.findById(citaEdicion.getIdCliente());
        Cliente cliente =  clienteOptional.get();

        EmailDTO emailDTO = new EmailDTO("EDICION DE RESERVA EN BARBER-SHOP", "Hola, " +cliente.getNombre()
                + " este es el correo de confirmacion de la edicion que hiciste a la cita " + citaEdicion.getId() + " que editaste en Barber-Shop" , cliente.getEmail());

        emailServicio.enviarCorreo(emailDTO);
        return new VistaEdicionCitaDTO(
                citaEdicion.getId(),
                citaEdicion.getIdCliente(),
                citaEdicion.getIdServicio(),
                citaEdicion.getIdEstilista(),
                citaEdicion.getFechaInicioCita());
    }

    private boolean verificarDisponibilidad(LocalDateTime fecha, String servicio) {
        Optional<Servicio> servicioOptional = servicioRepo.buscarServicioPorId(servicio);

        LocalDateTime nuevaFechaFin = fecha.plusMinutes(servicioOptional.get().getDuracionMinutos());

        List<Cita> cita = citaRepo.buscarCitasEnRango(fecha, nuevaFechaFin);

        return !cita.isEmpty();
    }

    @Override
    public void cancelarCita(String citaEliminar) throws Exception {
        if (citaEliminar == null) {
            throw new Exception("El id de la cita es obligatorio para eliminarla");
        }

        Optional<Cita> cita = citaRepo.findById(citaEliminar);
        if (cita.isPresent()) {
            Cita citaEliminada = cita.get();
            citaEliminada.setEstado(EstadoCita.CANCELADA);
            citaRepo.save(citaEliminada); // Guardar el estado actualizado
        } else {
            throw new Exception("Cita no encontrada");
        }
    }

    @Override
    public List<CitasDTO> listarCitas() {
        List<Cita> citas = citaRepo.listarCitas("PENDIENTE");

        List<CitasDTO> citasDTO = new ArrayList<>();

        for(Cita citasSistema : citas){

            Optional<Servicio> servicioOptional = servicioRepo.buscarServicioPorId(citasSistema.getIdServicio());
            Servicio servicio = servicioOptional.get();

            Optional<Cliente> clienteOptional = clienteRepo.findById(citasSistema.getIdCliente());
            Cliente cliente = clienteOptional.get();

            Optional<Estilista> estilistaOptional = estilistaRepo.findById(citasSistema.getIdEstilista());
            Estilista estilista = estilistaOptional.get();


            citasDTO.add(new CitasDTO(citasSistema.getId(), servicio.getNombreServicio(), estilista.getNombreEstilista(), cliente.getNombre(), citasSistema.getFecha(), citasSistema.getFecha()));
        }
        return citasDTO;
    }

    @Override
    public CitasDTO buscarCita(String id) throws Exception {
        Optional<Cita> citaRepos = citaRepo.findById(id);
        Cita cita = citaRepos.get();

        Optional<Servicio> servicioOptional = servicioRepo.buscarServicioPorId(cita.getIdServicio());
        Servicio servicio = servicioOptional.get();

        Optional<Cliente> clienteOptional = clienteRepo.findById(cita.getIdCliente());
        Cliente cliente = clienteOptional.get();

        Optional<Estilista> estilistaOptional = estilistaRepo.findById(cita.getIdEstilista());
        Estilista estilista = estilistaOptional.get();

        return new CitasDTO(cita.getId(), servicio.getNombreServicio(), estilista.getNombreEstilista(), cliente.getNombre(), cita.getFecha(), cita.getFecha());
    }
}
