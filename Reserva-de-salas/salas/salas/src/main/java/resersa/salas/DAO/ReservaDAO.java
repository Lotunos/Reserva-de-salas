package resersa.salas.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import resersa.salas.DTO.ReservaInputDTO;
import resersa.salas.Model.ReservaModel;
import resersa.salas.Model.SalaModel;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaDAO extends JpaRepository<ReservaModel, Integer> {
    List<ReservaModel> findBySalaIdSalaAndData(Integer idSala,LocalDate data);
}
