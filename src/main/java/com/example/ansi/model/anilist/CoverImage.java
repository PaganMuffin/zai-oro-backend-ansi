package com.example.ansi.model.anilist;

import com.google.gson.annotations.SerializedName;

public class CoverImage{

	@SerializedName("large")
	private String large;

	public String getLarge(){
		return large;
	}
}