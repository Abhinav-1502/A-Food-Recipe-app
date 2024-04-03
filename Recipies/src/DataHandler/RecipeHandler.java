package DataHandler;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import model.Recipe;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@SuppressWarnings("unused")
@SuppressWarnings("unused")
public class RecipeHandler{
//	A mongoClient variable which refers to same object whole session
	private static MongoClient mongoClient = DbConnection.startConnection();
	
    public static MongoCollection<Document> getCollection(){
    	MongoDatabase database = mongoClient.getDatabase("recipe-javafx");
        System.out.println("Connected to database:\n" + database);
        MongoCollection<Document> collection = database.getCollection("recipe");
        return collection;
    }
   
 
//    function to get a recipe from database, it take recipeId as parameter, returns a Recipe object
    public static Recipe getRecipe(String recipeId) {
    	MongoCollection<Document> collection = getCollection();
		Document recipeDoc = collection.find(eq("recipeId", recipeId)).first();
		if(recipeDoc== null) {
			return null;
		}
    	Recipe resultRecipe = docToRecipe(recipeDoc);
    	
    	return resultRecipe; 
    }
     
//    function to get all the recipes, it take s
    public static List<Recipe> getAllRecipes() {
    	MongoCollection<Document> collection = getCollection();
    	MongoCursor<Document> cursor = collection.find().iterator();

        // Create a List to store Recipe objects
        List<Recipe> recipeList = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document recipeDocument = cursor.next();

                // Convert the Document to a Recipe object
                Recipe recipe = docToRecipe(recipeDocument);

                // Add the Recipe object to the list
                recipeList.add(recipe);
            }
        } finally {
            cursor.close();
        }
        return recipeList;
    }
    
// function to add a recipe to recipe collection, it takes Database Collection object as parameter to work
    public static String addRecipe(Recipe recipe) {
    	MongoCollection<Document> collection = getCollection();
    	
    	try {
    		if(collection.find(eq("recipeId", recipe.getRecipeId())).first()!=null) {
    			return "A recipe with same recipeId is already present, please change the recipeId and try again";
    		}
    		else {
    			Document recipeDocument = recipeToDoc(recipe);
                // Insert the Document into the collection
               collection.insertOne(recipeDocument);
               return "Recipe added Successfully";
    		}
    	}catch(Exception e) {
    		return "An error has occured while processing your request";
    	}
    	 
    }
    
	public static String updateRecipe(Recipe recipe, String recipeId) {
    	MongoCollection<Document> collection = getCollection();
    	
    	Document recipDoc = recipeToDoc(recipe);
    	Document filter = new Document("recipeId", recipeId);
    	try{
    		collection.findOneAndUpdate(filter, new Document("$set",recipDoc));
    		return "Update Successfull";
    	}catch(Exception e) {
    		return e.getMessage();
    	}
    	
    }
    
    public static String deleteRecipe(String recipeId) {
    	MongoCollection<Document> collection = getCollection();
    	String message;
    	try {
    		Document doc = new Document("recipeId", recipeId);
    		Document result = (Document) collection.findOneAndDelete(doc);
    		if(result == null) {
    			message = "No such recipe found to delete";
    		}
    		else {
    			message = "Recipe Delete Successfull";
    		}
    	}catch(Exception e){
    		message = e.getMessage();
    	}
    	
    	return message;
    }
    
    public static boolean checkIfAvailable(String recipeId) {
    	MongoCollection<Document> collection = getCollection();
    	if( collection.find(eq("recipeId",recipeId)).first() != null ) {
    		return true;
    	}
    	return false;
    }
    
    public static void externalCallActor() {
    	Recipe sampleRecipe = getRecipe("69");
    	sampleRecipe.addComment("Burra padu ra mahesha");
    	
    	System.out.println(updateRecipe(sampleRecipe, "69"));
    	
    	
    }
    
    
//    Helper funcitons
	private static Document recipeToDoc(Recipe recipe) {
    	Document recipeDocument = new Document()
                .append("recipeId", recipe.getRecipeId())
                .append("userName", recipe.getUserName())
                .append("name", recipe.getName())
                .append("author", recipe.getAuthor())
                .append("prepTime", recipe.getPrepTime())
                .append("cookTime", recipe.getCookTime())
                .append("ingredients", recipe.getIngredients())
                .append("instructions", recipe.getInstructions())
                .append("ratings", recipe.getRatings())
                .append("comments", recipe.getComments())
                .append("imageUrl", recipe.getImage());
    	return recipeDocument;
    }

    @SuppressWarnings("unchecked")
	private static Recipe docToRecipe(Document recipeDocument) {
    	Recipe recipe = new Recipe();
    	if (recipeDocument != null) {
            recipe.setRecipeId(recipeDocument.getString("recipeId"));
            recipe.setUserName(recipeDocument.getString("userName"));
            recipe.setName(recipeDocument.getString("name"));
            recipe.setAuthor(recipeDocument.getString("author"));
            recipe.setPrepTime(recipeDocument.getString("prepTime"));
            recipe.setCookTime(recipeDocument.getString("cookTime"));
            recipe.setIngredients((List<String>) recipeDocument.get("ingredients"));
            recipe.setInstructions(recipeDocument.getString("instructions"));
            recipe.setRatings(recipeDocument.getDouble("ratings"));
            recipe.setComments((List<String>) recipeDocument.get("comments"));
            recipe.setImage(recipeDocument.getString("imageUrl"));
        }

        return recipe;
    }
    
    public static void main(String[] args) {
//    	externalCallActor();
    }
}