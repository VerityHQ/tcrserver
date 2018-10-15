package site.verity.tcr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Tcr.
 */
@Entity
@Table(name = "tcr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tcr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tcr_hash")
    private String tcrHash;

    @Column(name = "tcr_name")
    private String tcrName;

    @Column(name = "content")
    private String content;

    @Column(name = "bonding_curve")
    private Long bondingCurve;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTcrHash() {
        return tcrHash;
    }

    public Tcr tcrHash(String tcrHash) {
        this.tcrHash = tcrHash;
        return this;
    }

    public void setTcrHash(String tcrHash) {
        this.tcrHash = tcrHash;
    }

    public String getTcrName() {
        return tcrName;
    }

    public Tcr tcrName(String tcrName) {
        this.tcrName = tcrName;
        return this;
    }

    public void setTcrName(String tcrName) {
        this.tcrName = tcrName;
    }

    public String getContent() {
        return content;
    }

    public Tcr content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getBondingCurve() {
        return bondingCurve;
    }

    public Tcr bondingCurve(Long bondingCurve) {
        this.bondingCurve = bondingCurve;
        return this;
    }

    public void setBondingCurve(Long bondingCurve) {
        this.bondingCurve = bondingCurve;
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
        Tcr tcr = (Tcr) o;
        if (tcr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tcr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tcr{" +
            "id=" + getId() +
            ", tcrHash='" + getTcrHash() + "'" +
            ", tcrName='" + getTcrName() + "'" +
            ", content='" + getContent() + "'" +
            ", bondingCurve=" + getBondingCurve() +
            "}";
    }
}
