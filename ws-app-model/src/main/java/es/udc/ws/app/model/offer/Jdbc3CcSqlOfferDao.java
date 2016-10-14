package es.udc.ws.app.model.offer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;



public class Jdbc3CcSqlOfferDao extends AbstractSqlOfferDao{
	
	public Offer create(Connection connection, Offer offer){
		 /* Create "queryString". */
        String queryString = "INSERT INTO Offer"
                + " (name,  description, realPrice, discountPrice, commission, bookingDate, enjoymentDate, validOffer  )"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                        queryString, Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, offer.getName());
            preparedStatement.setString(i++, offer.getDescription());
            preparedStatement.setFloat(i++, offer.getRealPrice());
            preparedStatement.setFloat(i++, offer.getDiscountPrice());
            preparedStatement.setFloat(i++, offer.getCommission());
            Timestamp date = offer.getBookingDate() != null ? new Timestamp(
                    offer.getBookingDate().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++, date);
            Timestamp date2 = offer.getEnjoymentDate() != null ? new Timestamp(
                            offer.getEnjoymentDate().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++, date2);
            preparedStatement.setBoolean(i++, offer.getValidOffer());


            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long offerId = resultSet.getLong(1);

            /* Return offer. */
            return new Offer(offerId, offer.getName(), offer.getDescription(), offer.getRealPrice(), offer.getDiscountPrice(),
            		offer.getCommission(), offer.getBookingDate(),offer.getEnjoymentDate(), offer.getValidOffer());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

		
	}
}
