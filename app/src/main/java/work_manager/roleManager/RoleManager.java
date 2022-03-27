package work_manager.roleManager;

import work_manager.role.Role;

/**
 * Class for determining what authorities user roles have
 */
public class RoleManager {

    /**
     * The roles that can be given by a user to an other user
     */
    private final static Role[] grantableRoles = { Role.Admin, Role.Alkalmazott };

    /**
     * Checking whether one user has greater authority than the other
     * 
     * @param clientRole the logged in user's role
     * @param targetRole the role we are comparing against
     * @return whether the client's role is higher or not
     */
    public static boolean greaterClientAuthority(Role clientRole, Role targetRole) {
        if (clientRole == targetRole) {
            return false;
        }
        if (clientRole == Role.Admin && targetRole == Role.Alkalmazott || clientRole == Role.Készítő) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the given role has authority to edit users
     * 
     * @param clientRole
     * @return
     */
    public static boolean userManagementAuthority(Role clientRole) {
        if (clientRole == Role.Admin || clientRole == Role.Készítő) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the given role has authority to manage a project
     * 
     * @param clientRole
     * @return
     */
    public static boolean projectManagementAuthority(Role clientRole) {
        if (clientRole == Role.Admin || clientRole == Role.Készítő) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether a given role has authority to delete a project
     * 
     * @param clientRole
     * @return
     */
    public static boolean projectDeleteAuthority(Role clientRole) {
        if (clientRole == Role.Készítő) {
            return true;
        }
        return false;
    }

    public static Role[] getGrantableRoles() {
        return grantableRoles;
    }
}
