package model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
	private String recipeId;
	private String userName;
    private String name;
    private String author;
    private String prepTime;
    private String cookTime; 
    private List<String> ingredients;
    private String instructions;
    private double ratings = 0.0;
    private List<String> comments = new ArrayList <> (); 
    private String imageUrl;
    // Getter and setter methods for each property

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }
    
    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public List<String> getComments() {
        return comments;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public void setComments(List<String> listOfComments) {
		this.comments = listOfComments;
		
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipe {");
        sb.append("recipeId='").append(recipeId).append('\'');
        
        sb.append("userName='").append(userName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", prepTime='").append(prepTime).append('\'');
        sb.append(", cookTime='").append(cookTime).append('\'');
        sb.append(", ingredients=").append(ingredients);
        sb.append(", instructions='").append(instructions).append('\'');
        sb.append(", ratings=").append(ratings);
        sb.append(", comments=").append(comments);
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
