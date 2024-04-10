package Models;





import Services.Input;
import Services.logicaJogo;
import Views.Output;


public class Jogo {
	private ListaDuplamenteEncadeada jogadorPrincipal = new ListaDuplamenteEncadeada();
	private ListaDuplamenteEncadeada jogadorcomputador = new ListaDuplamenteEncadeada();
	private ListaDuplamenteEncadeada mesaCompra = new ListaDuplamenteEncadeada();
	private ListaDuplamenteEncadeada mesaJogo = new ListaDuplamenteEncadeada();
	private ListaDuplamenteEncadeada pecas = new ListaDuplamenteEncadeada();
	private logicaJogo jogo = new logicaJogo();
	private Output saida = new Output();
	private Input entrada = new Input();
	public void inicio() {
		while(true) {
			saida.exibirMensagem("iniciar um novo jogo? S / N");
			String option = entrada.receberInputString();
			
			if (option.equalsIgnoreCase("s")) {	
				
				jogo.iniciarJogo(pecas, jogadorPrincipal, jogadorcomputador, mesaCompra, mesaJogo);
			}else {
				saida.exibirMensagem("fim de jogo");
			}
			
		}
		
	}
	
	

		
}
