package Models;

import Views.Output;

public class ListaDuplamenteEncadeada {
	No inicio;
	No fim;
	public Output saida = new Output();
	public ListaDuplamenteEncadeada() {
		inicio = null;
		fim = null;
	}

	public void adicionar(Peca peca) {
		No novoNo = new No(peca);
		if (inicio == null) {
			inicio = novoNo;
			fim = novoNo;
		} else {
			fim.proximo = novoNo;
			novoNo.anterior = fim;
			fim = novoNo;
		}
	}
	public void removerTodasPecas() {
	    // Define o início como nulo, o que limpa toda a lista
	    inicio = null;
	    fim = null;
	}
	// Método sobrecarregado para imprimir uma peça por índice
	public void imprimirPorIndice(int indice) {
		if (indice < 0) {
			saida.exibirMensagem("Índice inválido.");
			
			return;
		}

		No atual = inicio;
		int contador = 0;
		while (atual != null) {
			if (contador == indice) {
				saida.exibirMensagem("[" + atual.peca.getNumero1() + "|" + atual.peca.getNumero2() + "]");
				return;
			}
			atual = atual.proximo;
			contador++;
		}
		saida.exibirMensagem("Índice fora do intervalo da lista.");
	}

	// Método sobrecarregado para remover uma peça por índice
	public void removerPorIndice(int indice) {
		if (indice < 0) {
			saida.exibirMensagem("Índice inválido.");
			return;
		}

		No atual = inicio;
		int contador = 0;
		while (atual != null) {
			if (contador == indice) {
				// Se encontrou a peça pelo índice, remove da lista atual
				if (atual.anterior != null) {
					atual.anterior.proximo = atual.proximo;
				} else {
					inicio = atual.proximo;
				}
				if (atual.proximo != null) {
					atual.proximo.anterior = atual.anterior;
				} else {
					fim = atual.anterior;
				}
				return;
			}
			atual = atual.proximo;
			contador++;
		}
		saida.exibirMensagem("Índice fora do intervalo da lista.");
	}

	// Método atualizado para imprimir todas as peças com seus índices
	public void imprimirTodasPecas() {
		No atual = inicio;
		int indice = 0; // Inicializa o contador de índice
		saida.exibirMensagem("-------------Peças-------------");
		while (atual != null) {
			saida.exibirMensagem(indice + ": [" + atual.peca.getNumero1() + "|" + atual.peca.getNumero2() + "]");
			atual = atual.proximo;
			indice++; // Incrementa o índice a cada peça impressa
		}
		System.out.println();
	}

	public void imprimirTodasPecasMesa() {
		No atual = inicio;
		saida.exibirMensagem("-------------Peças mesa-------------\n");
		while (atual != null) {
			System.out.print("[" + atual.peca.getNumero1() + "|" + atual.peca.getNumero2() + "] ");
			atual = atual.proximo;
		}
		System.out.print("\n\n");
	}

	public Peca transferirPecaPorIndice(int indice, ListaDuplamenteEncadeada listaDestino) {
	    No atual = inicio;
	    int contador = 0;
	    
	    while (atual != null) {
	        if (contador == indice) {
	            // Remove a peça da lista atual
	            if (atual.anterior != null) {
	                atual.anterior.proximo = atual.proximo;
	            } else {
	                inicio = atual.proximo;
	            }
	            if (atual.proximo != null) {
	                atual.proximo.anterior = atual.anterior;
	            } else {
	                fim = atual.anterior;
	            }

	            Peca pecaTransferida = atual.peca;

	            // Adiciona a peça na lista de destino
	            listaDestino.adicionar(pecaTransferida);

	            // Retorna a peça transferida
	            return pecaTransferida;
	        }
	        atual = atual.proximo;
	        contador++;
	    }
	    return null; // Retorna null se o índice for inválido
	}


	public Peca transferirPecaParaInicio(int indice, ListaDuplamenteEncadeada listaDestino) {
		No atual = inicio;
		int contador = 0;
		while (atual != null) {
			if (contador == indice) {
				// Cria uma nova instância do nó para representar a peça a ser transferida
				No noTransferido = new No(atual.peca);

				// Adiciona a peça na lista de destino no início
				listaDestino.inserirNoInicio(noTransferido.peca);

				// Remove o nó correspondente da lista original
				if (atual.anterior != null) {
					atual.anterior.proximo = atual.proximo;
				} else {
					inicio = atual.proximo;
				}
				if (atual.proximo != null) {
					atual.proximo.anterior = atual.anterior;
				} else {
					fim = atual.anterior;
				}

				// Retorna a peça transferida
				return noTransferido.peca;
			}
			atual = atual.proximo;
			contador++;
		}
		return null; // Retorna null se o índice for inválido
	}

	public Peca getPecaPorIndice(int indice) {
		No atual = inicio;
		int contador = 0;
		while (atual != null) {
			if (contador == indice) {
				return atual.peca; // Retorna a peça do índice especificado
			}
			atual = atual.proximo;
			contador++;
		}
		return null; // Retorna null se o índice for inválido
	}

	public int getUltimoIndice() {
		// Verifica se a lista está vazia
		if (estaVazia()) {
			return -1; // Retorna -1 se a lista estiver vazia
		}

		No atual = inicio;
		int ultimoIndice = 0; // Inicializa o contador do último índice como 0

		// Percorre a lista até o último nó
		while (atual.proximo != null) {
			atual = atual.proximo;
			ultimoIndice++; // Incrementa o contador do último índice
		}

		return ultimoIndice; // Retorna o último índice
	}

	public int getPrimeiroIndice() {
		// Verifica se a lista está vazia
		if (estaVazia()) {
			return -1; // Retorna -1 se a lista estiver vazia
		}
		int primeiroIndice = 0; // define o primeiro indice
		return primeiroIndice;

	}

	public void inverterNumerosPeca(int indice) {
		if (indice < 0) {
			saida.exibirMensagem("Índice inválido.");
			return;
		}

		No atual = inicio;
		int contador = 0;
		while (atual != null) {
			if (contador == indice) {
				int temp = atual.peca.getNumero1();
				atual.peca.setNumero1(atual.peca.getNumero2());
				atual.peca.setNumero2(temp);
				return;
			}
			atual = atual.proximo;
			contador++;
		}
		saida.exibirMensagem("Índice fora do intervalo da lista.");
	}

	// Método para verificar se a lista está vazia
	public boolean estaVazia() {
		return inicio == null;
	}

	// Método para retornar o tamanho da lista
	public int tamanho() {
		int contador = 0;
		No atual = inicio;
		while (atual != null) {
			contador++;
			atual = atual.proximo;
		}
		return contador;
	}

	public void inserirNoInicio(Peca peca) {
		// Criar um novo nó com a peça fornecida
		No novoNo = new No(peca);

		// Se a lista estiver vazia, o novo nó será tanto o início quanto o fim da lista
		if (estaVazia()) {
			inicio = novoNo;
			fim = novoNo;
		} else {
			// Ajustar as referências do novo nó e do início da lista
			novoNo.proximo = inicio;
			inicio.anterior = novoNo;
			inicio = novoNo;
		}
	}

	public Peca buscarPecaPorNumero(int numero1, int numero2) {
		No atual = inicio;
		while (atual != null) {
			Peca peca = atual.peca;
			if ((peca.getNumero1() == numero1 && peca.getNumero2() == numero2)
					|| (peca.getNumero1() == numero2 && peca.getNumero2() == numero1)) {
				// Retorna a peça se ambos os números forem encontrados na peça
				return peca;
			}
			atual = atual.proximo;
		}
		// Retorna null se a peça com os números fornecidos não for encontrada na lista
		return null;
	}

	public int indicePeca(Peca peca) {
		No atual = inicio;
		int indice = 0;
		while (atual != null) {
			if (atual.peca.equals(peca)) {
				return indice;
			}
			atual = atual.proximo;
			indice++;
		}
		// Se a peça não for encontrada, retorne -1
		return -1;
	}
	
	
	public boolean contemPeca(Peca peca) {
	    No atual = inicio;
	    while (atual != null) {
	        if (atual.peca.equals(peca)) {
	            return true; // Retorna verdadeiro se a peça for encontrada na lista
	        }
	        atual = atual.proximo;
	    }
	    return false; // Retorna falso se a peça não for encontrada na lista
	}
}
