package es.udc.ws.app.model.booking;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

/**
 * A factory to get
 * <code>SqlBookingDao</code> objects. <p> Required configuration parameters: <ul>
 * <li><code>SqlBookingDaoFactory.className</code>: it must specify the full class
 * name of the class implementing
 * <code>SqlBookingDao</code>.</li> </ul>
 */
public class SqlBookingDaoFactory {

    private final static String CLASS_NAME_PARAMETER = "SqlBookingDaoFactory.className";
    private static SqlBookingDao dao = null;

    private SqlBookingDaoFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static SqlBookingDao getInstance() {
        try {
            String daoClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class daoClass = Class.forName(daoClassName);
            return (SqlBookingDao) daoClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized static SqlBookingDao getDao() {

        if (dao == null) {
            dao = getInstance();
        }
        return dao;

    }
}
