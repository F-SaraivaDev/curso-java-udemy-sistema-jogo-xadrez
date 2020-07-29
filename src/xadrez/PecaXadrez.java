package xadrez;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca{
	
	private Cor cor;
	private int contadorMovimento;
	
	public PecaXadrez(Tabuleiro tabuleiro, Cor cor){
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

	public int getContadorMovimento() {
		return contadorMovimento;
	}

	public void aumentarContagemMovemimentos(){
		contadorMovimento++;
	}
	
	public void diminuirContagemMovemimentos(){
		contadorMovimento--;
	}
	
	public PosicaoXadrez getPosicaoXadrez(){
		return PosicaoXadrez.dePosicao(posicao);
	}
	
	protected boolean existeUmaPecaOponente(Posicao posicao){
		PecaXadrez px = (PecaXadrez)getTabuleiro().peca(posicao);
		return px != null && px.getCor() != cor;
	}
}
