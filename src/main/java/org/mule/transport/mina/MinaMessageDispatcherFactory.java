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

import org.mule.api.MuleException;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.MessageDispatcher;
import org.mule.transport.AbstractMessageDispatcherFactory;

/**
 * <code>MinaMessageDispatcherFactory</code> Todo document
 */
public class MinaMessageDispatcherFactory extends AbstractMessageDispatcherFactory {
    /*
     * For general guidelines on writing transports see
     * http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports
     */

    @Override
    public MessageDispatcher create(OutboundEndpoint endpoint) throws MuleException {
        return new MinaMessageDispatcher(endpoint);
    }
}
