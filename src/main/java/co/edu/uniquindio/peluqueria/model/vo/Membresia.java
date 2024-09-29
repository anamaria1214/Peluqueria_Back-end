package co.edu.uniquindio.peluqueria.model.vo;

import co.edu.uniquindio.peluqueria.model.enums.NivelMembresia;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Membresia {
    private NivelMembresia nivel;
    private int descuento;
}

