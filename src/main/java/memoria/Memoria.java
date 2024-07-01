package memoria;

import com.github.britooo.looca.api.core.Looca;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

import java.text.DecimalFormat;

public class Memoria {

    Looca looca = new Looca();

        private double memoriaUsoGB;

        private double memoriaTotalGB;

        private double memoriaPorcentagem;



    public double getMemoriaUsoGB() {
            return memoriaUsoGB;
        }

        public void setMemoriaUsoGB(double memoriaUsoGB) {
            this.memoriaUsoGB = memoriaUsoGB;
        }

        public double getMemoriaTotalGB() {
            return memoriaTotalGB;
        }

        public void setMemoriaTotalGB(double memoriaTotalGB) {
            this.memoriaTotalGB = memoriaTotalGB;
        }

        public double getMemoriaPorcentagem() {
            return memoriaPorcentagem;
        }

        public void setMemoriaPorcentagem(double memoriaPorcentagem) {
            this.memoriaPorcentagem = memoriaPorcentagem;
        }

    public Memoria() {
    }



    public Double memoriaEmUso(){
            Double memoria_uso_gigabytes = (double) (looca.getMemoria().getEmUso()) / (1024 * 1024 * 1024);
            DecimalFormat df = new DecimalFormat("#.##");
            String memoria_formatada = df.format(memoria_uso_gigabytes);
            memoria_formatada = memoria_formatada.replace("," , ".");
            setMemoriaUsoGB(Double.parseDouble(memoria_formatada));

            return Double.parseDouble(memoria_formatada) ;
        }
        public Double memoriaTotal(){
            Double memoria_total_gigabytes = (double) (looca.getMemoria().getTotal()) / (1024 * 1024 * 1024);
            DecimalFormat dfTotal = new DecimalFormat("#.##");
            String memoriaTotal_formatada = dfTotal.format(memoria_total_gigabytes);
            memoriaTotal_formatada = memoriaTotal_formatada.replace("," , ".");
            setMemoriaTotalGB(Double.parseDouble(memoriaTotal_formatada));
            return Double.parseDouble(memoriaTotal_formatada);
        }

        public Double memoriaPorcentagem(){
            Double memoria_porcentagem_gigabytes = getMemoriaUsoGB() / getMemoriaTotalGB() * 100;
            DecimalFormat dfPorcentagem = new DecimalFormat("#.##");
            String memoria_porcentagemFormatada = dfPorcentagem.format(memoria_porcentagem_gigabytes);
            memoria_porcentagemFormatada = memoria_porcentagemFormatada.replace(",", ".");

            setMemoriaPorcentagem(Double.parseDouble(memoria_porcentagemFormatada));

            return Double.parseDouble(memoria_porcentagemFormatada);
        }

    @Override
    public String toString() {
        return String.format(
                "Informações da memória RAM:\n" +
                        "Uso: %.2f GB\n" +
                        "Total: %.2f GB\n" +
                        "Porcentagem de uso: %.2f%%\n",
                memoriaEmUso(), memoriaTotal(), memoriaPorcentagem());
    }
}


