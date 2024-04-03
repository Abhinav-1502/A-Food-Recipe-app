package Controller;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

import DataHandler.RecipeHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Recipe;
import model.User;

@SuppressWarnings("unused")
public class HomePageController implements Initializable{
    @FXML
    private Button postRecipeBtn;

    @FXML
    private Button showAllRecipesBtn;
    
    @FXML
    private Button accountBtn;
   
    @FXML
    private Label sessionUserLabel;
    
    @FXML
    private Button logoutBtn;
    
    private User sessionUser;
     
    
    @SuppressWarnings("exports")
	public void setUserHomePage(User gotUser) {
    	this.sessionUser = gotUser;

    	sessionUserLabel.setText(sessionUser.getFirstName());
    }

    @FXML
    void listAllRecipes(ActionEvent event) {
    	showAllRecipesBtn.getScene().getWindow().hide();
    	Stage stage = new Stage();
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/fxml/ListRecipes.fxml"));
			Parent root = fxmlloader.load();
			
			ListRecipesController lrController = fxmlloader.getController();
			lrController.setUserListPage(this.sessionUser);
			lrController.displayRecipes(false);
    		Scene scene = new Scene(root);
    		

            // Set the stage to full screen
    		stage.setMaximized(true);
    		
    		stage.setScene(scene);
    		stage.show();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void postRecipe(ActionEvent event) {
    	postRecipeBtn.getScene().getWindow().hide();
    	Stage stage = new Stage();
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/fxml/PostRecipe.fxml"));
			Parent root = fxmlloader.load();
			PostRecipeController prController = fxmlloader.getController();
			prController.setUserPostRecipe(sessionUser);
			
    		Scene scene = new Scene(root);
   
    		stage.setScene(scene);
    		stage.show();
    	}catch(Exception e) {
    		e.printStackTrace(); 
    	}
    }
    
    @FXML
    void accountHandler(ActionEvent event) {
    	accountBtn.getScene().getWindow().hide();
    	Stage stage = new Stage();
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/fxml/Account.fxml"));
			Parent root = fxmlloader.load();
			
			AccountController acController = fxmlloader.getController();
			acController.setUserAccountPage(sessionUser);

    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}catch(Exception e) {
    		e.printStackTrace(); 
    	}
    }
    
    @FXML
    void logoutUser(ActionEvent event) {
    	logoutBtn.getScene().getWindow().hide();
    	Stage stage = new Stage();
    	try {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/fxml/LoginPage.fxml"));
			Parent root = fxmlloader.load();
    		Scene scene = new Scene(root);
    		
    		stage.setScene(scene);
    		stage.show();
    	}catch(Exception e) {
    		e.printStackTrace(); 
    	}
    }	
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
