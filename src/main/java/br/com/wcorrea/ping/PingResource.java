package br.com.wcorrea.ping;

import br.com.wcorrea.ping.modelo.Notificacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/ping")
public class PingResource {

    @PostMapping
    public ResponseEntity<String> pingar(@Valid @RequestBody Ping ping, HttpServletResponse response) throws Exception {

        for (Ping.Sites s : ping.getSites()) {
            new PingService(s.getHost(), s.isAcessivel(), ping.getEndpoint()).start();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("/notificacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void notificacao(@RequestBody Notificacao Notificacao, boolean acessivel) {
        System.out.println("URL: " + Notificacao.getUrl() + "   -   Acessível: " + Notificacao.isAcessivel());
    }
}
