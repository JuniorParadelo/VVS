package es.udc.ws.app.client.ui;

import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.client.service.ClientOfertaServiceFactory;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.OfertaReservadaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.enums.EstadoOferta;
import es.udc.ws.app.exceptions.ReservaExpirationException;
import es.udc.ws.app.exceptions.ReservaNoValidaException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class OfertaServiceClient {

    public static void main(String[] args) {

        if(args.length == 0) {
            printUsageAndExit();
        }
        
        ClientOfertaService clientOfertaService = ClientOfertaServiceFactory.getService();
        if("-addOffer".equalsIgnoreCase(args[0])) {
            validateArgs(args, 7, new int[] {5, 6});
            addOfer(clientOfertaService, args);

        } else if("-removeOffer".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});
            removeOffer(clientOfertaService, args);

        } else if("-updateOffer".equalsIgnoreCase(args[0])) {
           validateArgs(args, 8, new int[] {1, 6, 7});
           updateOffer(clientOfertaService, args);

        } else if("-findOffers".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});
            findOffers(clientOfertaService, args);
            
        } else if("-findOffer".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});
            findOffer(clientOfertaService, args);
            
        } else if("-reserveOffer".equalsIgnoreCase(args[0])) {
            validateArgs(args, 4, new int[] {1});
            reserveOffer(clientOfertaService, args);

        } else if("-claimReservation".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});
        	claimReservation(clientOfertaService, args);
        	
        } else if("-invalidateOffer".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});
            invalidateOffer(clientOfertaService, args);
        	
        } else if("-findOfferReservations".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});
            findOfferReservations(clientOfertaService, args);
        	
        } else if("-findUserReservations".equalsIgnoreCase(args[0])) {
            validateArgs(args, 3, new int[] {});
            findUserReservations(clientOfertaService, args);
        	
        } else if("-findUserReservedOffers".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});
            findUserReservedOffers(clientOfertaService, args);
        	
        }

    }

    private static void findUserReservedOffers(ClientOfertaService clientOfertaService, String[] args) {

        // [buscarOfertasReservadasDeUnUsuario] OfertaServiceClient -findUserReservedOffers <email>
    	
    	List<OfertaReservadaDto> ofertasReservadas = clientOfertaService.buscarOfertasReservadasDeUnUsuario(args[1]);
	    System.out.println("Encontradas " + ofertasReservadas.size() + " oferta(s) reservada(s) del usuario '" + args[1] + "'");
		for (OfertaReservadaDto i: ofertasReservadas) {
			System.out.println(i.toString());
		}
	}

	private static void findUserReservations(ClientOfertaService clientOfertaService, String[] args) {
    	
        // [buscarReservasDeUnUsuario] OfertaServiceClient -findUserReservations <email> <solo_validas>
		
    	List<ReservaDto> reservas = clientOfertaService.buscarReservasDeUnUsuario(args[1], Boolean.parseBoolean(args[2]));
	    System.out.println("Encontradas " + reservas.size() + " reserva(s) del usuario '" + args[1] + "'");
		for (ReservaDto i: reservas) {
			System.out.println(i.toString());
		}
	}

	private static void findOfferReservations(ClientOfertaService clientOfertaService, String[] args) {

        // [buscarReservasDeUnaOferta] OfertaServiceClient -findOfferReservations <codigoOferta>
    	
    	try {
			List<ReservaDto> reservas = clientOfertaService.buscarReservasDeUnaOferta(Long.parseLong(args[1]));
		    System.out.println("Encontradas " + reservas.size() + " reserva(s) de la oferta '" + args[1] + "'");
			for (ReservaDto i: reservas) {
				System.out.println(i.toString());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace(System.err);
			printUsageAndExit();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace(System.err);
		}

	}

	private static void invalidateOffer(ClientOfertaService clientOfertaService, String[] args) {

        // [invalidarOferta] OfertaServiceClient -invalidateOffer <codigoOferta>

        	try {
				clientOfertaService.invalidarOferta(Long.parseLong(args[1]));
	            System.out.println("Oferta invalidada correctamente");
			} catch (NumberFormatException e) {
				e.printStackTrace(System.err);
				printUsageAndExit();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
	}

	private static void claimReservation(ClientOfertaService clientOfertaService, String[] args) {

        // [reservarOferta] OfertaServiceClient -claimReservation <codigoReserva>

        try {
        	clientOfertaService.reclamarReserva(Integer.parseInt(args[1]));
            System.out.println("Reserva reclamada correctamente");
        } catch (NumberFormatException | InstanceNotFoundException | ReservaExpirationException | ReservaNoValidaException ex) {
            ex.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
	}

	private static void reserveOffer(ClientOfertaService clientOfertaService, String[] args) {

        // [reservarOferta] OfertaServiceClient -reserveOffer <ofertaId> <email> <tarjeta>

        try {
            Long reserva = clientOfertaService.reservarOferta(Long.parseLong(args[1]), args[2], args[3]);
            System.out.println("Oferta " + args[1] + " reservada correctamente con código de reserva " + reserva);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
	}

	private static void findOffers(ClientOfertaService clientOfertaService, String[] args) {

		// [buscarOfertas] OfertaServiceClient -findOffers <keywords>
	
	    List<OfertaDto> ofertas = clientOfertaService.buscarOfertas(args[1]);
	    System.out.println("Encontradas " + ofertas.size() + " ofertas(s) con keywords '" + args[1] + "'");
	    for (OfertaDto o: ofertas) {
	        System.out.println(o.toString());
	    }
	}

	private static void findOffer(ClientOfertaService clientOfertaService, String[] args) {
        
        // [buscarOferta] OfertaServiceClient -findOffer <ofertaID>

        OfertaDto oferta;
		try {
			oferta = clientOfertaService.buscarOferta(Long.parseLong(args[1]));
            System.out.println(oferta.toString());
		} catch (NumberFormatException e) {
			printUsageAndExit();
		} catch (InstanceNotFoundException e) {
            e.printStackTrace(System.err);
		}
	}

	private static void updateOffer(ClientOfertaService clientOfertaService, String[] args) {
		
        // [actualizarOferta] OfertaServiceClient -updateOffer <ofertaId> <nombre> <descripcion> <fechaReservar> <fechaReclamar> <precioReal> <precioDescontado>

        try {
        	Long ofertaId = Long.parseLong(args[1]);
        	EstadoOferta estado = clientOfertaService.buscarOferta(ofertaId).getEstado();
            clientOfertaService.actualizarOferta(new OfertaDto(Long.parseLong(args[1]), args[2], args[3], validateStringAsCalendar(args[4]), validateStringAsCalendar(args[5]), Float.parseFloat(args[6]), Float.parseFloat(args[7]), estado));
            System.out.println("Oferta " + args[1] + " actualizada correctamente");
         } catch (NumberFormatException | InputValidationException | InstanceNotFoundException ex) {
             ex.printStackTrace(System.err);
         } catch (Exception ex) {
             ex.printStackTrace(System.err);
         }
	}

	private static void removeOffer(ClientOfertaService clientOfertaService, String[] args) {
		
        // [eliminarOferta] OfertaServiceClient -removeOffer <ofertaId>

        try {           
        	clientOfertaService.eliminarOferta(Long.parseLong(args[1]));
            System.out.println("Oferta " + args[1] + " eliminada correctamente");
        } catch (NumberFormatException | InstanceNotFoundException ex) {
            ex.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
	}

	private static void addOfer(ClientOfertaService clientOfertaService, String[] args) {
		
        // [añadirOferta] OfertaServiceClient -addOffer <nombre> <descripcion> <fechaReservar> <fechaReclamar> <precioReal> <precioDescontado> 

        try {
            Long ofertaId = clientOfertaService.añadirOferta(new OfertaDto(null, args[1], args[2], validateStringAsCalendar(args[3]), validateStringAsCalendar(args[4]), Float.parseFloat(args[5]), Float.parseFloat(args[6]), EstadoOferta.VALIDA));
            System.out.println("Oferta " + ofertaId + " creada correctamente");
        } catch (NumberFormatException | InputValidationException ex) {
            ex.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
	}

	private static void validateArgs(String[] args, int expectedArgs, int[] numericArguments) {
		
        if(expectedArgs != args.length) {
        	System.out.println("expectedArgs: " + expectedArgs);
        	System.out.println("args.length: " + args.length);
            printUsageAndExit();
        }
        
        for(int i = 0 ; i< numericArguments.length ; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch(NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }
    
    private static Calendar validateStringAsCalendar(String exp) {
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	try {
			cal.setTime(sdf.parse(exp));
		} catch (ParseException e) {
			System.err.println("Date must have format: dd/MM/yyyy HH:mm");
			printUsageAndExit();
		}
    	return cal;
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "	[añadirOferta]		OfertaServiceClient -addOffer <nombre> <descripcion> <fechaReservar> <fechaReclamar> <precioReal> <precioDescontado>\n" +
                "	[eliminarOferta]	OfertaServiceClient -removeOffer <ofertaId>\n" +
                "	[actualizarOferta]	OfertaServiceClient -updateOffer <ofertaId> <nombre> <descripcion> <fechaReservar> <fechaReclamar> <precioReal> <precioDescontado>\n" +
                "	[buscarOferta]		OfertaServiceClient -findOffer <ofertaId>\n" +
                "	[buscarOfertas]		OfertaServiceClient -findOffers <keywords>\n" +
                "	[reservarOferta]	OfertaServiceClient -reserveOffer <ofertaId> <email> <tarjeta>\n" +
                "	[invalidarOferta] 	OfertaServiceClient -invalidateOffer <codigoOferta>\n" +
                "	[reclamarReserva]	OfertaServiceClient -claimReservation <codigoReserva>\n" +
                "	[buscarReservasDeUnaOferta] OfertaServiceClient -findOfferReservations <codigoOferta>\n" +
                "	[buscarReservasDeUnUsuario] OfertaServiceClient -findUserReservations <email> <solo_validas>\n" +
                "	[buscarOfertasReservadasDeUnUsuario] OfertaServiceClient -findUserReservedOffers <email>\n");
    }

}
