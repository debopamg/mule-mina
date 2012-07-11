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


import org.junit.Test;
import org.mule.api.endpoint.EndpointURI;
import org.mule.endpoint.MuleEndpointURI;
import org.mule.tck.junit4.AbstractMuleContextTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MinaEndpointTestCase extends AbstractMuleContextTestCase
{
    /* For general guidelines on writing transports see
       http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports */

    @Test
    public void validEndpointURI() throws Exception
    {
        EndpointURI url = new MuleEndpointURI("mina://localhost:7856", muleContext);
        assertEquals("mina", url.getScheme());
//        assertEquals("mina://localhost:7856", url.getAddress());
//        assertNull(url.getEndpointName());
        assertEquals(7856, url.getPort());
        assertEquals("localhost", url.getHost());
//        assertEquals("mina://localhost:7856", url.getAddress());
        assertEquals(0, url.getParams().size());
    }
}
