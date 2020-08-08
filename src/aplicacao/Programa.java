package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturada = new ArrayList<PecaXadrez>();

		while (!partidaXadrez.getCheckMate()) {
			try {
				UI.limpaTela();
				UI.imprimiPartida(partidaXadrez, capturada);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.limpaTela();
				UI.imprimiTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

				PecaXadrez pecaCapturada = partidaXadrez
						.movimentoPerformeceXadrez(origem, destino);
				
				if(pecaCapturada != null){
				   capturada.add(pecaCapturada);
				}
				
				if(partidaXadrez.getpromovida() != null){
					System.out.print("Insira a peça para promoção (B/C/T/R)");
					String tipo = sc.nextLine();
					partidaXadrez.substituirPecaPromovida(tipo);
				}

			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
				
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}

		}
		UI.limpaTela();
		UI.imprimiPartida(partidaXadrez, capturada);
	}
}
