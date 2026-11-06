package org.alixar.daw2.pablo.gestion_electrica.entity;
/**
* Uso el persistance ya que se usa aqui en spring para
* relacionar las 2 entidades
 */
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

/**
 * Representa una subestación eléctrica.
 *
 * Contiene sus datos principales y la lista de transformadores asociados.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subestacion {
    /**
     * Identificador único de la subestación.
     * La base de datos lo genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre de la subestación.
     * Este campo es obligatorio.
     */
    @NotEmpty(message = "{msg.subestacion.nombre.notEmpty}")
    private String nombre;
    /**
     * Ubicación de la subestación.
     * Este campo es obligatorio.
     */
    @NotEmpty(message = "{msg.subestacion.ubicacion.notEmpty}")
    private String ubicacion;
    /**
     * Fecha del último mantenimiento.
     * LocalDate almacena una fecha sin hora.
     */
    @NotNull(message = "{msg.subestacion.fecha.notNull}")
    private LocalDate fechaUltimoMantenimiento;
    /**
     * Indica si la subestación está en servicio.
     */
    @NotNull(message = "{msg.subestacion.servicio.notNull}")
    private Boolean enServicio;
    /**
     * Número de transformadores asociados a la subestación.
     */
    @NotNull(message = "{msg.subestacion.numeroTransformadores.notNull}")
    private Integer numeroTransformadores;
    /**
     * Lista de transformadores asociados a esta subestación.
     * La relación se define en el campo subestacion de Transformador.
     */
    @OneToMany(mappedBy = "subestacion")
    private List<Transformador> transformadores;
    /**
     * Crea una subestación sin especificar su identificador.
     *
     * @param nombre nombre de la subestación
     * @param ubicacion ubicación de la subestación
     * @param fechaUltimoMantenimiento fecha del último mantenimiento
     * @param enServicio indica si la subestación está en servicio
     * @param numeroTransformadores número de transformadores asociados
     */
    public Subestacion(String nombre, String ubicacion, LocalDate fechaUltimoMantenimiento, Boolean enServicio, Integer numeroTransformadores) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
        this.enServicio = enServicio;
        this.numeroTransformadores = numeroTransformadores;
    }
}