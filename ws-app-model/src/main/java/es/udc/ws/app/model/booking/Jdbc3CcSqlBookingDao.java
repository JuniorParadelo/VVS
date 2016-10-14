package es.udc.ws.app.model.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc3CcSqlBookingDao extends AbstractSqlBookingDao {

    @Override
    public Booking create(Connection connection, Booking booking) {
        /* Create "queryString". */
        String queryString = "INSERT INTO Booking"
                + " (offerId, userId, enjoymentDate, creditCardNumber,"
                + " state, bookingDate, code) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                        queryString, Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, booking.getOfferId());
            preparedStatement.setString(i++, booking.getUserId());
            Timestamp enjoymentDate = new Timestamp(booking.getEnjoymentDate().getTime()
                    .getTime());
            preparedStatement.setTimestamp(i++, enjoymentDate);
            preparedStatement.setString(i++, booking.getCreditCardNumber());
            preparedStatement.setString(i++, booking.getState().toString());
            Timestamp bookingDate = new Timestamp(booking.getBookingDate().getTime().getTime());
            preparedStatement.setTimestamp(i++, bookingDate);
            preparedStatement.setLong(i++, booking.getCode());
            
            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long bookingId = resultSet.getLong(1);

            /* Return booking. */
            return new Booking(bookingId, booking.getOfferId(), booking.getUserId(),
            		booking.getEnjoymentDate(), booking.getCreditCardNumber(), 
            		booking.getState(), booking.getBookingDate(), booking.getCode());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
