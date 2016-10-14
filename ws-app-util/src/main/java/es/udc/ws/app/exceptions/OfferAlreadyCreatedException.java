package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class OfferAlreadyCreatedException extends Exception {
	
	 private Long offerId;
	    

	 public OfferAlreadyCreatedException(Long offerId) {
	    super("offer with id=\"" + offerId );
	    this.offerId = offerId;
	 }

	 public Long getOfferId() {
	    return offerId;
	 }


	 public void setOfferId(Long offerId) {
	    this.offerId = offerId;
	 }
}
