package br.ufrn.imd.modelo;

/**
 * Esta é a classe que representa o navio Fragata. Ela é, portanto, uma subclasse da classe abstrata Navio.
 */

import java.util.Random;

public class Fragata extends Navio {
  
  public Fragata() {
    this.id = 3;
    this.tamanho = 4;
    this.ativo = true;
  }

  /**
   * Reduz o tamanho da Fragata, caso ela seja atingida por um jogador.
   * <br>
   * Ao assumir o tamanho 0, o atributo 'ativo' é alterado para false, para indicar que a Fragata foi destruída.   
   */  
  public void reduzirTamanho() {
    this.tamanho--;
    if (tamanho == 0) {
      this.ativo = false;
    }
  }

  /**
   * Define o posicionamento inicial da Fragata.
   * <br>
   * Inicialmente, é definida aleatoriamente a orientação da Fragata, por meio da geração de um inteiro aleatório entre 0 e 1. O valor 0 significa orientação Horizontal, 
   * enquanto o valor 1 significa orientação Vertical.
   * <br>
   * Posteriormente, a linha e a coluna iniciais em que o navio estará posicionado serão definidas, respeitando-se os limites do tabuleiro, de acordo com o tamanho do navio.
   */
  public void definirPosicionamento() {
    Random random = new Random();

    this.orientacao = random.nextInt(2);
    if (this.orientacao == 0) {
      this.linha = random.nextInt(10);
      this.coluna = random.nextInt(7);
    } else {
      this.linha = random.nextInt(7);
      this.coluna = random.nextInt(10);
    }
  }
}