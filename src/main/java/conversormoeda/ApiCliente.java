package conversormoeda;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiCliente {
    private static final String BASE_URL = "https://economia.awesomeapi.com.br/json/last/";
    private static final int TIMEOUT_SECONDS = 10;
    
    private final HttpClient httpClient;
    private final Gson gson;
    
    public ApiCliente() {
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
            .build();
        this.gson = new Gson();
    }
    
    public CotacaoMoeda obterCotacao(String moedaOrigem, String moedaDestino) {
        try {
            String par = moedaOrigem + "-" + moedaDestino;
            String url = BASE_URL + par;
            
            System.out.println("Consultando cotação: " + par + "...");
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .header("User-Agent", "ConversorMoedas/1.0")
                .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return processarResposta(response.body(), moedaOrigem, moedaDestino);
            } else {
                System.err.printf("Erro HTTP: %d - %s%n", response.statusCode(), 
                                response.body());
                return null;
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao consultar API: " + e.getMessage());
            return null;
        }
    }
    
    private CotacaoMoeda processarResposta(String responseBody, String moedaOrigem, String moedaDestino) {
        try {
            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
            
            String chave = moedaOrigem + moedaDestino;
            
            if (jsonResponse.has(chave)) {
                JsonObject cotacaoJson = jsonResponse.getAsJsonObject(chave);
                return gson.fromJson(cotacaoJson, CotacaoMoeda.class);
            } else {
                System.err.println("Par de moedas não encontrado na resposta: " + chave);
                return null;
            }
            
        } catch (JsonSyntaxException e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
            return null;
        }
    }
    
    public boolean isMoedaValida(String codigoMoeda) {
        String[] moedasSuportadas = {
            "USD", "BRL", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", 
            "CNY", "ARS", "BOB", "CLP", "COP", "UYU", "PYG"
        };
        
        for (String moeda : moedasSuportadas) {
            if (moeda.equalsIgnoreCase(codigoMoeda)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean testarConectividade() {
        try {
            CotacaoMoeda teste = obterCotacao("USD", "BRL");
            return teste != null;
        } catch (Exception e) {
            return false;
        }
    }
}
