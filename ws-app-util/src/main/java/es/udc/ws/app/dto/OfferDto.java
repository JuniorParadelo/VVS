package es.udc.ws.app.dto;

	import java.util.Calendar;

	public class OfferDto {

	    private Long offerId;
	    private String name;
	    private String description;
	    private float realPrice;
	    private float discountPrice;
	    private Calendar bookingDate;
	    private Calendar enjoymentDate;
	    
	    
	    public OfferDto(){
	    	
	    }

	    public OfferDto(Long offerId,String name, String description,float realPrice, float discountPrice,
	    		Calendar bookingDate, Calendar enjoymentDate) {
	        
	    	this.offerId = offerId;
	    	this.name=name;
	        this.description=description;
	        this.realPrice= realPrice;
	        this.discountPrice=discountPrice;
	        this.bookingDate=bookingDate;
	        this.enjoymentDate=enjoymentDate;
	        
	    }

	    public Long getOfferId() {
	        return offerId;
	    }

	    public void setOfferId(Long id) {
	        this.offerId = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }
	   
	    public float getRealPrice() {
	        return realPrice;
	    }

	    public void setRealPrice(float realPrice) {
	        this.realPrice = realPrice;
	    }
	    
	    public float getDiscountPrice() {
	        return discountPrice;
	    }

	    public void setDiscountPrice(float discountPrice) {
	        this.discountPrice = discountPrice;
	    }
	   
	    
	    public Calendar getBookingDate() {
	        return bookingDate;
	    }

	    public void setBookingDate(Calendar bookingDate) {
	        this.bookingDate = bookingDate;
	        if (enjoymentDate != null) {
	            this.bookingDate.set(Calendar.MILLISECOND, 0);
	        }
	    }
	    
	    public Calendar getEnjoymentDate() {
	        return enjoymentDate;
	    }

	    public void setEnjoymentDate(Calendar enjoymentDate) {
	        this.enjoymentDate = enjoymentDate;
	        if (enjoymentDate != null) {
	            this.enjoymentDate.set(Calendar.MILLISECOND, 0);
	        }
	    }
	    
	  @Override
	  public String toString(){
		  return "OfferDto [offerId=" + offerId +", name="+ name +",description="+description+
				  ", realPrice="+realPrice+", discountPrice= "+discountPrice+", bookingDate="+bookingDate+
				  ", enjoymentDate="+enjoymentDate+"]";
	  }

}
