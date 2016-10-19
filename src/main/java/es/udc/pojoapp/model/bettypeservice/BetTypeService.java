package es.udc.pojoapp.model.bettypeservice;

import java.util.List;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.betType.BetType;
import es.udc.pojoapp.model.option.Option;

public interface BetTypeService {
	
	//solo usada para los test
	public BetType findBetType(Long Id) throws InstanceNotFoundException;
	
	public Option findOption(Long Id) throws InstanceNotFoundException;
	
	public void updateState(Long betTypeId, List<Long> options) throws InstanceNotFoundException, 
															EventoNoEmpezadoException, OpcionesActualizadasException, UnicaGanadoraException;

	public void create(BetType bet, List<Option> options, Long eventId) throws EventEmpezadoException, 
															InstanceNotFoundException, BetTypesIgualesException;
}
