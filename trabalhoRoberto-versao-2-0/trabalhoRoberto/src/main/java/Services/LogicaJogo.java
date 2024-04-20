package Services;

import java.util.Random;
import Models.ListaDuplamenteEncadeada;
import Models.Peca;

public class LogicaJogo {
    private DeterminaInicioJogo inicio = new DeterminaInicioJogo();
    private LogicaComputador cpu = new LogicaComputador();
    private Random random = new Random();
    private Output saida = new Output();
    private Input entrada = new Input();
    private int vitoriasPlayer;
    private int vitoriasCPU;
    private int empate;
    private int contadorPassesJogadorPrincipal;
    
    
    private ListaDuplamenteEncadeada jogadorPrincipal = new ListaDuplamenteEncadeada();
	private ListaDuplamenteEncadeada jogadorcomputador = new ListaDuplamenteEncadeada();
	private ListaDuplamenteEncadeada mesaCompra = new ListaDuplamenteEncadeada();
	private ListaDuplamenteEncadeada mesaJogo = new ListaDuplamenteEncadeada();
	private ListaDuplamenteEncadeada pecas = new ListaDuplamenteEncadeada();
    
    public void inicio() {
		while(true) {
			saida.exibirMensagem("iniciar um novo jogo? S / N");
			String option = entrada.receberInputString();
			
			if (option.equalsIgnoreCase("s")) {	
				
				iniciarJogo(pecas, jogadorPrincipal, jogadorcomputador, mesaCompra, mesaJogo);
			}else {
				saida.exibirMensagem("fim de jogo");
			}
			
		}
		
	}
    

    private void distribuirPecas(ListaDuplamenteEncadeada pecas, ListaDuplamenteEncadeada jogadorPrincipal,
            ListaDuplamenteEncadeada jogadorComputador, ListaDuplamenteEncadeada mesaCompra) {
        // Adiciona todas as peças do jogo de dominó à lista pecas
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                pecas.adicionar(new Peca(i, j));
            }
        }

        // Distribui 7 peças para o jogadorPrincipal e jogadorComputador
        for (int i = 0; i < 7; i++) {
            int indiceAleatorio = random.nextInt(pecas.tamanho());
            pecas.transferirPecaPorIndice(indiceAleatorio, jogadorPrincipal);
            indiceAleatorio = random.nextInt(pecas.tamanho());
            pecas.transferirPecaPorIndice(indiceAleatorio, jogadorComputador);
        }
        
     // O restante das peças vai para a mesa de compra
        while (!pecas.estaVazia()) {
            pecas.transferirPecaPorIndice(0, mesaCompra);
        }
    }

    public void iniciarJogo(ListaDuplamenteEncadeada pecas, ListaDuplamenteEncadeada jogadorPrincipal,
            ListaDuplamenteEncadeada jogadorComputador, ListaDuplamenteEncadeada mesaCompra,
            ListaDuplamenteEncadeada mesaJogo) {
    	
        // Após adicionar todas as peças às listas, o jogo inicia
        distribuirPecas(pecas, jogadorPrincipal, jogadorComputador, mesaCompra);
        saida.exibirMensagem("Iniciando jogo");

        inicio.determinarJogadorInicial(jogadorPrincipal, jogadorComputador, mesaJogo);
        
        while (!jogadorPrincipal.estaVazia() && !jogadorComputador.estaVazia()) {
            jogadorPrincipal.imprimirTodasPecas();
            mesaJogo.imprimirTodasPecasMesa();

            saida.exibirMensagem("Escolha uma peça para inserir no jogo, (c) para comprar peça ou (p) para passar a vez");
            String entradaUsuario = entrada.receberInputString();

            processarEscolhaUsuario(entradaUsuario, jogadorPrincipal, jogadorComputador, mesaJogo, mesaCompra);
            
            if ( contadorPassesJogadorPrincipal >=2 && cpu.getContadorPassesJogadorCPU() >= 2) {
				break;
			}
        }
        
        verificarVencedor(pecas, jogadorPrincipal, jogadorComputador, mesaCompra, mesaJogo);
    }

    private void processarEscolhaUsuario(String entradaUsuario, ListaDuplamenteEncadeada jogadorPrincipal,
            ListaDuplamenteEncadeada jogadorComputador, ListaDuplamenteEncadeada mesaJogo,
            ListaDuplamenteEncadeada mesaCompra) {
        if (entradaUsuario.equalsIgnoreCase("c")) {
            comprarPeca(jogadorPrincipal, mesaCompra);
            cpu.logicaCpu(jogadorPrincipal, jogadorComputador, mesaJogo, mesaCompra);
        } else if (entradaUsuario.equalsIgnoreCase("p")) {
            passarVez(jogadorPrincipal, jogadorComputador, mesaJogo, mesaCompra);
        } else {
            try {
                int numero = Integer.parseInt(entradaUsuario);
                if (numero >= 0 && numero <= jogadorPrincipal.getUltimoIndice()) {
                    escolherPeca(numero, jogadorPrincipal, mesaJogo);
                    cpu.logicaCpu(jogadorPrincipal, jogadorComputador, mesaJogo, mesaCompra);
                } else {
                    saida.exibirMensagem("Opção inválida");
                }
            } catch (NumberFormatException e) {
                saida.exibirMensagem("Opção inválida");
            }
        }
    }


    private void comprarPeca(ListaDuplamenteEncadeada jogadorPrincipal, ListaDuplamenteEncadeada mesaCompra) {
        if (!mesaCompra.estaVazia()) {
            int indiceAleatorio = random.nextInt(mesaCompra.tamanho());
            mesaCompra.transferirPecaPorIndice(indiceAleatorio, jogadorPrincipal);
        } else {
            saida.exibirMensagem("Monte de compra vazio");
        }
    }

    private void passarVez(ListaDuplamenteEncadeada jogadorPrincipal, ListaDuplamenteEncadeada jogadorComputador,
            ListaDuplamenteEncadeada mesaJogo, ListaDuplamenteEncadeada mesaCompra) {
        contadorPassesJogadorPrincipal++;
        saida.exibirMensagem("Passou a vez");
        cpu.logicaCpu(jogadorPrincipal, jogadorComputador, mesaJogo, mesaCompra);
        
        if (contadorPassesJogadorPrincipal >= 2 && cpu.getContadorPassesJogadorCPU() >= 2) {
            // Encerra o jogo se ambos os jogadores passaram a vez duas vezes seguidas
            return;
        }
    }

    private void escolherPeca(int numero, ListaDuplamenteEncadeada jogadorPrincipal, ListaDuplamenteEncadeada mesaJogo) {
        saida.exibirMensagem("Inserir peça no início (1) ou final (2)");
        int posicao = Integer.parseInt(entrada.receberInputString());
        
        if (posicao == 1) {
            // Insere a peça no início da mesa de jogo
            jogadorPrincipal.transferirPecaParaInicio(numero, mesaJogo);
        } else if (posicao == 2) {
            // Insere a peça no final da mesa de jogo
            jogadorPrincipal.transferirPecaPorIndice(numero, mesaJogo);
        } else {
            saida.exibirMensagem("Opção inválida");
        }
    }

    private void verificarVencedor(ListaDuplamenteEncadeada pecas, ListaDuplamenteEncadeada jogadorPrincipal,
            ListaDuplamenteEncadeada jogadorComputador, ListaDuplamenteEncadeada mesaCompra,
            ListaDuplamenteEncadeada mesaJogo) {
        if (jogadorPrincipal.estaVazia()) {
            vitoriasPlayer++;
        } else if (mesaCompra.estaVazia() && contadorPassesJogadorPrincipal >= 2 && cpu.getContadorPassesJogadorCPU() >= 2) {
            // Verifica o vencedor com base na soma dos números das peças restantes
            Peca maiorPecaJogadorPrincipal = inicio.encontrarMaiorPeca(jogadorPrincipal);
            Peca maiorPecaJogadorCPU = inicio.encontrarMaiorPeca(jogadorComputador);
            
            int somaJogadorPrincipal = inicio.totalPeca(maiorPecaJogadorPrincipal);
            int somaJogadorCPU = inicio.totalPeca(maiorPecaJogadorCPU);
            
            if (somaJogadorPrincipal > somaJogadorCPU) {
                vitoriasPlayer++;
            } else if (somaJogadorCPU > somaJogadorPrincipal) {
                vitoriasCPU++;
            } else {
                empate++;
            }
        } else {
            vitoriasCPU++;
        }
        
        // Exibe estatísticas e reinicia o jogo
        exibirEstatisticas();
        
        saida.exibirMensagem("iniciar um novo jogo? S / N");
        String option = entrada.receberInputString();
        if (option.equalsIgnoreCase("s")) {	
			
        	reiniciarJogo(pecas, jogadorPrincipal, jogadorComputador, mesaCompra, mesaJogo);
		}else {
			saida.exibirMensagem("fim de jogo");
		}
        
}


    private void exibirEstatisticas() {
        saida.exibirMensagem("Jogador Principal venceu: " + vitoriasPlayer);
        saida.exibirMensagem("Computador venceu: " + vitoriasCPU);
        saida.exibirMensagem("Empate: " + empate);
    }

    private void reiniciarJogo(ListaDuplamenteEncadeada pecas, ListaDuplamenteEncadeada jogadorPrincipal,
            ListaDuplamenteEncadeada jogadorComputador, ListaDuplamenteEncadeada mesaCompra,
            ListaDuplamenteEncadeada mesaJogo) {
        // Limpa todas as listas e reinicia o jogo
        jogadorPrincipal.removerTodasPecas();
        jogadorComputador.removerTodasPecas();
        mesaCompra.removerTodasPecas();
        mesaJogo.removerTodasPecas();
        pecas.removerTodasPecas();
        
        contadorPassesJogadorPrincipal = 0;
        cpu.resetContadorPassesJogadorCPU();
        
        iniciarJogo(pecas, jogadorPrincipal, jogadorComputador, mesaCompra, mesaJogo);
    }
}
