package resersa.salas.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import resersa.salas.Model.SalaModel;
import resersa.salas.Service.SalaService;

@Controller
@RestController("/sala")
public class SalaControler {
    private SalaService service;
    public SalaControler(SalaService service){
        this.service = service;
    }
    @PostMapping("/alimentar")
    public ResponseEntity<String> alimentartable(){
        String inserir = service.AlimentarTabelaSala();
        return new ResponseEntity<>(inserir, HttpStatus.CREATED);
    }
}
