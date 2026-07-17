package resersa.salas.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import resersa.salas.DAO.SalaDAO;
import resersa.salas.DTO.SalaDTO;
import resersa.salas.Model.SalaModel;

@Service
public class SalaService {
    private SalaDAO dao;
    public SalaService(SalaDAO dao){
        this.dao = dao;
    }
    @Transactional
    public String AlimentarTabelaSala(){
        try {
            SalaDTO dto = new SalaDTO("Sala A", 6);
            SalaModel model = new SalaModel();
            model.setNome(dto.getNome());
            model.setCapacidade(dto.getCapacidade());
            dao.save(model);
            dto = new SalaDTO("Sala B", 10);
            SalaModel model1 = new SalaModel();
            model1.setNome(dto.getNome());
            model1.setCapacidade(dto.getCapacidade());
            dao.save(model1);
            dto = new SalaDTO("Sala C", 20);
            SalaModel model2 = new SalaModel();
            model2.setNome(dto.getNome());
            model2.setCapacidade(dto.getCapacidade());
            dao.save(model2);
        } catch(Exception e){
            return "" + e;
        }
        return "Sucesso";
    }
}
