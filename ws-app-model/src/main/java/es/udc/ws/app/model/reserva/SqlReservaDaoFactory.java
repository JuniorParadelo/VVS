package es.udc.ws.app.model.reserva;


import es.udc.ws.util.configuration.ConfigurationParametersManager;


public class SqlReservaDaoFactory {

	private final static String CLASS_NAME_PARAMETER="SqlReservaDaoFactory.className";
	private static SqlReservaDao dao = null;
	
	
	@SuppressWarnings("rawtypes")
	private static SqlReservaDao getInstance() {
		
		try {
			String nombreDao = ConfigurationParametersManager.getParameter(CLASS_NAME_PARAMETER);
			Class claseDao = Class.forName(nombreDao);
			
			return (SqlReservaDao) claseDao.newInstance();
			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public synchronized static SqlReservaDao getDao() {
		
		if (dao==null){
			dao=getInstance();
		}
		return dao;
		
		
	}
	
}
