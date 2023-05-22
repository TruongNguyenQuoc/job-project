package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomAccountDetailService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optional = accountRepository.findByUsername(username);

        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomAccountDetails(optional.get());
    }
}
