package org.prography.kagongsillok.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

}
