package br.com.wcorrea.ping;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Willian Vagner Vicente Correa
 * Ex: https://crunchify.com/how-to-implement-your-own-inetaddress-isreachablestring-address-int-port-int-timeout-method-in-java/
 */
public class Ping extends Thread {

    private String site;

    public Ping(String site) {
        this.site = site;
    }

    @Override
    public void run() {
        super.run();

//        Thread.sleep(1000);
        ping(site);
    }

    private static void ping(String host) {
        try {
            InetAddress addr = InetAddress.getByName(host);
            boolean reachable = addr.isReachable(3000);
            if (reachable) {
                System.out.println("InetAddress.isReachable(timeout) Result ==> Ping successful for host: " + host);
            } else {
                System.out.println("InetAddress.isReachable(timeout) Result ==> Ping failed for host: " + host);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> sites = new ArrayList();
        sites.add("www.google.com.br");
        sites.add("www.google2.com.br"); // Erro
        sites.add("127.0.0.1");
        sites.add("www.cits.br");
        sites.add("www.uol.comb.r"); //Erro
        sites.add("www.uol.com.br");
        sites.add("192.168.1.1");
        sites.add("192.168.1.102");

        for (String s : sites) {
            new Ping(s).start();
        }
    }
}
