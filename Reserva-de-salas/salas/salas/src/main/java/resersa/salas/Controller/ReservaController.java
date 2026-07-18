package resersa.salas.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resersa.salas.DTO.ReservaDTO;
import resersa.salas.Model.ReservaModel;
import resersa.salas.Service.ReservaService;

@RestController
@RequestMapping("/Reserva")
public class ReservaController {
    private ReservaService service;
    public ReservaController(ReservaService service){
        this.service = service;
    }
    @PostMapping("/adicionarReserva")
    public ResponseEntity<ReservaModel> criarReserva(@RequestBody ReservaDTO dto){
        ReservaModel model = service.criarReserva(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

}
