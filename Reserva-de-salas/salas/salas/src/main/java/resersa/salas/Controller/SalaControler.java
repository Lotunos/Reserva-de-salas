package resersa.salas.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import resersa.salas.Model.ReservaModel;
import resersa.salas.Model.SalaModel;
import resersa.salas.Service.SalaService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sala")
public class SalaControler {
    private final SalaService service;
    public SalaControler(SalaService service){
        this.service = service;
    }
    @PostMapping("/alimentar")
    public ResponseEntity<String> alimentartable(){
        String inserir = service.AlimentarTabelaSala();
        return new ResponseEntity<>(inserir, HttpStatus.CREATED);
    }
    @GetMapping("/getallsala")
    public ResponseEntity<List<SalaModel>> getAllSala(){
        List<SalaModel> model = service.getAllSala();
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @GetMapping("/getsala/{id}")
    public  ResponseEntity<SalaModel> getSala(@PathVariable int id){
        SalaModel model = service.getSala(id);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
}
