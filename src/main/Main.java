package main;

import javafx.application.Application;
import javafx.stage.Stage;
import jgame.JGame;
import main.GGame.GGame;

public class Main extends Application {
	@Override 
	public void start(Stage stage) {
		JGame jgame = new GGame(stage);
//		tgame.setDebugMode(true);
//		tgame.initialize(stage);
//		tgame.start();
		
//		ggame.setDebugMode(true);
//		ggame.initialize(stage);
//		ggame.start();
		
	   } 
   public static void main(String args[]){ 
      launch(args); 
   }
}
