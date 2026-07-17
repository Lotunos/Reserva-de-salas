package resersa.salas.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import resersa.salas.Model.SalaModel;

@Repository
public interface SalaDAO extends JpaRepository<SalaModel,Integer> {
}
