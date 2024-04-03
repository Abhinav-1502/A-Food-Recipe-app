package Controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import DataHandler.RecipeHandler;
import DataHandler.UserHandler;
import model.Recipe;
import model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings({ "exports", "unused" })
public class RecipeDisplayController implements Initializable{
 
    @FXML
    private TextArea commentBox;

    @FXML
    private Button commentButton;

    @FXML
    private GridPane commentsDisplayGrid;

    @FXML
    private Text complexity;

    @FXML
    private Text cookTime;

    @FXML
    private VBox ingredientsVbox;

    @FXML
    private VBox instructionsVbox;

    @FXML
    private Text prepTime;

    @FXML
    private Text recipeHeader;
    
    @FXML
    private Text instructionsTextBox;
    
    @FXML
    private GridPane ingredientsGrid;

    @FXML
    private Label recipeTitle;

    @FXML
    private ImageView recipe_image;
    
    @FXML
    private Hyperlink goBackLink;
    
    @FXML
    private Button editRecipeBtn;
    
    @FXML
    private Button deleteBtn;
    
    @FXML
    private Label yourRecipeLabel;
    
    
    private Recipe presentRecipe;
    
    private User sessionUser;
    
    
    public void setUserRecipePage(User gotUser) {
    	this.sessionUser = gotUser;
    }
    
    public void setRecipe(Recipe recipe) {
    	this.presentRecipe = recipe;
    }
    
    public void checkIfYours() {
    	List<String> usersRecipes = sessionUser.getRecipeIds();
    	if(usersRecipes.contains(presentRecipe.getRecipeId())) {
    		commentBox.setVisible(false);
    		commentBox.setPrefHeight(0);
    		commentButton.setVisible(false);
    		commentButton.setPrefHeight(0);
    		editRecipeBtn.setVisible(true);
    		deleteBtn.setVisible(true);
    		yourRecipeLabel.setVisible(true);
    	}
    }
    
    
    
    @FXML
    void handleGoBack(ActionEvent event) {
    	goBackLink.getScene().getWindow().hide();
    	openRecipeListPage();
    }
    
    public void openRecipeListPage() {
    	Stage stage = new Stage();
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/fxml/ListRecipes.fxml"));
			Parent root = fxmlloader.load();
			ListRecipesController lrController = fxmlloader.getController();
			lrController.setUserListPage(this.sessionUser);
			lrController.displayRecipes(false);
    		Scene scene = new Scene(root);
    		
    		stage.setMaximized(true);
    		
    		stage.setScene(scene);
    		stage.show();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
	public void displayRecipe(Recipe recipe) {
    	setRecipe(recipe);
    	recipeTitle.setText(presentRecipe.getName());
    	recipeHeader.setText(recipe.getName()+" by "+presentRecipe.getAuthor());
    	prepTime.setText("Prep Time:"+presentRecipe.getPrepTime());
    	cookTime.setText("Cook Time:"+presentRecipe.getCookTime());
    	instructionsTextBox.setText(presentRecipe.getInstructions());
    	
//    	Loads a placeholder image if the image is failed to load
    	Image foodImage;
    	try {
    		foodImage = new Image(getClass().getResourceAsStream("/assets/"+presentRecipe.getImage()));
    	}catch(Exception e) {
    		foodImage = new Image(getClass().getResourceAsStream("/assets/recipePlaceHolder.jpg"));
    	}
    	
    	recipe_image.setImage(foodImage);
    	
    	int col = 0;
    	int row = 0;
    	List<String> ingredients = presentRecipe.getIngredients();
    	for(int i =0; i< ingredients.size(); i++) {
    		Text ingredient = new Text();
    		ingredient.setText("> "+ingredients.get(i));
    		ingredient.setFont(Font.font("System", 25));
    		ingredientsGrid.add(ingredient, col++, row);
    		
    		if(col> 5) {
    			col = 0;
    			row ++;
    		}
    	}
    	
    	
    	int commentRows = 1;
    	for(int i=0; i<recipe.getComments().size();i++) {
//        	Creating a text node with comment text
    		Text comment = new Text();
        	comment.setText(recipe.getComments().get(i));
        	comment.setFont(Font.font("System", 28));
//    		Adding text node to new grid row
        	commentsDisplayGrid.add(comment, 0, commentRows++);
//        	Adding a seperator for next comment
        	Separator sep = new Separator();
    		sep.setOrientation(Orientation.HORIZONTAL);
    		commentsDisplayGrid.add(sep, 0, commentRows++);
    	}
    }
    
    @FXML
    void editRecipe(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Edit Confirmation");
        alert.setHeaderText("Edit Confirmation?");
        alert.setContentText("Are you sure you want to edit this recipe");
        
        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        
     // Check which button was clicked
        if (result.isPresent() && result.get() == buttonTypeOK) {
            openPostRecipe();
        } 
    }
    
    public void openPostRecipe() {
    	editRecipeBtn.getScene().getWindow().hide();
    	Stage stage = new Stage();
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/fxml/PostRecipe.fxml"));
			Parent root = fxmlloader.load();
			PostRecipeController prController = fxmlloader.getController();
			prController.setUserPostRecipe(sessionUser);
			prController.setUpdate(presentRecipe);
    		Scene scene = new Scene(root);
    		
    		stage.setScene(scene);
    		stage.show();
    	}catch(Exception e) {
    		e.printStackTrace(); 
    	}
    }
    
    @FXML
    void deleteRecipe(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Delete Confirmation?");
        alert.setContentText("Are you sure you want to delete this recipe");
        
        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        
     // Check which button was clicked
        if (result.isPresent() && result.get() == buttonTypeOK) {
            deleteThisRecipe();
            
        } 
    }
    
    public void deleteThisRecipe() {
    	//Delete Recipe From DB
    	String message = RecipeHandler.deleteRecipe(presentRecipe.getRecipeId());
    	System.out.println("\n"+message);
    	
    	//Delete Recipe Image from Assets -TBD
    	Path imagePath = Paths.get("./src/assets/"+presentRecipe.getImage());
    	try {
            // Delete the file
            Files.delete(imagePath);
            System.out.println("Image deleted successfully!");
        } catch (NoSuchFileException e) {
            System.err.println("Image file does not exist: " + e.getMessage());
        } catch (DirectoryNotEmptyException e) {
            System.err.println("Image file is a directory: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error deleting the file: " + e.getMessage());
        }
    			

    	//Delete RecipeId from User's recipeIDs
    	sessionUser.removeRecipeId(presentRecipe.getRecipeId());
    	System.out.println(UserHandler.updateUser(sessionUser.getUserName(), sessionUser)+"\n");
    	
    	//Alert Delete Successful
    	CommonMethods.showAlert(message);
    	//goBackToAllRecipies
    	deleteBtn.getScene().getWindow().hide();
    	openRecipeListPage();
    	
    }
    
    
    
    public void addCommentToDB(String comment) {
    	presentRecipe.addComment(comment);
    	String message = RecipeHandler.updateRecipe(presentRecipe, presentRecipe.getRecipeId());
    	if(message.matches("Update Successfull")) {
    		CommonMethods.showAlert("Comment Added!");
    	}else {
    		CommonMethods.showAlert("Error Occured While Adding Comment");
    	}
    }
    
    public void handleComments() {
    	addCommentToDB(commentBox.getText());
//    	Creating a text node with comment text
    	Text comment = new Text();
		comment.setText(commentBox.getText());
		comment.setFont(Font.font("System", 28));
//		Adding text node to new grid row
		int commentRows = commentsDisplayGrid.getRowCount();
    	commentsDisplayGrid.add(comment, 0, commentRows++);
//    	Adding a seperator for next comment
    	Separator sep = new Separator();
		sep.setOrientation(Orientation.HORIZONTAL);
		commentsDisplayGrid.add(sep, 0, commentRows++);
		
    	commentBox.clear();
    }
    
    public void externalClassActor() {
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
    
    
}
