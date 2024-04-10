package Services;

import java.util.Random;

import Models.ListaDuplamenteEncadeada;
import Models.Peca;
import Views.Output;

public class logicaJogo {
	private DeterminaInicioJogo inicio = new DeterminaInicioJogo();
	private LogicaComputador cpu = new LogicaComputador();
	private Random random = new Random();
	Output saida = new Output();
	Input Entrada = new Input();

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

			saida.exibirMensagem("Escolha uma peça pra inserir no jogo ou (c) para comprar peça");
			String indice = Entrada.receberInputString();
			try {
				int numero = Integer.parseInt(indice);
				if (numero >= 0 && numero <= jogadorPrincipal.getUltimoIndice()) {
					saida.exibirMensagem("inserir peça no incio (1) inserir peça no final (2)");
					int posicao = Entrada.receberInputInteiro();

					switch (posicao) {
					case 1:
						saida.exibirMensagem("Deseja girar a peça? (S/N)");
						String option = Entrada.receberInputString();
						if (option.equalsIgnoreCase("s")) {
							jogadorPrincipal.inverterNumerosPeca(numero);
							if (mesaJogo.estaVazia()
									|| jogadorPrincipal.getPecaPorIndice(numero).getNumero2() == mesaJogo
											.getPecaPorIndice(mesaJogo.getPrimeiroIndice()).getNumero1()) {
								jogadorPrincipal.transferirPecaParaInicio(numero, mesaJogo);
								cpu.logicaCpu(jogadorPrincipal, jogadorcomputador, mesaJogo, mesaCompra);
							} else {
								saida.exibirMensagem("A peça não pode ser encaixada na mesa.");
								jogadorPrincipal.inverterNumerosPeca(numero);
							}
						} else if (option.equalsIgnoreCase("n")) {
							// Verifica se a peça pode ser encaixada na mesa
							if (mesaJogo.estaVazia()
									|| jogadorPrincipal.getPecaPorIndice(numero).getNumero2() == mesaJogo
											.getPecaPorIndice(mesaJogo.getPrimeiroIndice()).getNumero1()) {
								jogadorPrincipal.transferirPecaParaInicio(numero, mesaJogo);
								cpu.logicaCpu(jogadorPrincipal, jogadorcomputador, mesaJogo, mesaCompra);
							} else {
								saida.exibirMensagem("A peça não pode ser encaixada na mesa.");
							}
						} else {
							saida.exibirMensagem("Opção incorreta.");
						}
						break;

					case 2:
						saida.exibirMensagem("deseja girar a peça S / N");
						option = Entrada.receberInputString();

						if (option.equalsIgnoreCase("s")) {
							jogadorPrincipal.inverterNumerosPeca(numero);
							if (mesaJogo.estaVazia()
									|| jogadorPrincipal.getPecaPorIndice(numero).getNumero1() == mesaJogo
											.getPecaPorIndice(mesaJogo.getUltimoIndice()).getNumero2()) {
								jogadorPrincipal.transferirPecaPorIndice(numero, mesaJogo);
								cpu.logicaCpu(jogadorPrincipal, jogadorcomputador, mesaJogo, mesaCompra);
							} else {
								saida.exibirMensagem("A peça não pode ser encaixada na mesa.");
								jogadorPrincipal.inverterNumerosPeca(numero);
							}
						} else if (option.equalsIgnoreCase("n")) {
							// Verifica se a peça pode ser encaixada na mesa
							if (mesaJogo.estaVazia()
									|| jogadorPrincipal.getPecaPorIndice(numero).getNumero1() == mesaJogo
											.getPecaPorIndice(mesaJogo.getUltimoIndice()).getNumero2()) {
								jogadorPrincipal.transferirPecaPorIndice(numero, mesaJogo);
								cpu.logicaCpu(jogadorPrincipal, jogadorcomputador, mesaJogo, mesaCompra);
							} else {
								saida.exibirMensagem("A peça não pode ser encaixada na mesa.");

							}
						} else {
							saida.exibirMensagem("valor incorreto");
						}
						break;

					default:
						saida.exibirMensagem("opcao invalida");
						break;
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

				} else {
					saida.exibirMensagem("opção incorreta");
				}

			}
		}
		verificarVencedor(pecas, jogadorPrincipal, jogadorcomputador, mesaCompra, mesaJogo);
	}

	// metodo para verificar o vencedor e encerrar o zerar as peças do jogo
	public void verificarVencedor(ListaDuplamenteEncadeada pecas, ListaDuplamenteEncadeada jogadorPrincipal,
			ListaDuplamenteEncadeada jogadorcomputador, ListaDuplamenteEncadeada mesaCompra,
			ListaDuplamenteEncadeada mesaJogo) {
		int vitoriasPlayer = 0;
		int vitoriasCPU = 0;
		if (jogadorPrincipal.estaVazia()) {
			vitoriasPlayer++;
			saida.exibirMensagem("Jogador Principal");
			saida.exibirMensagem("");
			saida.exibirMensagem("Jogador Principal Venceu : " + vitoriasPlayer);
			saida.exibirMensagem("computador venceu : " + vitoriasCPU);
			jogadorcomputador.imprimirTodasPecas();

		} else {
			vitoriasCPU++;
			saida.exibirMensagem("Jogador Principal");
			saida.exibirMensagem("");
			saida.exibirMensagem("Jogador Principal Venceu : " + vitoriasPlayer);
			saida.exibirMensagem("computador venceu : " + vitoriasCPU);
			jogadorPrincipal.imprimirTodasPecas();
		}
		jogadorPrincipal.removerTodasPecas();
		jogadorcomputador.removerTodasPecas();
		mesaCompra.removerTodasPecas();
		mesaJogo.removerTodasPecas();
		pecas.removerTodasPecas();
	}
}