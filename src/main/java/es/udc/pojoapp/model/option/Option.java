package es.udc.pojoapp.model.option;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;

import es.udc.pojoapp.model.betType.BetType;

@Entity
@Table(name="OptionB")
@BatchSize(size=10)
public class Option {

	@Id
	@Column(name="optionId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private Double coute;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="betTypeId")
	private BetType betType;
	
	private Boolean win;
	
	@Version
	private Long version;
	//En el punto buscar las apuesta de un usuario hablan de un estado Pendiente, ganada, perdida.


	public Option() {}
	
	public Option(String name, Double coute, BetType betType) {
		this.name = name;
		this.coute = coute;
		this.betType = betType;
		this.win = null;
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
	public Double getCoute() {
		return coute;
	}
	public void setCoute(Double coute) {
		this.coute = coute;
	}

	public BetType getBetType() {
		return betType;
	}
	public void setBetType(BetType betType) {
		this.betType = betType;
	}
	public Boolean getWin() {
		return win;
	}
	public void setWin(Boolean win) {
		this.win = win;
	}
	
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
}