package org.mule.transport.mina.events;

/**
 * Event fired when a session is opened on the mina endpoint (a connection
 * is opened)
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
public class SessionOpenedEvent extends MinaEvent {

    public SessionOpenedEvent(String minaSessionId) {
        super(minaSessionId);
    }

    @Override
    public String toString() {
        return "SessionOpenedEvent for session: " + this.minaSessionId;
    }
}