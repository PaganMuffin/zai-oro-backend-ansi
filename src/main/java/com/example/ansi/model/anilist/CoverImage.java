package com.example.ansi.model.anilist;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "covers")
public class CoverImage{

	@Id
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
	@Column(length = 36, nullable = false, updatable = false, insertable = false)
	@Getter
	private String id;

	@SerializedName("medium")
	@Getter
	private String medium;



}