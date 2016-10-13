package es.udc.ws.app.model.oferta;

import es.udc.ws.util.configuration.ConfigurationParametersManager;



public class SqlOfertaDaoFactory {

	private final static String CLASS_NAME_PARAMETER="SqlOfertaDaoFactory.className";
	private static SqlOfertaDao dao = null;
	
	@SuppressWarnings("rawtypes")
	private static SqlOfertaDao getInstance() {
		
		try{
			String nombreDao = ConfigurationParametersManager.getParameter(CLASS_NAME_PARAMETER);
			Class claseDao = Class.forName(nombreDao);
			return (SqlOfertaDao) claseDao.newInstance();	
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public synchronized static SqlOfertaDao getDao() {
		
		if (dao==null) {
			dao = getInstance();
		}
		return dao;
	}
	
	
}
