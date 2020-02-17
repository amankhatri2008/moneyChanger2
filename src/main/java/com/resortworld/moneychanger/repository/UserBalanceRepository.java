package com.resortworld.moneychanger.repository;

import com.resortworld.moneychanger.domain.UserBalance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserBalance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

    @Query("select userBalance from UserBalance userBalance where userBalance.name.login = ?#{principal.username}")
    List<UserBalance> findByNameIsCurrentUser();

}
