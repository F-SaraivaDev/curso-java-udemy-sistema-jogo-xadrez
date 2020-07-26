package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicao posicao){
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	
	}

	@Override
	public boolean[][] movimentoPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//Acima
		p.setValores(posicao.getLinha() -1, posicao.getColuna());
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Abaixo
		p.setValores(posicao.getLinha() +1, posicao.getColuna());
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() -1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Direita
		p.setValores(posicao.getLinha(), posicao.getColuna() +1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Noroeste
		p.setValores(posicao.getLinha() -1, posicao.getColuna() -1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Nordeste
		p.setValores(posicao.getLinha() -1, posicao.getColuna() +1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Sudoeste
		p.setValores(posicao.getLinha() +1, posicao.getColuna() -1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Sudeste
		p.setValores(posicao.getLinha() +1, posicao.getColuna() +1);
		if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		return matriz;
	}

}
