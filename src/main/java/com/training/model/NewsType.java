package com.training.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "new_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsType {

	@Id
	@Column(name = "news_type_id", length = 36)
	private String newsTypeId;

	@Column(name = "description", length = 10)
	private String description;

	@Column(name = "news_type_name", length = 50)
	private String newsTypeName;

	@OneToMany(mappedBy = "newType", cascade = CascadeType.ALL)
	private List<News> news;

}
