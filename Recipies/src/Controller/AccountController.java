package Controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

public class AccountController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button changePasswordBtn;

    @FXML
    private Button delteAccountBtn;
    
    @FXML
    private Button logoutBtn;
    
    @FXML
    private Button dashboardBtn;

    @FXML
    private Label emailId;

    @FXML
    private Label fullName;

    @FXML
    private Label phone;

    @FXML
    private Label userName;
    
    private User sessionUser;

    @SuppressWarnings("exports")
	public void setUserAccountPage(User gotUser) {
    	this.sessionUser = gotUser;
    	
    	userName.setText(sessionUser.getUserName());
    	fullName.setText(sessionUser.getFirstName()+" "+sessionUser.getLastName());
    	emailId.setText(sessionUser.getEmail());
    	phone.setText(sessionUser.getPhoneNo());
    }
    
    @FXML
    void changePasswordHandler(ActionEvent event) {
    	
    }

    @FXML
    void dashboardHandler(ActionEvent event) {
    	dashboardBtn.getScene().getWindow().hide();
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
    void deleteAccountHandler(ActionEvent event) {
    	
    }

    @FXML
    void logoutHandler(ActionEvent event) {
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
		// TODO Auto-generated method stub
		
	}
}
