package resersa.salas.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="reserva")
public class ReservaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idreserva;

    @Column(nullable = false, length = 100)
    private String responsavel;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate data;

    @Column(nullable=false)
    private LocalTime inicio;

    @Column(nullable=false)
    private LocalTime fim;

    @Column(nullable=false)
    private String observacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sala", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "reservas"})
    private SalaModel sala;
}