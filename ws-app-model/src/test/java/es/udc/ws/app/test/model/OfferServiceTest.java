package es.udc.ws.app.test.model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static es.udc.ws.app.model.util.ModelConstants.OFFER_DATA_SOURCE;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.app.exceptions.BookingIsNotValidException;
import es.udc.ws.app.exceptions.OfferAlreadyCreatedException;
import es.udc.ws.app.exceptions.OfferIsAlreadyInvalid;
import es.udc.ws.app.exceptions.UserAlreadyReservedException;
import es.udc.ws.app.exceptions.commissionUpperTheAverageException;
import es.udc.ws.app.model.booking.Booking;
import es.udc.ws.app.model.booking.Booking.State;
import es.udc.ws.app.model.booking.SqlBookingDao;
import es.udc.ws.app.model.booking.SqlBookingDaoFactory;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offerservice.OfferService;
import es.udc.ws.app.model.offerservice.OfferServiceFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

import org.junit.Test;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

public class OfferServiceTest {

	private final long NON_EXISTENT_OFFER_ID = -1;
	private final long NON_EXISTENT_BOOKING_ID = -1;
	private final String USER_ID = "ws-user";

	private final String VALID_CREDIT_CARD_NUMBER = "1234567890123456";
	private final String INVALID_CREDIT_CARD_NUMBER = "";

	private static OfferService offerService = null;

	private static SqlBookingDao bookingDao = null;

	@BeforeClass
	public static void init() {

		/*
		 * Create a simple data source and add it to "DataSourceLocator" (this
		 * is needed to test "es.udc.ws.movies.model.movieservice.MovieService"
		 */
		DataSource dataSource = new SimpleDataSource();

		/* Add "dataSource" to "DataSourceLocator". */
		DataSourceLocator.addDataSource(OFFER_DATA_SOURCE, dataSource);

		offerService = OfferServiceFactory.getService();

		bookingDao = SqlBookingDaoFactory.getDao();

	}

	private Offer getValidOffer(String name, String description) {
		Calendar k=Calendar.getInstance();
		k.set(2018, 1, 2);
		
		return new Offer(name, description, 19.95F, 10.95F, 0.5F, k, 
				k, true);
	}

	private Offer getValidOffer() {
		return getValidOffer("Oferta", "hola hola hola");
	}

	private Offer createOffer(Offer offer) {

		Offer addedOffer = null;
		try {
			addedOffer = offerService.addOffer(offer);
		} catch (InputValidationException e) {
			throw new RuntimeException(e);
		} catch (OfferAlreadyCreatedException e){
			throw new RuntimeException(e);
		} catch (commissionUpperTheAverageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addedOffer;
	}

	private void removeOffer(Long offerId) {

		try {
			offerService.removeOffer(offerId);
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException(e);
		} catch (UserAlreadyReservedException e) {
			e.printStackTrace();
		}
	}


	private void removeBooking(Long bookingId) {
		
		DataSource dataSource = DataSourceLocator
				.getDataSource(OFFER_DATA_SOURCE);
		
		try (Connection connection = dataSource.getConnection()) {

			try {
	
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				bookingDao.remove(connection, bookingId);
				
				connection.commit();
	
			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw new RuntimeException(e);
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException|Error e) {
				connection.rollback();
				throw e;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private void updateBooking(Booking booking) {
		
		DataSource dataSource = DataSourceLocator
				.getDataSource(OFFER_DATA_SOURCE);
		
		try (Connection connection = dataSource.getConnection()) {

			try {
	
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
	
				bookingDao.update(connection, booking);
				
				
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException|Error e) {
				connection.rollback();
				throw e;
			} catch (BookingIsNotValidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Test
	public void testAddOfferAndFindOffer() throws InputValidationException,
			InstanceNotFoundException {
		Offer offer = getValidOffer();
		Offer addedOffer = null;
		try{
			addedOffer = offerService.addOffer(offer);
		}catch(OfferAlreadyCreatedException e) {
			throw new RuntimeException(e);
		} catch (commissionUpperTheAverageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Offer foundOffer = offerService.findOfferByOfferId(addedOffer.getOfferId());
		assertEquals(foundOffer.getOfferId(), addedOffer.getOfferId());
  //      assertEquals(foundOffer, addedOffer);
		//Clear Database
		removeOffer(addedOffer.getOfferId());
	}

	@Test
	public void testAddInvalidOffer() {

		Offer offer = getValidOffer();
		Offer addedOffer = null;
		boolean exceptionCatched = false;

		try {
			offer.setName(null);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}catch (OfferAlreadyCreatedException e) {
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertTrue(exceptionCatched);

			exceptionCatched = false;
			offer = getValidOffer();
			offer.setName("");
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}catch (OfferAlreadyCreatedException e) {
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertTrue(exceptionCatched);

			exceptionCatched = false;
			offer = getValidOffer();
			offer.setDescription(null);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}catch (OfferAlreadyCreatedException e) {
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertTrue(exceptionCatched);

			exceptionCatched = false;
			offer = getValidOffer();
			offer.setDescription("");
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}catch (OfferAlreadyCreatedException e) {
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertTrue(exceptionCatched);

			exceptionCatched = false;
			offer = getValidOffer();
			offer.setRealPrice((short) -1);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}catch (OfferAlreadyCreatedException e) {
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertTrue(exceptionCatched);

			exceptionCatched = false;
			offer = getValidOffer();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, 1);
			offer.setBookingDate(calendar);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			} catch (OfferAlreadyCreatedException e) {
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			if (!exceptionCatched) {
				removeOffer(addedOffer.getOfferId());
			}
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentOffer() throws InstanceNotFoundException {

		offerService.findOfferByOfferId(NON_EXISTENT_OFFER_ID);

	}

	@Test(expected = UserAlreadyReservedException.class)
	public void testUpdateOffer() throws InputValidationException,
			InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		Long code = offerService.addBooking(offer.getOfferId(), USER_ID, VALID_CREDIT_CARD_NUMBER);
		try {
			offer.setName("new name");
			offer.setDescription("new description");
			offer.setRealPrice(20F);

			try {
				offerService.updateOffer(offer);
			} catch (UserAlreadyReservedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			// Clear Database
			removeOffer(offer.getOfferId());
		}
	}
	
	@Test
	public void testUpdateOffer2() throws InputValidationException,
			InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		try {
			offer.setName("new name");
			offer.setDescription("new description");
			Calendar cal = offer.getEnjoymentDate();
			cal.set(2017, 1, 2);
			offer.setEnjoymentDatee(cal);

			try {
				offerService.updateOffer(offer);
			} catch (UserAlreadyReservedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Offer updatedOffer = offerService.findOfferByOfferId(offer.getOfferId());
			//assertEquals(offer.getRealPrice(), updatedOffer.getRealPrice());

		} finally {
			// Clear Database
			removeOffer(offer.getOfferId());
		}
	}
	
	@Test
	public void testUpdateOffer3() throws InputValidationException,
			InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		try {
			offer.setName("new name");
			offer.setDescription("new description");
			Calendar cal = offer.getEnjoymentDate();
			cal.set(2017, 1, 2);
			offer.setEnjoymentDatee(cal);

			try {
				offerService.updateOffer(offer);
			} catch (UserAlreadyReservedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Offer updatedOffer = offerService.findOfferByOfferId(offer.getOfferId());
			//assertEquals(offer.getRealPrice(), updatedOffer.getRealPrice());

		} finally {
			// Clear Database
			removeOffer(offer.getOfferId());
		}
	}

	@Test(expected = InputValidationException.class)
	public void testUpdateInvalidOffer() throws InputValidationException,
			InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		try {
			// Check movie title not null
			offer = offerService.findOfferByOfferId(offer.getOfferId());
			offer.setName(null);
			try {
				offerService.updateOffer(offer);
			} catch (UserAlreadyReservedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			// Clear Database
			removeOffer(offer.getOfferId());
		}

	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testUpdateNonExistentOffer() throws InputValidationException,
			InstanceNotFoundException {

		Offer offer = getValidOffer();
		offer.setOfferId(NON_EXISTENT_OFFER_ID);
		try {
			offerService.updateOffer(offer);
		} catch (UserAlreadyReservedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (commissionUpperTheAverageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testRemoveOffer() throws InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		boolean exceptionCatched = false;
		try {
			offerService.removeOffer(offer.getOfferId());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		} catch (UserAlreadyReservedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(!exceptionCatched);

		offerService.findOfferByOfferId(offer.getOfferId());

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testRemoveNonExistentOffer() throws InstanceNotFoundException {

		try {
			offerService.removeOffer(NON_EXISTENT_OFFER_ID);
		} catch (UserAlreadyReservedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testFindOffer() {
		int i = 1;
		// Add movies
		List<Offer> offers = new LinkedList<Offer>();
		Offer offer1 = createOffer(getValidOffer("Offer title 1", "hola hola hola"));
		offers.add(offer1);
		Offer offer2 = createOffer(getValidOffer("Offer title 2", "hola 2"));
		offers.add(offer2);
		Offer offer3 = createOffer(getValidOffer("Offer title 3", "hola hola hola"));
		offers.add(offer3);

		try {
			List<Offer> foundOffers = offerService.findOfferByKeywords("hola", false, null);
			for (i=0; i<offers.size(); i++){
				assertFalse(offers.get(i).equals(foundOffers.get(i)));
			}
			foundOffers = offerService.findOfferByKeywords("hola 2", false, null);
			assertEquals(1, foundOffers.size());
			assertEquals(offers.get(1).getOfferId(), foundOffers.get(0).getOfferId());

			foundOffers = offerService.findOfferByKeywords("ola 3", false, null);
			assertEquals(0, foundOffers.size());
			
			foundOffers = offerService.findOfferByKeywords(null, false, null);
			assertEquals(3, foundOffers.size());
			
			foundOffers = offerService.findOfferByKeywords(null, true, null);
			assertEquals(3, foundOffers.size());
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(2017, 1, 2);
			foundOffers = offerService.findOfferByKeywords("hola", true, calendar);
			assertEquals(0, foundOffers.size());
			
			calendar = Calendar.getInstance();
			calendar.set(2019, 1, 2);
			foundOffers = offerService.findOfferByKeywords("hola", true, calendar);
			assertEquals(3, foundOffers.size());

		} finally {
			for (Offer offer : offers) {
				removeOffer(offer.getOfferId());
			}
		}

	}
	
		@Test
	public void testBookOfferAndFindBooking() throws InstanceNotFoundException,
			InputValidationException {

		Offer offer = createOffer(getValidOffer());
		Booking booking = null;
		Long code = null;
		
		try {
			Calendar beforebookingDate = Calendar.getInstance();
			beforebookingDate.add(Calendar.DAY_OF_MONTH,
					2);
			beforebookingDate.set(Calendar.MILLISECOND, 0);
			code = offerService.addBooking(offer.getOfferId(), USER_ID,
					VALID_CREDIT_CARD_NUMBER);

			Calendar afterbookingDate = Calendar.getInstance();
			afterbookingDate
					.add(Calendar.DAY_OF_MONTH, 2);
			afterbookingDate.set(Calendar.MILLISECOND, 0);
			Booking foundBooking = offerService.findBookingByCode(code);
			assertEquals(VALID_CREDIT_CARD_NUMBER,
					foundBooking.getCreditCardNumber());
			assertEquals(USER_ID, foundBooking.getUserId());
			assertEquals(offer.getOfferId(), foundBooking.getOfferId());
			//assertTrue(Calendar.getInstance().after(foundBooking.getBookingDate()));

		} finally {
			if (code != null) {
				removeBooking(offerService.findBookingByCode(code).getBookingId());
			}
			removeOffer(offer.getOfferId());
		}

	}
	
	@Test(expected = InputValidationException.class)
	public void testBuyOfferWithInvalidCreditCard() throws 
		InputValidationException, InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		try {
			Long code = offerService.addBooking(offer.getOfferId(), USER_ID,
					INVALID_CREDIT_CARD_NUMBER);
			removeBooking(offerService.findBookingByCode(code).getBookingId());
		} finally {
			removeOffer(offer.getOfferId());
		}

	}

	@Test (expected = RuntimeException.class)
	public void commissionUpperCreate() throws InputValidationException,
			InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		try {
			offer.setName("new name");
			offer.setDescription("new description");
			offer.setdiscountPrice(9F);
			try {
				offerService.updateOffer(offer);
			} catch (UserAlreadyReservedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Offer updatedOffer = offerService.findOfferByOfferId(offer.getOfferId());
			//assertEquals(offer.getRealPrice(), updatedOffer.getRealPrice());

		} finally {
			// Clear Database
			removeOffer(offer.getOfferId());
		}
	}
	
	@Test (expected = RuntimeException.class)
	public void commissionUpperUpdate() throws InputValidationException,
			InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		try {
			offer.setName("new name");
			offer.setDescription("new description");
			offer.setdiscountPrice(9F);

			try {
				offerService.updateOffer(offer);
			} catch (UserAlreadyReservedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (commissionUpperTheAverageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Offer updatedOffer = offerService.findOfferByOfferId(offer.getOfferId());

		} finally {
			// Clear Database
			removeOffer(offer.getOfferId());
		}
	}
	
	@Test (expected = RuntimeException.class)
	public void offerAlreadyCreated() throws InputValidationException,
			InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		try {
			Offer copia = createOffer(offer);
		} finally {
			// Clear Database
			removeOffer(offer.getOfferId());
		}
	}
	
	@Test// (expected = OfferIsAlreadyInvalid.class)       				REVISAAAAAAAR
	public void OfferIsAlreadyInvalid() throws InputValidationException,
	InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		try {
			try {
				offerService.invalidateOffer(offer.getOfferId());
			} catch (es.udc.ws.app.exceptions.OfferIsAlreadyInvalid e) {
				e.printStackTrace();
			}
			try {
				offerService.invalidateOffer(offer.getOfferId());
			} catch (es.udc.ws.app.exceptions.OfferIsAlreadyInvalid e) {
				e.printStackTrace();			
			}
			
		} finally {
			// Clear Database
			removeOffer(offer.getOfferId());
		}
	}
	
	@Test
	public void InvalidateOfferAndBookings()throws InputValidationException, InstanceNotFoundException, OfferIsAlreadyInvalid{
		Offer offer =createOffer(getValidOffer());
		Booking booking = null;
		Booking booking2 = null;
		Long long1 = null;
		Long long2 = null;
		try {
			Calendar beforebookingDate = Calendar.getInstance();
			beforebookingDate.add(Calendar.DAY_OF_MONTH,
					2);
			beforebookingDate.set(Calendar.MILLISECOND, 0);
			booking2 = booking;
			long1 = offerService.addBooking(offer.getOfferId(), USER_ID,
					VALID_CREDIT_CARD_NUMBER);
			long2 = offerService.addBooking(offer.getOfferId(), USER_ID,
					VALID_CREDIT_CARD_NUMBER);
			offerService.invalidateOffer(offer.getOfferId());
			assertEquals(State.nullbooking ,offerService.findBookingByCode(long1).getState());
			assertEquals(State.nullbooking ,offerService.findBookingByCode(long2).getState());
			//assertTrue(Calendar.getInstance().after(
				//	foundBooking.getBookingDate()));

		} finally {
			if (long1 != null) {
				removeBooking(offerService.findBookingByCode(long1).getBookingId());
			}
			if (long2 != null) {
				removeBooking(offerService.findBookingByCode(long2).getBookingId());
			}
			removeOffer(offer.getOfferId());
		}

		
		
		
	}
	
	@Test (expected = RuntimeException.class)
	public void UserAlreadyReserved() throws InputValidationException,
	InstanceNotFoundException {

		Offer offer = createOffer(getValidOffer());
		Long long1 = null;
		try {
			long1 = offerService.addBooking(offer.getOfferId(), USER_ID, VALID_CREDIT_CARD_NUMBER);
			try {
				offerService.removeOffer(offer.getOfferId());
			} catch (UserAlreadyReservedException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}

			
		} finally {
			// Clear Database
			removeBooking(offerService.findBookingByCode(long1).getBookingId());
			removeOffer(offer.getOfferId());
		}
	}
	
	@Test (expected = RuntimeException.class)
	public void bookingIsAlreadyClaim() throws OfferIsAlreadyInvalid, InstanceNotFoundException{
		
		Offer offer = null;
		Long long1 = null;
		try{
			offer = createOffer(getValidOffer());
			try {
				long1 = offerService.addBooking(offer.getOfferId(), USER_ID, VALID_CREDIT_CARD_NUMBER);
				offerService.claimBooking(long1);
				assertEquals(State.invalidbooking,offerService.findBookingByCode(long1).getState());
				offerService.claimBooking(long1);
			} catch (InputValidationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (BookingIsNotValidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}finally {
			// Clear Database
			removeBooking(offerService.findBookingByCode(long1).getBookingId());
			removeOffer(offer.getOfferId());
		}
		
	}
	

}
