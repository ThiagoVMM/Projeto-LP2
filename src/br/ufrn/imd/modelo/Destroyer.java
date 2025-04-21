package br.ufrn.imd.modelo;

/**
 * Esta é a classe que representa o navio Destroyer. Ela é, portanto, uma subclasse da classe abstrata Navio.
 */

import java.util.Random;

public class Destroyer extends Navio {

  public Destroyer() {
    this.id = 4;
    this.tamanho = 5;
    this.ativo = true;
  }

  /**
   * Reduz o tamanho do Destroyer, caso ele seja atingido por um jogador.
   * <br>
   * Ao assumir o tamanho 0, o atributo 'ativo' é alterado para false, para indicar que o Destroyer foi destruído.   
   */
  public void reduzirTamanho() {
    this.tamanho--;
    if (tamanho == 0) {
      this.ativo = false;
    }
  }

  /**
   * Define o posicionamento inicial do Destroyer.
   * <br>
   * Inicialmente, é definida aleatoriamente a orientação do Destroyer, por meio da geração de um inteiro aleatório entre 0 e 1. O valor 0 significa orientação Horizontal, 
   * enquanto o valor 1 significa orientação Vertical.
   * <br>
   * Posteriormente, a linha e a coluna iniciais em que o navio estará posicionado serão definidas, respeitando-se os limites do tabuleiro, de acordo com o tamanho do navio.
   */
  public void definirPosicionamento() {
    Random random = new Random();

    this.orientacao = random.nextInt(2);
    if (this.orientacao == 0) {
      this.linha = random.nextInt(10);
      this.coluna = random.nextInt(6);
    } else {
      this.linha = random.nextInt(6);
      this.coluna = random.nextInt(10);
    }
  }
}