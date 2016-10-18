Ir a ws-app -> mvn clean sql:execute install (para que autoconfigure la clase OfertsProviderService y se reinicien los auto_increments de mysql)
Ir a ws-app-service -> mvn jetty:run
Ir a ws-app-client:

#####################################
COMANDOS DEFENSA
#####################################

1. Añadir oferta (recibe nombre, descripción, fecha límite de reserva, fecha límite de
disfrute, precio real, precio descontado y, opcionalmente, la comisión). 

mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-addOffer 'Cena para dos' 'Cena con menú degustación para dos personas en el restaurante UDC' '01/08/2016 00:00' '31/12/2016 23:59' 80 40"
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-addOffer 'Casa rural' 'Habitación doble en la casa rural UDC' '01/12/2016 23:59' '31/12/2016 23:59' 100 70"
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-addOffer 'Fisioterapia' 'Bono de dos sesiones de fisioterapia' '16/01/2016 17:45' '30/06/2016 23:59' 60 30"

2. Actualizar (recibe el identificador de la oferta y los mismos campos que añadir oferta) y buscar oferta 

mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-updateOffer 2 'Casa rural' 'Habitación triple en la casa rural UDC' '01/12/2016 23:59' '31/12/2016 23:59' 150 100"
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-findOffer 2"
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-findOffer 244"
//falla

3. Reservar oferta (recibe identificador de la oferta, e-mail del usuario y número de tarjeta de crédito)

mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-reserveOffer 1 'usr1@udc.es' 1234567890123456"
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-reserveOffer 2 'usr1@udc.es' 1234567890123456"
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-reserveOffer 1 'usr2@udc.es' 1111222233334444"
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-reserveOffer 2 'usr2@udc.es' 1111222233334444"
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-reserveOffer 2 'usr2@udc.es' 1111222233334444"
 // falla (oferta ya reserva por ese usuario)
 
4. Actualizar (caso de error) y buscar oferta 
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-updateOffer 2 'Casa rural' 'Habitación triple en la casa rural UDC' '01/06/2016 23:59' '30/10/2016 23:59' 150 100"
// falla (tiene reservas y se adelanta la fecha de disfrute)
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-findOffer 2"

5. Reclamar reserva (recibe identificador o código de la reserva y, opcionalmente, el email del usuario) e invalidar oferta 

mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-claimReservation <reservCode2>"
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-invalidateOffer 2"
// invalida también las reservas no reclamadas (<reservId4>)
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-invalidateOffer 2"
// falla (oferta ya invalidada)
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-claimReservation <reservCode4>"
// falla (reserva invalidada)

6. Buscar reservas de una oferta (recibe el identificador de la oferta)

mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-findOfferReservations 2"
 // devuelve <reservId2> (Reclamada) y <reservId4> (Inválida)

7. Buscar reservas de un usuario (recibe el identificador de la oferta y un token indicando si devolver sólo las válidas o todas)

mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-findUserReservations 'usr2@udc.es' true"
 // devuelve <reservId3> (Pendiente)
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-findUserReservations 'usr2@udc.es' false"
 // devuelve <reservId3> (Pendiente) y <reservId4> (Inválida)

8. Buscar ofertas reservadas por un usuario (recibe el e-mail del usuario)

mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-findUserReservedOffers 'usr1@udc.es'"
// devuelve datos de <offerId1 + reservId1> y <offerId2 + reservId2>

9. Buscar ofertas por palabras clave contenidas en la descripción

mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-findOffers 'DoS de'"
// devuelve offerId1 y offerId3
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-findOffers 'udc'"
 // devuelve offerId1 (offerId2 está invalidada)

10. Reservar oferta (recibe identificador de la oferta, e-mail del usuario y número de tarjeta de crédito)

//update Oferta set fechaReservar = "2015-03-03 00:00:00" where ofertaId=3;
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-reserveOffer 3 'usr2@udc.es' 1111222233334444"
// falla (fecha reserva expirada)

11. Eliminar ofertas

mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-removeOffer 1"
// falla (tiene reservas)
mvn exec:java -Dexec.mainClass="es.udc.ws.app.client.ui.OfertaServiceClient" -Dexec.args="-removeOffer 3"