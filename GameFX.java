package lab7;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameFX extends Application{
	private Game console = new Game();
	private int numEnemies = -1;
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene s = new Scene(new Pane(), 600, 600);
		s.setRoot(welcome(s));
		primaryStage.setScene(s);
		primaryStage.show();
	}

	public Pane welcome(Scene s) {
		Text welcomeTxt = new Text("Welcome to the Dungeon!");
		Text goalTxt = new Text("Your goal is to escape the dungeon by getting to the stairs. \nThe stairs are located at (6,6).");
		ImageView welcomeImage = new ImageView("https://thumbs.gfycat.com/CheerfulEvergreenAlpinegoat-max-1mb.gif");
		welcomeImage.setFitHeight(300);
		welcomeImage.setFitWidth(300);
		Button nextBtn = new Button("NEXT");
		nextBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				s.setRoot(setupPC(s));
			}
		});
		VBox welcomePane = new VBox(20);
		welcomePane.setAlignment(Pos.CENTER);
		welcomePane.getChildren().addAll(welcomeTxt, goalTxt, welcomeImage, nextBtn);
		return welcomePane;
	}

	public Pane setupPC(Scene s) {
		s.getWindow().setHeight(200);
		Text setupPCtxt = new Text("What is your character's name? (Input name then press the next button): ");
		TextField setupPCtf = new TextField();
		Button nextBtn = new Button("NEXT");
		nextBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				console.pc = new PlayableCharacter(setupPCtf.getText());
				s.setRoot(setupEnemies(s));
			}
		});
		HBox setupPCPane = new HBox(20);
		setupPCPane.getChildren().addAll(setupPCtxt, setupPCtf, nextBtn);
		return setupPCPane;
	}

	public Pane setupEnemies(Scene s) {
		Text setupECount = new Text("How many enemies are there in this dungeon (Recommended: 3)");
		TextField setupECountTf = new TextField();
		Button nextBtn = new Button("NEXT");
		nextBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				numEnemies = Integer.parseInt(setupECountTf.getText());
				s.setRoot(setupEnemies2(s));
			}
		});
		HBox setupPane3 = new HBox(20);
		setupPane3.getChildren().addAll(setupECount, setupECountTf, nextBtn);
		return setupPane3;
	}

	public Pane setupEnemies2(Scene s) {
		Text enemyNameTxt = new Text(String.format("What is Enemy %d's name?", console.enemies.size()));
		TextField enemyName = new TextField();
		Button nextBtn = new Button("NEXT");
		nextBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				console.createEachEnemy(enemyName.getText());
				if(console.enemies.size() < numEnemies) {
					s.setRoot(setupEnemies2(s));

				}else {
					s.setRoot(gamePlay(s));
				}
			}
		});
		HBox setupPane3 = new HBox(20);
		setupPane3.getChildren().addAll(enemyNameTxt, enemyName, nextBtn);
		return setupPane3;
	}


	public Pane gamePlay(Scene s) {
		s.getWindow().setHeight(800);
		console.displayBoard();
		GridPane board = new GridPane();
		board.setMaxSize(100, 100);
		board.setMinSize(100, 100);
		board.setAlignment(Pos.CENTER);
		board.setGridLinesVisible(true);
		for(int i = 6; i >= 0; i--) {
			for(int j = 6; j >= 0; j--) {
				if(console.board[i][j].equals("$")) {						
					try {
						Image image = new Image(new FileInputStream("C:\\Users\\Kendall Eberly\\Downloads\\diploma.jpg"));
						Circle c1 = new Circle(40);
						c1.setFill(new ImagePattern(image));
						board.add(c1, j, i);
					} catch (FileNotFoundException e) {				
						e.printStackTrace();
					}

				}			
				else if(console.board[i][j].equals("@")) {
					try {
						Image image = new Image(new FileInputStream("C:\\Users\\Kendall Eberly\\Downloads\\kendall.jpg"));
						Circle c1 = new Circle(40);
						c1.setFill(new ImagePattern(image));
						board.add(c1, j, i);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

				}
				else if(console.board[i][j].equals("&")) {
					try {
						Image image = new Image(new FileInputStream("C:\\Users\\Kendall Eberly\\Downloads\\AndrewAllen-250x250.png"));
						Circle c1 = new Circle(40);
						c1.setFill(new ImagePattern(image));
						board.add(c1, j, i);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

				}
				else {
					Rectangle r1 = new Rectangle(80, 80);
					r1.setFill(Color.SNOW);
					r1.setStroke(Color.BLACK);
					board.add(r1, j, i);
				}
			}
		}
		Button nextBtn = new Button("Exit");
		nextBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				System.exit(0);
			}
		});
		Label title = new Label("Try to escape the Dungeon!");
		BorderPane gameLayout = new BorderPane();
		gameLayout.setCenter(board);
		gameLayout.setBottom(nextBtn);
		gameLayout.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		gameLayout.setLeft(pCControl(s));
		return gameLayout;
	}

	public Pane pCControl(Scene s) {

		TilePane controls = new TilePane();
		Button up = new Button("^");
		up.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				console.pc.move(new Scanner("s"));
				console.checkIfPlayerCanAttack();

				if(console.pc.getLocationX() ==6 && console.pc.getLocationY() ==6) {
					s.setRoot(youWin(s));
				}									
				else if(console.pc.getCurrentHitPoints() <= 0) {
					s.setRoot(youLose(s));				
				}else {
					console.doEnemiesTurn();
					s.setRoot(gamePlay(s));
				}

			}
		});
		Button down = new Button("v");
		down.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				console.pc.move(new Scanner("w"));
				console.checkIfPlayerCanAttack();
				if(console.pc.getLocationX() ==6 && console.pc.getLocationY() ==6) {
					s.setRoot(youWin(s));
				}									
				else if(console.pc.getCurrentHitPoints() <= 0) {
					s.setRoot(youLose(s));				
				}else {
					console.doEnemiesTurn();
					s.setRoot(gamePlay(s));
				}
			}
		});
		Button left = new Button("<");
		left.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				console.pc.move(new Scanner("a"));
				console.checkIfPlayerCanAttack();
				if(console.pc.getLocationX() ==6 && console.pc.getLocationY() ==6) {
					s.setRoot(youWin(s));
				}									
				else if(console.pc.getCurrentHitPoints() <= 0) {
					s.setRoot(youLose(s));				
				}else {
					console.doEnemiesTurn();
					s.setRoot(gamePlay(s));
				}
			}
		});	
		Button right = new Button(">");
		right.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				console.pc.move(new Scanner("d"));
				console.checkIfPlayerCanAttack();
				if(console.pc.getLocationX() ==6 && console.pc.getLocationY() ==6) {
					s.setRoot(youWin(s));
				}									
				else if(console.pc.getCurrentHitPoints() <= 0) {
					s.setRoot(youLose(s));				
				}else {
					console.doEnemiesTurn();
					s.setRoot(gamePlay(s));
				}
			}
		});		
		Button sa = new Button("SA");
		sa.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				console.pc.move(new Scanner("q"));
				console.checkIfPlayerCanAttack();
				if(console.pc.getLocationX() ==6 && console.pc.getLocationY() ==6) {
					s.setRoot(youWin(s));
				}									
				else if(console.pc.getCurrentHitPoints() <= 0) {
					s.setRoot(youLose(s));				
				}else {
					console.doEnemiesTurn();
					s.setRoot(gamePlay(s));
				}
			}
		});	
		controls.getChildren().addAll(up, down, left, right, sa);
		return controls;
	}

	public Pane youWin(Scene s) {
		Text welcomeTxt = new Text("You win!");
		welcomeTxt.setFont(Font.font(25));
		ImageView welcomeImage = new ImageView("C:\\Users\\Kendall Eberly\\Downloads\\youwin.jpg");
		welcomeImage.setFitHeight(250);
		welcomeImage.setFitWidth(280);
		Text newText = new Text("Play again?");
		Button nextBtn = new Button("Play again?");
		nextBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				s.setRoot(welcome(s));
			}
		});
		Button endBtn = new Button("Exit");
		endBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {

				System.exit(0);
			}
		});
		VBox welcomePane = new VBox(20);
		welcomePane.setAlignment(Pos.CENTER);
		welcomePane.getChildren().addAll(welcomeTxt, newText, nextBtn, welcomeImage, endBtn);
		return welcomePane;
	}

	public Pane youLose(Scene s) {
		Text welcomeTxt = new Text("You lose!");
		welcomeTxt.setFont(Font.font(25));
		ImageView welcomeImage = new ImageView("C:\\Users\\Kendall Eberly\\Downloads\\gameover.png");
		welcomeImage.setFitHeight(250);
		welcomeImage.setFitWidth(280);
		Text newText = new Text("Play again?");
		Button nextBtn = new Button("Play again?");
		nextBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				s.setRoot(welcome(s));
			}
		});
		Button endBtn = new Button("Exit");
		endBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				System.exit(0);
			}
		});
		VBox welcomePane = new VBox(20);
		welcomePane.setAlignment(Pos.CENTER);
		welcomePane.getChildren().addAll(welcomeTxt, newText, nextBtn, welcomeImage, endBtn);
		return welcomePane;
	}
}













