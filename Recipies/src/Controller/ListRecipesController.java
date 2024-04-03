package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import DataHandler.RecipeHandler;
import application.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Recipe;
import model.User;

public class ListRecipesController implements Initializable {

    @FXML
    private VBox displayFoodCard;

    @FXML
    private ImageView displayFoodImage;

    @FXML
    private Label displayFoodName;

    @FXML
    private Button goBackBtn;

    @FXML
    private ScrollPane scrollpaneGrid;
    
    @FXML
    private GridPane listGrid;
    
    @FXML
    private Label bigCardRecipeTitle;
    @FXML
    private ImageView bigCardRecipeImage;
    @FXML
    private Label bigCardChef;
    
    @FXML
    private Label notPostedLabel;
    
    @FXML
    private Text hiddenRecipeId;
    
    @FXML
    private Button openRecipeButton;
    
    @FXML
    private Button showAllRecipesBtn;

    @FXML
    private Button showYourRecipeBtn;
    
    private List<Recipe> recipeList; 
	private User sessionUser;
    
    private MyListener myListener;
    
    @SuppressWarnings("exports")
	public void setUserListPage(User gotUser) {
    	this.sessionUser = gotUser;
    }
    
    @FXML
    void handleGoBack(ActionEvent event) {
    	goBackBtn.getScene().getWindow().hide();
    	Stage stage = new Stage();
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/fxml/Dashboard.fxml"));
			Parent root = fxmlloader.load();
			HomePageController hpController = fxmlloader.getController();
			hpController.setUserHomePage(sessionUser);
    		Scene scene = new Scene(root);
    		
    		stage.setScene(scene);
    		stage.show();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void openRecipeDisplay(ActionEvent event) {
    	openRecipeButton.getScene().getWindow().hide();
    	Stage displayRecipe = new Stage();
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/fxml/RecipeDisplay.fxml"));
			Parent root = fxmlloader.load();
			
			RecipeDisplayController recipeController = fxmlloader.getController();
			
			recipeController.setUserRecipePage(this.sessionUser);
			String recipeId = hiddenRecipeId.getText();
			Recipe recipeToBeDisplayed = RecipeHandler.getRecipe(recipeId);
			
			recipeController.displayRecipe(recipeToBeDisplayed);
			recipeController.checkIfYours();
    		
    		Scene scene = new Scene(root);
			displayRecipe.setScene(scene);
			displayRecipe.show();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void displayUserRecipes(ActionEvent event) {
    	if(sessionUser.getRecipeIds().isEmpty()) {
    		notPostedLabel.setPrefHeight(80);
    		
    		scrollpaneGrid.setVisible(false);
    	}else {
    		listGrid.getChildren().clear();
    		displayRecipes(true);
    	}
    	showYourRecipeBtn.setPrefWidth(0);
    	showAllRecipesBtn.setPrefWidth(180);
    }
    
    @FXML
    void showAllRecipes(ActionEvent event) {
    	scrollpaneGrid.setVisible(true);
    	displayRecipes(false);
    	showYourRecipeBtn.setPrefWidth(180);
    	showAllRecipesBtn.setPrefWidth(0);
    }
    
    void bigCardDisplay(Recipe recipe) {
    	bigCardRecipeTitle.setText(recipe.getName());
    	
    	Image foodImage;
    	try {
    		foodImage = new Image(getClass().getResourceAsStream("/assets/"+recipe.getImage()));
    	}catch(Exception e) {
    		foodImage = new Image(getClass().getResourceAsStream("/assets/recipePlaceHolder.jpg"));
    	}
    	
    	bigCardRecipeImage.setImage(foodImage);
    	bigCardChef.setText(recipe.getAuthor());
    	hiddenRecipeId.setText(recipe.getRecipeId());
    }

    public void displayRecipes(boolean usersOnly) {
    	notPostedLabel.setPrefHeight(0);
		int row = 1;
		int col = 0	;
		List<String> userRecipes = sessionUser.getRecipeIds();
		if(usersOnly) {
			recipeList.clear();
			for(int i=0; i<userRecipes.size();i++) {
				recipeList.add(RecipeHandler.getRecipe(userRecipes.get(i)));
			}
		}else {
			recipeList = RecipeHandler.getAllRecipes();
		}
		
		
		if(recipeList.size()>0) {
			bigCardDisplay(recipeList.get(0));
			myListener = new MyListener() {

				@Override
				public void onClickListener(Recipe recipe) {
					bigCardDisplay(recipe);
				}
			};
		}
		// Clear existing children
		listGrid.getChildren().clear();
		// Reset row and column constraints
		listGrid.getRowConstraints().clear();
		listGrid.getColumnConstraints().clear();
		try {
			for(int i=0; i<recipeList.size(); i++) {
				FXMLLoader fxmlloader = new FXMLLoader();
				fxmlloader.setLocation(getClass().getResource("/fxml/foodCard.fxml"));
				AnchorPane anchorPane = fxmlloader.load();
				
				foodCardController foodCard = fxmlloader.getController();
				foodCard.setData(recipeList.get(i), myListener);
				
				if(col == 4) {
					col =0;
					row++;
				}
	    		listGrid.add(anchorPane, col++, row);
			}
		}catch(IOException e) {
    				e.printStackTrace();
		}
    }
    
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}

