package aws;

import conexao.Conexao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class DatabaseToS3Uploader {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public static void main(String[] args) {
        // Agendar a execução da tarefa de envio ao Amazon S3 a cada 24 horas
        scheduler.scheduleAtFixedRate(DatabaseToS3Uploader::uploadS3, 0, 24, TimeUnit.HOURS);
    }

    public static void uploadS3() {
        OperacoesS3 operacoes = new OperacoesS3("---", "---");

        String tableName = "registro"; // Defina o nome da tabela

        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

            // Cria um arquivo temporário com o prefixo "data" e extensão ".csv"
            File tempFile = File.createTempFile("data", ".csv");

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                // Obtém os metadados do ResultSet para saber o número de colunas e seus nomes
                ResultSetMetaData metaData = rs.getMetaData();
                int numColumns = metaData.getColumnCount();

                // Cria um StringBuilder para armazenar o cabeçalho do arquivo CSV
                StringBuilder header = new StringBuilder();

                // Itera sobre as colunas, construindo o cabeçalho separado por ponto e vírgula
                for (int i = 1; i <= numColumns; i++) {
                    header.append(metaData.getColumnLabel(i)); // Adiciona o nome da coluna
                    if (i < numColumns) {
                        header.append(";"); // Adiciona ponto e vírgula como delimitador entre as colunas
                    }
                }
                header.append("\n"); // Adiciona quebra de linha ao final do cabeçalho
                fos.write(header.toString().getBytes()); // Escreve o cabeçalho no arquivo

                // Itera sobre os resultados do ResultSet e escreve os dados no arquivo
                while (rs.next()) {
                    StringBuilder data = new StringBuilder();
                    for (int i = 1; i <= numColumns; i++) {
                        data.append(rs.getString(i)); // Adiciona o valor do campo
                        if (i < numColumns) {
                            data.append(";"); // Adiciona ponto e vírgula como delimitador entre os dados
                        }
                    }
                    data.append("\n"); // Adiciona quebra de linha ao final de cada linha de dados
                    fos.write(data.toString().getBytes()); // Escreve os dados no arquivo
                }
            } catch (IOException e) {
                e.printStackTrace(); // Imprime informações sobre a exceção em caso de erro
            }

            // Enviar o arquivo para o Amazon S3
            String fmt = tableName.toLowerCase() + "-sentinel";
            String key = "caixaRecursos.csv"; // Nome do arquivo no S3

            operacoes.criarBucket(fmt);
            operacoes.enviarArquivo(fmt, key, tempFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private static void manipularBucket() {
//        var operacoesS3 = new OperacoesS3(Credenciais.ACCESS_KEY, Credenciais.SECRET_KEY);
//        var nomeBucket = "teste-bucketbrunovinicius";
//        operacoesS3.criarBucket(nomeBucket);
//    }
//
//    private static void manipularArquivo() {
//        var operacoesS3 = new OperacoesS3(Credenciais.ACCESS_KEY, Credenciais.SECRET_KEY);
//        var nomeBucket = "bucket-caixa";
//        var origemArquivo = "C:\\Users\\bruno\\Documents\\faculdade-atividades\\Faculdade-2semestre\\analise\\wireframe.png";
//        var destinoArquivo = "wireframe.png";
//        operacoesS3.enviarArquivo(nomeBucket, destinoArquivo, new File(origemArquivo));
//        operacoesS3.listarArquivos(nomeBucket);
//    }

}
