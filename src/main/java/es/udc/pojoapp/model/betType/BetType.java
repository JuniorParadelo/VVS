package es.udc.pojoapp.model.betType;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;

import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.option.Option;

@Entity
@BatchSize(size=10)
public class BetType {
	
	@Id
	@Column(name="betTypeId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String question;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="eventId")
	private Event event;
	
	@OneToMany(mappedBy="betType")
	private List<Option> options;
	
	private boolean unica;

	public BetType() {}
	
	public BetType(String question, Event event, boolean unica) {
		this.question = question;
		this.event = event;
		this.unica = unica;
	}
	
	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}

	public boolean isUnica() {
		return unica;
	}

	public void setUnica(boolean unica) {
		this.unica = unica;
	}
	
	
}