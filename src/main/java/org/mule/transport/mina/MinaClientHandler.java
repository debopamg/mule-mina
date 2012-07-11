package org.mule.transport.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on Jun 19, 2012
 *
 * @author debopamg
 */
public class MinaClientHandler extends IoHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(
            MinaClientHandler.class);
    private Connector minaConnector;
    private MuleEvent sourceEvent;
    private OutboundEndpoint endpoint;

    public MinaClientHandler(Connector minaConnector,
                             OutboundEndpoint endpoint) {
        this.minaConnector = minaConnector;
        this.endpoint = endpoint;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        logger.info("messageReceived: {}", message);

        session.setAttribute(MinaConnector.LAST_MESSAGE_RECEIVED_ATTRIBUTE,
                message);

        MuleMessage sourceMessage = sourceEvent.getMessage();
        MuleMessage newMessage = minaConnector.createMuleMessageFactory().create(
                message, sourceMessage, null);
    }
}
