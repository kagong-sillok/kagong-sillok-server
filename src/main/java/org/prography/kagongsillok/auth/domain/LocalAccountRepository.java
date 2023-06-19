package org.prography.kagongsillok.auth.domain;

import java.util.Optional;
import org.prography.kagongsillok.auth.domain.entity.LocalAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalAccountRepository extends JpaRepository<LocalAccount, Long> {

    Optional<LocalAccount> findByLoginId(String loginId);
}
