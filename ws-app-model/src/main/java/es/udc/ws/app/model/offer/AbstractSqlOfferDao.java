package es.udc.ws.app.model.offer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlOfferDao implements SqlOfferDao {

    protected AbstractSqlOfferDao() {
    }

    @Override
    public Offer findOfferByOfferId (Connection connection, Long offerId) throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "SELECT name, description, realPrice, discountPrice, commission, "
                + "bookingDate, enjoymentDate, validOffer FROM Offer WHERE offerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, offerId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(offerId,
                        Offer.class.getName());
            }

            /* Get results. */
            i = 1;
            String name = resultSet.getString(i++);
            String description = resultSet.getString(i++);
            float realPrice = resultSet.getFloat(i++);
            float discountPrice = resultSet.getFloat(i++);
            float commission = resultSet.getFloat(i++);
            Calendar bookingDate = Calendar.getInstance();
            bookingDate.setTime(resultSet.getTimestamp(i++));
            Calendar enjoymentDate = Calendar.getInstance();
            enjoymentDate.setTime(resultSet.getTimestamp(i++));
            Boolean validOffer = resultSet.getBoolean(i++);

            /* Return offer. */
            return new Offer(offerId, name, description, realPrice, discountPrice, commission,
                    bookingDate, enjoymentDate, validOffer);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Offer> findOfferByKeywords(Connection connection,String keywords, Calendar bookingDate,boolean validOffer){
	
        /* Create "queryString". */
        String[] words = keywords != null ? keywords.split(" ") : null;
        String queryString = "SELECT offerId, name, description, realPrice, discountPrice, commission, "
                + "bookingDate, enjoymentDate, validOffer FROM Offer";
        
        if (bookingDate != null)
                queryString += " WHERE bookingDate < ? AND bookingDate > ?";

        if (validOffer==true)
        	if (bookingDate == null)
        		queryString += " WHERE validOffer = ?";
        	else
        		queryString += " AND validOffer = ?";

        if (words != null && words.length > 0) {
        	if (bookingDate == null && validOffer == false)
        		queryString += " WHERE";
            for (int i = 0; i < words.length; i++) {
                if (i > 0) {
                   queryString += " AND";
                }
                if (i==0 && (validOffer==true || bookingDate != null))
                    queryString += " AND";
                queryString += " LOWER(description) LIKE LOWER(?)";
            }
        }
       
        queryString += " ORDER BY name";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

        	int j = 1; 
        	
        	if (bookingDate != null){
            	preparedStatement.setTimestamp(j++, new Timestamp(bookingDate.getTimeInMillis()));
            	preparedStatement.setTimestamp(j++, new Timestamp(Calendar.getInstance().getTimeInMillis()));
        	}
        	
        	 if (validOffer == true)
             	preparedStatement.setBoolean(j++, validOffer);
        	
            if (keywords != null) {
                /* Fill "preparedStatement". */
                for (int i = 0; i < words.length; i++) {
                    preparedStatement.setString(j++, "%" + words[i] + "%");
                }
            }
            

           


            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read movies. */
            List<Offer> offers = new ArrayList<Offer>();

            while (resultSet.next()) {

                int i = 1;
                Long offerId = new Long(resultSet.getLong(i++));
                String name = resultSet.getString(i++);
                String description = resultSet.getString(i++);
                float realPrice = resultSet.getFloat(i++);
                float discountPrice = resultSet.getFloat(i++);
                float commission = resultSet.getFloat(i++);
                Calendar enjoymentDate = Calendar.getInstance();
                i++;
                enjoymentDate.setTime(resultSet.getTimestamp(i++));
               
                offers.add(new Offer(offerId, name, description, realPrice, discountPrice, commission,
                        bookingDate, enjoymentDate, validOffer));//AQUI HAY UN PROBLEMAAAA, CAMBIA EL VALIDOFFER

            }

            /* Return movies. */
            return offers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Connection connection, Offer offer)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "UPDATE Offer SET name = ?, description = ?, realPrice "
                + "= ?, discountPrice = ?, commission = ?, validOffer = ? WHERE offerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, offer.getName());
            preparedStatement.setString(i++, offer.getDescription());
            preparedStatement.setFloat(i++, offer.getRealPrice());
            preparedStatement.setFloat(i++, offer.getDiscountPrice());
            preparedStatement.setFloat(i++, offer.getCommission());
            preparedStatement.setBoolean(i++, offer.getValidOffer());
            preparedStatement.setLong(i++, offer.getOfferId());
            
            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(offer.getOfferId(),
                        Offer.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(Connection connection, Long offerId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "DELETE FROM Offer WHERE offerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, offerId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(offerId,
                        Offer.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
