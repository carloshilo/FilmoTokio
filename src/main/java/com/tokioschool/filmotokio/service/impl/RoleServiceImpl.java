package com.tokioschool.filmotokio.service.impl;

import com.tokioschool.filmotokio.domain.Role;
import com.tokioschool.filmotokio.repository.RoleRepository;
import com.tokioschool.filmotokio.service.RoleService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final @NonNull RoleRepository roleRepository;

  @Override
  public List<Role> findAll() {
    return roleRepository.findAll();
  }

  @Override
  public Role findById(long id) {
    return roleRepository.findById(id).orElse(null);
  }
}
