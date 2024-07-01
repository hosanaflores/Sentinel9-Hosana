package caixaEletronico;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class Caixa {

    private String idCaixa;

    public String getIdCaixa() {
        return idCaixa;
    }

    public void setIdCaixa(String idCaixa) {
        this.idCaixa = idCaixa;
    }

    public Caixa() {

    }

    public String caixaId() throws UnknownHostException {
        InetAddress dados = InetAddress.getLocalHost();
        return String.valueOf(dados);
    }
}
