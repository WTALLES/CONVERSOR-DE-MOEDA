package conversormoeda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Conversao {
    private String moedaOrigem;
    private String moedaDestino;
    private double valorOriginal;
    private double valorConvertido;
    private double taxa;
    private LocalDateTime dataHora;
    
    public Conversao(String moedaOrigem, String moedaDestino, double valorOriginal,
                     double valorConvertido, double taxa, LocalDateTime dataHora) {
        this.moedaOrigem = moedaOrigem;
        this.moedaDestino = moedaDestino;
        this.valorOriginal = valorOriginal;
        this.valorConvertido = valorConvertido;
        this.taxa = taxa;
        this.dataHora = dataHora;
    }
    
    public String getMoedaOrigem() { return moedaOrigem; }
    public String getMoedaDestino() { return moedaDestino; }
    public double getValorOriginal() { return valorOriginal; }
    public double getValorConvertido() { return valorConvertido; }
    public double getTaxa() { return taxa; }
    public LocalDateTime getDataHora() { return dataHora; }
    
    public void setMoedaOrigem(String moedaOrigem) { this.moedaOrigem = moedaOrigem; }
    public void setMoedaDestino(String moedaDestino) { this.moedaDestino = moedaDestino; }
    public void setValorOriginal(double valorOriginal) { this.valorOriginal = valorOriginal; }
    public void setValorConvertido(double valorConvertido) { this.valorConvertido = valorConvertido; }
    public void setTaxa(double taxa) { this.taxa = taxa; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    
    public String getDataHoraFormatada() {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
    
    @Override
    public String toString() {
        return String.format("%.2f %s = %.2f %s (Taxa: %.4f) - %s",
            valorOriginal, moedaOrigem, valorConvertido, moedaDestino, taxa, getDataHoraFormatada());
    }
}
