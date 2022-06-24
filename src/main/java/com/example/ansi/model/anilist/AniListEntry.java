package com.example.ansi.model.anilist;

import com.google.gson.annotations.SerializedName;

public class AniListEntry {

	@SerializedName("coverImage")
	private CoverImage coverImage;

	@SerializedName("season")
	private String season;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("idMal")
	private int idMal;

	@SerializedName("title")
	private Title title;

	@SerializedName("type")
	private String type;

	@SerializedName("seasonYear")
	private int seasonYear;

	@SerializedName("episodes")
	private int episodes;

	public CoverImage getCoverImage(){
		return coverImage;
	}

	public String getSeason(){
		return season;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public int getIdMal(){
		return idMal;
	}

	public Title getTitle(){
		return title;
	}

	public String getType(){
		return type;
	}

	public int getSeasonYear(){
		return seasonYear;
	}

	public int getEpisodes(){
		return episodes;
	}
}