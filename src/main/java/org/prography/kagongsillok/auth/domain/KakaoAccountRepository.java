package org.prography.kagongsillok.auth.domain;

import org.prography.kagongsillok.auth.domain.entity.KakaoAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoAccountRepository extends JpaRepository<KakaoAccount, Long> {

}
