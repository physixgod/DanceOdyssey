package devnatic.danceodyssey.RestController;

import devnatic.danceodyssey.DAO.Entities.Role;
import devnatic.danceodyssey.DAO.Repositories.RoleRepository;
import devnatic.danceodyssey.Services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")

public class RoleController {
@Autowired
private RoleRepository roleRepo;
    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

    @GetMapping
    public List<Role> getRoleById() {
        return roleRepo.findAll();
    }
}

