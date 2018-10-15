package site.verity.tcr.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The identity is composed of a chain and address similar to a DID
 */
@ApiModel(description = "The identity is composed of a chain and address similar to a DID")
@Entity
@Table(name = "identity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Identity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chain")
    private String chain;

    @Column(name = "identity_address")
    private String identityAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChain() {
        return chain;
    }

    public Identity chain(String chain) {
        this.chain = chain;
        return this;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getIdentityAddress() {
        return identityAddress;
    }

    public Identity identityAddress(String identityAddress) {
        this.identityAddress = identityAddress;
        return this;
    }

    public void setIdentityAddress(String identityAddress) {
        this.identityAddress = identityAddress;
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
        Identity identity = (Identity) o;
        if (identity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), identity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Identity{" +
            "id=" + getId() +
            ", chain='" + getChain() + "'" +
            ", identityAddress='" + getIdentityAddress() + "'" +
            "}";
    }
}
