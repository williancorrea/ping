package br.com.wcorrea.ping;

import lombok.Data;

import java.util.List;

@Data
public class Ping {
    private String endpoint;
    private List<Sites> sites;
    private boolean retornoIndividual;

    @Data
    static class Sites {
        private String host;
        private int porta;
        private boolean acessivel;
    }
}


