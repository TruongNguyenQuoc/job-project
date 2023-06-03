package com.project.tutoronline.repository;


import com.project.tutoronline.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

    Account findByEmail(String email);

    Account findByPhone(String phone);

}
