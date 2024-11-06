package co.edu.uniquindio.peluqueria.controller;

import co.edu.uniquindio.peluqueria.dto.CitaDTO.EditarCitaDTO;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.VistaCreacionCitaDTO;
import co.edu.uniquindio.peluqueria.dto.CitaDTO.VistaEdicionCitaDTO;
import co.edu.uniquindio.peluqueria.dto.CreacionEmpleadoDTO;
import co.edu.uniquindio.peluqueria.dto.EstilistasDTO;
import co.edu.uniquindio.peluqueria.dto.MensajeDTO;
import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.CrearServicioDTO;
import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.ItemServicioDTO;
import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.VistaCreacionServicioDTO;
import co.edu.uniquindio.peluqueria.dto.VistaCreacionEstilistaDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import co.edu.uniquindio.peluqueria.servicios.interfaces.EstilistaServicio;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ServicioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estilista")
public class EstilistaController {

    private final EstilistaServicio estilistaServicio;

    @PostMapping("/crear-estilista")
    public ResponseEntity<MensajeDTO<VistaCreacionEstilistaDTO>> crearEstilista(@Valid @RequestBody CreacionEmpleadoDTO empleado) throws Exception{
        VistaCreacionEstilistaDTO estilista = estilistaServicio.crearEstilista(empleado);
        return ResponseEntity.ok(new MensajeDTO<>(false, estilista));
    }

    @GetMapping("/obtener")
    public ResponseEntity<MensajeDTO<List<EstilistasDTO>>> listarServicios() throws Exception {
        List<Estilista> lista = estilistaServicio.obtenerEstilistas();
        List<EstilistasDTO> estilistasDTOS = new ArrayList<>();
        for (Estilista est : lista) {
            estilistasDTOS.add(new EstilistasDTO(est.getId(), est.getNombreEstilista(), est.getEspecialidad()));
        }
        return ResponseEntity.ok(new MensajeDTO<>(false, estilistasDTOS));
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<MensajeDTO<EstilistasDTO>> obtenerEstilista(@PathVariable String id) throws Exception {
        EstilistasDTO estilista = estilistaServicio.obtenerEstilista(id);
        EstilistasDTO estilistasDTO = new EstilistasDTO(estilista.id(), estilista.nombreEstilista(), estilista.especialidad());
        return ResponseEntity.ok(new MensajeDTO<>(false, estilistasDTO));
    }

    @PutMapping("/editar-estilista")
    public ResponseEntity<MensajeDTO<EstilistasDTO>> editarEstilista(@Valid @RequestBody EstilistasDTO estilistasDTO) throws Exception {
        EstilistasDTO estilistaEditado = estilistaServicio.editarEstilista(estilistasDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, estilistaEditado));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarEstilista(@PathVariable String id) throws Exception {
        estilistaServicio.eliminarEstilista(id);
        return ResponseEntity.ok(new MensajeDTO<>(false,"Estilista eliminado correctamente"));
    }
}
