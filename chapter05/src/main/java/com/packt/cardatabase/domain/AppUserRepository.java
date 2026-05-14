package com.packt.cardatabase.domain;

import java.util.Optional;

// import org.springframework.data.repository.CrudRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.ListCrudRepository;

// @RepositoryRestResource(exported = false)
public interface AppUserRepository extends ListCrudRepository<AppUser, Long> {
	Optional<AppUser> findByUsername(String username);
}
