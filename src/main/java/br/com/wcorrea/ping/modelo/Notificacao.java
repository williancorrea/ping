package br.com.wcorrea.ping.modelo;

import lombok.Data;

@Data
public class Notificacao {
    private String endereco;
    private int porta;
    private boolean acessivel;
}
