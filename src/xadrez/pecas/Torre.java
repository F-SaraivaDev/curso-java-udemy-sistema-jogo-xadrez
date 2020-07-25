package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] movimentoPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()]; 
		
		Posicao p = new Posicao(0, 0);
		
		//Acima
		p.setValores(posicao.getLinha() -1, posicao.getColuna());
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() -1);
		}
		if(getTabuleiro().posicaoExistente(p) && existeUmaPecaOponente(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() -1);
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() -1);
		}
		if(getTabuleiro().posicaoExistente(p) && existeUmaPecaOponente(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//direita
		p.setValores(posicao.getLinha(), posicao.getColuna() +1);
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() +1);
		}
		if(getTabuleiro().posicaoExistente(p) && existeUmaPecaOponente(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Baixo
		p.setValores(posicao.getLinha() +1, posicao.getColuna());
		while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().haUmaPeca(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() +1);
		}
		if(getTabuleiro().posicaoExistente(p) && existeUmaPecaOponente(p)){
			matriz[p.getLinha()][p.getColuna()] = true;
		}
			
		return matriz;
	}

}
