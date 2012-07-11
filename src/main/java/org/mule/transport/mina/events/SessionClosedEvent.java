package org.mule.transport.mina.events;

/**
 * Event fired when a session is closed on the mina endpoint (a connection
 * is closed)
 *
 * This event is received by the component if the sendOpenCloseEvents propery is
 * set to true in the endpoint. <br />
 * Components should have a method that can accept a SessionOpenedEvent object
 * as a parameter.
 *
 *
 * @author Marco D'Alia <flashserve -at- madarco dot net>
 *
 */
public class SessionClosedEvent extends MinaEvent {

    public SessionClosedEvent(String minaSessionId) {
        super(minaSessionId);
    }

    @Override
    public String toString() {
        return "SessionClosedEvent for session: " + this.minaSessionId;
    }
}