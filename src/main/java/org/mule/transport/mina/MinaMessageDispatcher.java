/*
 * Debopam Ghoshal
 * 19JUN2012
 * 
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.mina;

import java.net.InetSocketAddress;
import java.net.URI;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.transport.AbstractMessageDispatcher;
import org.mule.transport.mina.i18n.MinaMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>MinaMessageDispatcher</code>
 * TODO document
 */
public class MinaMessageDispatcher extends AbstractMessageDispatcher {

    private MinaConnector minaConnector;
    private IoConnector socketConnector;
    private OutboundEndpoint outboundEndpoint;
    private static final Logger logger = LoggerFactory.getLogger(
            MinaMessageDispatcher.class);

    /*
     * For general guidelines on writing transports see
     * http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports
     */
    public MinaMessageDispatcher(OutboundEndpoint endpoint) throws MuleException {
        super(endpoint);
        outboundEndpoint = endpoint;
        minaConnector = (MinaConnector) endpoint.getConnector();
    }

    @Override
    public void doConnect() throws Exception {
        /*
         * IMPLEMENTATION NOTE: Makes a connection to the underlying
         * resource. Where connections are managed by the connector this
         * method may do nothing
         */
        // If a resource for this Dispatcher needs a connection established,
        // then this is the place to do it
    }

    @Override
    public void doDisconnect() throws Exception {
        /*
         * IMPLEMENTATION NOTE: Disconnect any conections made in the connect
         * method
         */
        // If the connect method did not do anything then this method
        // shouldn't do anything either
    }

    @Override
    public void doDispatch(MuleEvent event) throws Exception {
        logger.info("doDispatch");
        sendTo(event.getMessageSourceURI(), event);
    }

    @Override
    public MuleMessage doSend(MuleEvent event) throws Exception {
        logger.info("doSend");
        sendTo(event.getMessageSourceURI(), event);
        return event.getMessage();
    }

    private void sendTo(URI endpointUri, MuleEvent event) throws Exception {
        logger.info("sendTo");
        IoSession session = event.getMessage().getInvocationProperty(
                MinaConnector.CLIENT_SESSION_PROPERTY);
        if (session == null) {
            logger.warn(
                    "SESSION NOT FOUND: " + endpointUri + " ENDPOINT: " + endpoint);
            sendToRemote(event);
        } else {
            Object payload = event.getMessage().getPayload();
            if (payload != null) {
                logger.info("sending: {}", payload);
                session.write(payload);
            }
        }
    }

    // This method should be used when the connection is initiated from the server
    private ConnectFuture sendToRemote(MuleEvent event) throws Exception {
        URI endpointUri = event.getMessageSourceURI();
        int port = endpointUri.getPort();
        if(endpointUri.getHost() == null) {
            endpointUri = endpoint.getEndpointURI().getUri();
            port = endpointUri.getPort();
        }

        logger.info("sendToRemote: {}:{}", endpointUri.getHost(), port);

        InetSocketAddress address = new InetSocketAddress(endpointUri.getHost(),
                port);

        try {
            socketConnector = new NioSocketConnector();
            ProtocolCodecFactory protocolCodecFactory = ((MinaConnector) connector).
                    getProtocolCodecFactory();

            socketConnector.getFilterChain().addLast("logger",
                    new LoggingFilter());

            socketConnector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(protocolCodecFactory));
        } catch (Exception ex) {
            logger.error("error while creating message dispatcher", ex);
            throw new MuleException(MinaMessages.createStaticMessage(
                    "error while creating message dispatcher"), ex) {
            };
        }
        IoHandler clientHandler = new MinaClientHandler(minaConnector,
                outboundEndpoint);

        socketConnector.setHandler(clientHandler);
        ConnectFuture cFuture = socketConnector.connect(address);
        return cFuture;
    }
}
