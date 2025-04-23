package com.api.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "person", schema = "public")
public class Person extends AbstractAuditableEntity {

    private static final long serialVersionUID = 910878531418724403L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "identity", unique = true)
    private String identity;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "country_code")
    private Integer countryCode;

    @Column(name = "direction")
    private String direction;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "age")
    private Integer age;
}
