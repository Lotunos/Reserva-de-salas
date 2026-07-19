package resersa.salas.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sala")
public class SalaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSala;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 100)
    private Integer capacidade;

    @OneToMany(mappedBy = "sala")
    @JsonIgnoreProperties("sala")
    private List<ReservaModel> reservas;

}