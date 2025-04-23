package com.api.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "client", schema = "public")
@PrimaryKeyJoinColumn(name = "id")
public class Client extends Person {

    private static final long serialVersionUID = 910878531418724403L;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
}
