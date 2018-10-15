package site.verity.tcr.repository;

import site.verity.tcr.domain.WalletToken;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WalletToken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletTokenRepository extends JpaRepository<WalletToken, Long> {

}
