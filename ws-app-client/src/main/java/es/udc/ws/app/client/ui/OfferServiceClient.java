package es.udc.ws.app.client.ui;

import es.udc.ws.app.client.service.ClientOfferService;
import es.udc.ws.app.client.service.ClientOfferServiceFactory;
import es.udc.ws.app.dto.BookingDto;
import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class OfferServiceClient {

    public static void main(String[] args) {

        if(args.length == 0) {
            printUsageAndExit();
        }
        ClientOfferService clientOfferService =
                ClientOfferServiceFactory.getService();
         DateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	     Calendar bookingDate = new GregorianCalendar();
	     Calendar enjoymentDate = new GregorianCalendar();
        if("-addOffer".equalsIgnoreCase(args[0])) {
            validateArgs(args, 7, new int[] {5, 6});

            /*[add] OfferServiceClient -addOffer <name> <description> <bookingDate> <enjoymentDate>
            <realPrice> <discountPrice>*/

            try {
            	bookingDate.setTime(formato.parse(args[3]));
            	enjoymentDate.setTime(formato.parse(args[4]));
                Long OfferId = clientOfferService.addOffer(new OfferDto(1L,args[1], args[2],
                		Short.valueOf(args[5]),Short.valueOf(args[6]),bookingDate,enjoymentDate));

                System.out.println("Offer " + OfferId + " created sucessfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else if("-removeOffer".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            // [remove] OfferServiceClient -removeOffer <OfferId>

            try {
                clientOfferService.removeOffer(Long.parseLong(args[1]));

                System.out.println("Offer with id " + args[1] +
                        " removed sucessfully");

            } catch (NumberFormatException | InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-updateOffer".equalsIgnoreCase(args[0])) {
           validateArgs(args, 8, new int[] {1, 6, 7});

           // [update] OfferServiceClient -updateOffer <OfferId> <name> <description> <bookingDate> <enjoymenteDate>
           //<realPrice> <discountPrice>
           try {
        	    bookingDate.setTime(formato.parse(args[4]));
           		enjoymentDate.setTime(formato.parse(args[5]));
                clientOfferService.updateOffer(new OfferDto(Long.valueOf(args[1]),
                        args[2], args[3],Short.valueOf(args[6]),Short.valueOf(args[7]), bookingDate,enjoymentDate));

                System.out.println("Offer " + args[1] + " updated sucessfully");

            } catch (NumberFormatException | InputValidationException |
                     InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
           
        } else if("-findOffer".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            // [find] OfferServiceClient -findOffer <offerId>
            
            try {
               OfferDto OfferDto = clientOfferService.findOffer(Long.parseLong(args[1]));
                    System.out.println("Found offer: " + OfferDto.getOfferId() +
                            " Name: " + OfferDto.getName() +
                            " Description: " + OfferDto.getDescription() +
                            " Real Price: " + OfferDto.getRealPrice()+
                            " Discount Price: " + OfferDto.getDiscountPrice() +
                            " Booking Date: " + OfferDto.getBookingDate()+
                            " Enjoyment Date: " + OfferDto.getEnjoymentDate());
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-findOffers".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            // [find] OfferServiceClient -findOffers <offerId>
            
            try {
                List<OfferDto> Offers = clientOfferService.findOffers(args[1]);
                System.out.println("Found " + Offers.size() +
                        " Offer(s) with offerId '" + args[1] + "'");
                for (int i = 0; i < Offers.size(); i++) {
                    OfferDto OfferDto = Offers.get(i);
                    System.out.println("Id: " + OfferDto.getOfferId() +
                            " Name: " + OfferDto.getName() +
                            " Description: " + OfferDto.getDescription() +
                            " Real Price: " + OfferDto.getRealPrice()+
                            " Discount Price: " + OfferDto.getDiscountPrice() +
                            " Booking Date: " + OfferDto.getBookingDate()+
                            " Enjoyment Date: " + OfferDto.getEnjoymentDate());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        
        } else if("-reserveOffer".equalsIgnoreCase(args[0])) {
            validateArgs(args, 4, new int[] {1});

            // [find] OfferServiceClient -reserveOffer <offerId> <userID> <creditCardNumber>

            
            try {
                Long bookingId = clientOfferService.reserveOffer(Long.parseLong(args[1]), args[2],args[3]);
                System.out.println("Reserve code " + bookingId);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        } else if("-invalidateOffer".equalsIgnoreCase(args[0])) {
            validateArgs(args, 4, new int[] {1});

            // [find] OfferServiceClient -invalidateOffer <offerId> <userID> <creditCardNumber>

            try {
                clientOfferService.invalidateOffer(Long.parseLong(args[1]));
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
            
        } else if("-claimReservation".equalsIgnoreCase(args[0])) {
            validateArgs(args, 4, new int[] {1});

            // [find] OfferServiceClient -claimReservation <bookingId> <userID> <creditCardNumber>
            
            try {
                clientOfferService.ClaimBooking(Long.parseLong(args[1]));
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
            
        } else if("-findUserOffers".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});

            // [find] OfferServiceClient -findUserOffers <userId>

            try {
                List<BookingDto> Bookings = clientOfferService.findUserOffers(args[1]);
                System.out.println("Found " + Bookings.size() +
                        " Offer(s) with keywords '" + args[1] + "'");
                for (int i = 0; i < Bookings.size(); i++) {
                    BookingDto BookingDto = Bookings.get(i);
                    System.out.println("Id: " + BookingDto.getBookingId() +
                    		"Offer Id: " + BookingDto.getOfferId() +
                            " User Id: " + BookingDto.getUserId() +
                            " creditCardNumber: " + BookingDto.getCreditCardNumber()+
                            " Booking Date: " + BookingDto.getBookingDate()+
                            " Enjoyment Date: " + BookingDto.getEnjoymentDate());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
            
        } else if("-findOfferReservations".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            // [find] OfferServiceClient -findOfferReservations <offerId>

            try {
                List<BookingDto> Bookings = clientOfferService.findOffersReservations(Long.parseLong(args[1]));
                System.out.println("Found " + Bookings.size() +
                        " Offer(s) with keywords '" + args[1] + "'");
                for (int i = 0; i < Bookings.size(); i++) {
                    BookingDto BookingDto = Bookings.get(i);
                    System.out.println("Id: " + BookingDto.getBookingId() +
                    		"Offer Id: " + BookingDto.getOfferId() +
                            " User Id: " + BookingDto.getUserId() +
                            " creditCardNumber: " + BookingDto.getCreditCardNumber()+
                            " Booking Date: " + BookingDto.getBookingDate()+
                            " Enjoyment Date: " + BookingDto.getEnjoymentDate());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        
        } else if("-findUserReservations".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});

            // [find] OfferServiceClient -findUserReservations <userId>

            try {
                List<BookingDto> Bookings = clientOfferService.findUserReservations(args[1], args[2]);
                System.out.println("Found " + Bookings.size() +
                        " Offer(s) with keywords '" + args[1] + "'");
                for (int i = 0; i < Bookings.size(); i++) {
                    BookingDto BookingDto = Bookings.get(i);
                    System.out.println("Id: " + BookingDto.getBookingId() +
                    		"Offer Id: " + BookingDto.getOfferId() +
                            " User Id: " + BookingDto.getUserId() +
                            " creditCardNumber: " + BookingDto.getCreditCardNumber()+
                            " Booking Date: " + BookingDto.getBookingDate()+
                            " Enjoyment Date: " + BookingDto.getEnjoymentDate());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }}}
            
    public static void validateArgs(String[] args, int expectedArgs,
                                    int[] numericArguments) {
        if(expectedArgs != args.length) {
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

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "    [add]    OfferServiceClient -addOffer <name> <description> <bookingDate> <enjoymentDate> <realPrice> <discountPrice>\n" +
                "    [remove] OfferServiceClient -removeOffer <OfferId>\n" +
                "    [update] OfferServiceClient -updateOffer <OfferId> <name> <description> <bookingDate> <enjoymentDate> <realPrice> <discountPrice>\n" +
                "    [findOffer]   OfferServiceClient -findOffer <offerId>\n" +
                "    [findOffers]   OfferServiceClient -findOffers <offerId>\n" +
                "    [reserveOffer]   OfferServiceClient -reserveOffer <offerId> <userId> <creditCardNumber>\n" +
                "    [invalidateOffer]   OfferServiceClient -invalidateOffer <offerId> <userId> <creditCardNumber>\n" +
                "    [claimReservation]   OfferServiceClient -claimReservation <bookingId> <userId> <creditCardNumber>\n" +
                "    [findUserOffers]   OfferServiceClient -findUserOffers <userId>\n" +
                "    [findOfferReservations]   OfferServiceClient -findOfferReservations <offerId>\n" +
                "    [findUserReservations]   OfferServiceClient -findUserReservations <userId>\n");
    }

}
