package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Recipe;

public class foodCardController implements Initializable{
	
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label authorLabel;

    @FXML
    private Label cookTimeLabel;

    @FXML
    private ImageView foodImage;
    @FXML
    void cardClick(MouseEvent event) {
    	myListener.onClickListener(recipeOfCard);
    }
    
    @FXML
    private Label prepTimelLabel;

    @FXML
    private Label recipeTitle;
    private Recipe recipeOfCard;
    
    private MyListener myListener;
    
    @SuppressWarnings("exports")
	public void setData(Recipe recipe, MyListener myListener) {
    	this.recipeOfCard = recipe;
    	this.myListener = myListener;
    	recipeTitle.setText(recipeOfCard.getName());
    	authorLabel.setText("by "+recipeOfCard.getAuthor());
    	prepTimelLabel.setText("Prep Time: "+recipeOfCard.getPrepTime());
    	cookTimeLabel.setText("Cook Time: "+recipeOfCard.getCookTime());
    	
    	
    	Image recipeImage;
    	try {
            // Attempt to load the image
            recipeImage = new Image(getClass().getResourceAsStream("/assets/" + recipeOfCard.getImage()));
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            recipeImage = new Image(getClass().getResourceAsStream("/assets/recipePlaceHolder.jpg"));
        }
    	foodImage.setImage(recipeImage);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
    
}