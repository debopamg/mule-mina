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

import com.mockobjects.dynamic.Mock;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.service.Service;
import org.mule.api.transport.MessageReceiver;
import org.mule.transport.AbstractMessageReceiverTestCase;

public class MinaMessageReceiverTestCase extends AbstractMessageReceiverTestCase {
    /*
     * For general guidelines on writing transports see
     * http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports
     */

    @Override
    public MessageReceiver getMessageReceiver() throws Exception {
        Mock mockService = new Mock(Service.class);
        mockService.expectAndReturn("getResponseTransformer", null);
        return new MinaMessageReceiver(endpoint.getConnector(),
                (Service) mockService.proxy(), endpoint);
    }

    @Override
    public InboundEndpoint getEndpoint() throws Exception {
        return muleContext.getEndpointFactory().getInboundEndpoint(
                "tcp://localhost:22000");
    }
}
