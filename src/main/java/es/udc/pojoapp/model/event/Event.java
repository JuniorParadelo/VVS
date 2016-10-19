package es.udc.pojoapp.model.event;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;

import es.udc.pojoapp.model.betType.BetType;
import es.udc.pojoapp.model.category.Category;

@Entity
@Table(name="Event")
@BatchSize(size=10)
public class Event {
	
	@Id
	@Column(name="eventId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private Calendar date;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category")
	private Category category;
	
	@OneToMany(mappedBy="event")
	private Set<BetType> betTypes;
	
	public Event() {}
	
	public Event(String name,Calendar date, Category category) {
		this.name = name;
		this.date = date;
		this.category = category;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    @Temporal(TemporalType.TIMESTAMP)
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<BetType> getBetTypes() {
		return betTypes;
	}

	public void setBetTypes(Set<BetType> betTypes) {
		this.betTypes = betTypes;
	}
	
	
}