package es.udc.ws.app.dto;

	import java.util.Calendar;
import java.util.Calendar;

	public class BookingDto {
		
		private Long bookingId;
		private Long offerId;
		private String userId;
		private Calendar bookingDate;
		private Calendar enjoymentDate;
		private String creditCardNumber;
		
		
		public BookingDto (){

		}
		
		public BookingDto(Long bookingId, Long offerId,String userId,String creditCardNumber,
				Calendar bookingDate,Calendar enjoymentDate){
			
			this.bookingId=bookingId;
			this.offerId=offerId;
			this.userId=userId;
			this.creditCardNumber=creditCardNumber;
			this.bookingDate=bookingDate;
			this.enjoymentDate=enjoymentDate;
		}
		
		public Long getBookingId(){
			return bookingId;
		}
		
		public void setBookindId(Long offerId){
			this.offerId=offerId;
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
		
		public Calendar getBookingDate(){
			return bookingDate;
		}
		
		public void setBookingDate(Calendar bookingDate){
			this.bookingDate =bookingDate;
			if(bookingDate != null){
				this.bookingDate.set(Calendar.MILLISECOND,0);
			}
		}
		
		public Calendar getEnjoymentDate(){
			return enjoymentDate;
		}
		
		public void setEnjoymentDate(Calendar enjoymentDate){
			this.enjoymentDate =enjoymentDate;
			if(enjoymentDate != null){
				this.enjoymentDate.set(Calendar.MILLISECOND,0);
			}
		}
		
		public String getCreditCardNumber(){
			return creditCardNumber;
		}

		public void setCreditCardNumber(String creditCardNumber){
			this.creditCardNumber =creditCardNumber;
		}
}