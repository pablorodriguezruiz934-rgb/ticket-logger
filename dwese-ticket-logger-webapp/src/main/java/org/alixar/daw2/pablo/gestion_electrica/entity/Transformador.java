package org.alixar.daw2.pablo.gestion_electrica.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
/**
 * Representa un transformador eléctrico asociado a una subestación.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transformador {
    /**
     * Identificador único del transformador.
     * La base de datos lo genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre del transformador.
     * Este campo es obligatorio.
     */
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;
    /**
     * Código identificativo del transformador.
     * Este campo es obligatorio.
     */
    @NotEmpty(message = "El código no puede estar vacío")
    private String codigo;
    /**
     * Fecha en que se fabricó el transformador.
     * Uso mejor el local date porque asi es mas secillo poner una fecha
     * A lo mejor lo cambio mas adelante
     */
    @NotNull(message = "La fecha de fabricación es obligatoria")
    private LocalDate fechaFabricacion;
    /**
     * Indica si el transformador está operativo.
     */
    @NotNull(message = "Debe indicar si está operativo")
    private Boolean operativo;
    /**
     * Capacidad del transformador expresada en kVA.
     * Debe ser un valor positivo.
     */
    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser positiva")
    private Double capacidadKva;
    /**
     * Subestación a la que pertenece el transformador.
     * La columna subestacion_id almacena la clave foránea.
     */
    @NotNull(message = "Debe seleccionar una subestación")
    @ManyToOne
    @JoinColumn(name = "subestacion_id")
    private Subestacion subestacion;
    /**
     * Crea un transformador sin especificar su identificador.
     *
     * @param nombre nombre del transformador
     * @param codigo código identificativo
     * @param fechaFabricacion fecha de fabricación
     * @param operativo indica si está operativo
     * @param capacidadKva capacidad expresada en kVA
     * @param subestacion subestación a la que pertenece
     */
    public Transformador(String nombre, String codigo, LocalDate fechaFabricacion, Boolean operativo, Double capacidadKva, Subestacion subestacion) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.fechaFabricacion = fechaFabricacion;
        this.operativo = operativo;
        this.capacidadKva = capacidadKva;
        this.subestacion = subestacion;
    }
}