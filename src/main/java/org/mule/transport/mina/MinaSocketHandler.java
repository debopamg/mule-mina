package org.mule.transport.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.mule.api.MessagingException;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractConnector;
import org.mule.transport.mina.events.MinaEvent;
import org.mule.transport.mina.events.SessionClosedEvent;
import org.mule.transport.mina.events.SessionOpenedEvent;
import org.mule.util.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Jun 19, 2012
 *
 * @author debopamg
 */
public class MinaSocketHandler extends IoHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(
            MinaSocketHandler.class);
    private Connector connector;
    private InboundEndpoint endpoint;
    protected final MinaMessageReceiver receiver;
    protected boolean waitForSynchronousResponse;

    public MinaSocketHandler(Connector connector, FlowConstruct flowConstruct,
                             InboundEndpoint endpoint) {
        this.connector = connector;
        this.endpoint = endpoint;
        this.receiver = (MinaMessageReceiver) ((AbstractConnector) connector).
                getReceiver(flowConstruct, endpoint);

        logger.info("creating socket handler..");
        waitForSynchronousResponse = MapUtils.getBooleanValue(endpoint.
                getProperties(),
                MinaConnector.WAIT_FOR_SYNCHRONOUS_RESPONSE_PROPERTY,
                ((MinaConnector) connector).isWaitForSynchronousResponse());
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error("caught exception: ", cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        logger.info("received message: {}", message);
        MuleMessage muleMessage = receiver.createMuleMessage(message);
        muleMessage.setInvocationProperty(MinaConnector.CLIENT_SESSION_PROPERTY, session);
        logger.info("created mule message: {}", muleMessage);
        receiver.routeMessage(muleMessage);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        logger.debug("session created...");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        logger.info("session opened successfully");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("closing session...");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        logger.info("session is idle...");
        if (logger.isTraceEnabled()) {
            logger.trace("Disconnectiong the idle session: " + session);
        }
        //TODO: send idle events
        session.close(true);
    }

    protected void sendSessionClosedEvent(IoSession session)
            throws MessagingException, Exception {
        MinaEvent evt = new SessionClosedEvent(endpoint.getEndpointURI().toString());
        MuleMessage muleMsg = this.createMessageWithId(session, evt);

        this.routeMessage(muleMsg, session);

        if (logger.isTraceEnabled()) {
            logger.trace("Session " + endpoint.getEndpointURI() + " closed");
        }
    }

    protected void sendSessionOpenedEvent(IoSession session)
            throws MessagingException, Exception {
        Number id = (Number) session.getAttribute(
                MinaConnector.MINA_SESSION_ID_PROPERTY);
        MinaEvent evt = new SessionOpenedEvent(endpoint.getEndpointURI().toString());
        MuleMessage muleMsg = this.createMessageWithId(session, evt);
        this.routeMessage(muleMsg, session);

        if (logger.isTraceEnabled()) {
            logger.trace("Session " + id + " opened");
        }
    }

    protected void routeMessage(MuleMessage msg, IoSession session) throws Exception {
        MuleEvent result = receiver.routeMessage(msg);
        if (null != result) {
            if (logger.isDebugEnabled()) {
                logger.debug(
                        "Message returned synchronously: " + result);
            }
            boolean ignoreMessage = (Boolean) result.getProperty(
                    MinaConnector.IGNORE_SYNCH_RESPONSE_PROPERTY, false);
            if (waitForSynchronousResponse && !ignoreMessage) {
                Object response = result.getMessage().getPayload();
                session.write(response);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.trace("Synchronous response in endpoint "
                            + this.endpoint.getName() + " ignored. " + result);
                }
            }
        }
    }

    protected MuleMessage createMessageWithId(IoSession session,
                                              Object message)
            throws CreateException, Exception {
        MuleMessage muleMsg = connector.createMuleMessageFactory().create(
                message, null);
        Number id = (Number) session.getAttribute(
                MinaConnector.MINA_SESSION_ID_PROPERTY);
        muleMsg.setOutboundProperty(MinaConnector.MINA_SESSION_ID_PROPERTY, id.
                longValue());
        return muleMsg;
    }
}
