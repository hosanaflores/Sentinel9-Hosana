package dao;

import com.github.britooo.looca.api.core.Looca;
import conexao.Conexao;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class internetDao {
    public Boolean statusMaquina;
    public Boolean statusMaquina() throws IOException {
        URL url = new URL("https://www.google.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        int responseCode = connection.getResponseCode();
        return (responseCode == HttpURLConnection.HTTP_OK);
    }

    public Boolean getStatusMaquina() {
        return statusMaquina;
    }

    public void setStatusMaquina(Boolean statusMaquina) {
        this.statusMaquina = statusMaquina;
    }

    public void checarStatus(internetDao daoInternet) {
        String sql = "UPDATE Registro SET statusInternet = ? WHERE fkCaixaAutomatico = 1;";

        PreparedStatement statement = null;

        try {
            statement = Conexao.getConnection().prepareStatement(sql);
            statement.setBoolean(1, (daoInternet.statusMaquina));
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
