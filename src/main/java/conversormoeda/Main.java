package conversormoeda;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ConversorMoeda conversor = new ConversorMoeda();
    
    public static void main(String[] args) {
        exibirBoasVindas();
        
        // Testar conectividade inicial
        conversor.testarConectividade();
        
        executarLoop();
        
        System.out.println("👋 Obrigado por usar o Conversor de Moedas!");
        System.out.println("💡 Desenvolvido com Java 11+ e AwesomeAPI");
    }
    
    private static void exibirBoasVindas() {
        System.out.println("============================================================");
        System.out.println("            🏦 CONVERSOR DE MOEDAS 🏦");
        System.out.println("============================================================");
        System.out.println("📈 Bem-vindo ao Conversor de Moedas em Tempo Real!");
        System.out.println("💱 Taxas atualizadas via AwesomeAPI");
        System.out.println("🚀 Desenvolvido em Java com tecnologias modernas");
        System.out.println("============================================================");
    }
    
    private static void executarLoop() {
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenu();
            int opcao = obterOpcaoUsuario();
            
            switch (opcao) {
                case 1:
                    conversor.converter("USD", "BRL");
                    pausar();
                    break;
                    
                case 2:
                    conversor.converter("BRL", "USD");
                    pausar();
                    break;
                    
                case 3:
                    conversor.converter("EUR", "BRL");
                    pausar();
                    break;
                    
                case 4:
                    conversor.converter("BRL", "EUR");
                    pausar();
                    break;
                    
                case 5:
                    conversor.converter("GBP", "BRL");
                    pausar();
                    break;
                    
                case 6:
                    conversor.converter("BRL", "GBP");
                    pausar();
                    break;
                    
                case 7:
                    conversor.converterPersonalizado();
                    pausar();
                    break;
                    
                case 8:
                    conversor.exibirHistorico();
                    pausar();
                    break;
                    
                case 9:
                    conversor.limparHistorico();
                    pausar();
                    break;
                    
                case 10:
                    conversor.testarConectividade();
                    pausar();
                    break;
                    
                case 11:
                    exibirAjuda();
                    pausar();
                    break;
                    
                case 0:
                    continuar = false;
                    System.out.println("🚪 Saindo...");
                    break;
                    
                default:
                    System.out.println("❌ Opção inválida! Tente novamente.");
                    pausar();
            }
        }
    }
    
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("                    MENU PRINCIPAL");
        System.out.println("============================================================");
        System.out.println("🇺🇸  1) Dólar [USD] ➡️  Real [BRL]");
        System.out.println("🇧🇷  2) Real [BRL] ➡️  Dólar [USD]");
        System.out.println("🇪🇺  3) Euro [EUR] ➡️  Real [BRL]");
        System.out.println("🇧🇷  4) Real [BRL] ➡️  Euro [EUR]");
        System.out.println("🇬🇧  5) Libra [GBP] ➡️  Real [BRL]");
        System.out.println("🇧🇷  6) Real [BRL] ➡️  Libra [GBP]");
        System.out.println("⚙️   7) Conversão personalizada");
        System.out.println("📊  8) Exibir histórico de conversões");
        System.out.println("🗑️   9) Limpar histórico");
        System.out.println("🔍 10) Testar conectividade API");
        System.out.println("❓ 11) Ajuda e informações");
        System.out.println("🚪  0) Sair");
        System.out.println("============================================================");
        System.out.print("👉 Escolha uma opção: ");
    }
    
    private static int obterOpcaoUsuario() {
        while (true) {
            try {
                int opcao = scanner.nextInt();
                if (opcao >= 0 && opcao <= 11) {
                    return opcao;
                } else {
                    System.out.print("❌ Opção deve estar entre 0 e 11. Tente novamente: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("❌ Por favor, digite um número válido: ");
                scanner.nextLine();
            }
        }
    }
    
    private static void exibirAjuda() {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("                  AJUDA E INFORMAÇÕES");
        System.out.println("============================================================");
        System.out.println("ℹ️  Como usar o conversor:");
        System.out.println("   1. Escolha uma das opções de conversão (1-6)");
        System.out.println("   2. Digite o valor que deseja converter");
        System.out.println("   3. Veja o resultado com a taxa atual");
        System.out.println();
        System.out.println("💡 Dicas importantes:");
        System.out.println("   • Use vírgula (,) como separador decimal");
        System.out.println("   • Valores devem ser positivos e menores que 1 bilhão");
        System.out.println("   • Para moedas personalizadas, use códigos de 3 letras");
        System.out.println();
        System.out.println("🌐 Moedas mais comuns suportadas:");
        System.out.println("   USD (Dólar), EUR (Euro), GBP (Libra), JPY (Iene)");
        System.out.println("   BRL (Real), AUD (Dólar Australiano), CAD (Dólar Canadense)");
        System.out.println("   CHF (Franco Suíço), CNY (Yuan), ARS (Peso Argentino)");
        System.out.println();
        System.out.println("🔄 As taxas são atualizadas em tempo real via AwesomeAPI");
        System.out.println("📊 Seu histórico é mantido durante toda a sessão");
        System.out.println("============================================================");
    }
    
    private static void pausar() {
        System.out.println();
        System.out.print("⏸️  Pressione ENTER para continuar...");
        scanner.nextLine();
        scanner.nextLine();
    }
}
