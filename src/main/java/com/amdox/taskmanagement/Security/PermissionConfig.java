package com.amdox.taskmanagement.Security;

import com.amdox.taskmanagement.Enum.Permission;
import com.amdox.taskmanagement.Enum.Role;

import java.util.*;

public class PermissionConfig {
    public static Map<Role, Set<Permission>> getPermission() {
        Map<Role, Set<Permission>> map = new HashMap<>();

        map.put(Role.ADMIN, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.ISSUE_CREATE,
                Permission.ISSUE_EDIT,
                Permission.ISSUE_DELETE,
                Permission.COMMENT_ADD,
                Permission.COMMENT_DELETE,
                Permission.USER_MANAGE
        )));

        map.put(Role.MANAGER, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.ISSUE_CREATE,
                Permission.ISSUE_EDIT,
                Permission.COMMENT_ADD
        )));

        map.put(Role.DEVELOPER, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.ISSUE_EDIT,
                Permission.COMMENT_ADD
        )));

        map.put(Role.TESTER, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.COMMENT_ADD
        )));

        // Default or other roles can be added here
        return map;
    }
}
