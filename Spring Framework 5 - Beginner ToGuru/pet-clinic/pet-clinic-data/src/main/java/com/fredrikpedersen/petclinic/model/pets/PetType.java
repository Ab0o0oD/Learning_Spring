package com.fredrikpedersen.petclinic.model.pets;

import com.fredrikpedersen.petclinic.model.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 15/01/2020 at 17:42
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "types")
public class PetType extends BaseEntity {

    @Column(name = "name")
    private String name;
}
