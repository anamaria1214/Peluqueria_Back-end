package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.CitaDTO.VistaEdicionCitaDTO;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.dto.EstilistasDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import co.edu.uniquindio.peluqueria.model.documentos.Servicio;
import co.edu.uniquindio.peluqueria.model.enums.EstadoCita;
import co.edu.uniquindio.peluqueria.repositorios.EstilistaRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CitaServicio;
import co.edu.uniquindio.peluqueria.servicios.interfaces.EstilistaServicio;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstilistaServicioImpl implements EstilistaServicio {

    private final EstilistaRepo estilistaRepo;
    private final CitaServicio citaServicio;

    public EstilistaServicioImpl(EstilistaRepo estilistaRepo,@Lazy CitaServicio citaServicio) {
        this.estilistaRepo = estilistaRepo;
        this.citaServicio = citaServicio;
    }

    @Override
    public boolean verificarDisponibilidadEstilista(EstilistaDisponiblesDTO estilistaDisponiblesDTO) throws Exception {
        Estilista estilista = estilistaRepo.findById(estilistaDisponiblesDTO.idEstilista()).get();
        if (estilista == null) {
            throw new Exception("Estilista no encontrado.");
        }
        Cita cita = citaServicio.encontrarCita(estilistaDisponiblesDTO);
        return cita==null;

    }

    @Override
    public List<Estilista> obtenerEstilistasDisponibles(LocalDateTime fechaHora) throws Exception {
        List<Estilista> estilistasDisponibles = new ArrayList<>();
        List<Estilista> estilistas = estilistaRepo.findAll();
        for (Estilista estilista : estilistas) {
            if (verificarDisponibilidadEstilista(new EstilistaDisponiblesDTO(estilista.getId(), fechaHora))) {
                estilistasDisponibles.add(estilista);
            }
        }
        return estilistasDisponibles;
    }

    @Override
    public List<Estilista> obtenerEstilistas() throws Exception {
        List<Estilista> estilistasSistema = estilistaRepo.findAll();
        return estilistasSistema;
    }

    @Override
    public Estilista encontrarEstilista(String idEstilista) throws Exception {
        return estilistaRepo.findById(idEstilista)
                .orElseThrow(() -> new Exception("Estilista no encontrado"));
    }

    @Override
    public Estilista guardarEstilista(Estilista estilista) throws Exception {
        return estilistaRepo.save(estilista);
    }

    @Override
    public void eliminarEstilista(String id) throws Exception {
        if (id == null) {
            throw new Exception("El id de la cita es obligatorio para eliminarla");
        }

        Optional<Estilista> estilistaOptional = estilistaRepo.findById(id);
        if (estilistaOptional.isPresent()) {
            Estilista estilista = estilistaOptional.get();
            estilistaRepo.delete(estilista); // Guardar el estado actualizado
        } else {
            throw new Exception("Estilista no encontrado");
        }

    }

    @Override
    public EstilistasDTO obtenerEstilista(String id) throws Exception {
        Optional<Estilista> estilista = estilistaRepo.findById(id);
        if (estilista.isPresent()) {
            Estilista estilista1= estilista.get();
            return new EstilistasDTO(estilista1.getId(), estilista1.getNombreEstilista(), estilista1.getEspecialidad());
        }else{
            throw new Exception("Estilista no encontrado");
        }
    }

    @Override
    public EstilistasDTO editarEstilista(EstilistasDTO estilistasDTO) throws Exception {

        if (estilistasDTO.id() == null) {
            throw new Exception("El id del estilista es obligatorio");
        }

        if (estilistasDTO.nombreEstilista() == null) {
            throw new Exception("El nombre del estilista es obligatoria");
        }

        if (estilistasDTO.especialidad() == null) {
            throw new Exception("La especialidad del estilista es obligatoria");
        }

        Optional<Estilista> estilistaRep =  estilistaRepo.findById(estilistasDTO.id());

        Estilista estilista = estilistaRep.get();

        estilista.setNombreEstilista(estilistasDTO.nombreEstilista());
        estilista.setEspecialidad(estilistasDTO.especialidad());

        estilistaRepo.save(estilista);

        return new EstilistasDTO(
                estilista.getId(),
                estilista.getNombreEstilista(),
                estilista.getEspecialidad());
    }

}
