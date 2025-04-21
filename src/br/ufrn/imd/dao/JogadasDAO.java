package br.ufrn.imd.dao;

/**
 * Esta classe é um repositório que registra todas as jogadas realizadas por um jogador na partida.
 * <br>
 * A princípio, esta classe possui apenas um atributo (jogadas).
 */

import java.util.ArrayList;

public class JogadasDAO {
	  private ArrayList<int[]> jogadas; //ArrayList que armazena todas as jogadas realizadas por um jogador.
	  
	  /**
	   * Método construtor que cria um novo repositório de jogadas.
	   */
	  public JogadasDAO() {
		  jogadas = new ArrayList<int[]>();
	  }
	  
	  /**
	   * Adiciona ao ArrayList 'jogadas' um vetor com dois inteiros, que representa a jogada realizada pelo jogador, no formato {linha, coluna}.
	   * @param linha
	   * @param coluna
	   */
	  public void addJogadas(int linha, int coluna) {
		  int[] dados = new int[2];
		  dados[0] = linha;
		  dados[1] = coluna;
		  jogadas.add(dados);
	  }
	  
	  /**
	   * Verifica se a jogada a ser feita pelo jogador já foi realizada antes, ou seja, se já está cadastrada no ArrayList 'jogadas'.
	   * @param linha
	   * @param coluna
	   * @return true se a jogada já foi realizada, false caso contrário.
	   */
	  public boolean verificarSeJaHouveEssaJogada(int linha, int coluna) {
		  for(int[] jogada : jogadas) {
			  if (linha == jogada[0] && coluna == jogada[1]) {
				  return true;
			  }
		  }
		  return false;
	  }
	  
	  /**
	   * Retorna a quantidade de jogadas realizadas pelo jogador, que será obtida a partir do tamanho do ArrayList jogadas.
	   * @return um inteiro que representa a quantidade de jogadas realizadas pelo jogador.
	   */
	  public int retornaQuantidadeDeJogadas() {
		  return jogadas.size();
	  }
	}