package com.example.ansi.model.anilist;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("Media")
	private AniListEntry media;

	public AniListEntry getMedia(){
		return media;
	}
}