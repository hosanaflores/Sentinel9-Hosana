package util;

import caixaEletronico.Caixa;
import com.github.britooo.looca.api.core.Looca;
import conexao.Conexao;
import memoria.Memoria;
import processador.Processador;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.text.StyledEditorKit;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.*;

public class Utility {

    // Método para enviar o código de verificação por e-mail
    private void enviarEmail(String destinatario, String codigoVerificacao) {
        // Configurações do servidor de e-mail
        String host = "smtp-mail.outlook.com";
        String usuario = "sentinel9-sptech@outlook.com";
        String senha = "sentinel9";
        int porta = 587; // Porta SMTP (normalmente 587 para TLS)

        // Configurações adicionais (opcional)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");


        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", porta);

        // Cria uma sessão de e-mail com autenticação
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha);
            }
        });

        try {
            // Cria uma mensagem de e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Código de Verificação");
            message.setText("Seu código de verificação é: " + codigoVerificacao);

            // Envia a mensagem de e-mail
            Transport.send(message);

            System.out.println("E-mail enviado com sucesso para: " + destinatario);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }

    // Método para gerar um código de verificação de 6 dígitos
    private String gerarCodigoVerificacao() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }

    public void validaLogin() {
        Caixa caixa = new Caixa();
        Main metodos = new Main();
        Looca looca = new Looca();
        Processador processador = new Processador();
        Boolean loginValido = true;

        System.out.println("███████╗███████╗███╗   ██╗████████╗██╗███╗   ██╗███████╗██╗     █████╗ \n" +
                "██╔════╝██╔════╝████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝██║    ██╔══██╗\n" +
                "███████╗█████╗  ██╔██╗ ██║   ██║   ██║██╔██╗ ██║█████╗  ██║    ╚██████║\n" +
                "╚════██║██╔══╝  ██║╚██╗██║   ██║   ██║██║╚██╗██║██╔══╝  ██║     ╚═══██║\n" +
                "███████║███████╗██║ ╚████║   ██║   ██║██║ ╚████║███████╗███████╗█████╔╝\n" +
                "╚══════╝╚══════╝╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚════╝ ");

        do {
            try {
                String email;
                String senha;
                Scanner input = new Scanner(System.in);

                System.out.println("Digite seu email:");
                email = input.nextLine();
                System.out.println("Digite sua senha:");
                senha = input.nextLine();

                PreparedStatement statement = null;
                String sql = "SELECT email,senha FROM usuariosAutorizados where email = '" + email + "' and  senha = '" + senha + "'";
                statement = Conexao.getConnection().prepareStatement(sql);
                statement.execute();

                // Se houver resultado, ou seja, se validar o email e senha, faça algo.
                if (statement.getResultSet().next()) {
                    // Obter informações da máquina
                    int id = looca.getRede().getParametros().getHostName().hashCode();
                    String nomeProcessador = processador.nomeDoProcessador();
                    Long frequenciaProcessador = looca.getProcessador().getFrequencia();

                    // Gere um código de verificação
                    String codigoVerificacao = gerarCodigoVerificacao();

                    // Envie o código de verificação por e-mail
                    enviarEmail(email, codigoVerificacao);

                    // Solicite ao usuário que insira o código de verificação
                    System.out.println("Digite o código de verificação enviado para o seu e-mail:");
                    String codigoInserido = input.nextLine();

                    // Verifique se o código inserido corresponde ao código gerado
                    if (codigoInserido.equals(codigoVerificacao)) {
                        System.out.println("Código de verificação correto. Login feito com sucesso!");
                        System.out.println(caixa.getIdCaixa());
                        String sqlMaquina = "SELECT * FROM caixaAutomatico WHERE idCaixaAutomatico = '" + id + "'";
                        PreparedStatement statement2 = Conexao.getConnection().prepareStatement(sqlMaquina);
                        statement2.execute();
                        statement2.getResultSet();

                        if (statement2.getResultSet().next()) {
                            System.out.println("Máquina encontrada!\n");
                            metodos.Monitorar();
                        } else {
                            System.out.println("Máquina não cadastrada, deseja cadastrá-la?");
                            // Restante do seu código de cadastro de máquina...
                        }
                    } else {
                        System.out.println("Código de verificação incorreto. Acesso negado.");
                        loginValido = false;
                    }
                } else {
                    System.out.println("Acesso negado.\n Tente novamente!");
                    loginValido = false;
                }

                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } while (!loginValido);
    }

    void limparTela() {
        for (int i = 0; i < 10; i++) {
            System.out.println("\n");
        }
    }
}
