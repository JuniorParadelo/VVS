package es.udc.ws.app.model.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.exceptions.BookingIsNotValidException;
import es.udc.ws.app.model.booking.Booking.State;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlBookingDao implements SqlBookingDao {

    protected AbstractSqlBookingDao() {
    }

    @Override
    public List<Booking> findBookingByOfferId(Connection connection, Long offerId) {

        /* Create "queryString". */
        Long offer = offerId;
        String queryString = "SELECT bookingId, offerId, userId, "
                + " enjoymentDate, creditCardNumber,state,"
                + " bookingDate FROM Booking";
        if (offer != null) {
            queryString += " WHERE offerId = " + offerId;
        }
        queryString += " ORDER BY offerId";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();
            /* Read movies. */
            List<Booking> bookings = new ArrayList<Booking>();

            while (resultSet.next()) {

                int i = 1;
                Long bookingId = new Long(resultSet.getLong(i++));
                Long offerID = new Long(resultSet.getString(i++));
                String userId = resultSet.getString(i++);
                Calendar enjoymentDate = Calendar.getInstance();
                enjoymentDate.setTime(resultSet.getTimestamp(i++));
                String creditCardNumber = resultSet.getString(i++);
                State state= State.valueOf(resultSet.getString(i++)); 
                Calendar bookingDate = Calendar.getInstance();
                bookingDate.setTime(resultSet.getTimestamp(i++));
                Long code = new Long(resultSet.getLong(i++));

                bookings.add(new Booking(bookingId, offerID, userId, enjoymentDate, creditCardNumber,
                		state, bookingDate, code));

            }

            /* Return movies. */
            return bookings;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    

    @Override
    public boolean hasTheOfferBookings(Connection connection, Long offerId) {

        /* Create "queryString". */
        Long offer = offerId;
        String queryString = "SELECT bookingId, offerId, userId, "
                + " enjoymentDate, creditCardNumber, state,"
                + " bookingDate FROM Booking";
        if (offer != null) {
            queryString += " WHERE offerId = " + offerId;
        }
        queryString += " ORDER BY offerId";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet!= null){
            	return true;
            }else{
            	return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    
    
    
    @Override
    public List<Booking> findBookingByUser(Connection connection, String userId) {

        /* Create "queryString". */
        String user = userId;
        String queryString = "SELECT bookingId, offerId, userId, "
                + " enjoymentDate, creditCardNumber,state,"
                + " bookingDate FROM booking";
        if (user != null) {
            queryString += " WHERE user = ?";
        }
        queryString += " ORDER BY offerId";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read movies. */
            List<Booking> bookings = new ArrayList<Booking>();

            while (resultSet.next()) {

                int i = 1;
                Long bookingId = new Long(resultSet.getLong(i++));
                Long offerID = new Long(resultSet.getString(i++));
                Calendar enjoymentDate = Calendar.getInstance();
                enjoymentDate.setTime(resultSet.getTimestamp(i++));
                String creditCardNumber = resultSet.getString(i++);
                State state= State.valueOf(resultSet.getString(i++)); 
                Calendar bookingDate = Calendar.getInstance();
                bookingDate.setTime(resultSet.getTimestamp(i++));
                Long code = new Long(resultSet.getLong(i++));
                
                bookings.add(new Booking(bookingId, offerID, userId, enjoymentDate, creditCardNumber,
                		state, bookingDate, code));

            }

            /* Return movies. */
            return bookings;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    
    @Override
    public Booking findBookingByCode (Connection connection, Long code) throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "SELECT bookingId, offerId, userId, "
                + " enjoymentDate, creditCardNumber, state,"
                + " bookingDate FROM Booking WHERE code = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, code.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(code,
                        Offer.class.getName());
            }

            /* Get results. */
            i = 1;
            Long bookingId = resultSet.getLong(i++);
            Long offerId = resultSet.getLong(i++);
            String userId = resultSet.getString(i++);
            Calendar enjoymentDate = Calendar.getInstance();
            enjoymentDate.setTime(resultSet.getTimestamp(i++));
            String creditCardNumber = resultSet.getString(i++);
            State state= State.valueOf(resultSet.getString(i++));
            Calendar bookingDate = Calendar.getInstance();
            bookingDate.setTime(resultSet.getTimestamp(i++));

            /* Return offer. */
            return new Booking(bookingId, offerId, userId, enjoymentDate, creditCardNumber, state, bookingDate, code);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Booking> findBookingByKeywords(Connection connection, String userId, State state) {

        /* Create "queryString". */
        String user = userId;
        String queryString = "SELECT bookingId, offerId, userId, "
                + " enjoymentDate, creditCardNumber, state,"
                + " bookingDate FROM booking";
        if (user != null) {
            queryString += " WHERE user = ?";
        }
        if (state== State.validBooking){
            	queryString += " AND state = ?";
        }
        queryString += " ORDER BY offerId";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read movies. */
            List<Booking> bookings = new ArrayList<Booking>();

            while (resultSet.next()) {

                int i = 1;
                Long bookingId = new Long(resultSet.getLong(i++));
                Long offerID = new Long(resultSet.getString(i++));
                Calendar enjoymentDate = Calendar.getInstance();
                enjoymentDate.setTime(resultSet.getTimestamp(i++));
                String creditCardNumber = resultSet.getString(i++);
                Calendar bookingDate = Calendar.getInstance();
                bookingDate.setTime(resultSet.getTimestamp(i++)); 
                Long code = new Long(resultSet.getLong(i++));

                bookings.add(new Booking(bookingId, offerID, userId, enjoymentDate, creditCardNumber,
                		state, bookingDate, code));

            }

            /* Return movies. */
            return bookings;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    @Override
    public void update(Connection connection, Booking booking)
            throws BookingIsNotValidException {
    	
      if (booking.getState()==State.validBooking){
    	  
        /* Create "queryString". */
        String queryString = "UPDATE Booking"
                + " SET state = 'invalidbooking' WHERE bookingId = ?";

       try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {


            int i = 1;
            
            preparedStatement.setLong(1, booking.getBookingId());
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      }else{
    	  throw new BookingIsNotValidException();
      }

    }

    
    
    
    
    @Override
    public void remove(Connection connection, Long bookingId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "DELETE FROM Booking WHERE bookingId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i, bookingId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(bookingId,
                        Booking.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Booking findBookingByBookingId (Connection connection, Long bookingId) throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "SELECT offerId, userId, "
                + " enjoymentDate, creditCardNumber, state,"
                + " bookingDate, code FROM Booking WHERE bookingId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, bookingId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(bookingId,
                        Offer.class.getName());
            }

            /* Get results. */
            i = 1;
            Long offerId = resultSet.getLong(i++);
            String userId = resultSet.getString(i++);
            Calendar enjoymentDate = Calendar.getInstance();
            enjoymentDate.setTime(resultSet.getTimestamp(i++));
            String creditCardNumber = resultSet.getString(i++);
            State state= State.valueOf(resultSet.getString(i++));
            Calendar bookingDate = Calendar.getInstance();
            bookingDate.setTime(resultSet.getTimestamp(i++));
            Long code = new Long(resultSet.getLong(i++));

            /* Return offer. */
            return new Booking(bookingId, offerId, userId, enjoymentDate, creditCardNumber, state, bookingDate, code);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void annulBooking(Connection connection,Long offerId)
            throws BookingIsNotValidException {
       
    	String queryString = "UPDATE Booking"
                + " SET state = 'nullbooking' WHERE offerId = ?";

       try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {


            int i = 1;
            
            preparedStatement.setLong(i++, offerId.longValue());
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            throw new BookingIsNotValidException();
        }

    }
}