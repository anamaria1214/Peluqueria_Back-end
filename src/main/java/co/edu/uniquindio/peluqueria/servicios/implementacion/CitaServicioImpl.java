package co.edu.uniquindio.peluqueria.servicios.implementacion;

import co.edu.uniquindio.peluqueria.dto.AsignarEstilistaDTO;
import co.edu.uniquindio.peluqueria.dto.EstilistaDisponiblesDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Cita;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import co.edu.uniquindio.peluqueria.repositorios.CitaRepo;
import co.edu.uniquindio.peluqueria.servicios.interfaces.CitaServicio;
import co.edu.uniquindio.peluqueria.servicios.interfaces.EstilistaServicio;

import java.util.List;

public class CitaServicioImpl implements CitaServicio {

    private final CitaRepo citaRepo;
    private final EstilistaServicio estilistaServicio;

    public CitaServicioImpl(CitaRepo citaRepo, EstilistaServicio estilistaServicio) {
        this.citaRepo = citaRepo;
        this.estilistaServicio = estilistaServicio;
    }

    @Override
    public void asignarEstilista(AsignarEstilistaDTO asignarEstilistaDTO) throws Exception {
        Cita cita = citaRepo.findById(asignarEstilistaDTO.idCita()).get();
        if (cita == null) {
            throw new Exception("Cita no encontrada");
        }
        Estilista estilista = estilistaServicio.encontrarEstilista(asignarEstilistaDTO.idEstilista());
        if (estilista == null) {
            throw new Exception("Estilista no encontrado");
        }
        cita.setIdEstilista(asignarEstilistaDTO.idEstilista());
        citaRepo.save(cita);
    }

    @Override
    public Cita encontrarCita(EstilistaDisponiblesDTO encontrarCitaDTO) throws Exception {
        try{
            return citaRepo.findByEstilistaIdAndFechaHora(encontrarCitaDTO.idEstilista(), encontrarCitaDTO.fechaHora());
        }catch (Exception e){
            throw new Exception("No se encontró la cita");
        }
    }

}
