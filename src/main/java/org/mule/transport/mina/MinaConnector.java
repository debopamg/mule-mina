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

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.transport.MessageReceiver;
import org.mule.transport.AbstractConnector;
import org.mule.transport.mina.i18n.MinaMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>MinaConnector</code> TODO document
 */
public class MinaConnector extends AbstractConnector {

    /**
     * This constant defines the main transport protocol identifier
     */
    public static final String MINA = "mina";
    public static final String MINA_SESSION_ID_PROPERTY = "MINA_SESSION_ID";
    public static final long MINA_SESSION_ID_NOT_SET = -1;
    public static final String LAST_MESSAGE_RECEIVED_ATTRIBUTE = "MINAMULE_LAST_MESSAGE_RECEIVED";
    public static final String IGNORE_SYNCH_RESPONSE_PROPERTY = "MINA_IGNORE_SYNCH_RESPONSE";
    public static final String WAIT_FOR_SYNCHRONOUS_RESPONSE_PROPERTY = "waitForSynchronousResponse";
    public static final String CLIENT_SESSION_PROPERTY = "CLIENT_SESSION";
    
    private boolean waitForSynchronousResponse = true;
    private String protocolCodecFactoryClass;
    private ProtocolCodecFactory protocolCodecFactory;
    private static final Logger logger = LoggerFactory.getLogger(
            MinaConnector.class);

    /*
     * For general guidelines on writing transports see
     * http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports
     */

    /*
     * IMPLEMENTATION NOTE: All configuaration for the transport should be set
     * on the Connector object, this is the object that gets configured in
     * MuleXml
     */
    public MinaConnector(MuleContext context) {
        super(context);
    }

    @Override
    public void doInitialise() throws InitialisationException {
        // Optional; does not need to be implemented. Delete if not required

        /*
         * IMPLEMENTATION NOTE: Is called once all bean properties have been
         * set on the connector and can be used to validate and initialise the
         * connectors state.
         */
        if (protocolCodecFactoryClass == null) {
            protocolCodecFactoryClass = ObjectSerializationCodecFactory.class.
                    getName();
        }
        try {
            Class<?> pcfClass = Class.forName(protocolCodecFactoryClass);
            protocolCodecFactory = (ProtocolCodecFactory) pcfClass.
                    newInstance();
            logger.info("protocol codec factory: {}", protocolCodecFactoryClass);
        } catch (Exception ex) {
            logger.error("error while initializing the connector", ex);
            throw new InitialisationException(MinaMessages.createStaticMessage(
                    "error while initialising the connector"), ex, this);
        }
    }

    @Override
    public void doConnect() throws Exception {
        // Optional; does not need to be implemented. Delete if not required

        /*
         * IMPLEMENTATION NOTE: Makes a connection to the underlying
         * resource. When connections are managed at the receiver/dispatcher
         * level, this method may do nothing
         */
    }

    @Override
    public void doDisconnect() throws Exception {
        // Optional; does not need to be implemented. Delete if not required

        /*
         * IMPLEMENTATION NOTE: Disconnects any connections made in the
         * connect method If the connect method did not do anything then this
         * method shouldn't do anything either.
         */
    }

    @Override
    public void doStart() throws MuleException {
        // Optional; does not need to be implemented. Delete if not required

        /*
         * IMPLEMENTATION NOTE: If there is a single server instance or
         * connection associated with the connector i.e. Jms Connection or Jdbc
         * Connection,
         * this method should put the resource in a started state here.
         */
    }

    @Override
    public void doStop() throws MuleException {
        // Optional; does not need to be implemented. Delete if not required

        /*
         * IMPLEMENTATION NOTE: Should put any associated resources into a
         * stopped state. Mule will automatically call the stop() method.
         */
    }

    @Override
    public void doDispose() {
        // Optional; does not need to be implemented. Delete if not required

        /*
         * IMPLEMENTATION NOTE: Should clean up any open resources associated
         * with the connector.
         */
    }

    @Override
    public String getProtocol() {
        return MINA;
    }

    @Override
    protected MessageReceiver createReceiver(FlowConstruct flowConstruct,
                                             InboundEndpoint endpoint) throws Exception {
        return (MinaMessageReceiver) getServiceDescriptor().
                createMessageReceiver(this, flowConstruct,
                endpoint);
    }

    /**
     * @return the waitForSynchronousResponse
     */
    public boolean isWaitForSynchronousResponse() {
        return waitForSynchronousResponse;
    }

    /**
     * @param waitForSynchronousResponse the waitForSynchronousResponse to set
     */
    public void setWaitForSynchronousResponse(boolean waitForSynchronousResponse) {
        this.waitForSynchronousResponse = waitForSynchronousResponse;
    }

    /**
     * @return the protocolCodecFactory
     */
    public String getProtocolCodecFactoryClass() {
        return protocolCodecFactoryClass;
    }

    /**
     * @param protocolCodecFactory the protocolCodecFactory to set
     */
    public void setProtocolCodecFactoryClass(String protocolCodecFactory) {
        this.protocolCodecFactoryClass = protocolCodecFactory;
    }

    /**
     * @return the protocolCodecFactory
     */
    public ProtocolCodecFactory getProtocolCodecFactory() {
        return protocolCodecFactory;
    }
}
