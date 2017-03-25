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

package org.apache.guacamole.net.auth;

import java.util.Collection;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.form.Form;

/**
 * The context of an active user. The functions of this class enforce all
 * permissions and act only within the rights of the associated user.
 *
 * @author Michael Jumper
 */
public interface UserContext {

    /**
     * Returns the User whose access rights control the operations of this
     * UserContext.
     *
     * @return The User whose access rights control the operations of this
     *         UserContext.
     */
    User self();

    /**
     * Returns the AuthenticationProvider which created this UserContext, which
     * may not be the same AuthenticationProvider that authenticated the user
     * associated with this UserContext.
     *
     * @return
     *     The AuthenticationProvider that created this UserContext.
     */
    AuthenticationProvider getAuthenticationProvider();

    /**
     * Retrieves a Directory which can be used to view and manipulate other
     * users, but only as allowed by the permissions given to the user of this
     * UserContext.
     *
     * @return A Directory whose operations are bound by the restrictions
     *         of this UserContext.
     *
     * @throws GuacamoleException If an error occurs while creating the
     *                            Directory.
     */
    Directory<User> getUserDirectory() throws GuacamoleException;

    /**
     * Retrieves a Directory which can be used to view and manipulate
     * connections and their configurations, but only as allowed by the
     * permissions given to the user.
     *
     * @return A Directory whose operations are bound by the permissions of 
     *         the user.
     *
     * @throws GuacamoleException If an error occurs while creating the
     *                            Directory.
     */
    Directory<Connection> getConnectionDirectory()
            throws GuacamoleException;

    /**
     * Retrieves a Directory which can be used to view and manipulate
     * connection groups and their members, but only as allowed by the
     * permissions given to the user.
     *
     * @return A Directory whose operations are bound by the permissions of
     *         the user.
     *
     * @throws GuacamoleException If an error occurs while creating the
     *                            Directory.
     */
    Directory<ConnectionGroup> getConnectionGroupDirectory()
            throws GuacamoleException;

    /**
     * Retrieves a Directory which can be used to view and manipulate
     * active connections, but only as allowed by the permissions given to the
     * user.
     *
     * @return
     *     A Directory whose operations are bound by the permissions of the
     *     user.
     *
     * @throws GuacamoleException
     *     If an error occurs while creating the Directory.
     */
    Directory<ActiveConnection> getActiveConnectionDirectory()
            throws GuacamoleException;

    /**
     * Retrieves a Directory which can be used to view and manipulate
     * sharing profiles and their configurations, but only as allowed by the
     * permissions given to the user.
     *
     * @return
     *     A Directory whose operations are bound by the permissions of the
     *     user.
     *
     * @throws GuacamoleException
     *     If an error occurs while creating the Directory.
     */
    Directory<SharingProfile> getSharingProfileDirectory()
            throws GuacamoleException;

    /**
     * Retrieves all connection records visible to current user. The resulting
     * set of connection records can be further filtered and ordered using the
     * methods defined on ConnectionRecordSet.
     *
     * @return
     *     A set of all connection records visible to the current user.
     *
     * @throws GuacamoleException
     *     If an error occurs while retrieving the connection records.
     */
    ConnectionRecordSet getConnectionHistory() throws GuacamoleException;

    /**
     * Retrieves a connection group which can be used to view and manipulate
     * connections, but only as allowed by the permissions given to the user of 
     * this UserContext.
     *
     * @return A connection group whose operations are bound by the restrictions
     *         of this UserContext.
     *
     * @throws GuacamoleException If an error occurs while creating the
     *                            Directory.
     */
    ConnectionGroup getRootConnectionGroup() throws GuacamoleException;

    /**
     * Retrieves a collection of all attributes applicable to users. This
     * collection will contain only those attributes which the current user has
     * general permission to view or modify. If there are no such attributes,
     * this collection will be empty.
     *
     * @return
     *     A collection of all attributes applicable to users.
     */
    Collection<Form> getUserAttributes();

    /**
     * Retrieves a collection of all attributes applicable to connections. This
     * collection will contain only those attributes which the current user has
     * general permission to view or modify. If there are no such attributes,
     * this collection will be empty.
     *
     * @return
     *     A collection of all attributes applicable to connections.
     */
    Collection<Form> getConnectionAttributes();

    /**
     * Retrieves a collection of all attributes applicable to connection
     * groups. This collection will contain only those attributes which the
     * current user has general permission to view or modify. If there are no
     * such attributes, this collection will be empty.
     *
     * @return
     *     A collection of all attributes applicable to connection groups.
     */
    Collection<Form> getConnectionGroupAttributes();

    /**
     * Retrieves a collection of all attributes applicable to sharing profiles.
     * This collection will contain only those attributes which the current user
     * has general permission to view or modify. If there are no such
     * attributes, this collection will be empty.
     *
     * @return
     *     A collection of all attributes applicable to sharing profile.
     */
    Collection<Form> getSharingProfileAttributes();

}
