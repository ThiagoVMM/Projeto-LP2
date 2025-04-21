package br.ufrn.imd.modelo;

/**
 * Esta é uma classe abstrata que serve como modelo para as demais classes de navios que a herdarão.
 * <br>
 * As subclasses que herdarem desta classe deverão possuir cinco atributos (id, tamanho, ativo, orientacao, linha e coluna).
 */

public abstract class Navio {
	  protected int id;
	  protected int tamanho;
	  protected boolean ativo;
	  protected int orientacao;
	  protected int linha;
	  protected int coluna;

	  /**
	   * Método construtor que cria um novo Navio.
	   */
	  public Navio() {
	    this.ativo = true;
	    this.orientacao = 0;
	  }
	  
	  /**
	   * Método que define a orientação do navio.
	   * @param orientacao
	   */
	  public void setOrientacao(int orientacao) {
		  this.orientacao = orientacao;
	  }

	  /**
	   * Método que retorna a orientação do navio.
	   * @return '0' se o navio estiver na horizontal, '1' se o navio estiver na vertical.
	   */
	  public int getOrientacao() {
		  return this.orientacao;
	  }

	  /**
	   * Método que retorna o id do navio.
	   * @return 1, se for uma corveta; 2, se for um submarino; 3, se for uma fragata; e 4, se for um destroyer.
	   */
	  public int getId() {
		  return this.id;
	  }

	  /**
	   * Método que retorna o tamanho do navio.
	   * @return 2, para uma corveta; 3, para um submarino; 4, para uma fragata; e 5, para um destroyer
	   */
	  public int getTamanho() {
		  return this.tamanho;
	  }

	  /**
	   * Método que retorna a condição do navio.
	   * @return true, se o navio estiver ativo, ou seja, se pelo menos uma parte do navio ainda não foi atingida; e false, se o navio foi destruido.
	   */
	  public boolean getAtivo() {
		  return this.ativo;
	  }

	  /**
	   * Método que define a condição do navio.
	   * @param ativo
	   */
	  public void setAtivo(boolean ativo) {
		  this.ativo = ativo;
	  }

	  /**
	   * Método que retorna a linha em que o navio está posicionado.
	   * @return Um inteiro de 0 a 9, que representa a linha do tabuleiro na qual o navio está posicionado.
	   */
	  public int getLinha() {
		  return this.linha;
	  }

	  /**
	   * Método que define a linha em que o navio deverá ser posicionado. 
	   * @param linha
	   */
	  public void setLinha(int linha) {
		  this.linha = linha;
	  }

	  /**
	   * Método que retorna a coluna em que o navio está posicionado.
	   * @return
	   */
	  public int getColuna() {
		  return this.coluna;
	  }

	  /**
	   * Método que define a coluna em que o navio deverá ser posicionado. 
	   * @param coluna
	   */
	  public void setColuna(int coluna) {
		  this.coluna = coluna;
	  }

	  /**
	   * Método que reduz o tamanho o tamanho do navio, se ele for atingido.
	   * <br>
	   * Este método deve ser implementado pelas subclasses.
	   */
	  public abstract void reduzirTamanho();

	  /**
	   * Método que define o posicionamento do navio no tabuleiro.
	   * <br>
	   * Esté método deve ser implementado pelas subclasses, para definir a lógica de posicionamento.
	   */
	  public abstract void definirPosicionamento();
	}
