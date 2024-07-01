package util;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import dao.MemoriaDao;
import dao.internetDao;
import disco.Disco;
import memoria.Memoria;
import processador.Processador;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.jna.platform.win32.Advapi32Util;

import java.net.InetAddress;


public class Main {
    void Monitorar() {
        Looca apiLooca = new Looca();
        Timer timer = new Timer();
        MemoriaDao dao = new MemoriaDao();
        Memoria memoriaComputador = new Memoria();
        Processador processador = new Processador();
        Disco disco = new Disco();
        Looca looca = new Looca();

        internetDao daoInternet = new internetDao();

        Integer intervalo = 1000; //milissegundos = (5 segundos)



        timer.scheduleAtFixedRate(new TimerTask() { // instância anônima
            public void run() {
                try {
                    dao.cadastrarDados();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, intervalo);

        Scanner leitor = new Scanner(System.in);
        Integer opcao;
        boolean statusMaquina;

        do {
            System.out.println("""
            ╔════════════════════════════╗
            ║   Escolha um hardware para  ║
            ║        listar a captura      ║
            ║                              ║
            ║   1 - Processador            ║
            ║   2 - Uso de memória         ║
            ║   3 - Memória total          ║
            ║   4 - Porcentagem de memória ║
            ║       utilizada              ║
            ║   5 - Uso do disco           ║
            ║   6 - Disco total            ║
            ║   7 - Porcentagem do disco   ║
            ║       utilizada              ║
            ║   0 - Sair                   ║
            ╚════════════════════════════╝
            """);

            opcao = leitor.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println(processador.toString());
                    break;
                case 2:
                    System.out.println("Uso de memória: " + memoriaComputador.memoriaEmUso().toString() + "GB");
                    break;
                case 3:
                    System.out.println("Memória total: " + memoriaComputador.memoriaTotal() + "GB");
                    break;
                case 4:
                    System.out.println("Porcentagem de memória utilizada: " + memoriaComputador.memoriaPorcentagem() + "%");
                    break;
                case 5:
                    System.out.println("Uso do disco: " + disco.usoDisco().toString() + "GB");
                    break;
                case 6:
                    System.out.println("Disco total: " + disco.espacoDisco().toString() + "GB");
                    break;
                case 7:
                    System.out.println("Porcentagem do disco utilizada: " + disco.porcentagemDoDisco().toString() + "%");
                    break;
                case 0:
                    System.out.println("Até a proxima!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
            }

            try {
                boolean temInternet = daoInternet.statusMaquina();
                if (temInternet) {
                    statusMaquina = true;
                    daoInternet.setStatusMaquina(statusMaquina);
                } else {
                    statusMaquina = false;
                    daoInternet.setStatusMaquina(statusMaquina);
                }
            } catch (MalformedURLException e) {
                statusMaquina = false;
                daoInternet.setStatusMaquina(statusMaquina);
            } catch (IOException e) {
                statusMaquina = false;
                daoInternet.setStatusMaquina(statusMaquina);
            }

        } while (opcao != 0);

// Cancelar a execução da thread do timer
        timer.cancel();
    }
}
