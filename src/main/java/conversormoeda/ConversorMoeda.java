package conversormoeda;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

public class ConversorMoeda {
    private final ApiCliente apiCliente;
    private final Scanner scanner;
    private final List<Conversao> historico;
    
    public ConversorMoeda() {
        this.apiCliente = new ApiCliente();
        this.scanner = new Scanner(System.in);
        this.historico = new ArrayList<>();
    }
    
    public void converter(String moedaOrigem, String moedaDestino) {
        try {
            System.out.printf("%nConvertendo de %s para %s%n", moedaOrigem, moedaDestino);
            
            if (!apiCliente.isMoedaValida(moedaOrigem) || !apiCliente.isMoedaValida(moedaDestino)) {
                System.out.println("⚠️  Uma ou ambas as moedas podem não ser suportadas.");
                System.out.println("Tentando realizar a conversão mesmo assim...");
            }
            
            double valor = obterValorUsuario();
            
            CotacaoMoeda cotacao = apiCliente.obterCotacao(moedaOrigem, moedaDestino);
            
            if (cotacao != null) {
                double valorConvertido = valor * cotacao.getBid();
                
                exibirResultado(cotacao, valor, valorConvertido, moedaOrigem, moedaDestino);
                salvarNoHistorico(moedaOrigem, moedaDestino, valor, valorConvertido, cotacao.getBid());
                
                System.out.println("✅ Conversão realizada com sucesso!");
                
            } else {
                System.out.println("❌ Erro ao obter cotação. Verifique se as moedas são válidas e tente novamente.");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro durante a conversão: " + e.getMessage());
            scanner.nextLine();
        }
    }
    
    private double obterValorUsuario() {
        while (true) {
            try {
                System.out.print("💰 Digite o valor a ser convertido: ");
                double valor = scanner.nextDouble();
                
                if (valor <= 0) {
                    System.out.println("⚠️  Por favor, digite um valor positivo maior que zero.");
                    continue;
                }
                
                if (valor > 1_000_000_000) {
                    System.out.println("⚠️  Valor muito alto. Digite um valor menor que 1 bilhão.");
                    continue;
                }
                
                return valor;
                
            } catch (InputMismatchException e) {
                System.out.println("⚠️  Por favor, digite um número válido.");
                scanner.nextLine();
            }
        }
    }
    
    private void exibirResultado(CotacaoMoeda cotacao, double valor, double valorConvertido, 
                                String moedaOrigem, String moedaDestino) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("             RESULTADO DA CONVERSÃO");
        System.out.println("============================================================");
        System.out.printf("📋 Par de moedas: %s%n", cotacao.getName());
        System.out.printf("💵 Valor original: %,.2f %s%n", valor, moedaOrigem);
        System.out.printf("📈 Taxa de câmbio: %.4f%n", cotacao.getBid());
        System.out.printf("💰 Valor convertido: %,.2f %s%n", valorConvertido, moedaDestino);
        
        if (cotacao.getPctChange() != null && !cotacao.getPctChange().isEmpty()) {
            try {
                double mudanca = Double.parseDouble(cotacao.getPctChange());
                String emoji = mudanca >= 0 ? "📈" : "📉";
                System.out.printf("%s Variação hoje: %s%%%n", emoji, cotacao.getPctChange());
            } catch (NumberFormatException e) {
                // Ignora se não conseguir converter
            }
        }
        
        System.out.printf("🕐 Atualizado em: %s%n", cotacao.getCreateDate());
        System.out.println("============================================================");
    }
    
    private void salvarNoHistorico(String moedaOrigem, String moedaDestino, 
                                  double valorOriginal, double valorConvertido, double taxa) {
        Conversao conversao = new Conversao(
            moedaOrigem, moedaDestino, valorOriginal, 
            valorConvertido, taxa, LocalDateTime.now()
        );
        historico.add(conversao);
    }
    
    public void converterPersonalizado() {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("        CONVERSÃO PERSONALIZADA");
        System.out.println("==================================================");
        
        String moedaOrigem = obterCodigoMoeda("Digite o código da moeda de ORIGEM (ex: USD, EUR, JPY): ");
        String moedaDestino = obterCodigoMoeda("Digite o código da moeda de DESTINO (ex: BRL, USD, EUR): ");
        
        if (moedaOrigem.equals(moedaDestino)) {
            System.out.println("⚠️  As moedas de origem e destino não podem ser iguais!");
            return;
        }
        
        converter(moedaOrigem, moedaDestino);
    }
    
    private String obterCodigoMoeda(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String codigo = scanner.next().toUpperCase().trim();
            
            if (codigo.length() == 3 && codigo.matches("[A-Z]{3}")) {
                return codigo;
            } else {
                System.out.println("⚠️  Código deve ter exatamente 3 letras (ex: USD, BRL, EUR)");
            }
        }
    }
    
    public void exibirHistorico() {
        System.out.println();
        System.out.println("================================================================================");
        System.out.println("                    HISTÓRICO DE CONVERSÕES");
        System.out.println("================================================================================");
        
        if (historico.isEmpty()) {
            System.out.println("📝 Nenhuma conversão foi realizada ainda.");
            System.out.println("   Faça algumas conversões para ver o histórico aqui!");
            System.out.println("================================================================================");
            return;
        }
        
        System.out.printf("%-4s %-8s %-8s %-12s %-12s %-8s %-20s%n",
            "ID", "ORIGEM", "DESTINO", "VLR ORIG", "VLR CONV", "TAXA", "DATA/HORA");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (int i = 0; i < historico.size(); i++) {
            Conversao c = historico.get(i);
            System.out.printf("%-4d %-8s %-8s %,-12.2f %,-12.2f %-8.4f %-20s%n",
                i + 1, 
                c.getMoedaOrigem(), 
                c.getMoedaDestino(), 
                c.getValorOriginal(),
                c.getValorConvertido(), 
                c.getTaxa(), 
                c.getDataHoraFormatada());
        }
        
        System.out.println("================================================================================");
        System.out.printf("📊 Total de conversões realizadas: %d%n", historico.size());
        System.out.println("================================================================================");
    }
    
    public void limparHistorico() {
        if (historico.isEmpty()) {
            System.out.println("📝 O histórico já está vazio.");
            return;
        }
        
        System.out.print("⚠️  Tem certeza que deseja limpar todo o histórico? (S/N): ");
        String confirmacao = scanner.next().toUpperCase();
        
        if (confirmacao.equals("S") || confirmacao.equals("SIM")) {
            historico.clear();
            System.out.println("✅ Histórico limpo com sucesso!");
        } else {
            System.out.println("❌ Operação cancelada.");
        }
    }
    
    public void testarConectividade() {
        System.out.println();
        System.out.print("🔍 Testando conectividade com a API... ");
        
        if (apiCliente.testarConectividade()) {
            System.out.println("✅ Conexão OK!");
        } else {
            System.out.println("❌ Falha na conexão!");
            System.out.println("Verifique sua conexão com a internet e tente novamente.");
        }
    }
    
    public List<Conversao> getHistorico() {
        return new ArrayList<>(historico);
    }
}
