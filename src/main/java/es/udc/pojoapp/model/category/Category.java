package es.udc.pojoapp.model.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;


@Entity
@Table(name="Category")
@BatchSize(size=10)
public class Category {
	
	@Id
	@Column(name="categoryId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long categoryId;
	
	private String name;
		
	public Category() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getIdentificador() {
		return categoryId;
	}
	public void setIdentificador(Long identificador) {
		this.categoryId = identificador;
	}
		
}