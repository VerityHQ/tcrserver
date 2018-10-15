package site.verity.tcr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TcrStake.
 */
@Entity
@Table(name = "tcr_stake")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TcrStake implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "token")
    private String token;

    @Column(name = "shares_issued")
    private Long sharesIssued;

    @Column(name = "owner_identity")
    private String ownerIdentity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public TcrStake amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public TcrStake token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getSharesIssued() {
        return sharesIssued;
    }

    public TcrStake sharesIssued(Long sharesIssued) {
        this.sharesIssued = sharesIssued;
        return this;
    }

    public void setSharesIssued(Long sharesIssued) {
        this.sharesIssued = sharesIssued;
    }

    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public TcrStake ownerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
        return this;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TcrStake tcrStake = (TcrStake) o;
        if (tcrStake.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tcrStake.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TcrStake{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", token='" + getToken() + "'" +
            ", sharesIssued=" + getSharesIssued() +
            ", ownerIdentity='" + getOwnerIdentity() + "'" +
            "}";
    }
}
