package com.example.aplikasisipj.Model.SignUp;

import com.google.gson.annotations.SerializedName;

public class Signup{

	@SerializedName("data")
	private SignupData signupData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(SignupData signupData){
		this.signupData = signupData;
	}

	public SignupData getData(){
		return signupData;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}