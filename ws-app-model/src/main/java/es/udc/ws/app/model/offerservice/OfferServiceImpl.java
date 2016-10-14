package es.udc.ws.app.model.offerservice;

import static es.udc.ws.app.model.util.ModelConstants.OFFER_DATA_SOURCE;
import es.udc.ws.app.exceptions.BookingIsNotValidException;
import es.udc.ws.app.exceptions.OfferIsAlreadyInvalid;
import es.udc.ws.app.exceptions.UserAlreadyReservedException;
import es.udc.ws.app.exceptions.OfferAlreadyCreatedException;
import es.udc.ws.app.exceptions.commissionUpperTheAverageException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offer.SqlOfferDao;
import es.udc.ws.app.model.offer.SqlOfferDaoFactory;
import es.udc.ws.app.model.booking.Booking;
import es.udc.ws.app.model.booking.Booking.State;
import es.udc.ws.app.model.booking.SqlBookingDao;
import es.udc.ws.app.model.booking.SqlBookingDaoFactory;
import es.udc.ws.app.validator.Validator;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.validation.PropertyValidator;

public class OfferServiceImpl implements OfferService {
    /*
     * IMPORTANT: Some JDBC drivers require "setTransactionIsolation" to
     * be called before "setAutoCommit".
     */

	private DataSource dataSource;
    private SqlOfferDao offerDao = null;
    private SqlBookingDao bookingDao = null;

    public OfferServiceImpl() {
        dataSource = DataSourceLocator.getDataSource(OFFER_DATA_SOURCE);
    	offerDao = SqlOfferDaoFactory.getDao();
    	bookingDao = SqlBookingDaoFactory.getDao();
    }

    private void validateOffer(Offer offer) throws InputValidationException, commissionUpperTheAverageException {

        PropertyValidator.validateMandatoryString("name", offer.getName());
        PropertyValidator.validateMandatoryString("description", offer.getDescription());
        Validator.validateNotNegativeFloat("real price", offer.getRealPrice());
        Validator.validateNotNegativeFloat("discounted price", offer.getDiscountPrice());
        Validator.validateNotNegativeFloat("commission", offer.getCommission());
        if (offer.getCommission() > (offer.getDiscountPrice()*0.05F))
        	throw new commissionUpperTheAverageException();
        Validator.validateFutureDate("creation date", offer.getBookingDate());
        Validator.validateFutureDate("creation date", offer.getEnjoymentDate());
        
    }
    
    
    @Override
    public Offer addOffer(Offer offer) throws InputValidationException, OfferAlreadyCreatedException, commissionUpperTheAverageException{
    	validateOffer(offer);
    	try (Connection connection = dataSource.getConnection()){

    		try{
    			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    			connection.setAutoCommit(false);
    			
    			if (offer.getOfferId()!= null){
    				throw new OfferAlreadyCreatedException(offer.getOfferId());
    			}else{
    				Offer createdOffer = offerDao.create(connection, offer);
    			
    				connection.commit();
    			
    				return createdOffer;
    			}
    		}catch(SQLException e){
    			connection.rollback();
    			throw new RuntimeException(e);
    		}catch (RuntimeException | Error e){
    			connection.rollback();
    			throw e;
    		}
    		
    	}catch (SQLException e) {
    		throw new RuntimeException(e);
    	}
   }
    
    @Override
	public void updateOffer(Offer offer) throws InstanceNotFoundException, InputValidationException, 
	commissionUpperTheAverageException, UserAlreadyReservedException{	
    	
    	validateOffer(offer);
    	
		try (Connection connection = dataSource.getConnection()){
			try{
				
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
				if (offer.getOfferId()== null){
					throw new InputValidationException("oferta no valida");
				}else{
					if (bookingDao.findBookingByOfferId(connection, offer.getOfferId()).isEmpty()){
					
						offerDao.update(connection, offer);
					
						connection.commit();
					}else{
						Offer o=offerDao.findOfferByOfferId(connection, offer.getOfferId());
						if (o.getDiscountPrice()>=offer.getDiscountPrice() && 
								(o.getEnjoymentDate().before(offer.getEnjoymentDate()) || 
										o.getEnjoymentDate() == (offer.getEnjoymentDate()))){
							
							offerDao.update(connection, offer);
							
							connection.commit();
						}else{
							//throw new InputValidationException("Precio o fecha invalidos");
							throw new UserAlreadyReservedException();
						}
					}
				}
			}catch (InstanceNotFoundException e){
				connection.commit();
				throw e;
			}catch (SQLException e){
				connection.rollback();
				throw new RuntimeException(e); 
			}catch (RuntimeException | Error e){
				connection.rollback();
				throw e;
			}
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
    
    @Override
    public void removeOffer(Long offerId) throws UserAlreadyReservedException, InstanceNotFoundException{
    	
        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                
                if (bookingDao.findBookingByOfferId(connection, offerId).isEmpty()){
                	/* Do work. */
                	offerDao.remove(connection, offerId);

                	/* Commit. */
                	connection.commit();
                }else{
                	throw new UserAlreadyReservedException();
                }
            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Offer findOfferByOfferId(Long offerId) throws InstanceNotFoundException{
        try (Connection connection = dataSource.getConnection()) {
            return offerDao.findOfferByOfferId(connection, offerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public List<Booking> findBookingByUser(String userId) {
    	try(Connection connection =dataSource.getConnection()){
    		return bookingDao.findBookingByUser(connection, userId);
    	}catch (SQLException e){
    		throw new RuntimeException(e);
    	}
    }
    
    @Override
	public List<Offer> findOfferByKeywords (String description, boolean validOffer, Calendar bookingDate){
            try (Connection connection = dataSource.getConnection()) {
            	if (bookingDate != null){
	            	if (bookingDate.after(Calendar.getInstance())){
	                    return offerDao.findOfferByKeywords(connection, description, bookingDate, validOffer);
	            	} else {
	            		throw new IllegalArgumentException("Fecha anterior a la actual");
	            	}
            	} else
                    return offerDao.findOfferByKeywords(connection, description, bookingDate, validOffer);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    
    @Override
    public List<Booking> findBookingsOfOffer(Long offerId)throws InstanceNotFoundException{
    	try (Connection connection = dataSource.getConnection()){
    		Offer offer=offerDao.findOfferByOfferId(connection, offerId);
    		if (offer==null){
    			throw new InstanceNotFoundException(offer, "Id de la oferta no es valida");
    		}else{
    			return bookingDao.findBookingByOfferId(connection, offerId);
    		}
    	}catch(SQLException e){
    		throw new RuntimeException(e);
    	}
    }
    
    @Override
    public void invalidateOffer(Long offerId) throws OfferIsAlreadyInvalid, InstanceNotFoundException{
    	try (Connection connection = dataSource.getConnection()){
    		Offer offer=offerDao.findOfferByOfferId(connection, offerId);
    		if (offer==null){
    			throw new InstanceNotFoundException(offer, "Id de la oferta no es valida");
    		}else{
	    		if (offer.getValidOffer()==false){
	    			throw new OfferIsAlreadyInvalid();
	    		}else{
	    			offer.setvalidOffer(false);
	    			offerDao.update(connection, offer);
	    			if (bookingDao.hasTheOfferBookings(connection, offerId)==true){
	    				try{
	    					bookingDao.annulBooking(connection, offerId);
	    				}catch (BookingIsNotValidException e){
	    					throw new RuntimeException(e);
	    				}
	    			}
	    		}
    		}
    	}catch (SQLException e){
    		throw new RuntimeException(e);
    	}
    }
    
    
    @Override
	public Long addBooking (Long offerId, String userId, String creditCardNumber) throws InstanceNotFoundException, InputValidationException{
        PropertyValidator.validateCreditCard(creditCardNumber);

        try (Connection connection = dataSource.getConnection()) {
            try {
                /* Prepare connection. */
                connection
                        .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                /* Do work. */
                Offer offer = offerDao.findOfferByOfferId(connection, offerId);
        		if (offer==null || offer.getValidOffer()==false){
        			throw new InstanceNotFoundException(offer, "Id de la oferta no es valida");
        		}else{
	                Calendar bookingDate = Calendar.getInstance();
	                Booking booking = bookingDao.create(connection, new Booking(offerId, userId,
	                        offer.getEnjoymentDate(), creditCardNumber, State.validBooking, 
	                        offer.getBookingDate(), null));
	
	                /* Commit. */
	                connection.commit();
	                return booking.getCode();
        		}
            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void claimBooking (Long code)throws InstanceNotFoundException,BookingIsNotValidException{
    	 try (Connection connection = dataSource.getConnection()) {
             try {
                 connection
                         .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                 connection.setAutoCommit(false);
                 Booking booking = bookingDao.findBookingByCode(connection, code);
                 if (booking==null){
         			throw new InstanceNotFoundException(booking, "Id de la reserva no es valida");
         		}else{
         			bookingDao.update(connection, booking);
         			connection.commit();
         		}
             } catch (InstanceNotFoundException e) {
                 connection.commit();
                 throw e;
             } catch (SQLException e) {
                 connection.rollback();
                 throw new RuntimeException(e);
             } catch (RuntimeException | Error e) {
                 connection.rollback();
                 throw e;
             }

         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
   }
    
    
    
    @Override
    public Booking findBookingByBookingId(Long bookingId) throws InstanceNotFoundException{
        try (Connection connection = dataSource.getConnection()) {
            return bookingDao.findBookingByBookingId(connection, bookingId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Booking findBookingByCode(Long code) throws InstanceNotFoundException{
        try (Connection connection = dataSource.getConnection()) {
            return bookingDao.findBookingByCode(connection, code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}