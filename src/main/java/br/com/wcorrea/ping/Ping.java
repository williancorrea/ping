package br.com.wcorrea.ping;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Ping {

    @NotNull
    private String endpoint;
    @NotNull
    private List<Sites> sites;
    private boolean retornoIndividual;

    @Data
    static class Sites {
        @NotNull
        @Size(min = 7)
        private String endereco;
        private int porta;
        private boolean acessivel;
    }
}


