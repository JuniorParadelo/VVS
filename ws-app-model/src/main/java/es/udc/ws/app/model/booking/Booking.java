package es.udc.ws.app.model.booking;

import java.util.Calendar;

public class Booking {
	
	public enum State{validBooking,nullbooking,invalidbooking};
	private Long bookingId;
	private Long offerId;
	private String userId;
	private Calendar enjoymentDate;
	private String creditCardNumber;
	private Calendar bookingDate;
	private State state;
	private Long code;
	
	public Booking(Long offerId,String userId,Calendar enjoymentDate,
			String creditCardNumber, State state, Calendar bookingDate, Long code){
		
		this.offerId=offerId;
		this.userId=userId;
		this.enjoymentDate=enjoymentDate;
		this.creditCardNumber=creditCardNumber;
		this.state=state;
		this.code= 5000 - offerId;
		
		this.bookingDate=bookingDate;
		if(enjoymentDate !=null){
			this.enjoymentDate.set(Calendar.MILLISECOND,0);
		}
		if(bookingDate !=null){
			this.bookingDate.set(Calendar.MILLISECOND,0);
		}
		
	}
	
	public Booking (Long bookingId,Long offerId,String userId,
			Calendar enjoymentDate,String creditCardNumber,State state, Calendar bookingDate, Long code){
		this(offerId,userId,enjoymentDate,creditCardNumber,state ,bookingDate, code);
		this.bookingId= bookingId;
		this.code=code;
	}
	
	public Booking(Long bookingId,Long offerId,String userId, String creditCardNumber, 
			Calendar bookingDate, Calendar enjoymentDate){
		
	}
	
	public Long getBookingId(){
		return bookingId;
	}
	
	public Long getOfferId(){
		return offerId;
	}
	
	public void setOfferId(Long offerId){
		this.offerId=offerId;
	}
	
	public String getUserId(){
		return userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public Calendar getEnjoymentDate(){
		return enjoymentDate;
	}
	
	public Calendar getBookingDate(){
		return bookingDate;
	}

	public void setEnjoymentDate(Calendar enjoymentDate){
		this.enjoymentDate =enjoymentDate;
		if(enjoymentDate != null){
			this.enjoymentDate.set(Calendar.MILLISECOND,0);
		}
	}
	
	public void setbookingDate(Calendar bookingDate){
		this.bookingDate =bookingDate;
		if(bookingDate != null){
			this.bookingDate.set(Calendar.MILLISECOND,0);
		}
	}
	
	public String getCreditCardNumber(){
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber){
		this.creditCardNumber =creditCardNumber;
	}
	
	public Long getCode(){
		return code;
	}

	public void setCode(Long code){
		this.code = code;
	}
	
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	
	public int hashCode(){
		final int prime =31;
		int result =1;
		result = prime * result + ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
		result = prime * result + ((enjoymentDate ==null) ? 0 : enjoymentDate.hashCode());
		result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result +((bookingDate == null) ? 0 : bookingDate.hashCode());
		result = prime * result +((bookingId == null) ? 0 : bookingId.hashCode());
		result = prime * result *((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	
	public boolean equals (Object obj){
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Booking other = (Booking) obj;
		if (creditCardNumber !=null){
			return false;
		}else if (!creditCardNumber.equals(other.creditCardNumber)){
			return false;
		}
		if(offerId == null){
			if(other.offerId!=null){
				return false;
			}
		}else if (!offerId.equals(other.offerId)){
			return false;
		}
		if(state == null){
			if(other.state!=null){
				return false;
			}
		}else if (!state.equals(other.state)){
			return false;
		}
		if(bookingDate == null){
			if(other.bookingDate!=null){
				return false;
			}
		}else if (!bookingDate.equals(other.bookingDate)){
			return false;
		}
		if(enjoymentDate == null){
			if(other.enjoymentDate!=null){
				return false;
			}
		}else if (!enjoymentDate.equals(other.enjoymentDate)){
			return false;
		}
		if(bookingId == null){
			if(other.bookingId!=null){
				return false;
			}
		}else if (!bookingId.equals(other.bookingId)){
			return false;
		}
		if(userId == null){
			if(other.userId!=null){
				return false;
			}
		}else if (!userId.equals(other.userId)){
			return false;
		}
		
		return true;
	}
}
