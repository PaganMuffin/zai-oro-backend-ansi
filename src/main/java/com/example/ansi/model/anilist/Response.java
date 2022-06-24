package com.example.ansi.model.anilist;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("data")
	private Data data;

	public Data getData(){
		return data;
	}
}