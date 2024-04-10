package Services;

import Models.ListaDuplamenteEncadeada;
import Models.Peca;
import Views.Output;

public class DeterminaInicioJogo {

	private Output saida = new Output();

	public void determinarJogadorInicial(ListaDuplamenteEncadeada jogadorPrincipal,
			ListaDuplamenteEncadeada jogadorcomputador, ListaDuplamenteEncadeada mesaJogo) {
		Peca maiorPecaJogador = encontrarMaiorPeca(jogadorPrincipal);
		Peca maiorPecaComputador = encontrarMaiorPeca(jogadorcomputador);

		boolean jogadorPossuiCarretao = ehCarretao(maiorPecaJogador);
		boolean computadorPossuiCarretao = ehCarretao(maiorPecaComputador);

		if (jogadorPossuiCarretao && computadorPossuiCarretao) {
// Ambos têm carretão, compara os valores dos carretões
			int totalJogador = totalPeca(maiorPecaJogador);
			int totalComputador = totalPeca(maiorPecaComputador);
			if (totalJogador > totalComputador) {
				saida.exibirMensagem("Você possui um carretão maior. Você inicia o jogo!");
			} else if (totalComputador > totalJogador) {
				saida.exibirMensagem("O computador possui um carretão maior. O computador inicia o jogo!");
				jogadorcomputador.transferirPecaPorIndice(jogadorcomputador.indicePeca(maiorPecaComputador), mesaJogo);
				mesaJogo.imprimirTodasPecas();
			} else {
				saida.exibirMensagem("Ambos têm carretões de igual valor. Sorteio para determinar quem inicia...");
// quem inicia é o player humano
			}
		} else if (jogadorPossuiCarretao) {
			saida.exibirMensagem("Você possui um carretão. Você inicia o jogo!");
		} else if (computadorPossuiCarretao) {
			saida.exibirMensagem("O computador possui um carretão. O computador inicia o jogo!");
			jogadorcomputador.transferirPecaPorIndice(jogadorcomputador.indicePeca(maiorPecaComputador), mesaJogo);
			mesaJogo.imprimirTodasPecas();
		} else if (maiorPecaJogador != null && maiorPecaComputador != null) {
// Não há carretões, compara as peças maiores
			int totalJogador = totalPeca(maiorPecaJogador);
			int totalComputador = totalPeca(maiorPecaComputador);
			if (totalJogador > totalComputador) {
				saida.exibirMensagem("Você possui a maior peça. Você inicia o jogo!");
			} else if (totalComputador > totalJogador) {
				saida.exibirMensagem("O computador possui a maior peça. O computador inicia o jogo!");
				jogadorcomputador.transferirPecaPorIndice(jogadorcomputador.indicePeca(maiorPecaComputador), mesaJogo);
				mesaJogo.imprimirTodasPecas();
			} else {
				saida.exibirMensagem(
						"Ambos os jogadores têm peças de igual valor. Sorteio para determinar quem inicia...");
// quem inicia é o player humano
			}
		} else {
// Ambos os jogadores não têm peças
			saida.exibirMensagem("Ambos os jogadores não têm peças. O jogo termina em empate.");
		}
	}

	private Peca encontrarMaiorPeca(ListaDuplamenteEncadeada jogador) {
		if (jogador.estaVazia()) {
			return null; // Retorna null se a lista estiver vazia
		}

		Peca maiorPeca = jogador.getPecaPorIndice(0);

		for (int i = 1; i <= jogador.getUltimoIndice(); i++) {
			Peca pecaAtual = jogador.getPecaPorIndice(i);
			if (pecaAtual != null && totalPeca(pecaAtual) > totalPeca(maiorPeca)) {
				maiorPeca = pecaAtual;
			}
		}

		return maiorPeca;
	}

	private boolean ehCarretao(Peca peca) {
		return peca.getNumero1() == peca.getNumero2();
	}

	private int totalPeca(Peca peca) {
		return peca.getNumero1() + peca.getNumero2();
	}
}
