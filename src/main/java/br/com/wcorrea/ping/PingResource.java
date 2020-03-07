package br.com.wcorrea.ping;

import br.com.wcorrea.ping.modelo.Notificacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/ping")
public class PingResource {

    @PostMapping
    public ResponseEntity<String> pingar(@Valid @RequestBody Ping ping, HttpServletResponse response) throws Exception {

        log.info("Iniciando manipulacao dos enderecos: {}", ping);
        for (Ping.Sites s : ping.getSites()) {
            new PingService(s.getEndereco(), s.getPorta(), s.isAcessivel(), ping.getEndpoint()).start();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("/notificacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void notificacao(@RequestBody Notificacao notificacao, boolean acessivel) {
        System.out.println("Endereco: " + notificacao.getEndereco() + "   Porta: " + notificacao.getPorta() + "   Acessível: " + notificacao.isAcessivel());
    }
}
