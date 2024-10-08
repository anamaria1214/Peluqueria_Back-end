package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import co.edu.uniquindio.peluqueria.repositorios.EstilistaRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CitaServicio;
import co.edu.uniquindio.peluqueria.servicios.interfaces.EstilistaServicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EstilistaServicioImpl implements EstilistaServicio {

    private final EstilistaRepo estilistaRepo;
    private final CitaServicio citaServicio;

    public EstilistaServicioImpl(EstilistaRepo estilistaRepo, CitaServicio citaServicio) {
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
    public Estilista encontrarEstilista(String idEstilista) throws Exception {
        try{
            return estilistaRepo.findById(idEstilista).get();
        }catch (Exception e){
            throw new Exception("Estilista no encontrado");
        }
    }
}
