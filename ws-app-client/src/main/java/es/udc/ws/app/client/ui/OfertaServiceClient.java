package es.udc.ws.app.client.ui;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;


import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.client.service.ClientOfertaServiceFactory;
import es.udc.ws.app.dto.MixtoDto;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.FechaReservaExpiradaException;
import es.udc.ws.app.exceptions.OfertaYaReservadaException;
import es.udc.ws.app.exceptions.ReservaYaReclamadaException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class OfertaServiceClient {

	public static void main(String[] args) {

		
        if(args.length == 0) {
            printUsageAndExit("vacio");
        }
        
        /* Recogemos el cliente */
        
        ClientOfertaService clientOfertaService = ClientOfertaServiceFactory.getService();
                
        /*	Segun el comando introducido por el usuario llamamos a un metodo o a otro*/
        
        if("-annadirO".equalsIgnoreCase(args[0])) {
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 		Opcion Annadir					");
        	System.out.println();
        	
            validateArgs(args, 7, new int[] {5, 6});

            // [add] OfertaServiceClient -annadirO <nombre> <descrip> <flimRes> <fLimRec> <preal> <pdesc> 

            try {
            	
            	SimpleDateFormat d = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            	Calendar fLimRes = Calendar.getInstance();
            	fLimRes.setTime(d.parse(args[3]));
            	
            	Calendar fLimRec = Calendar.getInstance();
            	fLimRec.setTime(d.parse(args[4]));

    			OfertaDto o  = clientOfertaService.annadirOferta(new OfertaDto(args[1],args[2],
                		fLimRes,fLimRec,
                		Float.parseFloat(args[5]),Float.parseFloat(args[6]),false));
                
                System.out.println("Oferta Creada");
                System.out.println(o.toString());
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
            } catch (NumberFormatException | InputValidationException ex) {
            	ex.printStackTrace(System.err);
            	
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
            } catch (Exception ex) {
                ex.printStackTrace(System.err);

                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
            }
            
            
            
        }
        
        if("-actualizarO".equalsIgnoreCase(args[0])){
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 			Opcion Actualizar				");
        	System.out.println();
        	
        	validateArgs(args,8,new int[] {1,6,7});
        	
        	// [update] OfertaServiceClient -actualizarO <id> <nombre> <descrip> <flimRec> <fLimRes> <preal> <pdesc>
        
        	try {
            	SimpleDateFormat d = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            	Calendar fLimRes = Calendar.getInstance();
            	fLimRes.setTime(d.parse(args[4]));
            	
            	Calendar fLimRec = Calendar.getInstance();
            	fLimRec.setTime(d.parse(args[5]));

    			OfertaDto o  = new OfertaDto(Long.parseLong(args[1]),args[2],args[3],
                		fLimRes,fLimRec,Float.parseFloat(args[6]),Float.parseFloat(args[7]),false);
    			clientOfertaService.actualizarOferta(o);
                
                
                System.out.println("Oferta Actualizada");
                System.out.println(o.toString());
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");

            }
        	catch (NumberFormatException | InputValidationException ex) {
            	ex.printStackTrace(System.err);
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
            	
            }
        	catch (Exception ex) {
                ex.printStackTrace(System.err);
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
            }
        
        }
        
        
        
        if("-inv".equalsIgnoreCase(args[0])){
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 			Opcion Invalidar				");
        	System.out.println();
        	
        	
        	validateArgs(args,2,new int[] {1});
        	
        	// [invalidar] OfertaServiceClient -inv <idOferta>
        
        	try {
      
    			clientOfertaService.invalidarOferta(Long.parseLong(args[1]));
                
                System.out.println("Oferta con ID "+args[1]+" invalidada");
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");

            } 
        	catch (NumberFormatException | InputValidationException ex) {
            	ex.printStackTrace(System.err);
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
            	
            } 
        	catch (Exception ex) {
                ex.printStackTrace(System.err);
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
            } 
        
        }
        if("-borrarO".equalsIgnoreCase(args[0])){
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 			Opcion Borrar					");
        	System.out.println();
        	
        	
        	validateArgs(args,2,new int[] {1});
        	
        	// [Borrar oferta] OfertaServiceClient -inv <idOferta>
        
        	try {
      
    			clientOfertaService.borrarOferta(Long.parseLong(args[1]));
                
                System.out.println("Oferta con ID "+args[1]+" borrada");
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");

            }
        	catch (NumberFormatException | InputValidationException ex) {
            	ex.printStackTrace(System.err);
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
            } 
        	catch (Exception ex) {
                ex.printStackTrace(System.err);
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
            }
        
        }
        
        if("-buscarOfertaID".equalsIgnoreCase(args[0])){
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 		Opcion Buscar Oferta Por ID			");
        	System.out.println();
        	
        	
        	validateArgs(args,2,new int[] {1});
        	
        	// [buscarOfertaID] OfertaServiceClient -buscarOfertaID <idOferta>
        
        	try {
      
    			OfertaDto o = clientOfertaService.buscarOfertaID(Long.parseLong(args[1]));
    			System.out.println("Oferta Encontrada: ");
    			o.toString();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
            }
        	catch (NumberFormatException | InputValidationException ex) {
            	ex.printStackTrace(System.err);
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
            }
        	catch (Exception ex) {
                ex.printStackTrace(System.err);
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
            }
        
        }
        
        
        if("-buscarOfertaPClave".equalsIgnoreCase(args[0])){
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 	Opcion Buscar Oferta Por Palabras		");
        	System.out.println();
        	
        	validateArgs(args,2,new int[] {});
        	
        	// [buscarOfertaPClave] OfertaServiceClient -buscarOfertaPClave <palabrasClave>
        
    		List<OfertaDto> ofertas = new ArrayList<>();
			try {
				ofertas = clientOfertaService.buscarOfertaPalabrasClave(args[1]);

				System.out.println("Ofertas Encontradas");
				System.out.println();
				for ( OfertaDto o : ofertas  ) {
					o.toString();

				}

                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			}
        }
        
        if("-reservarO".equalsIgnoreCase(args[0])){
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 			Opcion Reservar					");
        	System.out.println();
        	
        	
        	validateArgs(args,4,new int[] {1});	
        	// [reservarO] OfertaServiceClient -reservarO <idOferta> <usuario> <num tarj>
        	
        		try {
					long id = clientOfertaService.reservarOferta(args[2], args[3], Long.parseLong(args[1]));
					System.out.println("Reservada con el usuario "+ args[2]+" la oferta "+args[1]+" ["+id+"]");
	                System.out.println();
	                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
	                
				} catch (NumberFormatException e) {
					e.printStackTrace();
	                System.out.println();
	                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
	                
				} catch (InputValidationException e) {
					e.printStackTrace();
	                System.out.println();
	                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
	                
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
	                System.out.println();
	                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
	                
				} catch (OfertaYaReservadaException e) {
					e.printStackTrace();
	                System.out.println();
	                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
	                
				} catch (FechaReservaExpiradaException e) {
					e.printStackTrace();
	                System.out.println();
	                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
	                
				} 
        	
        }
        if("-buscarResID".equalsIgnoreCase(args[0])){
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 		Opcion Buscar Reserva Por ID		");
        	System.out.println();
        	
        	validateArgs(args, 2, new int[] {1});
        	//[buscarResID] OfertaServiceClient -buscarResID <idOferta>
    		List<ReservaDto> reservas = new ArrayList<>();

        	try {
				reservas=clientOfertaService.buscarReservaIDOferta(Long.parseLong(args[1]));
				
				System.out.println("Reservas Encontradas");
				System.out.println();
				for ( ReservaDto r : reservas  ) {
					r.toString();
				}
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			} catch (InputValidationException e) {
				e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			}
        }
        
        
        if("-buscarResUs".equalsIgnoreCase(args[0])){
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 	Opcion Buscar Reserva Por Usuario		");
        	System.out.println();
        	
        	
        	validateArgs(args,3,new int[] {});
        	
        	List<ReservaDto>reservas = new ArrayList<>();
        	
        	try {
        		
        		if (args[2].equals("validas")){
    				reservas=clientOfertaService.buscarReservaUsuario(args[1], true);
        		}
        		if (args[2].equals("todas")){
    				reservas=clientOfertaService.buscarReservaUsuario(args[1], false);
        		}
        		
        		System.out.println("Reservas Encontradas");
				System.out.println();
				
				for ( ReservaDto r : reservas  ) {
					r.toString();
				}
				
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			} catch (InputValidationException e) {
				e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			}
        	
        	
        }
        
        if("-buscarOfUs".equalsIgnoreCase(args[0])){
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 	Opcion Buscar Oferta Por Usuario		");
        	System.out.println();
        	
        	
        	validateArgs(args,2, new int[]{});
        	
        	List<MixtoDto> mix = new ArrayList<>();
        	try{
        		mix=clientOfertaService.buscarOfertasUsuario(args[1]);
        		
        		System.out.println("Mixtos Encontrados");
				System.out.println();
        		for ( MixtoDto mixt : mix  ) {
					mixt.imprimirMixto();
				}

                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        		
        	}catch(InputValidationException e){
        		e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
        	}catch(DatatypeConfigurationException e){
        		e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
        	}catch(InstanceNotFoundException e){
        		e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
        	}
        	
        }
        
        
        if("-reclamarRes".equalsIgnoreCase(args[0])){
        	
        	System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
        	System.out.println(" 		Opcion Reclamar Reserva				");
        	System.out.println();
        	
        	validateArgs(args,2, new int [] {1});
        	
        	try {
				clientOfertaService.reclamarReserva(Long.parseLong(args[1]));
				System.out.println("Reserva con codigo "+args[1]+" reclamada");
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			} catch (InputValidationException e) {
				e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			} catch (ReservaYaReclamadaException e) {
				e.printStackTrace();
                System.out.println();
                System.out.println("*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.");
                
			}
        	
        }
        
	}
        
	
	public static void validateArgs(String[] args, int expectedArgs,int[] numericArguments) {
		
		if (expectedArgs != args.length) {
			printUsageAndExit(args[0]);
		}
		
		for (int i = 0; i < numericArguments.length; i++) {
			int position = numericArguments[i];
			
			try {
				Float.parseFloat(args[position]);
			}
			catch (NumberFormatException n) {
				printUsageAndExit(args[0]);
			}
			
		}
	}

	public static void printUsageAndExit(String opcion) {
		
		printUsage(opcion);
		System.exit(-1);
	
	}

	
	public static void printUsage(String opcion) {
		
		switch (opcion) {
			case "vacio" :	System.err.println("Usage:\n"
					+ "		Introduzca una opcion correcta \n");
				break;
				
			case "-annadirO" : System.err.println("Usage:\n"
					+ "    [annadirOferta] OfertaServiceClient -annadirO <nombre> <descrip> <flimRes> <fLimRec> <preal> <pdesc> \n");
				break;
					
			case "-actualizarO" : System.err.println("Usage:\n"
					+ "    [actualizarOferta] OfertaServiceClient -actualizarO <IdOferta> <nombre> <descrip> <flimRes> <fLimRec> <preal> <pdesc>\n");
				break;
					
			case "-borrarO" : System.err.println("Usage:\n"
					+ "    [borrarOferta] OfertaServiceClient -borrarO  \n");
				break;
					
			case "-buscarOfID" : System.err.println("Usage:\n"
					+ "    [buscarOferta por ID] OfertaServiceClient -buscarOfID  \n");
				break;
				
			case "-buscarOfPalabras" : System.err.println("Usage:\n"
					+ "    [buscarOferta por Palabras Clave] OfertaServiceClient -buscarOfPalabras  \n");
				break;
				
			case "-inv": System.err.println("Usage:\n"
					+ "    [invalidarOferta] OfertaServiceClient -inv  <IdOferta>\n");
				break;
				
			case "-reservarOf" : System.err.println("Usage:\n"
					+ "    [reservarOferta] OfertaServiceClient -reservarOf  \n");
				break;
				
			case "-reclamarRes" : System.err.println("Usage:\n"
					+ "    [reclamarReserva] OfertaServiceClient -reclamarRes  \n");
				break;
				
			case "-buscarResID" : System.err.println("Usage:\n"
					+ "    [buscar Reserva por ID] OfertaServiceClient -buscarResID  \n");
				break;
				
			case "-buscarResUsuario" : System.err.println("Usage:\n"
					+ "    [buscar Reserva por Usuario] OfertaServiceClient -buscarResUsuario  \n");
				break;
				
			case "-buscarOfUs" : System.err.println("Usage:\n"
					+ "    [buscar Reserva por Usuario] OfertaServiceClient -buscarResUsuario  \n");
				break;
		}
	}

}
