package br.ufrn.imd.controle;

/**
 * Esta é a classe controller do arquivo TelaInicial.fxml
 */

import java.io.IOException;
import br.ufrn.imd.MainApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;

public class TelaInicialController {
    @FXML
    private Label lblPainel;

    @FXML
    private Button btnIniciar;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private MenuItem novoJogoMenuItem;

    @FXML
    private MenuItem sairMenuItem;

    @FXML
    private MenuItem comoJogarMenuItem;

    @FXML
    private MenuItem sobreMenuItem;
    
    String wallpaper = getClass().getResource("/img/wallpaper.png").toExternalForm();

    /**
     * Método para realizar a mudança de tela, quando o usuário clicar no botão btnIniciar.
     * @param e
     */
    @FXML
    void onClickBtnIniciar(ActionEvent event) {    	
    	MainApp.mudarTela(2);
    }
    
    /**
     * Método para alterar a cor do botão btnIniciar, quando o mouse estiver dentro do botão.
     * @param event
     */
    @FXML
    void onMouseEntered(MouseEvent event) {
    	btnIniciar.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-background-color: #999999;");
    }
    
    /**
     * Método para alterar a cor do botão btnIniciar, quando o mouse sair do botão.
     * @param event
     */
    @FXML
    void onMouseExited(MouseEvent event) {
    	btnIniciar.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-background-color: #808080;");
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
    
    /**
     * Método para para executar a lógica de inicialização necessária, após a interface ter sido carregada, mas antes de o usuário interagir com ela.
     */
    @FXML
    private void initialize() {
    	anchorPane.setStyle("-fx-background-image: url('" + wallpaper + "'); -fx-background-size: cover; -fx-background-repeat: no-repeat;");
    }
}