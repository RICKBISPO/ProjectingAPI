package com.bispo.projecting.model.repository;

import com.bispo.projecting.model.entity.Project;
import com.bispo.projecting.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllUsersByProject(Project project);

    UserDetails findByEmail(String email);

}
