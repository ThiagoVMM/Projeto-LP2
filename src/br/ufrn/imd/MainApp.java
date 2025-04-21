package br.ufrn.imd;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class MainApp extends Application {
	
	private static Stage stage;
	
	private static Scene telaInicial;
	private static Scene telaJogo;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		
		primaryStage.setTitle("Batalha Naval");
		
		Parent fxmlTelaInicial = FXMLLoader.load(this.getClass().getResource("/br/ufrn/imd/visao/TelaInicial.fxml"));
		telaInicial = new Scene(fxmlTelaInicial);
		
		
		Parent fxmlJogo = FXMLLoader.load(this.getClass().getResource("/br/ufrn/imd/visao/TelaJogo.fxml"));
		telaJogo = new Scene(fxmlJogo);
		

		primaryStage.setScene(telaInicial);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
		
		primaryStage.resizableProperty().setValue(Boolean.FALSE);
		primaryStage.show();
	}
	
	public static void mudarTela(int tela) {
    	switch (tela) {
    		case 1:
    			stage.setScene(telaInicial);
    			break;
    		case 2:
    			stage.setScene(telaJogo);
    			break;
    	}
    }
	
	public static void restart() {
        stage.close();
        Platform.runLater(() -> {
            try {
                new MainApp().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
