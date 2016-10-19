package es.udc.pojoapp.model.bettypeservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pojoapp.model.betType.BetType;
import es.udc.pojoapp.model.betType.BetTypeDao;
import es.udc.pojoapp.model.event.Event;
import es.udc.pojoapp.model.event.EventDao;
import es.udc.pojoapp.model.option.Option;
import es.udc.pojoapp.model.option.OptionDao;

@Service("betTypeService")
public class BetTypeServiceImpl implements BetTypeService {

	@Autowired
	private BetTypeDao betDao;
	
	@Autowired
	private EventDao eventDao;

	@Autowired
	private OptionDao optionDao;

	@Override
	@Transactional
	public void create(BetType bet, List<Option> options,Long eventId) throws InstanceNotFoundException, EventEmpezadoException, BetTypesIgualesException {
		Event event = eventDao.find(eventId);
		if (betDao.findEqualsBetTypesEvent(bet.getQuestion(), eventId))
			throw new BetTypesIgualesException("Existe un tipo de apuesta ya creado igual al que se intenta crear asociado al evento: "+eventId);
		if (event.getDate().before(Calendar.getInstance()))
			throw new EventEmpezadoException("Intento de crear tipos de apuesta en el evento que ya empezó: "+eventId);
		bet.setEvent(event);
		betDao.save(bet);
		if (event.getBetTypes() == null) {
			Set<BetType> be = new HashSet<BetType>();
			be.add(bet);
			event.setBetTypes(be);
		}
		else
			event.getBetTypes().add(bet);
		
		for (Option o : options) {
			o.setBetType(bet);
			optionDao.save(o);
			if (bet.getOptions() == null) {
				List<Option> opts = new ArrayList<Option>();
				opts.add(o);
				bet.setOptions(opts);
			}
			else
				bet.getOptions().add(o);
		}
	}

	//solo usada para los test
	@Override
	@Transactional(readOnly = true)
	public BetType findBetType(Long Id) throws InstanceNotFoundException {
		return betDao.find(Id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Option findOption(Long Id) throws InstanceNotFoundException {
		return optionDao.find(Id);
	}

	@Override
	@Transactional
	public void updateState(Long betTypeId, List<Long> options) throws InstanceNotFoundException, EventoNoEmpezadoException, OpcionesActualizadasException, UnicaGanadoraException{
		List<Option> optionslist = betDao.find(betTypeId).getOptions();
		Event evento = betDao.find(betTypeId).getEvent();
		if (Calendar.getInstance().before(evento.getDate()))
			throw new EventoNoEmpezadoException("Intento de actualizar opciones asociadas "
					+ "a un evento que todavía no empezó, con código de betType: "+ betTypeId);
		if ((betDao.find(betTypeId).isUnica()) && (options.size()!=1))
			throw new UnicaGanadoraException("Intento setear multiples opciones ganadoras en el tipo de apuesta "+betTypeId
					+" que solo admite una única opción ganadora.");
		for (Option o : optionslist) {
			if (o.getWin() != null)
				throw new OpcionesActualizadasException("Las opciones del tipo de apuesta: "+betTypeId
						+" ya fueron actualizadas antes o por otro usuario administrador");
			if (options.contains(o.getId())) {
				o.setWin(true);
			} else {
				o.setWin(false);
			}

		}
	}
}
