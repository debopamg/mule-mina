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

import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.junit.Test;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractConnectorTestCase;

import static org.junit.Assert.assertEquals;

public class MinaConnectorTestCase extends AbstractConnectorTestCase {
    /*
     * For general guidelines on writing transports see
     * http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports
     */

    @Override
    public Connector createConnector() throws Exception {
        /*
         * IMPLEMENTATION NOTE: Create and initialise an instance of your
         * connector here. Do not actually call the connect method.
         */

        MinaConnector connector = new MinaConnector(muleContext);
        connector.setName("MinaConnector");
        // TODO Set any additional properties on the connector here
        return connector;
    }

    @Override
    public String getTestEndpointURI() {
        return "mina://localhost:56801";
    }

    @Override
    public Object getValidMessage() throws Exception {
        return "Hello".getBytes();
    }

    @Test
    public void customProperties() throws Exception {
        MinaConnector conn = (MinaConnector)getConnector();
        conn.setProtocolCodecFactoryClass(
                ObjectSerializationCodecFactory.class.getName());
        
        assertEquals(ObjectSerializationCodecFactory.class.getName(),
                conn.getProtocolCodecFactoryClass());
    }
}
