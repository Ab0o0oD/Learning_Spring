package com.fredrikpedersen.petclinic.model;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 16/01/2020 at 13:31
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass //Indicates that this is not going to be a database entity, and that other classes will inherit from it.
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
