package dao;

import com.github.britooo.looca.api.core.Looca;
import conexao.Conexao;
import disco.Disco;
import memoria.Memoria;
import processador.Processador;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.regex.*;


public class MemoriaDao {

    public void cadastrarDados() throws UnknownHostException {
        String sql = "INSERT INTO registro (processador, memoria, memoriaTotal, memoriaPorcentagem, dadosRede, statusInternet, discoPercentual, discoUso, discoTotal, fkCaixaAutomatico) VALUES (?,?,?,?,?,?,?,?,?,?)";

        
        Looca apiLooca = new Looca();
        Memoria memoria = new Memoria();
        Processador processador = new Processador();
        Disco disco = new Disco();
        internetDao internet = new internetDao();
        boolean statusMaquina;

        try {
            boolean temInternet = internet.statusMaquina();
            if (temInternet) {
                statusMaquina = true;
                internet.setStatusMaquina(statusMaquina);
            } else {
                statusMaquina = false;
                internet.setStatusMaquina(statusMaquina);
            }
        } catch (MalformedURLException e) {
            statusMaquina = false;
            internet.setStatusMaquina(statusMaquina);
        } catch (IOException e) {
            statusMaquina = false;
            internet.setStatusMaquina(statusMaquina);
        }

        int idCaixaAutomatico = apiLooca.getRede().getParametros().getHostName().hashCode();
        try (Connection connection1 = Conexao.getConnection();

             PreparedStatement statement1 = connection1.prepareStatement(sql)) {

            statement1.setDouble(1, processador.processadorEmUso());
            statement1.setDouble(2, (memoria.memoriaEmUso()));

            statement1.setDouble(3, memoria.memoriaTotal());


            statement1.setDouble(4, (memoria.memoriaPorcentagem()));

            statement1.setString(5, apiLooca.getRede().getParametros().getHostName());

            statement1.setBoolean(6, (internet.statusMaquina));
            statement1.setDouble(7, (disco.porcentagemDoDisco()));
            statement1.setDouble(8, (disco.usoDisco()));
            statement1.setDouble(9, (disco.espacoDisco()));
            statement1.setInt(10, idCaixaAutomatico);
            statement1.execute();

//            statement2.setDouble(1, apiLooca.getProcessador().getUso());
//            Double memoria_uso_gigabytes2 = (double) (apiLooca.getMemoria().getEmUso()) / (1024 * 1024 * 1024);
//            DecimalFormat df2 = new DecimalFormat("#.##");
//            String memoria_formatada2 = df2.format(memoria_uso_gigabytes2);
//            memoria_formatada2 = memoria_formatada2.replace("," , ".");
//            statement2.setString(2, (memoria_formatada2));
//            Double memoria_total_gigabytes2 = (double) (apiLooca.getMemoria().getTotal()) / (1024 * 1024 * 1024);
//            DecimalFormat dfTotal2 = new DecimalFormat("#.##");
//            String memoriaTotal_formatada2 = dfTotal2.format(memoria_total_gigabytes2);
//            memoriaTotal_formatada2 = memoriaTotal_formatada2.replace("," , ".");
//            statement2.setString(3, memoriaTotal_formatada2);
//
//            Double memoria_porcentagem_gigabytes2 = (double) (apiLooca.getMemoria().getEmUso()) / (double) (apiLooca.getMemoria().getTotal()) * 100;
//            DecimalFormat dfPorcentagem2 = new DecimalFormat("#.##");
//            String memoriaPorcentagem_formatada2 = dfPorcentagem2.format(memoria_porcentagem_gigabytes2);
//            memoriaPorcentagem_formatada2 = memoriaPorcentagem_formatada2.replace("," , ".");
//            statement2.setString(4, (memoriaPorcentagem_formatada2));
//
//            statement2.setString(5, apiLooca.getRede().getParametros().getHostName());
//            statement2.setInt(6, apiLooca.getGrupoDeDiscos().getQuantidadeDeDiscos());
//            statement2.setBoolean(7, (internet.statusMaquina));
//            statement2.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar dados", e);
        }
    }
}
