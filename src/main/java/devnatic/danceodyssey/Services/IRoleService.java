package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.Role;

public interface IRoleService {
    Role createRole(Role role);

    Role updateRole(Long id, Role role);

    void deleteRole(Long id);

    Role getRoleById(Long id);
}
