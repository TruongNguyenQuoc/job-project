package com.example.restapi.repository;

import com.example.restapi.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAccountByFullName(String fullName);

    Optional<Account> findByUsername(String username);

    Optional<Account> findAccountByPhone(String phone);

    Optional<Account> findAccountByEmail(String email);
}
