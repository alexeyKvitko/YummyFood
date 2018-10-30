package ru.yummy.food.entity;


import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;

@Entity
@Table( name = "menu_item" )
@AutoProperty
public class MenuItem {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column( name = "company_id" )
    private Integer companyId;

    @Column( name = "type_id" )
    private Integer typeId;

    @Column( name = "category_id" )
    private Integer categoryId;

    @Column( name = "entity_id" )
    private Integer entityId;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId( Integer companyId ) {
        this.companyId = companyId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId( Integer typeId ) {
        this.typeId = typeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId( Integer categoryId ) {
        this.categoryId = categoryId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId( Integer entityId ) {
        this.entityId = entityId;
    }

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
