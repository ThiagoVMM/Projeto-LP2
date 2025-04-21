package br.ufrn.imd.controle;

/**
 * Esta é a classe controller do arquivo TelaJogo.fxml.
 */

import java.io.IOException;
import java.util.Random;
import br.ufrn.imd.MainApp;
import br.ufrn.imd.dao.JogadasDAO;
import br.ufrn.imd.modelo.Corveta;
import br.ufrn.imd.modelo.Destroyer;
import br.ufrn.imd.modelo.Fragata;
import br.ufrn.imd.modelo.Submarino;
import br.ufrn.imd.modelo.Tabuleiro;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;

public class TelaJogoController {
	@FXML
    private MenuItem sairMenuItem;
	
	@FXML
    private MenuItem comoJogarMenuItem;
	
	@FXML
    private MenuItem sobreMenuItem;
	
    @FXML
    private Label lblJogadasUser;
    
    @FXML
    private Button btnPodeJogar;

    @FXML
    private Button btnAtirar;

    @FXML
    private Label lblJogadasMaquina;

    @FXML
    private Label lblPainel;

    @FXML
    private GridPane gridPane1;

    @FXML
    private GridPane gridPane2;
    
    private int linhaClicada;
    private int colunaClicada;
    private Pane ultimaCelulaClicada;
    private boolean turnoUsuario = true; // Variável para controlar o turno do jogador
    private boolean imagemSelecionada = false;
    private String idImagem;
    private final Random random = new Random();

    Corveta corvetaJog = new Corveta();
    Submarino submarinoJog = new Submarino();
    Fragata fragataJog = new Fragata();
    Destroyer destroyerJog = new Destroyer();

    Corveta corvetaMaq = new Corveta();
    Submarino submarinoMaq = new Submarino();
    Fragata fragataMaq = new Fragata();
    Destroyer destroyerMaq = new Destroyer();
    
    JogadasDAO jogador = new JogadasDAO();
    JogadasDAO computador = new JogadasDAO();
    
    Tabuleiro tabJog = new Tabuleiro();
    Tabuleiro tabMaq = new Tabuleiro();
    
    String imagemAlvo = getClass().getResource("/img/alvo.png").toExternalForm();
    String imagemExplosao = getClass().getResource("/img/explosao.png").toExternalForm();
    String imagemTiroAgua = getClass().getResource("/img/tiro-na-agua.png").toExternalForm();
   
    
    // Cria as imagens para o tabuleiro do jogador
    ImageView corvetaHImagem = new ImageView(new Image(getClass().getResource("/img/corvetaH.png").toExternalForm()));
    ImageView corvetaVImagem = new ImageView(new Image(getClass().getResource("/img/corvetaV.png").toExternalForm()));
    ImageView submarinoHImagem = new ImageView(new Image(getClass().getResource("/img/submarinoH.png").toExternalForm()));
    ImageView submarinoVImagem = new ImageView(new Image(getClass().getResource("/img/submarinoV.png").toExternalForm()));
    ImageView fragataHImagem = new ImageView(new Image(getClass().getResource("/img/fragataH.png").toExternalForm()));
    ImageView fragataVImagem = new ImageView(new Image(getClass().getResource("/img/fragataV.png").toExternalForm()));
    ImageView destroyerHImagem = new ImageView(new Image(getClass().getResource("/img/destroyerH.png").toExternalForm()));
    ImageView destroyerVImagem = new ImageView(new Image(getClass().getResource("/img/destroyerV.png").toExternalForm()));
 // Cria as imagens para o tabuleiro da máquina
    ImageView corvetaHFinalImagem = new ImageView(new Image(getClass().getResource("/img/corvetaH-destruida.png").toExternalForm()));
    ImageView corvetaVFinalImagem = new ImageView(new Image(getClass().getResource("/img/corvetaV-destruida.png").toExternalForm()));
    ImageView submarinoHFinalImagem = new ImageView(new Image(getClass().getResource("/img/submarinoH-destruido.png").toExternalForm()));
    ImageView submarinoVFinalImagem = new ImageView(new Image(getClass().getResource("/img/submarinoV-destruido.png").toExternalForm()));
    ImageView fragataHFinalImagem = new ImageView(new Image(getClass().getResource("/img/fragataH-destruida.png").toExternalForm()));
    ImageView fragataVFinalImagem = new ImageView(new Image(getClass().getResource("/img/fragataV-destruida.png").toExternalForm()));
    ImageView destroyerHFinalImagem = new ImageView(new Image(getClass().getResource("/img/destroyerH-destruido.png").toExternalForm()));
    ImageView destroyerVFinalImagem = new ImageView(new Image(getClass().getResource("/img/destroyerV-destruido.png").toExternalForm()));
    
    /**
     * Método para para executar a lógica de inicialização necessária, após a interface ter sido carregada, mas antes de o usuário interagir com ela.
     */
    @FXML
    private void initialize() {
    	iniciarCelulas(gridPane1, true);
    	iniciarCelulas(gridPane2, false);
    	
    	gridPane2.setDisable(true);
        btnAtirar.setDisable(true);
        
        lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
    	tabJog.iniciarTabuleiro();
        tabMaq.iniciarTabuleiro();
        
        GridPane.setColumnSpan(corvetaHImagem, 2); // Ocupa 2 colunas
        GridPane.setRowSpan(corvetaHImagem, 1);    // Ocupa 1 linhas
        corvetaHImagem.setPreserveRatio(true);
        corvetaHImagem.setFitWidth(60); // Tamanho da ImageView
        corvetaHImagem.setFitHeight(30); // Tamanho da ImageView
        corvetaHImagem.setId("corvetaH");
        corvetaHImagem.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                selecionarNavio(corvetaHImagem, gridPane1);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rotacionarNavio(corvetaHImagem);
            }
        });
        
        GridPane.setColumnSpan(corvetaVImagem, 1); // Ocupa 2 colunas
        GridPane.setRowSpan(corvetaVImagem, 2);    // Ocupa 2 linhas
        corvetaVImagem.setPreserveRatio(true);
        corvetaVImagem.setFitWidth(30); // Tamanho da ImageView
        corvetaVImagem.setFitHeight(60); // Tamanho da ImageView
        corvetaVImagem.setId("corvetaV");
        corvetaVImagem.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                selecionarNavio(corvetaVImagem, gridPane1);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rotacionarNavio(corvetaVImagem);
            }
        });
        
        GridPane.setColumnSpan(submarinoHImagem, 3); // Ocupa 2 colunas
        GridPane.setRowSpan(submarinoHImagem, 1);    // Ocupa 2 linhas
        submarinoHImagem.setPreserveRatio(true);
        submarinoHImagem.setFitWidth(90); // Tamanho da ImageView
        submarinoHImagem.setFitHeight(30); // Tamanho da ImageView
        submarinoHImagem.setId("submarinoH");
        submarinoHImagem.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                selecionarNavio(submarinoHImagem, gridPane1);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rotacionarNavio(submarinoHImagem);
            }
        });
        
        GridPane.setColumnSpan(submarinoVImagem, 1); // Ocupa 2 colunas
        GridPane.setRowSpan(submarinoVImagem, 3);    // Ocupa 2 linhas
        submarinoVImagem.setPreserveRatio(true);
        submarinoVImagem.setFitWidth(30); // Tamanho da ImageView
        submarinoVImagem.setFitHeight(90); // Tamanho da ImageView
        submarinoVImagem.setId("submarinoV");
        submarinoVImagem.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                selecionarNavio(submarinoVImagem, gridPane1);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rotacionarNavio(submarinoVImagem);
            }
        });
        
        GridPane.setColumnSpan(fragataHImagem, 4); // Ocupa 2 colunas
        GridPane.setRowSpan(fragataHImagem, 1);    // Ocupa 2 linhas
        fragataHImagem.setPreserveRatio(true);
        fragataHImagem.setFitWidth(120); // Tamanho da ImageView
        fragataHImagem.setFitHeight(30); // Tamanho da ImageView
        fragataHImagem.setId("fragataH");
        fragataHImagem.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                selecionarNavio(fragataHImagem, gridPane1);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rotacionarNavio(fragataHImagem);
            }
        });
        
        GridPane.setColumnSpan(fragataVImagem, 1); // Ocupa 2 colunas
        GridPane.setRowSpan(fragataVImagem, 4);    // Ocupa 2 linhas
        fragataVImagem.setPreserveRatio(true);
        fragataVImagem.setFitWidth(30); // Tamanho da ImageView
        fragataVImagem.setFitHeight(120); // Tamanho da ImageView
        fragataVImagem.setId("fragataV");
        fragataVImagem.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                selecionarNavio(fragataVImagem, gridPane1);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rotacionarNavio(fragataVImagem);
            }
        });
        
        GridPane.setColumnSpan(destroyerHImagem, 5); // Ocupa 2 colunas
        GridPane.setRowSpan(destroyerHImagem, 1);    // Ocupa 2 linhas
        destroyerHImagem.setPreserveRatio(true);
        destroyerHImagem.setFitWidth(150); // Tamanho da ImageView
        destroyerHImagem.setFitHeight(30); // Tamanho da ImageView
        destroyerHImagem.setId("destroyerH");
        destroyerHImagem.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                selecionarNavio(destroyerHImagem, gridPane1);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rotacionarNavio(destroyerHImagem);
            }
        });
        
        GridPane.setColumnSpan(destroyerVImagem, 1); // Ocupa 2 colunas
        GridPane.setRowSpan(destroyerVImagem, 5);    // Ocupa 2 linhas
        destroyerVImagem.setPreserveRatio(true);
        destroyerVImagem.setFitWidth(30); // Tamanho da ImageView
        destroyerVImagem.setFitHeight(150); // Tamanho da ImageView
        destroyerVImagem.setId("destroyerV");
        destroyerVImagem.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                selecionarNavio(destroyerVImagem, gridPane1);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rotacionarNavio(destroyerVImagem);
            }
        });
        
        /* ----------------------------------- */
        GridPane.setColumnSpan(corvetaHFinalImagem, 2); // Ocupa 2 colunas
        GridPane.setRowSpan(corvetaHFinalImagem, 1);    // Ocupa 1 linhas
        corvetaHFinalImagem.setPreserveRatio(true);
        corvetaHFinalImagem.setFitWidth(60); // Tamanho da ImageView
        corvetaHFinalImagem.setFitHeight(30); // Tamanho da ImageView
        
        
        GridPane.setColumnSpan(corvetaVFinalImagem, 1); // Ocupa 2 colunas
        GridPane.setRowSpan(corvetaVFinalImagem, 2);    // Ocupa 2 linhas
        corvetaVFinalImagem.setPreserveRatio(true);
        corvetaVFinalImagem.setFitWidth(30); // Tamanho da ImageView
        corvetaVFinalImagem.setFitHeight(60); // Tamanho da ImageView
        
        
        GridPane.setColumnSpan(submarinoHFinalImagem, 3); // Ocupa 2 colunas
        GridPane.setRowSpan(submarinoHFinalImagem, 1);    // Ocupa 2 linhas
        submarinoHFinalImagem.setPreserveRatio(true);
        submarinoHFinalImagem.setFitWidth(90); // Tamanho da ImageView
        submarinoHFinalImagem.setFitHeight(30); // Tamanho da ImageView
        
        
        GridPane.setColumnSpan(submarinoVFinalImagem, 1); // Ocupa 2 colunas
        GridPane.setRowSpan(submarinoVFinalImagem, 3);    // Ocupa 2 linhas
        submarinoVFinalImagem.setPreserveRatio(true);
        submarinoVFinalImagem.setFitWidth(30); // Tamanho da ImageView
        submarinoVFinalImagem.setFitHeight(90); // Tamanho da ImageView
        
        
        GridPane.setColumnSpan(fragataHFinalImagem, 4); // Ocupa 2 colunas
        GridPane.setRowSpan(fragataHFinalImagem, 1);    // Ocupa 2 linhas
        fragataHFinalImagem.setPreserveRatio(true);
        fragataHFinalImagem.setFitWidth(120); // Tamanho da ImageView
        fragataHFinalImagem.setFitHeight(30); // Tamanho da ImageView
        
        
        GridPane.setColumnSpan(fragataVFinalImagem, 1); // Ocupa 2 colunas
        GridPane.setRowSpan(fragataVFinalImagem, 4);    // Ocupa 2 linhas
        fragataVFinalImagem.setPreserveRatio(true);
        fragataVFinalImagem.setFitWidth(30); // Tamanho da ImageView
        fragataVFinalImagem.setFitHeight(120); // Tamanho da ImageView
        
        
        GridPane.setColumnSpan(destroyerHFinalImagem, 5); // Ocupa 2 colunas
        GridPane.setRowSpan(destroyerHFinalImagem, 1);    // Ocupa 2 linhas
        destroyerHFinalImagem.setPreserveRatio(true);
        destroyerHFinalImagem.setFitWidth(150); // Tamanho da ImageView
        destroyerHFinalImagem.setFitHeight(30); // Tamanho da ImageView
        
        
        GridPane.setColumnSpan(destroyerVFinalImagem, 1); // Ocupa 2 colunas
        GridPane.setRowSpan(destroyerVFinalImagem, 5);    // Ocupa 2 linhas
        destroyerVFinalImagem.setPreserveRatio(true);
        destroyerVFinalImagem.setFitWidth(30); // Tamanho da ImageView
        destroyerVFinalImagem.setFitHeight(150); // Tamanho da ImageView

        // ---------------- POSICIONAMENTO DE NAVIOS DE FORMA ALEATÓRIA ----------------
        // ---------------- JOGADOR ----------------

        // posicionarNavio(corvetaJog);
        corvetaJog.definirPosicionamento();
        tabJog.posicionarNavio(corvetaJog);
        
        if(corvetaJog.getOrientacao() == 0) {
        	// Define a posição da célula para a ImageView
            GridPane.setColumnIndex(corvetaHImagem, corvetaJog.getColuna());
            GridPane.setRowIndex(corvetaHImagem, corvetaJog.getLinha());
            // Adiciona a ImageView ao GridPane
            gridPane1.getChildren().add(corvetaHImagem);
        } else {
        	GridPane.setColumnIndex(corvetaVImagem, corvetaJog.getColuna());
            GridPane.setRowIndex(corvetaVImagem, corvetaJog.getLinha());
            gridPane1.getChildren().add(corvetaVImagem);
        }

        // posicionarNavio(submarinoJog);
        submarinoJog.definirPosicionamento();

        while (!tabJog.verificarSeHaNavioNaLocalizacao(submarinoJog)) {
          submarinoJog.definirPosicionamento();

          tabJog.verificarSeHaNavioNaLocalizacao(submarinoJog);
        }

        tabJog.posicionarNavio(submarinoJog);
        
        if(submarinoJog.getOrientacao() == 0) {
        	// Define a posição da célula para a ImageView
            GridPane.setColumnIndex(submarinoHImagem, submarinoJog.getColuna());
            GridPane.setRowIndex(submarinoHImagem, submarinoJog.getLinha());
            // Adiciona a ImageView ao GridPane
            gridPane1.getChildren().add(submarinoHImagem);
        } else {
        	GridPane.setColumnIndex(submarinoVImagem, submarinoJog.getColuna());
            GridPane.setRowIndex(submarinoVImagem, submarinoJog.getLinha());
            gridPane1.getChildren().add(submarinoVImagem);
        }

        // posicionarNavio(fragataJog);
        fragataJog.definirPosicionamento();

        while (!tabJog.verificarSeHaNavioNaLocalizacao(fragataJog)) {
          fragataJog.definirPosicionamento();

          tabJog.verificarSeHaNavioNaLocalizacao(fragataJog);
        }

        tabJog.posicionarNavio(fragataJog);
        
        if(fragataJog.getOrientacao() == 0) {
        	// Define a posição da célula para a ImageView
            GridPane.setColumnIndex(fragataHImagem, fragataJog.getColuna());
            GridPane.setRowIndex(fragataHImagem, fragataJog.getLinha());
            // Adiciona a ImageView ao GridPane
            gridPane1.getChildren().add(fragataHImagem);
        } else {
        	GridPane.setColumnIndex(fragataVImagem, fragataJog.getColuna());
            GridPane.setRowIndex(fragataVImagem, fragataJog.getLinha());
            gridPane1.getChildren().add(fragataVImagem);
        }

        // posicionarNavio(destroyerJog);
        destroyerJog.definirPosicionamento();

        while (!tabJog.verificarSeHaNavioNaLocalizacao(destroyerJog)) {
          destroyerJog.definirPosicionamento();

          tabJog.verificarSeHaNavioNaLocalizacao(destroyerJog);
        }

        tabJog.posicionarNavio(destroyerJog);
        
        if(destroyerJog.getOrientacao() == 0) {
        	// Define a posição da célula para a ImageView
            GridPane.setColumnIndex(destroyerHImagem, destroyerJog.getColuna());
            GridPane.setRowIndex(destroyerHImagem, destroyerJog.getLinha());
            // Adiciona a ImageView ao GridPane
            gridPane1.getChildren().add(destroyerHImagem);
        } else {
        	GridPane.setColumnIndex(destroyerVImagem, destroyerJog.getColuna());
            GridPane.setRowIndex(destroyerVImagem, destroyerJog.getLinha());
            gridPane1.getChildren().add(destroyerVImagem);
        }

        // ---------------- MÁQUINA ----------------

        // posicionarNavio(corvetaMaq);
        corvetaMaq.definirPosicionamento();
        tabMaq.posicionarNavio(corvetaMaq);

        // posicionarNavio(submarinoMaq);
        submarinoMaq.definirPosicionamento();

        while (!tabMaq.verificarSeHaNavioNaLocalizacao(submarinoMaq)) {
          submarinoMaq.definirPosicionamento();

          tabMaq.verificarSeHaNavioNaLocalizacao(submarinoMaq);
        }

        tabMaq.posicionarNavio(submarinoMaq);

        // posicionarNavio(fragataMaq);
        fragataMaq.definirPosicionamento();

        while (!tabMaq.verificarSeHaNavioNaLocalizacao(fragataMaq)) {
          fragataMaq.definirPosicionamento();

          tabMaq.verificarSeHaNavioNaLocalizacao(fragataMaq);
        }

        tabMaq.posicionarNavio(fragataMaq);

        // posicionarNavio(destroyerMaq);
        destroyerMaq.definirPosicionamento();

        while (!tabMaq.verificarSeHaNavioNaLocalizacao(destroyerMaq)) {
          destroyerMaq.definirPosicionamento();

          tabMaq.verificarSeHaNavioNaLocalizacao(destroyerMaq);
        }

        tabMaq.posicionarNavio(destroyerMaq);
    }
    
    /**
     * Método para iniciar, atribuir propriedades e adicionar Panes a um GridPane.
     * @param gridPane
     * @param primeiroGrid
     */
    private void iniciarCelulas(GridPane gridPane, boolean primeiroGrid) {
    	for (int i = 0; i < 10; i++) {
    		for (int j = 0; j < 10; j++) {
    			Pane celula = new Pane();
    			celula.setStyle("-fx-border-color: white; -fx-border-width: 1px; -fx-background-color: #3eb1df;");
    			celula.setMinSize(30, 30);
    			
    			final int linhaAtual = i;
                final int colunaAtual = j;
    			
    			celula.setOnMouseClicked(event -> manipularCelulaClicada(event, linhaAtual, colunaAtual, primeiroGrid));
                celula.setId("inativa");
    			
    			gridPane.add(celula, j, i);
    		}
    	}
    }
    
    /**
     * Método para retornar um node de um GridPane, a partir da linha e coluna do GridPane.
     * @param linha
     * @param coluna
     * @param gridPane
     * @return
     */
    private Pane retornarNodeGrid(final int linha, final int coluna, GridPane gridPane) {
        for (Node node : gridPane.getChildren().filtered(node -> node instanceof Pane)) {
            if (GridPane.getRowIndex(node) == linha && GridPane.getColumnIndex(node) == coluna) {
                return (Pane) node;
            }
        }
        return null;
    }
    
    /**
     * Método para manipular as células clicadas no GridPane.
     * @param event
     * @param linha
     * @param coluna
     * @param primeiroGrid
     */
    private void manipularCelulaClicada(MouseEvent event, int linha, int coluna, boolean primeiroGrid) {
    	if (imagemSelecionada) {
    		if (idImagem.equals("corvetaH")) {
    			if (coluna + corvetaJog.getTamanho() > 10 || 
    					!tabJog.verificarSeHaNavioNaLocalizacao(linha, coluna, corvetaJog)) {
    				lblPainel.setText("Não é possível posicionar a corveta aqui!");
    			} else {
					tabJog.limparPosicaoAntigaDoNavio(corvetaJog);
					corvetaJog.setLinha(linha);
					corvetaJog.setColuna(coluna);
					tabJog.posicionarNavio(corvetaJog);
		            GridPane.setColumnIndex(corvetaHImagem, corvetaJog.getColuna());
		            GridPane.setRowIndex(corvetaHImagem, corvetaJog.getLinha());
		            corvetaHImagem.setOpacity(1);
		            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
		            btnPodeJogar.setDisable(false);
		            imagemSelecionada = false;
    			}
    		} else if (idImagem.equals("corvetaV")) {
    			if (linha + corvetaJog.getTamanho() > 10 ||
    					!tabJog.verificarSeHaNavioNaLocalizacao(linha, coluna, corvetaJog)) {
    				lblPainel.setText("Não é possível posicionar a corveta aqui!");
    			} else {
					tabJog.limparPosicaoAntigaDoNavio(corvetaJog);
					corvetaJog.setLinha(linha);
					corvetaJog.setColuna(coluna);
					tabJog.posicionarNavio(corvetaJog);
		            GridPane.setColumnIndex(corvetaVImagem, corvetaJog.getColuna());
		            GridPane.setRowIndex(corvetaVImagem, corvetaJog.getLinha());
		            corvetaVImagem.setOpacity(1);
		            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
		            btnPodeJogar.setDisable(false);
		            imagemSelecionada = false;
    			}
    		} else if (idImagem.equals("submarinoH")) {
    			if (coluna + submarinoJog.getTamanho() > 10 ||
    					!tabJog.verificarSeHaNavioNaLocalizacao(linha, coluna, submarinoJog)) {
    				lblPainel.setText("Não é possível posicionar o submarino aqui!");
    			} else {
					tabJog.limparPosicaoAntigaDoNavio(submarinoJog);
					submarinoJog.setLinha(linha);
					submarinoJog.setColuna(coluna);
					tabJog.posicionarNavio(submarinoJog);
		            GridPane.setColumnIndex(submarinoHImagem, submarinoJog.getColuna());
		            GridPane.setRowIndex(submarinoHImagem, submarinoJog.getLinha());
		            submarinoHImagem.setOpacity(1);
		            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
		            btnPodeJogar.setDisable(false);
		            imagemSelecionada = false;
    			}
    		} else if (idImagem.equals("submarinoV")) {
    			if (linha + submarinoJog.getTamanho() > 10 ||
    					!tabJog.verificarSeHaNavioNaLocalizacao(linha, coluna, submarinoJog)) {
    				lblPainel.setText("Não é possível posicionar o submarino aqui!");
    			} else {
					tabJog.limparPosicaoAntigaDoNavio(submarinoJog);
					submarinoJog.setLinha(linha);
					submarinoJog.setColuna(coluna);
					tabJog.posicionarNavio(submarinoJog);
		            GridPane.setColumnIndex(submarinoVImagem, submarinoJog.getColuna());
		            GridPane.setRowIndex(submarinoVImagem, submarinoJog.getLinha());
		            submarinoVImagem.setOpacity(1);
		            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
		            btnPodeJogar.setDisable(false);
		            imagemSelecionada = false;
    			}
    		}else if (idImagem.equals("fragataH")) {
    			if (coluna + fragataJog.getTamanho() > 10 ||
    					!tabJog.verificarSeHaNavioNaLocalizacao(linha, coluna, fragataJog)) {
    				lblPainel.setText("Não é possível posicionar a fragata aqui!");
    			} else {
					tabJog.limparPosicaoAntigaDoNavio(fragataJog);
					fragataJog.setLinha(linha);
					fragataJog.setColuna(coluna);
					tabJog.posicionarNavio(fragataJog);
		            GridPane.setColumnIndex(fragataHImagem, fragataJog.getColuna());
		            GridPane.setRowIndex(fragataHImagem, fragataJog.getLinha());
		            fragataHImagem.setOpacity(1);
		            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
		            btnPodeJogar.setDisable(false);
		            imagemSelecionada = false;
    			}
    		} else if (idImagem.equals("fragataV")) {
    			if (linha + fragataJog.getTamanho() > 10 ||
    					!tabJog.verificarSeHaNavioNaLocalizacao(linha, coluna, fragataJog)) {
    				lblPainel.setText("Não é possível posicionar a fragata aqui!");
    			} else {
					tabJog.limparPosicaoAntigaDoNavio(fragataJog);
					fragataJog.setLinha(linha);
					fragataJog.setColuna(coluna);
					tabJog.posicionarNavio(fragataJog);
		            GridPane.setColumnIndex(fragataVImagem, fragataJog.getColuna());
		            GridPane.setRowIndex(fragataVImagem, fragataJog.getLinha());
		            fragataVImagem.setOpacity(1);
		            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
		            btnPodeJogar.setDisable(false);
		            imagemSelecionada = false;
    			}
    		} else if (idImagem.equals("destroyerH")) {
    			if (coluna + destroyerJog.getTamanho() > 10 ||
    					!tabJog.verificarSeHaNavioNaLocalizacao(linha, coluna, destroyerJog)) {
    				lblPainel.setText("Não é possível posicionar o destroyer aqui!");
    			} else {
					tabJog.limparPosicaoAntigaDoNavio(destroyerJog);
					destroyerJog.setLinha(linha);
					destroyerJog.setColuna(coluna);
					tabJog.posicionarNavio(destroyerJog);
		            GridPane.setColumnIndex(destroyerHImagem, destroyerJog.getColuna());
		            GridPane.setRowIndex(destroyerHImagem, destroyerJog.getLinha());
		            destroyerHImagem.setOpacity(1);
		            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
		            btnPodeJogar.setDisable(false);
		            imagemSelecionada = false;
    			}
    		} else if (idImagem.equals("destroyerV")) {
    			if (linha + destroyerJog.getTamanho() > 10 ||
    					!tabJog.verificarSeHaNavioNaLocalizacao(linha, coluna, destroyerJog)) {
    				lblPainel.setText("Não é possível posicionar o destroyer aqui!");
    			} else {
					tabJog.limparPosicaoAntigaDoNavio(destroyerJog);
					destroyerJog.setLinha(linha);
					destroyerJog.setColuna(coluna);
					tabJog.posicionarNavio(destroyerJog);
		            GridPane.setColumnIndex(destroyerVImagem, destroyerJog.getColuna());
		            GridPane.setRowIndex(destroyerVImagem, destroyerJog.getLinha());
		            destroyerVImagem.setOpacity(1);
		            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
		            btnPodeJogar.setDisable(false);
		            imagemSelecionada = false;
    			}
    		}
    	}
    	
        if (!primeiroGrid) {
        	if (ultimaCelulaClicada != null) {
        		if (!ultimaCelulaClicada.getId().equals("ativo")) {
        			ultimaCelulaClicada.setStyle("-fx-background-color: #3eb1df; -fx-border-color: white;");
        		}
        	} 
            linhaClicada = linha;
            colunaClicada = coluna;
            ultimaCelulaClicada = (Pane) event.getSource();
            ultimaCelulaClicada.setStyle("-fx-background-color: #3eb1df; -fx-background-image: url('" + imagemAlvo + "'); -fx-background-size: cover; -fx-border-color: white;");
        }
        
    	if (jogador.verificarSeJaHouveEssaJogada(linhaClicada, colunaClicada)) {
    		btnAtirar.setDisable(true);
    	} else if (!primeiroGrid) {
    		btnAtirar.setDisable(false);
    	}
    }
    
    /**
     * Método para selecionar o navio a ser reposicionado.
     * @param imagem
     * @param gridpane
     */
    private void selecionarNavio(ImageView imagem, GridPane gridpane) {
    	if (imagemSelecionada) {
    		lblPainel.setText("Reposicione primeiro o outro navio!");
    	} else {
    		imagem.setOpacity(0.5);
        	imagemSelecionada = true;
        	idImagem = imagem.getId();
        	lblPainel.setText("Clique em outra célula para reposicionar seu navio!");
        	btnPodeJogar.setDisable(true);
    	}
    }
    
    /**
     * Método para realizar a rotação do navio, ao clicar com o botão direito do mouse.
     * @param imagem
     */
    private void rotacionarNavio (ImageView imagem) {
    	if (imagem.getId().equals("corvetaH")) {
    		if (corvetaJog.getLinha() + corvetaJog.getTamanho() > 10 || 
    				!tabJog.verificarSeHaNavioNaLocalizacao(corvetaJog, 1)) {
				lblPainel.setText("Não é possível rotacionar a corveta aqui!");
			} else {
				tabJog.limparPosicaoAntigaDoNavio(corvetaJog);
				corvetaJog.setOrientacao(1);
				tabJog.posicionarNavio(corvetaJog);
				gridPane1.getChildren().remove(corvetaHImagem);
				GridPane.setColumnIndex(corvetaVImagem, corvetaJog.getColuna());
	            GridPane.setRowIndex(corvetaVImagem, corvetaJog.getLinha());
	            gridPane1.getChildren().add(corvetaVImagem);
	            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
			}
    	} else if (imagem.getId().equals("corvetaV")) {
    		if (corvetaJog.getColuna() + corvetaJog.getTamanho() > 10 || 
    				!tabJog.verificarSeHaNavioNaLocalizacao(corvetaJog, 0)) {
    			lblPainel.setText("Não é possível rotacionar a corveta aqui!");
			} else {
				tabJog.limparPosicaoAntigaDoNavio(corvetaJog);
				corvetaJog.setOrientacao(0);
				tabJog.posicionarNavio(corvetaJog);
				gridPane1.getChildren().remove(corvetaVImagem);
				GridPane.setColumnIndex(corvetaHImagem, corvetaJog.getColuna());
	            GridPane.setRowIndex(corvetaHImagem, corvetaJog.getLinha());
	            gridPane1.getChildren().add(corvetaHImagem);
	            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
			}
    	} else if (imagem.getId().equals("submarinoH")) {
    		if (submarinoJog.getLinha() + submarinoJog.getTamanho() > 10 || 
    				!tabJog.verificarSeHaNavioNaLocalizacao(submarinoJog, 1)) {
    			lblPainel.setText("Não é possível rotacionar o submarino aqui!");
			} else {
				tabJog.limparPosicaoAntigaDoNavio(submarinoJog);
				submarinoJog.setOrientacao(1);
				tabJog.posicionarNavio(submarinoJog);
				gridPane1.getChildren().remove(submarinoHImagem);
				GridPane.setColumnIndex(submarinoVImagem, submarinoJog.getColuna());
	            GridPane.setRowIndex(submarinoVImagem, submarinoJog.getLinha());
	            gridPane1.getChildren().add(submarinoVImagem);
	            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
			}
    	} else if (imagem.getId().equals("submarinoV")) {
    		if (submarinoJog.getColuna() + submarinoJog.getTamanho() > 10 || 
    				!tabJog.verificarSeHaNavioNaLocalizacao(submarinoJog, 0)) {
    			lblPainel.setText("Não é possível rotacionar o submarino aqui!");
			} else {
				tabJog.limparPosicaoAntigaDoNavio(submarinoJog);
				submarinoJog.setOrientacao(0);
				tabJog.posicionarNavio(submarinoJog);
				gridPane1.getChildren().remove(submarinoVImagem);
				GridPane.setColumnIndex(submarinoHImagem, submarinoJog.getColuna());
	            GridPane.setRowIndex(submarinoHImagem, submarinoJog.getLinha());
	            gridPane1.getChildren().add(submarinoHImagem);
	            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
			}
    	} else if (imagem.getId().equals("fragataH")) {
    		if (fragataJog.getLinha() + fragataJog.getTamanho() > 10 || 
    				!tabJog.verificarSeHaNavioNaLocalizacao(fragataJog, 1)) {
    			lblPainel.setText("Não é possível rotacionar a fragata aqui!");
			} else {
				tabJog.limparPosicaoAntigaDoNavio(fragataJog);
				fragataJog.setOrientacao(1);
				tabJog.posicionarNavio(fragataJog);
				gridPane1.getChildren().remove(fragataHImagem);
				GridPane.setColumnIndex(fragataVImagem, fragataJog.getColuna());
	            GridPane.setRowIndex(fragataVImagem, fragataJog.getLinha());
	            gridPane1.getChildren().add(fragataVImagem);
	            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
			}
    	} else if (imagem.getId().equals("fragataV")) {
    		if (fragataJog.getColuna() + fragataJog.getTamanho() > 10 || 
    				!tabJog.verificarSeHaNavioNaLocalizacao(fragataJog, 0)) {
    			lblPainel.setText("Não é possível rotacionar a fragata aqui!");
			} else {
				tabJog.limparPosicaoAntigaDoNavio(fragataJog);
				fragataJog.setOrientacao(0);
				tabJog.posicionarNavio(fragataJog);
				gridPane1.getChildren().remove(fragataVImagem);
				GridPane.setColumnIndex(fragataHImagem, fragataJog.getColuna());
	            GridPane.setRowIndex(fragataHImagem, fragataJog.getLinha());
	            gridPane1.getChildren().add(fragataHImagem);
	            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
			}
    	} else if (imagem.getId().equals("destroyerH")) {
    		if (destroyerJog.getLinha() + destroyerJog.getTamanho() > 10 || 
    				!tabJog.verificarSeHaNavioNaLocalizacao(destroyerJog, 1)) {
    			lblPainel.setText("Não é possível rotacionar o destroyer aqui!");
			} else {
				tabJog.limparPosicaoAntigaDoNavio(destroyerJog);
				destroyerJog.setOrientacao(1);
				tabJog.posicionarNavio(destroyerJog);
				gridPane1.getChildren().remove(destroyerHImagem);
				GridPane.setColumnIndex(destroyerVImagem, destroyerJog.getColuna());
	            GridPane.setRowIndex(destroyerVImagem, destroyerJog.getLinha());
	            gridPane1.getChildren().add(destroyerVImagem);
	            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
			}
    	} else if (imagem.getId().equals("destroyerV")) {
    		if (destroyerJog.getColuna() + destroyerJog.getTamanho() > 10 || 
    				!tabJog.verificarSeHaNavioNaLocalizacao(destroyerJog, 0)) {
    			lblPainel.setText("Não é possível rotacionar o destroyer aqui!");
			} else {
				tabJog.limparPosicaoAntigaDoNavio(destroyerJog);
				destroyerJog.setOrientacao(0);
				tabJog.posicionarNavio(destroyerJog);
				gridPane1.getChildren().remove(destroyerVImagem);
				GridPane.setColumnIndex(destroyerHImagem, destroyerJog.getColuna());
	            GridPane.setRowIndex(destroyerHImagem, destroyerJog.getLinha());
	            gridPane1.getChildren().add(destroyerHImagem);
	            lblPainel.setText("Clique em 'PODE JOGAR' para iniciar o jogo!");
			}
    	}
    }
    
    /**
     * Método acionado ao apertar o botão btnAtirar.
     * <br>
     * Este método é reponsável por controlar o turno do usuário.
     * @param event
     */
    @FXML
    void onClickBtnAtirar(ActionEvent event) {
    	jogador.addJogadas(linhaClicada, colunaClicada);
    	lblJogadasUser.setText("Suas Jogadas: " + jogador.retornaQuantidadeDeJogadas()); 	
    	
    	if(tabMaq.verificarSeAcertouNavio(linhaClicada, colunaClicada)) {
    		if (tabMaq.retornaValor(linhaClicada, colunaClicada) == 1) {
    			corvetaMaq.reduzirTamanho();
    			if (corvetaMaq.getAtivo()) {
					lblPainel.setText("Você atingiu um navio do inimigo!");
				} else {
					if (corvetaMaq.getOrientacao() == 0) {
			            GridPane.setColumnIndex(corvetaHFinalImagem, corvetaMaq.getColuna());
			            GridPane.setRowIndex(corvetaHFinalImagem, corvetaMaq.getLinha());
			            gridPane2.getChildren().add(corvetaHFinalImagem);
					} else {
						GridPane.setColumnIndex(corvetaVFinalImagem, corvetaMaq.getColuna());
			            GridPane.setRowIndex(corvetaVFinalImagem, corvetaMaq.getLinha());
			            gridPane2.getChildren().add(corvetaVFinalImagem);
					}
					lblPainel.setText("Você atingiu e afundou a Corveta do inimigo!");
				}	
    		} else if (tabMaq.retornaValor(linhaClicada, colunaClicada) == 2) {
    			submarinoMaq.reduzirTamanho();
    			if (submarinoMaq.getAtivo()) {
					lblPainel.setText("Você atingiu um navio do inimigo!");
				} else {
					if (submarinoMaq.getOrientacao() == 0) {
			            GridPane.setColumnIndex(submarinoHFinalImagem, submarinoMaq.getColuna());
			            GridPane.setRowIndex(submarinoHFinalImagem, submarinoMaq.getLinha());
			            gridPane2.getChildren().add(submarinoHFinalImagem);
					} else {
						GridPane.setColumnIndex(submarinoVFinalImagem, submarinoMaq.getColuna());
			            GridPane.setRowIndex(submarinoVFinalImagem, submarinoMaq.getLinha());
			            gridPane2.getChildren().add(submarinoVFinalImagem);
					}
					lblPainel.setText("Você atingiu e afundou o Submarino do inimigo!");
				}
    		} else if (tabMaq.retornaValor(linhaClicada, colunaClicada) == 3) {
    			fragataMaq.reduzirTamanho();
				if (fragataMaq.getAtivo()) {
					lblPainel.setText("Você atingiu um navio do inimigo!");
				} else {
					if (fragataMaq.getOrientacao() == 0) {
			            GridPane.setColumnIndex(fragataHFinalImagem, fragataMaq.getColuna());
			            GridPane.setRowIndex(fragataHFinalImagem, fragataMaq.getLinha());
			            gridPane2.getChildren().add(fragataHFinalImagem);
					} else {
						GridPane.setColumnIndex(fragataVFinalImagem, fragataMaq.getColuna());
			            GridPane.setRowIndex(fragataVFinalImagem, fragataMaq.getLinha());
			            gridPane2.getChildren().add(fragataVFinalImagem);
					}
					lblPainel.setText("Você atingiu e afundou a Fragata do inimigo!");
				}
    		} else {
    			destroyerMaq.reduzirTamanho();
				if (destroyerMaq.getAtivo()) {
					lblPainel.setText("Você atingiu um navio do inimigo!");
				} else {
					if (destroyerMaq.getOrientacao() == 0) {
			            GridPane.setColumnIndex(destroyerHFinalImagem, destroyerMaq.getColuna());
			            GridPane.setRowIndex(destroyerHFinalImagem, destroyerMaq.getLinha());
			            gridPane2.getChildren().add(destroyerHFinalImagem);
					} else {
						GridPane.setColumnIndex(destroyerVFinalImagem, destroyerMaq.getColuna());
			            GridPane.setRowIndex(destroyerVFinalImagem, destroyerMaq.getLinha());
			            gridPane2.getChildren().add(destroyerVFinalImagem);
					}
					lblPainel.setText("Você atingiu e afundou o Destroyer do inimigo!");
				}
    		}
    		
    		ultimaCelulaClicada.setStyle("-fx-background-color: #3eb1df; -fx-background-image: url('" + imagemExplosao + "'); -fx-background-size: cover; -fx-border-color: white;");
    		ultimaCelulaClicada.setDisable(true);
    		ultimaCelulaClicada.setId("ativo");

    		//Se o jogador afundar todos os navios do computador, mudar para a TelaFinalJogador, que indica o final do jogo e a vitória do jogador
        	if(!corvetaMaq.getAtivo() && !submarinoMaq.getAtivo() && !fragataMaq.getAtivo() && !destroyerMaq.getAtivo()) {
        		lblPainel.setText("FIM DE JOGO!\nVocê venceu!");
        		btnAtirar.setDisable(true);
        		btnPodeJogar.setDisable(true);
        		gridPane2.setDisable(true);
        	}
    		
        	turnoUsuario = true;
    	} else {
    		ultimaCelulaClicada.setStyle("-fx-background-color: #3eb1df; -fx-background-image: url('" + imagemTiroAgua + "'); -fx-background-size: cover; -fx-border-color: white;");
    		ultimaCelulaClicada.setDisable(true);
    		ultimaCelulaClicada.setId("ativo");
    		lblPainel.setText("Você deu um tiro na água!");
            turnoUsuario = false; // Computador joga
            gridPane2.setDisable(true); //Desativar o tabuleiro do inimigo, para impedir que o usuário jogue
            turnoComputador();
    	}
        
    	btnAtirar.setDisable(true);
    }
    
    /**
     * Método para controlar o turno do computador.
     */
    private void turnoComputador() {
        while (!turnoUsuario) { 
        	int linha = 0, coluna = 0;        	

        	for (int i = 0; i < 4; i++) {
        		linha = random.nextInt(10);
                coluna = random.nextInt(10);
                
                if (tabJog.verificarSeAcertouNavio(linha, coluna)) {
                	break;
                }
        	}
        	
            while (computador.verificarSeJaHouveEssaJogada(linha, coluna)) {                
                for (int i = 0; i < 4; i++) {
            		linha = random.nextInt(10);
                    coluna = random.nextInt(10);
                    
                    if (tabJog.verificarSeAcertouNavio(linha, coluna)) {
                    	break;
                    }
            	}
                
                computador.verificarSeJaHouveEssaJogada(linha, coluna);
            }
            
            computador.addJogadas(linha, coluna);
            lblJogadasMaquina.setText("Jogadas da Máquina: " + computador.retornaQuantidadeDeJogadas());
            
            Pane celula = retornarNodeGrid(linha, coluna, gridPane1);
            
            if (tabJog.verificarSeAcertouNavio(linha, coluna)) {
            	if (tabJog.retornaValor(linha, coluna) == 1) {
        			corvetaJog.reduzirTamanho();
        			if (corvetaJog.getAtivo()) {
        				lblPainel.setText("Computador atingiu sua Corveta!");
    				} else {
    					lblPainel.setText("Computador atingiu e afundou sua Corveta!");
    				}	
        		} else if (tabJog.retornaValor(linha, coluna) == 2) {
        			submarinoJog.reduzirTamanho();
        			if (submarinoJog.getAtivo()) {
        				lblPainel.setText("Computador atingiu seu Submarino!");
    				} else {
    					lblPainel.setText("Computador atingiu e afundou seu Submarino!");
    				}
        		} else if (tabJog.retornaValor(linha, coluna) == 3) {
        			fragataJog.reduzirTamanho();
    				if (fragataJog.getAtivo()) {
    					lblPainel.setText("Computador atingiu sua Fragata!");
    				} else {
    					lblPainel.setText("Computador atingiu e afundou sua Fragata!");
    				}
        		} else {
        			destroyerJog.reduzirTamanho();
    				if (destroyerJog.getAtivo()) {
    					lblPainel.setText("Computador atingiu seu Destroyer!");
    				} else {
    					lblPainel.setText("Computador atingiu e afundou seu Destroyer!");
    				}
        		}
            	
            	ImageView explosao = new ImageView(new Image(getClass().getResource("/img/explosao.png").toExternalForm()));
            	GridPane.setColumnSpan(explosao, 1);
                GridPane.setRowSpan(explosao, 1);
                explosao.setPreserveRatio(true);
                explosao.setFitWidth(30);
                explosao.setFitHeight(30);
                GridPane.setColumnIndex(explosao, coluna);
	            GridPane.setRowIndex(explosao, linha);
	            gridPane1.getChildren().add(explosao);
	            	
            	//Se o computador afundar todos os navios do jogador, mudar para a TelaFinalMaquina, que indica o final do jogo e a vitória do computador
            	if(!corvetaJog.getAtivo() && !submarinoJog.getAtivo() && !fragataJog.getAtivo() && !destroyerJog.getAtivo()) {
            		lblPainel.setText("FIM DE JOGO!\nO computador venceu!");
            		btnAtirar.setDisable(true);
            		btnPodeJogar.setDisable(true);
            		gridPane2.setDisable(true);
            		turnoUsuario = true; // Impede que o computador atire novamente
            	}
            } else {
            	celula.setStyle("-fx-background-color: #3eb1df; -fx-background-image: url('" + imagemTiroAgua + "'); -fx-background-size: cover; -fx-border-color: white;");
            	lblPainel.setText("Computador atirou e atingiu a água!");
                turnoUsuario = true; // Jogador joga
                gridPane2.setDisable(false);
            }
        }
    }

    /**
     * Método acionado ao apertar o botão btnPodeJogar, para impedir o reposicionamento manual dos navios.
     * @param event
     */
    @FXML
    void onClickBtnPodeJogar(ActionEvent event) {
    	lblPainel.setText("Selecione uma célula do oceano inimigo e clique em ATIRAR, para realizar um disparo.");
        gridPane1.setDisable(true);
        gridPane2.setDisable(false);
        btnPodeJogar.setDisable(true); // Desabilitar o botão "Iniciar" após o clique
    }
    
    /**
     * Método para iniciar um novo jogo, ao clicar na opção 'Novo Jogo' do menu Jogo.
     * @param event
     */
    @FXML
    void onClickNovoJogo(ActionEvent event) {
    	MainApp.restart();
    }
    
    /**
     * Método para sair do jogo, ao clicar na opção 'Sair' do menu Jogo.
     * @param event
     */
    @FXML
    void sairDoJogo(ActionEvent event) {
    	Platform.exit();
    }
    
    /**
     * Método para abrir uma janela com instruções de como jogar, ao clicar na opção 'Como jogar' do menu Ajuda.
     * @param event
     */
    @FXML
    void abrirJanelaComoJogar(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/visao/TelaComoJogar.fxml"));
            VBox root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Como Jogar");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
            stage.setScene(new Scene(root));
            stage.resizableProperty().setValue(false);
            stage.showAndWait(); // Mostra a janela e espera até que ela seja fechada
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Método para abrir uma janela com informações do programa, ao clicar na opção 'Sobre' do menu Ajuda.
     * @param event
     */
    @FXML
    void abrirJanelaSobre(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/visao/TelaSobre.fxml"));
            VBox root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Sobre o Programa");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
            stage.setScene(new Scene(root));
            stage.resizableProperty().setValue(false);
            stage.showAndWait(); // Mostra a janela e espera até que ela seja fechada
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}