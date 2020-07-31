package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();

	}

	private boolean testTorreCastling(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor()
				&& p.getContadorMovimento() == 0;
	}

	@Override
	public boolean[][] movimentoPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro()
				.getColunas()];

		Posicao p = new Posicao(0, 0);

		// Acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Abaixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Movimento especial Castling
		if (getContadorMovimento() == 0 && !partidaXadrez.getCheck()) {
			// Movimento especial castling torre do rei - roque pequeno
			Posicao posT1 = new Posicao(posicao.getLinha(),
					posicao.getColuna() + 3);
			if (testTorreCastling(posT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(),
						posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(),
						posicao.getColuna() + 2);
				if (getTabuleiro().peca(p1) == null
						&& getTabuleiro().peca(p2) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
		}
		if (getContadorMovimento() == 0 && !partidaXadrez.getCheck()) {
			// Movimento especial castling torre do rei - roque grande
			Posicao posT2 = new Posicao(posicao.getLinha(),
					posicao.getColuna() - 4);
			if (testTorreCastling(posT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(),
						posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(),
						posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(),
						posicao.getColuna() - 3);
				if (getTabuleiro().peca(p1) == null
						&& getTabuleiro().peca(p2) == null
						&& getTabuleiro().peca(p3) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}
		}

		return matriz;
	}

}
