package br.com.wcorrea.ping.modelo;

import lombok.Data;

@Data
public class Notificacao {
    private String url;
    private int porta;
    private boolean acessivel;
}
