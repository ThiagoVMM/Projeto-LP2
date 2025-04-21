module Projeto_BatalhaNaval {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	
	exports br.ufrn.imd.controle to javafx.fxml;
	
	opens br.ufrn.imd.controle to javafx.fxml;
	
	opens br.ufrn.imd to javafx.graphics, javafx.fxml;
}
