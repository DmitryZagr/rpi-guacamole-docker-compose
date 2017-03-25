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

import java.util.Collection;
import org.apache.ibatis.annotations.Param;
import org.apache.guacamole.auth.jdbc.user.UserModel;
import org.apache.guacamole.net.auth.permission.ObjectPermission;

/**
 * Mapper for object-related permissions.
 *
 * @author Michael Jumper
 */
public interface ObjectPermissionMapper extends PermissionMapper<ObjectPermissionModel> {

    /**
     * Retrieve the permission of the given type associated with the given
     * user and object, if it exists. If no such permission exists, null is
     * returned.
     *
     * @param user
     *     The user to retrieve permissions for.
     * 
     * @param type
     *     The type of permission to return.
     * 
     * @param identifier
     *     The identifier of the object affected by the permission to return.
     *
     * @return
     *     The requested permission, or null if no such permission is granted
     *     to the given user for the given object.
     */
    ObjectPermissionModel selectOne(@Param("user") UserModel user,
            @Param("type") ObjectPermission.Type type,
            @Param("identifier") String identifier);

    /**
     * Retrieves the subset of the given identifiers for which the given user
     * has at least one of the given permissions.
     *
     * @param user
     *     The user to check permissions of.
     *
     * @param permissions
     *     The permissions to check. An identifier will be included in the
     *     resulting collection if at least one of these permissions is granted
     *     for the associated object
     *
     * @param identifiers
     *     The identifiers of the objects affected by the permissions being
     *     checked.
     *
     * @return
     *     A collection containing the subset of identifiers for which at least
     *     one of the specified permissions is granted.
     */
    Collection<String> selectAccessibleIdentifiers(@Param("user") UserModel user,
            @Param("permissions") Collection<ObjectPermission.Type> permissions,
            @Param("identifiers") Collection<String> identifiers);

}
