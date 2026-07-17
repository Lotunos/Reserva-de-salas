package resersa.salas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="Sala")
public class SalaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSala;
    @Column(nullable = false, length = 10)
    private String nome;
    @Column(nullable = false, length = 3)
    private Integer capacidade;
    @OneToMany(mappedBy = "sala")
    private List<ReservaModel> reservas;

}
