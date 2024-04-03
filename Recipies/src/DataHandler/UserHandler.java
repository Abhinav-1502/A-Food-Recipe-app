package DataHandler;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import model.Recipe;
import model.User;

@SuppressWarnings("unused")
public class UserHandler {
//	A mongoClient variable which refers to same object whole session
	private static MongoClient mongoClient = DbConnection.startConnection();
	
//	Method to get users collection from database
	public static MongoCollection<Document> getCollection(){
    	MongoDatabase database = mongoClient.getDatabase("recipe-javafx");
        System.out.println("Connected to database:\n" + database);
        MongoCollection<Document> collection = database.getCollection("users");
        return collection;
    }
	
//  Method to get a user from database, it take userId as parameter, returns a Recipe object
	public static User getUser(String userName) {
		MongoCollection<Document> collection = getCollection();
		Document userDoc = collection.find(eq("userName", userName)).first();
		  
		if(userDoc== null) {
			return null;
		}
	  	User resultUser = docToUser(userDoc);
	  	return resultUser;
	  }
	
//	Method to return all the users object as User List
	public static List<User> getAllUsers(){
		MongoCollection<Document> collection = getCollection();
		MongoCursor<Document> cursor = collection.find().iterator();
		
		List<User> userList = new ArrayList<>();
		try {
            while (cursor.hasNext()) {
                Document userDocument = cursor.next();
                // Convert the Document to a User object
                User user = docToUser(userDocument);
                // Add the User object to the list
                userList.add(user);
            }
        } finally {
            cursor.close();
        }
        return userList;
	}
	
//	Method to add user to DB, takes user object as input and returns a message
	public static String addUser(User user) {
		String message;
		MongoCollection<Document> collection = getCollection();
		try {
			if(collection.find(eq("userName",user.getUserName())).first()!=null){
				return "A User With Same UserName is already present, please change the userId and try again";
			}
			else {
				Document userDocument = userToDoc(user);
				collection.insertOne(userDocument);
				return "Account Created SuccessFully";
			}
		}catch(Exception e) {
    		return "An error has occured while processing your request";
    	}
	}
	
//  Method to update User, Takes user name and updated user object and returns meessage
	public static String updateUser(String userName, User user) {
		MongoCollection<Document> collection = getCollection();
		Document userDoc = userToDoc(user);
    	Document filter = new Document("userName", userName);
    	try{
    		collection.findOneAndUpdate(filter, new Document("$set",userDoc));
    		return "User Update Successfull";
    	}catch(Exception e) {
    		return e.getMessage();
    	}
	}
	
//	Method to delete a user from DB, takes user name as input and retu
	public static String deleteUser(String userName) {
		MongoCollection<Document> collection = getCollection();
    	String message;
    	try {
    		Document result = collection.findOneAndDelete(eq("userName",userName));
    		if(result == null) {
    			message = "No such recipe found to delete";
    		}
    		else {
    			message = "Delete Successfull";
    		}
    	}catch(Exception e){
    		message = e.getMessage();
    	}
    	
    	return message;
	}
	
//	Method to check if user exists, takes user name as input and return boolean
	public static boolean checkIfUserExists(String userName) {
		MongoCollection<Document> collection = getCollection();
    	if( collection.find(eq("userName",userName)).first() != null ) {
    		return true;
    	}
    	return false;
	}
	
//	Method to check if password matches, takes user name as passsword as input
	
	public static boolean checkPasswordMatch(String userName, String password) {
		MongoCollection<Document> collection = getCollection();
		Document userDoc = collection.find(eq("userName",userName)).first();
		User user  = docToUser(userDoc);
		if(user.getPassword().matches(password)) {
			return true;
		}
		return false;
	}
	
//Helper funcitons
	private static Document userToDoc(User user) {
	    Document userDocument = new Document()
	            .append("firstName", user.getFirstName())
	            .append("lastName", user.getLastName())
	            .append("email", user.getEmail())
	            .append("phoneNo", user.getPhoneNo())
	            .append("userName", user.getUserName())
	            .append("password", user.getPassword())
	            .append("seqQuestion", user.getSeqQuestion())
	            .append("seqAnswer", user.getSeqAnswer())
	            .append("recipeIds", user.getRecipeIds());
	
	    return userDocument;
}

@SuppressWarnings("unchecked")
	private static User docToUser(Document userDocument) {
	    User user = new User(); // Assuming User is your model class for User
	
	    if (userDocument != null) {
	        user.setFirstName(userDocument.getString("firstName"));
	        user.setLastName(userDocument.getString("lastName"));
	        user.setEmail(userDocument.getString("email"));
	        user.setPhoneNo(userDocument.getString("phoneNo"));
	        user.setUserName(userDocument.getString("userName"));
	        user.setPassword(userDocument.getString("password"));
	        user.setSeqQuestion(userDocument.getString("seqQuestion"));
	        user.setSeqAnswer(userDocument.getString("seqAnswer"));
	        user.setRecipeIds((List<String>) userDocument.get("recipeIds"));
	    }
	
	    return user;
	}

//  External call actor to test out the method, to be deleted
	public static void externalCallActor() {
		User exampleUser = new User();
        exampleUser.setFirstName("Abhinav");
        exampleUser.setLastName("Chary");
        exampleUser.setEmail("abhinav.doe@example.com");
        exampleUser.setPhoneNo("+616161");
        exampleUser.setUserName("abbu1502");
        exampleUser.setPassword("secretpassword");
        exampleUser.setSeqQuestion("What is your favorite color?");
        exampleUser.setSeqAnswer("Blue");

        // Adding an example recipe id to the user
        exampleUser.addRecipeId("recipe122");
        exampleUser.addRecipeId("recipe63");
        
        System.out.println(addUser(exampleUser));
        
		
	}
	
	
	public static void main(String[] args) {
//			externalCallActor();
		
		
	}
	
}
