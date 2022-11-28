package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.dominio.Role;
import com.tokioschool.filmotokio.repository.RoleRepository;
import com.tokioschool.filmotokio.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final @NonNull RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
