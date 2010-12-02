package dk.statsbiblioteket.doms.wowza.plugin.domslive;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import dk.statsbiblioteket.doms.wowza.plugin.Ticket;

/* $Id$
 * $Revision$
 * $Date$
 * $Author$
 *
 * The DOMS project.
 * Copyright (C) 2007-2009  The State and University Library
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

public class TicketChecker {
    private WebResource restApi;
    protected String location;

    /**
     * TODO javadoc
     * @param ticketCheckerLocation
     */
    public TicketChecker(String ticketCheckerLocation) {
        Client client = Client.create();
        restApi = client.resource(ticketCheckerLocation);
    }

    /**
     * TODO javadoc
     *
     * @param ticket
     * @param shardUrl
     * @param ipOfPlayer
     * @return
     */
    public boolean isTicketValid(String ticket, String shardUrl,
                                 String ipOfPlayer) {
        try {
            Ticket ticketXml = restApi
                    .path("/resolveTicket")
                    .queryParam("ID", ticket)
                    .get(Ticket.class);
            String shardUrlFromTicket = ticketXml.getUrl();
            String ipOfPlayerFromTicket = ticketXml.getUsername();

            

            if (shardUrl.equals(shardUrlFromTicket)
                    && ipOfPlayer.equals(ipOfPlayerFromTicket)) {
                return true;
            } else {
                return false;
            }
        }  catch (UniformInterfaceException e) {
            return false;
        }
    }


    


}