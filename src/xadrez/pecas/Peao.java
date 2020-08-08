package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] movimentoPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro()
				.getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p)
					&& !getTabuleiro().haUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1,
					posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p)
					&& !getTabuleiro().haUmaPeca(p)
					&& getTabuleiro().posicaoExistente(p2)
					&& !getTabuleiro().haUmaPeca(p2)
					&& getContadorMovimento() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && existeUmaPecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && existeUmaPecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			// Movimento especial en passant Branco
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(),
						posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExistente(esquerda)
						&& existeUmaPecaOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez
								.getEnPassantVulnerável()) {
					matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(),
						posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExistente(direita)
						&& existeUmaPecaOponente(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez
								.getEnPassantVulnerável()) {
					matriz[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}

		} else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p)
					&& !getTabuleiro().haUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1,
					posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p)
					&& !getTabuleiro().haUmaPeca(p)
					&& getTabuleiro().posicaoExistente(p2)
					&& !getTabuleiro().haUmaPeca(p2)
					&& getContadorMovimento() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && existeUmaPecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && existeUmaPecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			// Movimento especial en passant Preto
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(),
						posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExistente(esquerda)
						&& existeUmaPecaOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez
								.getEnPassantVulnerável()) {
					matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(),
						posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExistente(direita)
						&& existeUmaPecaOponente(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez
								.getEnPassantVulnerável()) {
					matriz[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}

		return matriz;
	}

	@Override
	public String toString() {
		return "P";
	}

}
