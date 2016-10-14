package es.udc.ws.app.exceptions;



@SuppressWarnings("serial")
public class UserAlreadyReservedException extends Exception {
    

    public UserAlreadyReservedException() {
        super("User Already Reserved" );
    }

}