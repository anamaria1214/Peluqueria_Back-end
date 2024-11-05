package co.edu.uniquindio.peluqueria.model.documentos;

import co.edu.uniquindio.peluqueria.model.enums.EstadoCuenta;
import co.edu.uniquindio.peluqueria.model.enums.EstadoCupon;
import co.edu.uniquindio.peluqueria.model.enums.Rol;
import co.edu.uniquindio.peluqueria.model.vo.HistorialServicio;
import co.edu.uniquindio.peluqueria.model.vo.Membresia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("cupones")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cupon {

    @Id
    private String id;
    private String codigo;
    private float valor;
    private EstadoCupon estadoCupon;

    public Cupon(@Length(max = 10, message = "El código del cupón no debe exceder los 10 caracteres") String codigo,
                 @NotNull(message = "El estado del cupón es obligatorio") EstadoCupon estado,
                 @Positive(message = "El descuento debe ser un número positivo") float descuento) {
    }

    public Cupon(@NotBlank(message = "El código del cupón es obligatorio") String id, @NotNull(message = "El estado del cupón es obligatorio") EstadoCupon estado, @Positive(message = "El descuento debe ser un número positivo") float descuento, @NotNull(message = "El estado del cupón es obligatorio") EstadoCupon estado1, @Length(max = 10, message = "El código del cupón no debe exceder los 10 caracteres") String codigo) {
    }
}
