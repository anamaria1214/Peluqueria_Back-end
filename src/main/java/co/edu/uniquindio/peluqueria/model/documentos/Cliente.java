package co.edu.uniquindio.peluqueria.model.documentos;

import co.edu.uniquindio.peluqueria.model.vo.HistorialServicio;
import co.edu.uniquindio.peluqueria.model.vo.Membresia;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("clientes")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {
    @Id
    private String id;
    private String nombre;
    private String telefono;
    private String correo;
    private List<HistorialServicio> historialServicios;
    private Membresia membresia;
    private String password;
}
