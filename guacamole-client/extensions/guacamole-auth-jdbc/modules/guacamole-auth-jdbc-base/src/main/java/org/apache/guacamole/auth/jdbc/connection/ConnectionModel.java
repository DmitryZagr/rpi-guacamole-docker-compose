/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.guacamole.auth.jdbc.connection;

import java.util.HashSet;
import java.util.Set;
import org.apache.guacamole.auth.jdbc.base.ChildObjectModel;

/**
 * Object representation of a Guacamole connection, as represented in the
 * database.
 *
 * @author Michael Jumper
 */
public class ConnectionModel extends ChildObjectModel {

    /**
     * The human-readable name associated with this connection.
     */
    private String name;

    /**
     * The name of the protocol to use when connecting to this connection.
     */
    private String protocol;

    /**
     * The maximum number of connections that can be established to this
     * connection concurrently, zero if no restriction applies, or null if the
     * default restrictions should be applied.
     */
    private Integer maxConnections;

    /**
     * The maximum number of connections that can be established to this
     * connection concurrently by any one user, zero if no restriction applies,
     * or null if the default restrictions should be applied.
     */
    private Integer maxConnectionsPerUser;

    /**
     * The identifiers of all readable sharing profiles associated with this
     * connection.
     */
    private Set<String> sharingProfileIdentifiers = new HashSet<String>();

    /**
     * Creates a new, empty connection.
     */
    public ConnectionModel() {
    }

    /**
     * Returns the name associated with this connection.
     *
     * @return
     *     The name associated with this connection.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name associated with this connection.
     *
     * @param name
     *     The name to associate with this connection.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the protocol to use when connecting to this
     * connection.
     *
     * @return
     *     The name of the protocol to use when connecting to this connection.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Sets the name of the protocol to use when connecting to this connection.
     *
     * @param protocol
     *     The name of the protocol to use when connecting to this connection.
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Returns the maximum number of connections that can be established to
     * this connection concurrently.
     *
     * @return
     *     The maximum number of connections that can be established to this
     *     connection concurrently, zero if no restriction applies, or null if
     *     the default restrictions should be applied.
     */
    public Integer getMaxConnections() {
        return maxConnections;
    }

    /**
     * Sets the maximum number of connections that can be established to this
     * connection concurrently.
     *
     * @param maxConnections
     *     The maximum number of connections that can be established to this
     *     connection concurrently, zero if no restriction applies, or null if
     *     the default restrictions should be applied.
     */
    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    /**
     * Returns the maximum number of connections that can be established to
     * this connection concurrently by any one user.
     *
     * @return
     *     The maximum number of connections that can be established to this
     *     connection concurrently by any one user, zero if no restriction
     *     applies, or null if the default restrictions should be applied.
     */
    public Integer getMaxConnectionsPerUser() {
        return maxConnectionsPerUser;
    }

    /**
     * Sets the maximum number of connections that can be established to this
     * connection concurrently by any one user.
     *
     * @param maxConnectionsPerUser
     *     The maximum number of connections that can be established to this
     *     connection concurrently by any one user, zero if no restriction
     *     applies, or null if the default restrictions should be applied.
     */
    public void setMaxConnectionsPerUser(Integer maxConnectionsPerUser) {
        this.maxConnectionsPerUser = maxConnectionsPerUser;
    }

    /**
     * Returns the identifiers of all readable sharing profiles associated with
     * this connection. This is set only when the connection is queried, and has
     * no effect when a connection is inserted, updated, or deleted.
     *
     * @return
     *     The identifiers of all readable sharing profiles associated with
     *     this connection.
     */
    public Set<String> getSharingProfileIdentifiers() {
        return sharingProfileIdentifiers;
    }

    /**
     * Sets the identifiers of all readable sharing profiles associated with
     * this connection. This should be set only when the connection is queried,
     * as it has no effect when a connection is inserted, updated, or deleted.
     *
     * @param sharingProfileIdentifiers
     *     The identifiers of all readable sharing profiles associated with
     *     this connection.
     */
    public void setSharingProfileIdentifiers(Set<String> sharingProfileIdentifiers) {
        this.sharingProfileIdentifiers = sharingProfileIdentifiers;
    }

    @Override
    public String getIdentifier() {

        // If no associated ID, then no associated identifier
        Integer id = getObjectID();
        if (id == null)
            return null;

        // Otherwise, the identifier is the ID as a string
        return id.toString();

    }

    @Override
    public void setIdentifier(String identifier) {
        throw new UnsupportedOperationException("Connection identifiers are derived from IDs. They cannot be set.");
    }

}
