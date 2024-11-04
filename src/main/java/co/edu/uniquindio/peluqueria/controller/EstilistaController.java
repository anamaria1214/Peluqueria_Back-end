package co.edu.uniquindio.peluqueria.controller;

import co.edu.uniquindio.peluqueria.dto.EstilistasDTO;
import co.edu.uniquindio.peluqueria.dto.MensajeDTO;
import co.edu.uniquindio.peluqueria.dto.ServicioDTOs.ItemServicioDTO;
import co.edu.uniquindio.peluqueria.model.documentos.Estilista;
import co.edu.uniquindio.peluqueria.servicios.interfaces.EstilistaServicio;
import co.edu.uniquindio.peluqueria.servicios.interfaces.ServicioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/estilista")
public class EstilistaController {

    private final EstilistaServicio estilistaServicio;

    @GetMapping("/obtener")
    public ResponseEntity<MensajeDTO<List<EstilistasDTO>>> listarServicios() throws Exception {
        List<Estilista> lista = estilistaServicio.obtenerEstilistas();
        List<EstilistasDTO> estilistasDTOS = new ArrayList<>();
        for (Estilista est : lista) {
            estilistasDTOS.add(new EstilistasDTO(est.getId(), est.getNombreEstilista(), est.getEspecialidad()));
        }
        return ResponseEntity.ok(new MensajeDTO<>(false, estilistasDTOS));
    }
}
