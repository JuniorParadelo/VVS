package es.udc.ws.app.model.offer;

import java.util.Calendar;

public class Offer {

    private Long offerId;
    private String name;
    private String description;
    private float realPrice;
    private float discountPrice;
    private float commission;
    private Calendar bookingDate;
    private Calendar enjoymentDate;
    private boolean validOffer;
    
    public Offer (String name, String description,float realPrice, float discountPrice,Calendar bookingDate,
    		Calendar enjoymentDate) {
    	this.name = name;
        this.description = description;
        this.bookingDate=bookingDate;
        if (bookingDate != null) {
            this.bookingDate.set(Calendar.MILLISECOND, 0);
        }
        this.enjoymentDate = enjoymentDate;
        if (enjoymentDate != null) {
            this.enjoymentDate.set(Calendar.MILLISECOND, 0);
        }
        this.realPrice=realPrice;
        this.discountPrice = discountPrice;
        this.commission=this.calulerCommision(discountPrice);
        this.validOffer=true;
    }

    public float calulerCommision (float discountPrice){
    	return (discountPrice* (0.05F));
    }
    
    public Offer(Long offerId,String name, String description, float realPrice, float discountPrice, 
    		float commission, Calendar bookingDate, Calendar enjoymentDate, boolean validOffer) {
        this(name, description,realPrice, discountPrice,bookingDate, enjoymentDate);
        this.offerId = offerId;
        this.validOffer = validOffer;
    }
    
    public Offer(String name, String description, float realPrice, float discountPrice, 
    		float commission, Calendar bookingDate, Calendar enjoymentDate, boolean validOffer) {
        
    	this.name = name;
        this.description = description;
        this.bookingDate=bookingDate;
        if (bookingDate != null) {
            this.bookingDate.set(Calendar.MILLISECOND, 0);
        }
        this.enjoymentDate = enjoymentDate;
        if (enjoymentDate != null) {
            this.enjoymentDate.set(Calendar.MILLISECOND, 0);
        }
        this.realPrice=realPrice;
        this.discountPrice = discountPrice;
        this.commission=commission;
        this.validOffer=validOffer;
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
    
    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
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

    public void setdiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Calendar getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Calendar bookingDate) {
        this.bookingDate = bookingDate;
        if (bookingDate != null) {
            this.bookingDate.set(Calendar.MILLISECOND, 0);
        }
    }
    public Calendar getEnjoymentDate() {
        return enjoymentDate;
    }

    public void setEnjoymentDatee(Calendar enjoymentDate) {
        this.enjoymentDate = enjoymentDate;
        if (enjoymentDate != null) {
            this.enjoymentDate.set(Calendar.MILLISECOND, 0);
        }
    }
    
    public boolean getValidOffer() {
        return validOffer;
    }

    public void setvalidOffer(boolean validOffer) {
        this.validOffer = validOffer;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((bookingDate == null) ? 0 : bookingDate.hashCode());
        result = prime * result
                + ((enjoymentDate == null) ? 0 : enjoymentDate.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
        result = prime * result + Float.floatToIntBits(realPrice);
        result = prime * result + Float.floatToIntBits(discountPrice);
        result = prime * result + Float.floatToIntBits(commission);
        if (validOffer == true)  
        	result = prime * result + 1;
        else 
        	result = prime * result + 0;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Offer other = (Offer) obj;
        if (bookingDate == null) {
            if (other.bookingDate != null) {
                return false;
            }
        } else if (!bookingDate.equals(other.bookingDate)) {
            return false;
        }
        if (enjoymentDate == null) {
            if (other.enjoymentDate != null) {
                return false;
            }
        } else if (!enjoymentDate.equals(other.enjoymentDate)) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (offerId == null) {
            if (other.offerId != null) {
                return false;
            }
        } else if (!offerId.equals(other.offerId)) {
            return false;
        }
        if (Float.floatToIntBits(realPrice) != Float.floatToIntBits(other.realPrice)) {
            return false;
        }
        if (Float.floatToIntBits(discountPrice) != Float.floatToIntBits(other.discountPrice)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
