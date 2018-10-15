package site.verity.tcr.repository;

import site.verity.tcr.domain.TcrStake;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TcrStake entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TcrStakeRepository extends JpaRepository<TcrStake, Long> {

}
