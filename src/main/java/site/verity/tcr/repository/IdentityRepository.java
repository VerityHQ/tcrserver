package site.verity.tcr.repository;

import site.verity.tcr.domain.Identity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Identity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdentityRepository extends JpaRepository<Identity, Long> {

}
