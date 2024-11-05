package co.edu.uniquindio.peluqueria.model.documentos;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("inventario")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Inventario {
    @Id
    private String id;
    private String producto;
    private int cantidad;
    private double precio;

    public Inventario(String nombre, int cantidad, double precio) {
        this.producto = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }
}
