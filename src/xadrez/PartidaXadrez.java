package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez promovida;
	private PecaXadrez enPassantVulnerável;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		check = false;
		inicioPartida();
	}

	public PecaXadrez getpromovida() {
		return promovida;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaXadrez getEnPassantVulnerável() {
		return enPassantVulnerável;
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro
				.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}

		}
		return matriz;
	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentoPossiveis();
	}

	public PecaXadrez movimentoPerformeceXadrez(PosicaoXadrez posicaoOrigem,
			PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);

		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("Voce nao pode se colocar em check!");
		}

		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);

		// Movimento especial Promoção
		promovida = null;
		if (pecaMovida instanceof Peao) {
			if ((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0)
					|| (pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
				promovida = (PecaXadrez) tabuleiro.peca(destino);
				promovida = substituirPecaPromovida("R");
			}
		}

		check = (testarCheck(oponente(jogadorAtual))) ? true : false;

		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;

		} else {
			proximoTurno();
		}

		// Movimento especial en passant
		if (pecaMovida instanceof Peao
				&& (destino.getLinha() == origem.getLinha() - 2 || destino
						.getLinha() == origem.getLinha() + 2)) {
			enPassantVulnerável = pecaMovida;

		} else {
			enPassantVulnerável = null;
		}

		return (PecaXadrez) pecaCapturada;
	}

	public PecaXadrez substituirPecaPromovida(String tipo){
		if(promovida == null){
			throw new IllegalStateException("Nao ha peca para ser promovida!");
		}
		if(!tipo.equals("B") && !tipo.equals("C") 
				&& !tipo.equals("T") && !tipo.equals("R")){
			return promovida;
		}
		
		Posicao pos = promovida.getPosicaoXadrez().paraPosicao();
		Peca p = tabuleiro.removePeca(pos); 
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(tipo, promovida.getCor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
		
	}
	
	private PecaXadrez novaPeca(String tipo, Cor cor){
		if(tipo.equals("B")) return new Bispo(tabuleiro,cor);
		if(tipo.equals("C")) return new Cavalo(tabuleiro,cor);
		if(tipo.equals("T")) return new Torre(tabuleiro,cor);
		return new Rainha(tabuleiro,cor);
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(origem);
		p.aumentarContagemMovemimentos();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// Movimento especial castling torre do rei - roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(),
					origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(),
					origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.aumentarContagemMovemimentos();
		}

		// Movimento especial castling lado da rainha - roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(),
					origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(),
					origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.aumentarContagemMovemimentos();
		}

		// Movimento especial en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna()
					&& pecaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(destino.getLinha() + 1,
							destino.getColuna());

				} else {
					posicaoPeao = new Posicao(destino.getLinha() - 1,
							destino.getColuna());

				}
				pecaCapturada = tabuleiro.removePeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	public void desfazerMovimento(Posicao origem, Posicao destino,
			Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(destino);
		p.diminuirContagemMovemimentos();
		tabuleiro.colocarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// Movimento especial castling torre do rei - roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(),
					origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(),
					origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.diminuirContagemMovemimentos();
		}

		// Movimento especial castling lado da rainha - roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(),
					origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(),
					origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.diminuirContagemMovemimentos();
		}
		// Movimento especial en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna()
					&& pecaCapturada == enPassantVulnerável) {
				PecaXadrez peao = (PecaXadrez) tabuleiro.removePeca(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(3, destino.getColuna());

				} else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.colocarPeca(peao, posicaoPeao);
			}
		}
	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezException("Nao ha peca na posicao de origem");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peca escolhida nao eh sua");
		}
		if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()) {
			throw new XadrezException(
					"Nao existe movimentos possiveis para a peca escolhida");
		}
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossiveis(destino)) {
			throw new XadrezException(
					"A peca escolhida nao pode se mover para a posicao de destino");
		}

	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream()
				.filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());

		for (Peca peca : lista) {
			if (peca instanceof Rei) {
				return (PecaXadrez) peca;
			}
		}
		throw new IllegalStateException("Nao existe o rei da cor " + cor
				+ " no tabuleiro!");
	}

	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecaOponente = pecasNoTabuleiro.stream()
				.filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca peca : pecaOponente) {
			boolean matriz[][] = peca.movimentoPossiveis();
			if (matriz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream()
				.filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());

		for (Peca peca : lista) {
			boolean matriz[][] = peca.movimentoPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Posicao origem = ((PecaXadrez) peca).getPosicaoXadrez()
								.paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testarCheck) {
							return false;
						}
					}
				}
			}

		}
		return true;
	}

	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca,
				new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);

	}

	private void inicioPartida() {

		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
	}

}
