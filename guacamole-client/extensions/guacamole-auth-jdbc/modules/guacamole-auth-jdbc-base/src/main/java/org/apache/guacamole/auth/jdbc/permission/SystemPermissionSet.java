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

package org.apache.guacamole.auth.jdbc.permission;

import org.apache.guacamole.auth.jdbc.user.ModeledUser;
import com.google.inject.Inject;
import java.util.Collections;
import java.util.Set;
import org.apache.guacamole.auth.jdbc.user.ModeledAuthenticatedUser;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.auth.jdbc.base.RestrictedObject;
import org.apache.guacamole.net.auth.permission.SystemPermission;

/**
 * A database implementation of SystemPermissionSet which uses an injected
 * service to query and manipulate the system permissions associated with a
 * particular user.
 *
 * @author Michael Jumper
 */
public class SystemPermissionSet extends RestrictedObject
    implements org.apache.guacamole.net.auth.permission.SystemPermissionSet {

    /**
     * The user associated with this permission set. Each of the permissions in
     * this permission set is granted to this user.
     */
    private ModeledUser user;

    /**
     * Service for reading and manipulating system permissions.
     */
    @Inject
    private SystemPermissionService systemPermissionService;
    
    /**
     * Creates a new SystemPermissionSet. The resulting permission set
     * must still be initialized by a call to init(), or the information
     * necessary to read and modify this set will be missing.
     */
    public SystemPermissionSet() {
    }

    /**
     * Initializes this permission set with the current user and the user
     * to whom the permissions in this set are granted.
     *
     * @param currentUser
     *     The user who queried this permission set, and whose permissions
     *     dictate the access level of all operations performed on this set.
     *
     * @param user
     *     The user to whom the permissions in this set are granted.
     */
    public void init(ModeledAuthenticatedUser currentUser, ModeledUser user) {
        super.init(currentUser);
        this.user = user;
    }

    @Override
    public Set<SystemPermission> getPermissions() throws GuacamoleException {
        return systemPermissionService.retrievePermissions(getCurrentUser(), user);
    }

    @Override
    public boolean hasPermission(SystemPermission.Type permission)
            throws GuacamoleException {
        return systemPermissionService.retrievePermission(getCurrentUser(), user, permission) != null;
    }

    @Override
    public void addPermission(SystemPermission.Type permission)
            throws GuacamoleException {
        addPermissions(Collections.singleton(new SystemPermission(permission)));
    }

    @Override
    public void removePermission(SystemPermission.Type permission)
            throws GuacamoleException {
        removePermissions(Collections.singleton(new SystemPermission(permission)));
    }

    @Override
    public void addPermissions(Set<SystemPermission> permissions)
            throws GuacamoleException {
        systemPermissionService.createPermissions(getCurrentUser(), user, permissions);
    }

    @Override
    public void removePermissions(Set<SystemPermission> permissions)
            throws GuacamoleException {
        systemPermissionService.deletePermissions(getCurrentUser(), user, permissions);
    }

}
