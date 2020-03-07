package br.com.wcorrea.ping;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Willian Vagner Vicente Correa
 * Ex: https://crunchify.com/how-to-implement-your-own-inetaddress-isreachablestring-address-int-port-int-timeout-method-in-java/
 */
@Slf4j
@Data
public class PingService extends Thread {

    private String url;
    private Integer porta;
    private boolean acessivel;
    private String endpoint;
    private boolean retornoIndividual;

    //TODO: VERIFICAR COMO FAZER O RETORNO EM LOT

    public PingService(String url, int porta, boolean acessivel, String endpoint) {
        this.url = url;
        this.endpoint = endpoint;
        this.acessivel = acessivel;
        this.porta = porta;
    }

    @SneakyThrows
    @Override
    public void run() {
        super.run();

        if(porta == 0){
            ping(url, endpoint);
        }else{
            ping(url, (porta > 0) ? porta : 80, 3000, endpoint);
        }
    }

    private static void notificador(String url, int porta, boolean acessivel, String endpoint) {
        Thread t = new Thread() {
            @SneakyThrows
            public void run() {
//                log.info("Notificacao - Inicio - URL: {}:{} - Endpoint: {}", url, porta, endpoint);
                JSONObject json = new JSONObject();
                json.put("endereco", url);
                json.put("porta", porta);
                json.put("acessivel", acessivel);

                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(endpoint);

                StringEntity entity = new StringEntity(json.toString());
                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                CloseableHttpResponse response = client.execute(httpPost);
                client.close();
//                log.info("Notificacao - Fim - URL: {}:{} - Endpoint: {}", url, porta, endpoint);
            }
        };
        t.start();
    }

    private static void ping(String url, String endpoint) {
        InetAddress addr;
        boolean acessivel = false;
        try {
            addr = InetAddress.getByName(url);
            acessivel = addr.isReachable(3000);
            notificador(url, 0, acessivel, endpoint);
        } catch (Exception e) {
//            e.printStackTrace();
            notificador(url, 0, false, endpoint);
        }
    }

    private static void ping(String url, int porta, int timeout, String endpoint) {
        try {
            try (Socket s = new Socket()) {
                s.connect(new InetSocketAddress(url, porta), timeout);
            }
//            log.info("Conexao bem sucedida no endereco {}:{}", url, porta);
            notificador(url, porta, true, endpoint);
        } catch (IOException exception) {
//            log.error("Conexao mal sucedida no endereco {}:{}", url, porta);
            notificador(url, porta, false, endpoint);
        }
    }
}
