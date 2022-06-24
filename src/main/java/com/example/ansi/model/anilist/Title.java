package com.example.ansi.model.anilist;

import com.google.gson.annotations.SerializedName;

public class Title{

	@SerializedName("romaji")
	private String romaji;

	public String getRomaji(){
		return romaji;
	}
}