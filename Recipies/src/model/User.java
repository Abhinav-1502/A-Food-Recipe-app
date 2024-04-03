package model;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNo;
	private String userName;
	private String password;
	private String seqQuestion;
	private String seqAnswer;
	
	private List<String> recipeIds = new ArrayList<>(); 
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSeqQuestion() {
		return seqQuestion;
	}
	public void setSeqQuestion(String seqQuestion) {
		this.seqQuestion = seqQuestion;
	}
	public String getSeqAnswer() {
		return seqAnswer;
	}
	public void setSeqAnswer(String seqAnswer) {
		this.seqAnswer = seqAnswer;
	}
	public List<String> getRecipeIds() {
		return recipeIds;
	}
	public void setRecipeIds(List<String> recipeIds) {
		this.recipeIds = recipeIds;
	}
	public void addRecipeId(String recipeId) {
		this.recipeIds.add(recipeId);
	}
	
	public void removeRecipeId(String recipeId) {
		this.recipeIds.remove(recipeId);
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Users{")
          .append("firstName='").append(firstName).append('\'')
          .append(", lastName='").append(lastName).append('\'')
          .append(", email='").append(email).append('\'')
          .append(", phoneNo='").append(phoneNo).append('\'')
          .append(", userName='").append(userName).append('\'')
          .append(", password='").append(password).append('\'')
          .append(", seqQuestion='").append(seqQuestion).append('\'')
          .append(", seqAnswer='").append(seqAnswer).append('\'')
          .append(", recipeIds=").append(recipeIds)
          .append('}');
        return sb.toString();
    }
}
