package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class UI {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void limpaTela() {   
	    System.out.print("\033[H\033[2J");   
	    System.out.flush();   
	}   
	
	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc){
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
			
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo posicao de xadrez. Valores validos sao de a1 a h8.");
		}
	}
	
	public static void imprimiPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturada){
		imprimiTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		imprimiPecaCapturada(capturada);
		System.out.println();
		System.out.println("Turno: " + partidaXadrez.getTurno());
		if(!partidaXadrez.getCheckMate()){
			System.out.println("Esperando jogador: " + partidaXadrez.getJogadorAtual());
			if(partidaXadrez.getCheck()){
				System.out.println("CHECK!");
			}
		}else{
			System.out.println("CHECKMATE!");
			System.out.println("Vencedor: " + partidaXadrez.getJogadorAtual());
		}	
	}

	public static void imprimiTabuleiro(PecaXadrez[][] pecas) {
		for(int i = 0; i < pecas.length; i++){
			System.out.print((8 - i) + " ");
			for(int j = 0; j < pecas.length; j++){
				imprimiPeca(pecas[i][j], false);
			}
			System.out.println();
		}
        System.out.println("  a b c d e f g h");
	}
	
	public static void imprimiTabuleiro(PecaXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		for(int i = 0; i < pecas.length; i++){
			System.out.print((8 - i) + " ");
			for(int j = 0; j < pecas.length; j++){
				imprimiPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void imprimiPeca(PecaXadrez peca, boolean background) {
		if(background){
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
    	if (peca == null) {
            System.out.print("-" +  ANSI_RESET);
        }
        else {
            if (peca.getCor() == Cor.BRANCO) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	private static void imprimiPecaCapturada(List<PecaXadrez> capturada){
		List<PecaXadrez> branca = capturada.stream()
				                           .filter(x -> x.getCor() == Cor.BRANCO)
				                           .collect(Collectors.toList());
		List<PecaXadrez> preto = capturada.stream()
										   .filter(x -> x.getCor() == Cor.PRETO)
	                           			   .collect(Collectors.toList());
		
		System.out.println("Pecas capturadas: ");
		System.out.print("Branca:");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(branca.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Preta :");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preto.toArray()));
		System.out.print(ANSI_RESET);
	}

}










