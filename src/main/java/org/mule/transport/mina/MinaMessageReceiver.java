/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.mina;

import java.net.InetSocketAddress;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractMessageReceiver;
import org.mule.transport.ConnectException;
import org.mule.transport.mina.i18n.MinaMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>MinaMessageReceiver</code> TODO document
 */
public class MinaMessageReceiver extends AbstractMessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(
            MinaMessageReceiver.class);
    private IoAcceptor acceptor;

    /*
     * For general guidelines on writing transports see
     * http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports
     */
    public MinaMessageReceiver(Connector connector, FlowConstruct flowConstruct,
                               InboundEndpoint endpoint)
            throws CreateException {
        super(connector, flowConstruct, endpoint);
    }

    @Override
    public void doConnect() throws ConnectException {
        /*
         * IMPLEMENTATION NOTE: This method should make a connection to the
         * underlying
         * transport i.e. connect to a socket or register a soap service. When
         * there is no connection to be made this method should be used to
         * check that resources are available. For example the
         * FileMessageReceiver checks that the directories it will be using
         * are available and readable. The MessageReceiver should remain in a
         * 'stopped' state even after the doConnect() method is called. This
         * means that a connection has been made but no events will be
         * received until the start() method is called.
         *
         * Calling start() on the MessageReceiver will call doConnect() if the
         * receiver
         * hasn't connected already.
         */
        /*
         * IMPLEMENTATION NOTE: If you need to spawn any threads such as
         * worker threads for this receiver you can schedule a worker thread
         * with the work manager i.e.
         *
         * getWorkManager().scheduleWork(worker, WorkManager.INDEFINITE, null,
         * null);
         * Where 'worker' implemments javax.resource.spi.work.Work
         */
        /*
         * IMPLEMENTATION NOTE: When throwing an exception from this method
         * you need to throw an ConnectException that accepts a Message, a
         * cause exception and a reference to this MessageReceiver i.e.
         *
         * throw new ConnectException(new
         * Message(Messages.FAILED_TO_SCHEDULE_WORK), e, this);
         */
        // TODO the code necessary to connect to the underlying resource
        try {
            EndpointURI endpointURI = endpoint.getEndpointURI();
            logger.info("starting MinaMessageReceiver at port: {}", endpointURI.
                    getPort());

            acceptor = new NioSocketAcceptor();
            acceptor.getFilterChain().addLast("logger", new LoggingFilter());

            acceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(((MinaConnector) connector).
                    getProtocolCodecFactory()));

            acceptor.setHandler(new MinaSocketHandler(
                    (Connector) connector, flowConstruct,
                    (InboundEndpoint) endpoint));

            acceptor.bind(new InetSocketAddress(endpointURI.getPort()));
        } catch (Exception ex) {
            logger.error("error while starting message receiver", ex);
            throw new ConnectException(MinaMessages.createStaticMessage(
                    "Error while starting message receiver"), ex, this);
        }
    }

    @Override
    public void doDisconnect() throws ConnectException {
        /*
         * IMPLEMENTATION NOTE: Disconnects and tidies up any rources allocted
         * using the doConnect() method. This method should return the
         * MessageReceiver into a disconnected state so that it can be
         * connected again using the doConnect() method.
         */
        // TODO release any resources here
    }

    @Override
    public void doStart() {
        // Optional; does not need to be implemented. Delete if not required

        /*
         * IMPLEMENTATION NOTE: Should perform any actions necessary to enable
         * the reciever to start reciving events. This is different to the
         * doConnect() method which actually makes a connection to the
         * transport, but leaves the MessageReceiver in a stopped state. For
         * polling-based MessageReceivers the start() method simply starts the
         * polling thread. What action is performed here depends on
         * the transport being used. Most of the time a custom provider
         * doesn't need to override this method.
         */
    }

    @Override
    public void doStop() {
        // Optional; does not need to be implemented. Delete if not required

        /*
         * IMPLEMENTATION NOTE: Should perform any actions necessary to stop
         * the reciever from receiving events.
         */
    }

    @Override
    public void doDispose() {
        // Optional; does not need to be implemented. Delete if not required

        /*
         * IMPLEMENTATION NOTE: Is called when the Conector is being dispoed
         * and should clean up any resources. The doStop() and doDisconnect()
         * methods will be called implicitly when this method is called.
         */
    }
}
