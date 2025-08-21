package com.epicode.progettofinaleepicode.entity;



import javax.persistence.Entity;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
	
	private String title;
	
	private String content;
	
	private String picture;

}
