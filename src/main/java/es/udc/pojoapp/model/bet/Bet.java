package es.udc.pojoapp.model.bet;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.udc.pojoapp.model.option.Option;
import es.udc.pojoapp.model.userprofile.UserProfile;

@Entity
public class Bet {
	
	@Id
	@Column(name="betId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Float betAmount;
	
	@Column(name="betDate")
	private Calendar date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="optionId")
	private Option option;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	private UserProfile userId;

	public Bet() {}
	
	public Bet(Option option, Float betAmount, UserProfile user, Calendar fecha) {
		this.option = option;
		this.betAmount = betAmount;
		this.userId = user;
		this.date = fecha;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
	public Option getOption() {
		return option;
	}
	public void setOption(Option option) {
		this.option = option;
	}
	public Float getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(Float betAmount) {
		this.betAmount = betAmount;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	public UserProfile getUserId() {
		return userId;
	}
	public void setUserId(UserProfile user) {
		this.userId = user;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public Double getGanancia(){
		return this.option.getCoute()*this.betAmount;
	}
}