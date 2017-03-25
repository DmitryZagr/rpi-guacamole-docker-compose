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

package org.apache.guacamole.rest.user;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.GuacamoleSecurityException;
import org.apache.guacamole.net.auth.AuthenticationProvider;
import org.apache.guacamole.net.auth.Credentials;
import org.apache.guacamole.net.auth.User;
import org.apache.guacamole.net.auth.Directory;
import org.apache.guacamole.net.auth.UserContext;
import org.apache.guacamole.net.auth.credentials.GuacamoleCredentialsException;
import org.apache.guacamole.rest.directory.DirectoryObjectResource;
import org.apache.guacamole.rest.directory.DirectoryObjectTranslator;
import org.apache.guacamole.rest.permission.PermissionSetResource;

/**
 * A REST resource which abstracts the operations available on an existing
 * User.
 *
 * @author Michael Jumper
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource
        extends DirectoryObjectResource<User, APIUser> {

    /**
     * The UserContext associated with the Directory which contains the User
     * exposed by this resource.
     */
    private final UserContext userContext;

    /**
     * The Directory which contains the User object represented by this
     * UserResource.
     */
    private final Directory<User> directory;

    /**
     * The User object represented by this UserResource.
     */
    private final User user;

    /**
     * Creates a new UserResource which exposes the operations and subresources
     * available for the given User.
     *
     * @param userContext
     *     The UserContext associated with the given Directory.
     *
     * @param directory
     *     The Directory which contains the given User.
     *
     * @param user
     *     The User that this UserResource should represent.
     *
     * @param translator
     *     A DirectoryObjectTranslator implementation which handles Users.
     */
    @AssistedInject
    public UserResource(@Assisted UserContext userContext,
            @Assisted Directory<User> directory,
            @Assisted User user,
            DirectoryObjectTranslator<User, APIUser> translator) {
        super(directory, user, translator);
        this.userContext = userContext;
        this.directory = directory;
        this.user = user;
    }

    @Override
    public void updateObject(APIUser modifiedObject) throws GuacamoleException {

        // A user may not use this endpoint to modify himself
        if (userContext.self().getIdentifier().equals(modifiedObject.getUsername()))
            throw new GuacamoleSecurityException("Permission denied.");

        super.updateObject(modifiedObject);

    }

    /**
     * Updates the password for an individual existing user.
     *
     * @param userPasswordUpdate
     *     The object containing the old password for the user, as well as the
     *     new password to set for that user.
     *
     * @param request
     *     The HttpServletRequest associated with the password update attempt.
     *
     * @throws GuacamoleException
     *     If an error occurs while updating the user's password.
     */
    @PUT
    @Path("password")
    public void updatePassword(APIUserPasswordUpdate userPasswordUpdate,
            @Context HttpServletRequest request) throws GuacamoleException {

        // Build credentials
        Credentials credentials = new Credentials();
        credentials.setUsername(user.getIdentifier());
        credentials.setPassword(userPasswordUpdate.getOldPassword());
        credentials.setRequest(request);
        credentials.setSession(request.getSession(true));

        // Verify that the old password was correct
        try {
            AuthenticationProvider authProvider = userContext.getAuthenticationProvider();
            if (authProvider.authenticateUser(credentials) == null)
                throw new GuacamoleSecurityException("Permission denied.");
        }

        // Pass through any credentials exceptions as simple permission denied
        catch (GuacamoleCredentialsException e) {
            throw new GuacamoleSecurityException("Permission denied.");
        }

        // Set password to the newly provided one
        user.setPassword(userPasswordUpdate.getNewPassword());
        directory.update(user);

    }

    /**
     * Returns a resource which abstracts operations available on the overall
     * permissions granted to the User represented by this UserResource.
     *
     * @return
     *     A resource which representing the permissions granted to the User
     *     represented by this UserResource.
     */
    @Path("permissions")
    public PermissionSetResource getPermissions() {
        return new PermissionSetResource(user);
    }

}
