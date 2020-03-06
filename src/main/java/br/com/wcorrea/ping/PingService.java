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

import java.net.InetAddress;

/**
 * @author Willian Vagner Vicente Correa
 * Ex: https://crunchify.com/how-to-implement-your-own-inetaddress-isreachablestring-address-int-port-int-timeout-method-in-java/
 */
@Slf4j
@Data
public class PingService extends Thread {

    private String site;
    private boolean acessivel;
    private String endpoint;
    private boolean retornoIndividual;

    //TODO: VERIFICAR COMO FAZER O RETORNO EM LOT

    public PingService(String site, boolean acessivel, String endpoint) {
        this.site = site;
        this.endpoint = endpoint;
        this.acessivel = acessivel;
    }

    @Override
    public void run() {
        super.run();
//        Thread.sleep(1000);
        ping(site, endpoint);
    }

    private static void ping(String host, String endpoint) {
        InetAddress addr;
        boolean acessivel = false;
        try {
            addr = InetAddress.getByName(host);
            acessivel = addr.isReachable(3000);
            if (acessivel) {
                System.out.println("InetAddress.isReachable(timeout) Result ==> Ping successful for host: " + host);
            } else {
                System.out.println("InetAddress.isReachable(timeout) Result ==> Ping failed for host: " + host);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        notificador(host, acessivel, endpoint);
    }

    private static void notificador(String url, boolean acessivel, String endpoint) {
        Thread t = new Thread() {
            @SneakyThrows
            public void run() {
                JSONObject json = new JSONObject();
                json.put("url", url);
                json.put("acessivel", acessivel);

                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(endpoint);

                StringEntity entity = new StringEntity(json.toString());
                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                CloseableHttpResponse response = client.execute(httpPost);
                client.close();
            }
        };
        t.start();
    }
}
