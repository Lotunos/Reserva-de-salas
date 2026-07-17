package resersa.salas.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import resersa.salas.Model.ReservaModel;

@Repository
public interface ReservaDAO extends JpaRepository<ReservaModel, Integer> {
}
