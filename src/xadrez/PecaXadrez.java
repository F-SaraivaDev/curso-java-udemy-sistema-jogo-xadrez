package xadrez;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca{
	
	private Cor cor;
	
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor){
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	protected boolean existeUmaPecaOponente(Posicao posicao){
		PecaXadrez px = (PecaXadrez)getTabuleiro().peca(posicao);
		return px != null && px.getCor() != cor;
	}
}
