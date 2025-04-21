package br.ufrn.imd.modelo;

/**
 * Esta classe representa o tabuleiro do jogo Batalha Naval.
 * <br>
 * Possui uma constante DIM, que representa a dimensão do tabuleiro, e um array bidimensional (mapa), que indicará o posicionamento dos navios ou a ausência deles
 * em determinado local do tabuleiro, conforme as informações abaixo:
 * <br> 
 * 0 - representa a água e, consequentemente, a ausência de navio (tiro na água);
 * <br>
 * 1 - indica que uma Corveta está posicionada em determinada localização;
 * <br>
 * 2 - indica que um Submarino está posicionado em determinada localização;
 * <br>
 * 3 - indica que uma Fragata está posicionada em determinada localização;
 * <br>
 * 4 - indica que um Destroyer está posicionado em determinada localização;
 * 
 * @author Thiago Vitor Moreira Maia
 */

public class Tabuleiro {
	  public static final int DIM = 10; // (dimensão do mapa)
	  private int mapa[][]; // Array bidimensional, que representará um tabuleiro com os navios posicionados

	  public Tabuleiro() {
	    mapa = new int[DIM][DIM];
	  }
	  
	  /**
	   * Método que inicializa o tabuleiro. A princípio, o tabuleiro será preenchido com o valor 0 em cada posição.
	   */
	  public void iniciarTabuleiro() {
	    for (int i = 0; i < DIM; i++) {
	      for (int j = 0; j < DIM; j++) {
	        mapa[i][j] = 0;
	      }
	    }
	  }

	  /**
	   * Método que posiciona um navio no tabuleiro.
	   * <br>
	   * Este método recebe um navio como parâmetro. A partir das informações coletadas dos atributos do navio (linha, coluna, tamanho e orientação),
	   * ele será posicionado no tabuleiro. 
	   * @param navio
	   */
	  public void posicionarNavio(Navio navio) {
	    if (navio.getOrientacao() == 0) { // horizontal
	      for (int i = navio.getColuna(); i < navio.getColuna() + navio.getTamanho(); i++) {
	        mapa[navio.getLinha()][i] = navio.getId();
	      }
	    } else { // vertical
	      for (int i = navio.getLinha(); i < navio.getLinha() + navio.getTamanho(); i++) {
	        mapa[i][navio.getColuna()] = navio.getId();
	      }
	    }
	  }
	  
	  /**
	   * Método que limpa a posição atual do navio.
	   * <br>
	   * Este método recebe um navio como parâmetro. A partir das informações coletadas dos atributos do navio (linha, coluna, tamanho e orientação),
	   * a sua posição no tabuleiro será alterada para 0. Este método pode ser utilizado, por exemplo, no processo de rotação ou reposicionamento do navio
	   * para outro local do tabuleiro.
	   * @param navio
	   */
	  public void limparPosicaoAntigaDoNavio(Navio navio) {
		    if (navio.getOrientacao() == 0) { // horizontal
		      for (int i = navio.getColuna(); i < navio.getColuna() + navio.getTamanho(); i++) {
		        mapa[navio.getLinha()][i] = 0;
		      }
		    } else { // vertical
		      for (int i = navio.getLinha(); i < navio.getLinha() + navio.getTamanho(); i++) {
		        mapa[i][navio.getColuna()] = 0;
		      }
		    }
		  }

	  /**
	   * Método para verificar se há algum navio posicionado no trajeto em que se pretende posicionar um outro navio.
	   * @param navio
	   * @return true, se houver um navio na localização; false, caso contrário
	   */
	  public boolean verificarSeHaNavioNaLocalizacao(Navio navio) {
		    if (navio.orientacao == 0) {
		      for (int i = navio.coluna; i < navio.coluna + navio.tamanho; i++) {
		        if (mapa[navio.linha][i] != 0) {
		          return false;
		        }
		      }
		    } else {
		      for (int i = navio.linha; i < navio.linha + navio.tamanho; i++) {
		        if (mapa[i][navio.coluna] != 0) {
		          return false;
		        }
		      }
		    }
		    
		    return true;
	  }
	  
	  /**
	   * Método para verificar se há algum navio posicionado no trajeto em que se pretende reposicionar um outro navio.
	   * @param linha
	   * @param coluna
	   * @param navio
	   * @return true, se houver um navio na localização; false, caso contrário
	   */
	  public boolean verificarSeHaNavioNaLocalizacao(int linha, int coluna, Navio navio) {		  
		  if(navio.orientacao == 0) {
			  for (int i = coluna; i < coluna+navio.tamanho; i++) {
				  if(mapa[linha][i] != 0 && mapa[linha][i] != navio.id) {
					  return false;
				  }
			  } 
		  } else {
			  for (int i = linha; i < linha+navio.tamanho; i++) {
				  if(mapa[i][coluna] != 0 && mapa[i][coluna] != navio.id) {
					  return false;
				  }
			  } 
		  }
		  
		  return true;
	  }
	  
	  /**
	   * Método para verificar se há algum navio posicionado no trajeto em que se pretende rotacionar um outro navio.
	   * @param navio
	   * @param orientacao
	   * @return true, se houver um navio na localização; false, caso contrário
	   */
	  public boolean verificarSeHaNavioNaLocalizacao(Navio navio, int orientacao) {		  
		  if(orientacao == 0) {
			  for (int i = navio.coluna; i < navio.coluna+navio.tamanho; i++) {
				  if(mapa[navio.linha][i] != 0 && mapa[navio.linha][i] != navio.id) {
					  return false;
				  }
			  } 
		  } else {
			  for (int i = navio.linha; i < navio.linha+navio.tamanho; i++) {
				  if(mapa[i][navio.coluna] != 0 && mapa[i][navio.coluna] != navio.id) {
					  return false;
				  }
			  } 
		  }
		  
		  return true;
	  }
	  
	  /**
	   * Este método verifica se um disparo realizado pelo jogador atingiu algum navio no tabuleiro.
	   * @param linha
	   * @param coluna
	   * @return true, se acertou um navio; false, caso contrário.
	   */
	  public boolean verificarSeAcertouNavio(int linha, int coluna) {
	    if (mapa[linha][coluna] != 0) {
	      return true;
	    }
	    return false;
	  }
	  
	  /**
	   * Este método retorna um valor do tabuleiro, para permitir a identificação do navio que foi atingido ou se o jogador realizou um disparo na água.
	   * @param i
	   * @param j
	   * @return 0, se ocorreu tiro na água; 1, se atingiu a Corveta; 2, se atingiu o Submarino; 3, se atingiu a Fragata; e 4, se atingiu o Destroyer.
	   */
	  public int retornaValor(int i, int j) {
		  return this.mapa[i][j];
	  }
	}
