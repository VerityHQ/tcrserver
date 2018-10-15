package site.verity.tcr.repository;

import site.verity.tcr.domain.Tcr;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tcr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TcrRepository extends JpaRepository<Tcr, Long> {

}
