package Services;

import java.util.Random;

import Models.ListaDuplamenteEncadeada;
import Models.Peca;
import Views.Output;

public class LogicaComputador {
	
	private Output saida = new Output();
	public void logicaCpu(ListaDuplamenteEncadeada jogadorPrincipal,ListaDuplamenteEncadeada jogadorcomputador, ListaDuplamenteEncadeada mesaJogo,ListaDuplamenteEncadeada mesaCompra) {
		
		// Percorre todas as peças do computador
		for (int i = 0; i <= jogadorcomputador.getUltimoIndice(); i++) {
			Peca pecaComputador = jogadorcomputador.getPecaPorIndice(i);

			// Verifica se a peça pode ser encaixada no início da mesa
			if (mesaJogo.estaVazia() || pecaComputador.getNumero2() == mesaJogo.getPecaPorIndice(0).getNumero1()) {
				// Se a peça corresponder ao número da primeira peça na mesa, jogue-a no início
				// da mesa
				jogadorcomputador.transferirPecaParaInicio(i, mesaJogo);
				saida.exibirMensagem("O computador jogou uma peça no início da mesa.");
				return; // Sai do método após jogar uma peça
			}

			// Verifica se a peça pode ser encaixada no final da mesa
			if (mesaJogo.estaVazia() || pecaComputador.getNumero1() == mesaJogo
					.getPecaPorIndice(mesaJogo.getUltimoIndice()).getNumero2()) {
				// Se a peça corresponder ao número da última peça na mesa, jogue-a no final da
				// mesa
				jogadorcomputador.transferirPecaPorIndice(i, mesaJogo);
				saida.exibirMensagem("O computador jogou uma peça no final da mesa.");
				return; // Sai do método após jogar uma peça
			}

			// Se a peça não encaixa em nenhum lado, gire-a e verifique novamente
			pecaComputador.girar();
			if (mesaJogo.estaVazia() || pecaComputador.getNumero2() == mesaJogo.getPecaPorIndice(0).getNumero1()) {
				jogadorcomputador.transferirPecaParaInicio(i, mesaJogo);
				saida.exibirMensagem("O computador jogou uma peça no início da mesa após girá-la.");
				return; // Sai do método após jogar uma peça
			}

			if (mesaJogo.estaVazia() || pecaComputador.getNumero1() == mesaJogo
					.getPecaPorIndice(mesaJogo.getUltimoIndice()).getNumero2()) {
				jogadorcomputador.transferirPecaPorIndice(i, mesaJogo);
				saida.exibirMensagem("O computador jogou uma peça no final da mesa após girá-la.");
				return; // Sai do método após jogar uma peça
			}
		}

		// Se nenhuma peça puder ser jogada, o computador compra uma peça
		if (!mesaCompra.estaVazia()) {
			int indiceAleatorio = new Random().nextInt(mesaCompra.tamanho());
			mesaCompra.transferirPecaPorIndice(indiceAleatorio, jogadorcomputador);
			saida.exibirMensagem("O computador comprou uma peça.");
			return;
		} else {
			saida.exibirMensagem("O computador passou a vez.");
			return;
		}
	}
}
