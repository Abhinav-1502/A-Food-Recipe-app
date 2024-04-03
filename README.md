## Overview

Nostalgic Food and Recipes is a JavaFX application that allows users to explore, share, and discover nostalgic recipes. Users can browse through a collection of recipes, post their own recipes, manage their accounts, and interact with other users.

## Features

- **Recipe Management:** Users can browse through a curated collection of nostalgic recipes, view recipe details, and search for specific recipes based on various criteria.

- **User Authentication:** Secure user authentication system allows users to create accounts, log in, and log out securely.

- **Account Management:** Registered users can manage their account information, including profile details and preferences.

- **Recipe Posting:** Users can contribute to the platform by posting their own nostalgic recipes, complete with ingredients, instructions, and images.

- **Interactivity:** Users can engage with the community by liking, commenting, and sharing recipes with others.

## File Structure

- **Application Package:**
  - Main.java: The main class responsible for initializing the code and launching the application.
  - LoginDesign.css: A CSS file for styling aspects across the project.

- **Controller Package:**
  - AccountController.java: Manages account-related functionalities.
  - CommonMethods.java: Contains reusable methods shared across controllers.
  - FoodCardController.java: Controls the behavior of food card components.
  - HomePageController.java: Handles events and actions on the home page.
  - ListRecipesController.java: Manages the listing and display of recipes.
  - LoginController.java: Controls the login functionality.
  - PostRecipeController.java: Manages the posting of new recipes.
  - RecipeDisplayController.java: Handles the display of individual recipes.

- **DataHandlers Package:**
  - DbConnection.java: Establishes and manages the database connection.
  - RecipeHandler.java: Manages interactions with the recipe-related data in the database.
  - UserHandler.java: Handles user-related data operations.

- **FXML Package:**
  - Account.fxml: The FXML file for the account page.
  - AccountPage.fxml: FXML file for the account details page.
  - Dashboard.fxml: FXML file for the dashboard.
  - FoodCard.fxml: FXML file defining the structure of the food card.
  - HomePage.fxml: FXML file for the main home page.
  - ListRecipes.fxml: FXML file for listing recipes.
  - LoginPage.fxml: FXML file for the login page.
  - PostRecipe.fxml: FXML file for posting new recipes.
  - RecipeDisplay.fxml: FXML file for displaying individual recipes.
  - RecipeList.fxml: FXML file for listing multiple recipes.

- **Model Package:**
  - Recipe.java: Represents the structure of a recipe.
  - User.java: Represents the structure of a user.

## Instructions To Run The Project: "Nostalgic Food and Recipes"

1. **Open the File Directory:**
   Navigate to the "Recipe" directory in the project's source folder.

2. **Open Main.java:**
   Inside the "Recipe" directory, locate the file named "Main.java." This is the main class responsible for initializing the code and launching the application.

3. **Configure JavaFX Libraries:**
   After opening Main.java, go to `Preferences -> Java -> Build Path -> User Libraries`.
   Then, add the external JAR files listed in the provided "javafx-sdk-21.0.1" folder.
   This step is crucial as it includes the FontAwesome icons used in the project's display logo.

4. **Run the Code:**
   After configuring the JavaFX libraries, build and run the "Main.java" file.
   Ensure that your Java Development Kit (JDK) is properly installed and configured.

5. **Functionality Testing:**
   After successfully running the code, the entire application should function seamlessly.
   Test the various functionalities, including user authentication, recipe management, and account management.
