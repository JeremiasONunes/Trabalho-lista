
public class backup {
	/*
	 * package Services;

	import java.util.Random;

	import Models.ListaDuplamenteEncadeada;
	import Models.Peca;

	public class logicaJogo {
		private DeterminaInicioJogo inicio = new DeterminaInicioJogo();
		private LogicaComputador cpu = new LogicaComputador();
		private Random random = new Random();
		Output saida = new Output();
		Input Entrada = new Input();
		private int vitoriasPlayer;
		private int vitoriasCPU;
		private int empate;
		private int contadorPassesJogadorPrincipal;
		public void distribuirPecas(ListaDuplamenteEncadeada pecas, ListaDuplamenteEncadeada jogadorPrincipal,
				ListaDuplamenteEncadeada jogadorcomputador, ListaDuplamenteEncadeada mesaCompra) {
			Random random = new Random();

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
				pecas.transferirPecaPorIndice(indiceAleatorio, jogadorcomputador);
			}
			
			// O restante das peças vai para a mesa
					while (!pecas.estaVazia()) {
						pecas.transferirPecaPorIndice(0, mesaCompra);
					}
			
			
		}

		public void iniciarJogo(ListaDuplamenteEncadeada pecas, ListaDuplamenteEncadeada jogadorPrincipal,
				ListaDuplamenteEncadeada jogadorcomputador, ListaDuplamenteEncadeada mesaCompra,
				ListaDuplamenteEncadeada mesaJogo) {
			// após adicionar todas as peças as listas o jogo inicia
			distribuirPecas(pecas, jogadorPrincipal, jogadorcomputador, mesaCompra);
			saida.exibirMensagem("iniciando jogo");

			inicio.determinarJogadorInicial(jogadorPrincipal, jogadorcomputador, mesaJogo);
			while (!jogadorPrincipal.estaVazia() && !jogadorcomputador.estaVazia()) {
				jogadorPrincipal.imprimirTodasPecas();
				mesaJogo.imprimirTodasPecasMesa();

				saida.exibirMensagem("Escolha uma peça pra inserir no jogo ou (c) para comprar peça ou (p) para passar a vez");
				String indice = Entrada.receberInputString();
				try {
					int numero = Integer.parseInt(indice);
					if (numero >= 0 && numero <= jogadorPrincipal.getUltimoIndice()) {
						saida.exibirMensagem("inserir peça no incio (1) inserir peça no final (2)");
						String posicao = Entrada.receberInputString();
						
						try {
							int numero1 = Integer.parseInt(posicao);
							switch (numero1) {
							case 1:
								// Verifica se a peça pode ser encaixada diretamente na mesa
								if (mesaJogo.estaVazia() ||
								    jogadorPrincipal.getPecaPorIndice(numero).getNumero2() == mesaJogo.getPecaPorIndice(mesaJogo.getPrimeiroIndice()).getNumero1()) {
									contadorPassesJogadorPrincipal = 0;
									jogadorPrincipal.transferirPecaParaInicio(numero, mesaJogo);
								    cpu.logicaCpu(jogadorPrincipal, jogadorcomputador, mesaJogo, mesaCompra);
								    
								} else {
								    // Se não puder ser encaixada diretamente, gira a peça
								    jogadorPrincipal.inverterNumerosPeca(numero);
		
								    // Verifica se a peça pode ser encaixada após girar
								    if (mesaJogo.estaVazia() ||
								        jogadorPrincipal.getPecaPorIndice(numero).getNumero2() == mesaJogo.getPecaPorIndice(mesaJogo.getPrimeiroIndice()).getNumero1()) {
								    	contadorPassesJogadorPrincipal = 0;
								    	jogadorPrincipal.transferirPecaParaInicio(numero, mesaJogo);
								        cpu.logicaCpu(jogadorPrincipal, jogadorcomputador, mesaJogo, mesaCompra);
								        contadorPassesJogadorPrincipal = 0;
								    } else {
								        saida.exibirMensagem("A peça não pode ser encaixada na mesa.");
								        // Se ainda assim não puder ser encaixada, imprime a mensagem e mantém a peça como está
								    }
								}
		
								break;
		
							case 2:
								// Verifica se a peça pode ser encaixada diretamente na mesa
								if (mesaJogo.estaVazia() ||
								        jogadorPrincipal.getPecaPorIndice(numero).getNumero1() == mesaJogo.getPecaPorIndice(mesaJogo.getUltimoIndice()).getNumero2()) {
									contadorPassesJogadorPrincipal = 0;
									jogadorPrincipal.transferirPecaPorIndice(numero, mesaJogo);
								    cpu.logicaCpu(jogadorPrincipal, jogadorcomputador, mesaJogo, mesaCompra);
								  
								} else {
								    // Se não puder ser encaixada diretamente, gira a peça
								    jogadorPrincipal.inverterNumerosPeca(numero);
		
								    // Verifica se a peça pode ser encaixada após girar
								    if (mesaJogo.estaVazia() ||
								            jogadorPrincipal.getPecaPorIndice(numero).getNumero1() == mesaJogo.getPecaPorIndice(mesaJogo.getUltimoIndice()).getNumero2()) {
								    	contadorPassesJogadorPrincipal = 0;
								    	jogadorPrincipal.transferirPecaPorIndice(numero, mesaJogo);
								        cpu.logicaCpu(jogadorPrincipal, jogadorcomputador, mesaJogo, mesaCompra);
								        
								    } else {
								        saida.exibirMensagem("A peça não pode ser encaixada na mesa.");
								        // Se ainda assim não puder ser encaixada, imprime a mensagem e mantém a peça como está
								    }
								}
		
								break;
		
							default:
								saida.exibirMensagem("opcao invalida");
								break;
							}
						
						}catch (NumberFormatException e) {
							saida.exibirMensagem("opção invalida");
						}

					}
						

				} catch (NumberFormatException e) {
					if (indice.equalsIgnoreCase("c")) {
						if (!mesaCompra.estaVazia()) {
							int indiceAleatorio = random.nextInt(mesaCompra.tamanho());
							mesaCompra.transferirPecaPorIndice(indiceAleatorio, jogadorPrincipal);
							cpu.logicaCpu(jogadorPrincipal, jogadorcomputador, mesaJogo, mesaCompra);
						} else {
							saida.exibirMensagem("Monte vazio");
							
						}

					} else if (indice.equalsIgnoreCase("p") && mesaCompra.estaVazia()){
						contadorPassesJogadorPrincipal++;
						saida.exibirMensagem("passou a vez");
						cpu.logicaCpu(jogadorPrincipal, jogadorcomputador, mesaJogo, mesaCompra);
						
						
						System.out.println(contadorPassesJogadorPrincipal);
						System.out.println(cpu.getContadorPassesJogadorCPU());
						if ( contadorPassesJogadorPrincipal >=2 && cpu.getContadorPassesJogadorCPU() >= 2) {
							break;
						}
						
					}else {
						saida.exibirMensagem("opção invalida");
					}

				}
			}
			verificarVencedor(pecas, jogadorPrincipal, jogadorcomputador, mesaCompra, mesaJogo);
		}

		// metodo para verificar o vencedor e encerrar o zerar as peças do jogo
		public void verificarVencedor(ListaDuplamenteEncadeada pecas, ListaDuplamenteEncadeada jogadorPrincipal,
				ListaDuplamenteEncadeada jogadorcomputador, ListaDuplamenteEncadeada mesaCompra,
				ListaDuplamenteEncadeada mesaJogo) {
			
			if (jogadorPrincipal.estaVazia()) {
				vitoriasPlayer++;
				saida.exibirMensagem("Jogador Principal");
				saida.exibirMensagem("");
				saida.exibirMensagem("Jogador Principal Venceu : " + vitoriasPlayer);
				saida.exibirMensagem("computador venceu : " + vitoriasCPU);
				saida.exibirMensagem("empate: " + empate);
				jogadorcomputador.imprimirTodasPecas();

			} else if (mesaCompra.estaVazia() && contadorPassesJogadorPrincipal>=2 && cpu.getContadorPassesJogadorCPU() >= 2) {
			    Peca pecaJogadorPrincipal = inicio.encontrarMaiorPeca(jogadorPrincipal);
			    Peca pecaJogadorCPU = inicio.encontrarMaiorPeca(jogadorcomputador);	
			    
			    int somaJogadorPrincipal = inicio.totalPeca(pecaJogadorPrincipal );
			    int somaJogadorCPU = inicio.totalPeca(pecaJogadorCPU);
			    if (somaJogadorPrincipal > somaJogadorCPU) {
			        vitoriasPlayer++;
			        saida.exibirMensagem("Jogador Principal");
					saida.exibirMensagem("");
					saida.exibirMensagem("Jogador Principal Venceu : " + vitoriasPlayer);
					saida.exibirMensagem("computador venceu : " + vitoriasCPU);
					saida.exibirMensagem("empate: " + empate);
			    } else if (somaJogadorCPU > somaJogadorPrincipal) {
			        vitoriasCPU++;
			        saida.exibirMensagem("Jogador Principal");
					saida.exibirMensagem("");
					saida.exibirMensagem("Jogador Principal Venceu : " + vitoriasPlayer);
					saida.exibirMensagem("computador venceu : " + vitoriasCPU);
					saida.exibirMensagem("empate: " + empate);

			    } else {
			    	empate++;
			    	saida.exibirMensagem("Jogador Principal");
					saida.exibirMensagem("");
					saida.exibirMensagem("Jogador Principal Venceu : " + vitoriasPlayer);
					saida.exibirMensagem("computador venceu : " + vitoriasCPU);
					saida.exibirMensagem("empate: " + empate);
			    }
			}
	else {
				vitoriasCPU++;
				saida.exibirMensagem("Jogador Principal");
				saida.exibirMensagem("");
				saida.exibirMensagem("Jogador Principal Venceu : " + vitoriasPlayer);
				saida.exibirMensagem("computador venceu : " + vitoriasCPU);
				saida.exibirMensagem("empate: " + empate);
				jogadorPrincipal.imprimirTodasPecas();
			}
			jogadorPrincipal.removerTodasPecas();
			jogadorcomputador.removerTodasPecas();
			mesaCompra.removerTodasPecas();
			mesaJogo.removerTodasPecas();
			pecas.removerTodasPecas();
		}
		
		
	}
	 */
}
