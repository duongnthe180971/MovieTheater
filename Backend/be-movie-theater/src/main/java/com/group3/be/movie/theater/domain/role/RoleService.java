package com.group3.be.movie.theater.domain.role;

import com.group3.be.movie.theater.domain.permession.Permission;
import com.group3.be.movie.theater.domain.permession.PermissionService;
import com.group3.be.movie.theater.domain.role.dto.ResPermissionRoleDTO;
import com.group3.be.movie.theater.domain.role.dto.ResRoleDTO;
import com.group3.be.movie.theater.domain.seat_status.SeatStatus;
import com.group3.be.movie.theater.util.BaseService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final BaseService baseService;
    private final PermissionService permissionService;

    public List<ResRoleDTO> getAllRoleDTO() {
        return roleRepository.findAll().stream().map((r) -> baseService.convertObjectToObject(r, ResRoleDTO.class)).toList();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void createDefaultRole() {
        Role admin = new Role();
        admin.setRoleName("Admin");
        admin.setDescription("The Admin has the highest authority in the system. They can manage users, assign roles, control content, configure system settings, and access all data.");
        // admin.setPermissions(permissionService.getAllPermissions());
        Role employee = new Role();
        employee.setRoleName("Employee");
        employee.setDescription("The Employee has moderate access, mainly handling tasks related to their job. They do not have administrative or user management privileges.");
        Role member = new Role();
        member.setRoleName("Member");
        member.setDescription("The Member is a regular user who can use system services but has no administrative rights.");
        // save
        roleRepository.saveAllAndFlush(List.of(admin, employee, member));
    }

   @Transactional
    public void assignDefaultPermissionsToRoles() {

        Role admin = roleRepository.findByRoleName("Admin").orElse(null);

        if (admin != null && admin.getPermissions() != null && !admin.getPermissions().isEmpty()) {
            return; 
        }

        List<Permission> allPermissions = permissionService.getAllPermissions();
        if (allPermissions.isEmpty()) return;

        Role employee = roleRepository.findByRoleName("Employee").orElse(null);
        Role member = roleRepository.findByRoleName("Member").orElse(null);

        if (admin != null) {
            admin.setPermissions(new ArrayList<>(allPermissions));
            roleRepository.save(admin);
        }

        if (employee != null) {
            List<Permission> employeePermissions = allPermissions.stream()
                .filter(p -> 
                    !p.getModule().equals("Permission") && 
                    !p.getModule().equals("Role") &&
                    !(p.getModule().equals("Account") && p.getMethod().equals("DELETE"))
                ).collect(java.util.stream.Collectors.toList()); 
            
            employee.setPermissions(employeePermissions);
            roleRepository.save(employee);
        }

        if (member != null) {
            List<Permission> memberPermissions = allPermissions.stream()
                .filter(p -> 
                    (p.getModule().equals("Account") && (
                        p.getApiPath().contains("/profile") || 
                        p.getApiPath().contains("/auth/") || 
                        p.getMethod().equals("PUT")
                    )) ||
                    p.getModule().equals("File") ||
                    
                    (p.getModule().equals("Invoice") && (p.getMethod().equals("GET") || p.getMethod().equals("POST"))) ||
                    (p.getModule().equals("Type") && (p.getMethod().equals("GET") || p.getMethod().equals("POST"))) ||
                    
                    (p.getModule().equals("Movie") && p.getMethod().equals("GET")) ||
                    (p.getModule().equals("Schedule") && p.getMethod().equals("GET")) ||
                    (p.getModule().equals("ShowDate") && p.getMethod().equals("GET")) ||
                    (p.getModule().equals("Seat") && p.getMethod().equals("GET")) ||
                    (p.getModule().equals("SeatType") && p.getMethod().equals("GET")) ||
                    (p.getModule().equals("SeatStatus") && p.getMethod().equals("GET")) ||
                    (p.getModule().equals("Promotion") && p.getMethod().equals("GET")) ||   // <--- MỚI THÊM
                    (p.getModule().equals("CinemaRoom") && p.getMethod().equals("GET")) ||  // <--- MỚI THÊM
                    
                    p.getModule().equals("ScheduleSeat")

                ).collect(java.util.stream.Collectors.toList()); 
            
            member.setPermissions(memberPermissions);
            roleRepository.save(member);
        }
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, List<Long> selectedPermissions) {
        Role roleDB = findRoleById(id);
        List<Permission> permissions = new ArrayList<>();
        selectedPermissions.forEach(p -> permissions.add(permissionService.findPermissionById(p)));
        roleDB.setPermissions(permissions);
        return roleRepository.save(roleDB);
    }

    public Role findRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
    public ResPermissionRoleDTO findPermissionRoleById(Long id) {
        return baseService.convertObjectToObject(findRoleById(id), ResPermissionRoleDTO.class);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByRoleName(name).orElse(null);
    }

    public boolean isExistRoleById(Long id) {
        return roleRepository.existsById(id);
    }

}
