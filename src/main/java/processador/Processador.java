package processador;

import com.github.britooo.looca.api.core.Looca;

import java.text.DecimalFormat;

public class Processador {

    Looca looca = new Looca();

    private Double processadorUso;
    private String processadorNome;

    public Processador() {
    }

    public String getProcessadorNome() {

        return processadorNome;
    }

    public void setProcessadorNome(String processadorNome) {
        this.processadorNome = processadorNome;
    }

    public Double getProcessadorUso() {
        return processadorUso;

    }

    public void setProcessadorUso(Double processadorUso) {
        this.processadorUso = processadorUso;
    }

    public Double processadorEmUso(){
        Double valor1 = looca.getProcessador().getUso();
        DecimalFormat dfTotal = new DecimalFormat("#.##");
        String processadorEmUso_Formatado = dfTotal.format(valor1);
        processadorEmUso_Formatado = processadorEmUso_Formatado.replace("," , ".");
        Double usoDoProcessador = Double.parseDouble(processadorEmUso_Formatado);
        setProcessadorUso(usoDoProcessador);
        return getProcessadorUso();
    }

    public String nomeDoProcessador(){
        setProcessadorNome(looca.getProcessador().getNome());
        return getProcessadorNome();
    }

    @Override
    public String toString() {
        return String.format(
                "Informações do Processador:\n" +
                        "Nome: %s GB\n" +
                        "Uso: %.2f%%\n",
                nomeDoProcessador(), processadorEmUso());
    }
}
