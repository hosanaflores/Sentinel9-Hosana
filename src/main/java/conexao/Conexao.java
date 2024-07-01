package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String url = "jdbc:mysql://localhost/caixaEletronico";
    private static final String user = "root";
    private static final String password = "root";

//    private static final String url1 = "jdbc:mysql://172.31.18.4/caixaEletronico";
//    private static final String user1 = "aluno";
//    private static final String password1 = "sptech100";

//     private static final String url = "jdbc:mysql://172.31.86.185/caixaEletronico";
//    private static final String user = "aluno";
//    private static final String password = "sptech100";



    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

//    public static Connection getConnection2() throws SQLException {
//        return DriverManager.getConnection(url1, user1, password1);
//    }

}
