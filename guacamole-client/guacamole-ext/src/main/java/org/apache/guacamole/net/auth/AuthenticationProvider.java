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

import org.apache.guacamole.GuacamoleException;

/**
 * Provides means of authorizing users and for accessing and managing data
 * associated with those users. Access to such data is limited according to the
 * AuthenticationProvider implementation.
 *
 * @author Michael Jumper
 */
public interface AuthenticationProvider {

    /**
     * Returns the identifier which uniquely and consistently identifies this
     * AuthenticationProvider implementation. This identifier may not be null
     * and must be unique across all AuthenticationProviders loaded by the
     * Guacamole web application.
     *
     * @return
     *     The unique identifier assigned to this AuthenticationProvider, which
     *     may not be null.
     */
    String getIdentifier();

    /**
     * Returns an AuthenticatedUser representing the user authenticated by the
     * given credentials, if any.
     *
     * @param credentials
     *     The credentials to use for authentication.
     *
     * @return
     *     An AuthenticatedUser representing the user authenticated by the
     *     given credentials, if any, or null if the credentials are invalid.
     *
     * @throws GuacamoleException
     *     If an error occurs while authenticating the user, or if access is
     *     temporarily, permanently, or conditionally denied, such as if the
     *     supplied credentials are insufficient or invalid.
     */
    AuthenticatedUser authenticateUser(Credentials credentials)
            throws GuacamoleException;

    /**
     * Returns a new or updated AuthenticatedUser for the given credentials
     * already having produced the given AuthenticatedUser. Note that because
     * this function will be called for all future requests after initial
     * authentication, including tunnel requests, care must be taken to avoid
     * using functions of HttpServletRequest which invalidate the entire request
     * body, such as getParameter(). Doing otherwise may cause the
     * GuacamoleHTTPTunnelServlet to fail.
      *
     * @param credentials
     *     The credentials to use for authentication.
     *
     * @param authenticatedUser
     *     An AuthenticatedUser object representing the user authenticated by
     *     an arbitrary set of credentials. The AuthenticatedUser may come from
     *     this AuthenticationProvider or any other installed
     *     AuthenticationProvider.
     *
     * @return
     *     An updated AuthenticatedUser representing the user authenticated by
     *     the given credentials, if any, or null if the credentials are
     *     invalid.
     *
     * @throws GuacamoleException
     *     If an error occurs while updating the AuthenticatedUser.
     */
    AuthenticatedUser updateAuthenticatedUser(AuthenticatedUser authenticatedUser,
            Credentials credentials) throws GuacamoleException;

    /**
     * Returns the UserContext of the user authenticated by the given
     * credentials.
     *
     * @param authenticatedUser
     *     An AuthenticatedUser object representing the user authenticated by
     *     an arbitrary set of credentials. The AuthenticatedUser may come from
     *     this AuthenticationProvider or any other installed
     *     AuthenticationProvider.
     *
     * @return
     *     A UserContext describing the permissions, connection, connection
     *     groups, etc. accessible or associated with the given authenticated
     *     user, or null if this AuthenticationProvider refuses to provide any
     *     such data.
     *
     * @throws GuacamoleException
     *     If an error occurs while creating the UserContext.
     */
    UserContext getUserContext(AuthenticatedUser authenticatedUser)
            throws GuacamoleException;

    /**
     * Returns a new or updated UserContext for the given AuthenticatedUser
     * already having the given UserContext. Note that because this function
     * will be called for all future requests after initial authentication,
     * including tunnel requests, care must be taken to avoid using functions
     * of HttpServletRequest which invalidate the entire request body, such as
     * getParameter(). Doing otherwise may cause the GuacamoleHTTPTunnelServlet
     * to fail.
      *
     * @param context
     *     The existing UserContext belonging to the user in question.
     *
     * @param authenticatedUser
     *     An AuthenticatedUser object representing the user authenticated by
     *     an arbitrary set of credentials. The AuthenticatedUser may come from
     *     this AuthenticationProvider or any other installed
     *     AuthenticationProvider.
     *
     * @param credentials
     *     The credentials which were most recently submitted. These are not
     *     guaranteed to be the same as the credentials associated with the
     *     AuthenticatedUser when they originally authenticated.
     *
     * @return
     *     An updated UserContext describing the permissions, connection,
     *     connection groups, etc. accessible or associated with the given
     *     authenticated user, or null if this AuthenticationProvider refuses
     *     to provide any such data.
     *
     * @throws GuacamoleException
     *     If an error occurs while updating the UserContext.
     */
    UserContext updateUserContext(UserContext context,
            AuthenticatedUser authenticatedUser,
            Credentials credentials) throws GuacamoleException;
    
}
