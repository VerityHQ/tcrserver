package site.verity.tcr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A WalletToken.
 */
@Entity
@Table(name = "wallet_token")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WalletToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identity_address")
    private String identityAddress;

    @Column(name = "token")
    private String token;

    @Column(name = "amount")
    private Long amount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentityAddress() {
        return identityAddress;
    }

    public WalletToken identityAddress(String identityAddress) {
        this.identityAddress = identityAddress;
        return this;
    }

    public void setIdentityAddress(String identityAddress) {
        this.identityAddress = identityAddress;
    }

    public String getToken() {
        return token;
    }

    public WalletToken token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getAmount() {
        return amount;
    }

    public WalletToken amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
        WalletToken walletToken = (WalletToken) o;
        if (walletToken.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), walletToken.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WalletToken{" +
            "id=" + getId() +
            ", identityAddress='" + getIdentityAddress() + "'" +
            ", token='" + getToken() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
