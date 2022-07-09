package com.recipe2plate.api.database.seeders;

import com.recipe2plate.api.entities.Role;
import com.recipe2plate.api.repositories.RoleRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class RoleSeeder implements ApplicationListener<ApplicationStartedEvent> {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        roleRepository.save(new Role(null, "ROLE_ADMIN"));
        roleRepository.save(new Role(null, "ROLE_USER"));
    }
}
