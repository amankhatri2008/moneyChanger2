package com.resortworld.moneychanger.repository;

import com.resortworld.moneychanger.domain.Usertransactions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Usertransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsertransactionsRepository extends JpaRepository<Usertransactions, Long> {

    @Query("select usertransactions from Usertransactions usertransactions where usertransactions.user.login = ?#{principal.username}")
    List<Usertransactions> findByUserIsCurrentUser();

}
