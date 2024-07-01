package disco;

import com.github.britooo.looca.api.core.Looca;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class Disco {

    Looca looca = new Looca();


    private Long espaçoDiscoTotal;

    private Double usoDisco;

    private Double porcentagemDisco;

    ;

    public Disco() {

    }
    File disco = File.listRoots()[0];

    File[] roots = File.listRoots();

    public void disco() {
        for (File root : roots) {

            System.out.println("Disco: " + root.getAbsolutePath());
            System.out.println("Espaço total: " + root.getTotalSpace() + " bytes");
            System.out.println("Espaço livre: " + root.getFreeSpace() + " bytes");
            System.out.println(); // Adiciona uma linha em branco para separar os discos
        }
    }

    public Double espacoDisco(){
        Double espacoDoDisco1 = (disco.getTotalSpace()  / 1073741824.0);

        DecimalFormat dfTotal = new DecimalFormat("#.##");
        String espacoDisco_Formatado = dfTotal.format(espacoDoDisco1);
        espacoDisco_Formatado = espacoDisco_Formatado.replace("," , ".");

        return Double.parseDouble(espacoDisco_Formatado);


    }

    public Double usoDisco(){
        Double usoDoDisco1 = (disco.getTotalSpace() - disco.getFreeSpace()) / 1073741824.0;

        DecimalFormat dfTotal = new DecimalFormat("#.##");
        String discoEmUso_Formatado = dfTotal.format(usoDoDisco1);
        discoEmUso_Formatado = discoEmUso_Formatado.replace("," , ".");
        Double usoDoDisco2 = Double.parseDouble(discoEmUso_Formatado);
        setUsoDisco(usoDoDisco2);
        return usoDoDisco2;
    }

    public Double porcentagemDoDisco(){


        Double percentualUso = (100.0 * (disco.getTotalSpace() - disco.getUsableSpace())) / disco.getTotalSpace();

        DecimalFormat df = new DecimalFormat("#.##");
        String percentualUsoString = df.format(percentualUso);
        percentualUsoString = percentualUsoString.replace("," , ".");
        Double porcentagemUso = Double.parseDouble(percentualUsoString);
        return porcentagemUso;

    }



    public Long getEspaçoDiscoTotal() {
        return espaçoDiscoTotal;
    }

    public void setEspaçoDiscoTotal(Long espaçoDiscoTotal) {
        this.espaçoDiscoTotal = espaçoDiscoTotal;
    }

    public Double getUsoDisco() {
        return usoDisco;
    }

    public void setUsoDisco(Double usoDisco) {
        this.usoDisco = usoDisco;
    }

    public Double getPorcentagemDisco() {
        return porcentagemDisco;
    }

    public void setPorcentagemDisco(Double porcentagemDisco) {
        this.porcentagemDisco = porcentagemDisco;
    }

    public String toString() {
        return String.format(
                "Informações de Disco:\n" +
                        "Uso: %.2f GB\n" +
                        "Total: %.2f GB\n" +
                        "Porcentagem de uso: %.2f%% \n",
                usoDisco(), espacoDisco(), porcentagemDoDisco());
    }
}
