package ru.yummy.eat.entity;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bonus")
@AutoProperty
public class Bonus implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="client_uuid")
    private String clientUuid;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name="company_name")
    private String companyName;

    @Column(name="company_logo")
    private String companyLogo;

    @Column(name="bonus_type")
    private String bonusType;

    @Column(name="bonus_value")
    private String bonusValue;

//    @ManyToOne
//    private OurClient client;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
    }

    public String getBonusValue() {
        return bonusValue;
    }

    public void setBonusValue(String bonusValue) {
        this.bonusValue = bonusValue;
    }

//    public OurClient getClient() {
//        return client;
//    }
//
//    public void setClient(OurClient client) {
//        this.client = client;
//    }

    @Override
    public boolean equals( Object o ) {
        return Pojomatic.equals( this, o );
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode( this );
    }

    @Override
    public String toString() {
        return Pojomatic.toString( this );
    }
}
