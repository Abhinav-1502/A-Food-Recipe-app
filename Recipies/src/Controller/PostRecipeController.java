package Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import DataHandler.RecipeHandler;
import DataHandler.UserHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Recipe;
import model.User;

@SuppressWarnings("unused")
public class PostRecipeController implements Initializable {
	
    @FXML
    private TextField cookTimeTF;

    @FXML
    private Button editIngredientsBtn;

    @FXML
    private Text ingredientsSubmitted;

    @FXML
    private TextArea ingredientsTB;

    @FXML
    private TextArea instructionsTB;
    
    @FXML
    private Button listIngredientsBtn;

    @FXML
    private TextField prepTimeTF;

    @FXML
    private TextField recipeIdTF;

    @FXML
    private TextField recipeNameTF;

    @SuppressWarnings("rawtypes")
	@FXML
    private ComboBox timeCB1;

    @SuppressWarnings("rawtypes")
	@FXML
    private ComboBox timeCB2;

    @FXML
    private Text validTick;
    
    @FXML
    private Hyperlink goBackLink;
    
    @FXML
    private ImageView recipeImage;
    
    final FileChooser fc = new FileChooser();
    
    private boolean isUpdate = false;
    
    private User sessionUser;
    
    private Recipe updateRecipe;
    Path confirmRecipeImage;
    String imageExtension;
    
    @SuppressWarnings({ "unchecked", "exports" })
	public void setUpdate(Recipe updateRecipe) {
    	this.isUpdate = true;
    	
//    	Set Previus data to recipe
    	recipeNameTF.setText(updateRecipe.getName());
    	
    	String prepTime = updateRecipe.getPrepTime();
    	String cookTime = updateRecipe.getCookTime();
    	if(prepTime.endsWith("Minutes")) {
    		timeCB1.setValue("Minutes");
    	}
    	else {
    		timeCB1.setValue("Hours");
    	}
    	if(cookTime.endsWith("Minutes")) {
    		timeCB2.setValue("Minutes");
    	}
    	else {
    		timeCB2.setValue("Hours");
    	}
    	
    	
    	prepTimeTF.setText(extractPrefixNumber(prepTime));
    	cookTimeTF.setText(extractPrefixNumber(cookTime));
    	recipeIdTF.setText(updateRecipe.getRecipeId());
    	ingredientsTB.setText(arrayToString(updateRecipe.getIngredients()));
    	Image foodImage = new Image(getClass().getResourceAsStream("/assets/"+updateRecipe.getImage()));
    	recipeImage.setImage(foodImage);
    	instructionsTB.setText(updateRecipe.getInstructions());
    	
    	recipeIdTF.setDisable(true);
    }
    
    @SuppressWarnings("exports")
	public void setUserPostRecipe(User gotUser) {
    	this.sessionUser = gotUser;
    }
    
    private String extractPrefixNumber(String input) {
        StringBuilder numericalPrefix = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                numericalPrefix.append(c);
            } else {
                break;
            }
        }
        return numericalPrefix.toString();
    }
    
//  Helper Functions for handling submit
    private boolean areAllFieldsEntered() {
        // Check if all text fields and text areas have non-empty text
    	if(recipeNameTF.getText().isEmpty() ||
                recipeIdTF.getText().isEmpty() ||
                prepTimeTF.getText().isEmpty() ||
                cookTimeTF.getText().isEmpty() ||
                ingredientsTB.getText().isEmpty() ||
                instructionsTB.getText().isEmpty()) {
    		showAlert("Enter all the fields");
    		return false;
    	}else if(recipeNameTF.getText().length()<6){
    		showAlert("Recipe Name should be longer than 5 characters");
    		return false;
    	}
    	else if(instructionsTB.getText().length()<50) {
    		showAlert("Please be more descriptive on the instructions (Enter more characters)");
    		return false;
    	}
    	return true;
    }
    
    private boolean checkIfIdExists() {
    	String checkRecipeId = recipeIdTF.getText();
    	if(RecipeHandler.checkIfAvailable(checkRecipeId)) {
    		showAlert("Recipe Id Already Exists Please Try a Different Id");
    		recipeIdTF.clear();
    		return true;
    	}
    	return false;
    }
    
    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return ""; // No file extension found
    }
    
    private boolean copyImage() {
    	imageExtension = getFileExtension(confirmRecipeImage.toString());
    	String destinationPath = "./src/assets/";
    	Path destinationPath1 = Paths.get(destinationPath+recipeNameTF.getText()+"."+imageExtension);
    	try {
			Files.copy(confirmRecipeImage, destinationPath1, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Copy Successful");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

    	return true;
    }
    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @SuppressWarnings("unchecked")
	private Recipe getData() {
    	Recipe recipe = new Recipe();
    	recipe.setUserName(sessionUser.getUserName());
    	recipe.setRecipeId(recipeIdTF.getText());
    	recipe.setAuthor(sessionUser.getFirstName());
    	recipe.setName(recipeNameTF.getText());
    	
    	if(timeCB1.getValue()==null) {
    		timeCB1.setValue("Hours");
    	}
    	recipe.setPrepTime(prepTimeTF.getText()+timeCB1.getValue().toString());
    	if(timeCB2.getValue()==null) {
    		timeCB2.setValue("Hours");
    	}
    	recipe.setCookTime(cookTimeTF.getText()+timeCB2.getValue().toString());
    	
    	
    	List<String> ingridientsArr = stringToArrayList(ingredientsTB.getText());
    	recipe.setIngredients(ingridientsArr);
    	
    	
    	recipe.setInstructions(instructionsTB.getText());
    	return recipe;
    }
    @SuppressWarnings("exports")
	public Recipe setImageToRecipe(Recipe recipe) {
    	if(!(confirmRecipeImage==null)) {
    		copyImage();
    		recipe.setImage(recipeNameTF.getText()+"."+imageExtension);
    		
			}
    	else {
    		recipe.setImage("recipePlaceHolder.jpg");
    	}
    	return recipe;
    }
    
    @SuppressWarnings("exports")
	public static boolean displayRecipeSummary(Recipe recipe) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Recipe Summary");
        alert.setHeaderText("Recipe Details");

        StringBuilder summary = new StringBuilder();
        summary.append("Recipe Name: ").append(recipe.getName()).append("\n");
        summary.append("Prep Time: ").append(recipe.getPrepTime()).append("\n");
        summary.append("Cook Time: ").append(recipe.getCookTime()).append("\n");
        summary.append("Ingredients: ").append(recipe.getIngredients()).append("\n");
        summary.append("Instructions: ").append(recipe.getInstructions());

        alert.setContentText(summary.toString());

        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        // Show the Alert and wait for user's response
        Optional<ButtonType> result = alert.showAndWait();

        // Check which button was clicked
        if (result.isPresent() && result.get() == buttonTypeOK) {
            return true;
            // Perform the action for OK
        } else {
            return false;
            // Perform the action for Cancel
        }
    }
    
    

    @FXML
    void submtiForm(ActionEvent event) {
    	if(isUpdate) {
    		Recipe recipe = getData();
    		if(displayRecipeSummary(recipe)) {
    			recipe = setImageToRecipe(recipe);
    			String message = RecipeHandler.updateRecipe(recipe, recipe.getRecipeId());
        		showAlert(message);
    		}
    		
    	}else {
    		if(areAllFieldsEntered() && !checkIfIdExists()) {
        		showAlert("All fields are entered");
        		Recipe recipe = getData();
        		if(displayRecipeSummary(recipe)) {
            		recipe = setImageToRecipe(recipe);
            		sessionUser.addRecipeId(recipe.getRecipeId());
            		UserHandler.updateUser(sessionUser.getUserName(), sessionUser);
            		String message = RecipeHandler.addRecipe(recipe);
            		showAlert(message);
        			
        		}else {
        			showAlert("Post Cancelled");
        		}
        	}
    	}
    	
    	
    }
    
    @FXML
    void checkIdAvailability(ActionEvent event) {
    	if(!checkIfIdExists()) {
    		validTick.setVisible(true);
    	}
    }
    
    @FXML
    void listIngredientsListener(ActionEvent event) {
    	ingredientsSubmitted.setVisible(true);
    	String ingredients = ingredientsTB.getText();
    	ingredientsSubmitted.setFont(Font.font("System", 16));
    	ingredientsSubmitted.setText(stringToStringList(ingredients));
    	ingredientsTB.setVisible(false);
    }
    
    @FXML
    void editIngredientsListener(ActionEvent event) {
    	ingredientsSubmitted.setVisible(false);
    	ingredientsSubmitted.setText("");
    	ingredientsTB.setVisible(true);
    }
    
    @FXML
    void selectImageHandler(ActionEvent event) {
    	fc.setTitle("Select Recipe Picture");
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.gif)", "*.png", "*.jpg", "*.jpeg", "*.gif");
    	
    	fc.getExtensionFilters().add(extFilter);
    	File file = fc.showOpenDialog(null);
    	String imagePath = file.getAbsolutePath();
    	Image image = new Image(file.toURI().toString());
    	recipeImage.setImage(image);
    	confirmRecipeImage = file.toPath();
    }
    
    @FXML
    void handleGoBack(ActionEvent event) {
    	goBackLink.getScene().getWindow().hide();
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
    
   
    
//    Method to convert String of ingredients separated by to array of strings and vise ver
    public List<String> stringToArrayList(String ingredients){
    	// Split the string using commas
        String[] parts = ingredients.split(",");
        // Create an ArrayList to store the parts
        ArrayList<String> ingredientsList = new ArrayList<>();
        // Add the parts to the ArrayList
        for (String part : parts) {
        	ingredientsList.add(part);
        }
    	return ingredientsList;
    }
    
//    converts string seperated by commas to a single string that can print as a list 
    public String stringToStringList(String ingredients) {
    	// Split the string into an array of steps
        String[] stepsArray = ingredients.split(",");
        // Use a StringBuilder to build the formatted string
        StringBuilder formattedString = new StringBuilder();
        // Append the steps with numbers
        for (int i = 0; i < stepsArray.length; i++) {
            formattedString.append((i + 1)).append(". ").append(stepsArray[i]);
            // Add newline character after each step except the last one
            if (i < stepsArray.length - 1) {
                formattedString.append("\n");
            }
        }
        return formattedString.toString();
    }
//    convert array of strings to a single string seperated by commas
    public String arrayToString(List<String> ingredientsList) {
    	return String.join(",", ingredientsList);
    }
    
//    Text Formatters for text fields and text ares
    TextFormatter<String> numOnly1 = new TextFormatter<>(change -> {
        if (!change.getText().matches("\\d*")) {
            change.setText(""); // Remove non-numeric characters
        }
        return change;
    });
    
    TextFormatter<String> numOnly2 = new TextFormatter<>(change -> {
        if (!change.getText().matches("\\d*")) {
            change.setText(""); // Remove non-numeric characters
        }
        return change;
    });
    
    // Use a TextFormatter to allow only characters and commas
    UnaryOperator<TextFormatter.Change> filter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("[a-zA-Z,\\s]*")) {
            return change;
        } else {
            return null; // Reject the change
        }
    };
    TextFormatter<String> charAndCommasOnly = new TextFormatter<>(filter);
    
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<String> timeItems = new ArrayList <> (); 
		timeItems.add("Hours");
		timeItems.add("Minutes");
		timeCB1.getItems().addAll(timeItems);
		timeCB2.getItems().addAll(timeItems);
		prepTimeTF.setTextFormatter(numOnly1);
		cookTimeTF.setTextFormatter(numOnly2);
		
		ingredientsTB.setTextFormatter(charAndCommasOnly);
		
	}

	
}
