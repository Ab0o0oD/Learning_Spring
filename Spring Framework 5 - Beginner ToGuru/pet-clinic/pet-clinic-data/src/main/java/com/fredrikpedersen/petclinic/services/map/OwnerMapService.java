package com.fredrikpedersen.petclinic.services.map;

import com.fredrikpedersen.petclinic.model.people.owners.Owner;
import com.fredrikpedersen.petclinic.model.pets.Pet;
import com.fredrikpedersen.petclinic.services.OwnerService;
import com.fredrikpedersen.petclinic.services.PetService;
import com.fredrikpedersen.petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.RequestingUserName;
import java.util.Set;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 16/01/2020 at 14:25
 */

@Service
@Profile({"default", "map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner save(Owner owner) {
        if (owner != null) {
            if (owner.getPets() != null) {

                owner.getPets().forEach(pet -> {
                   if (pet.getPetType() != null) {
                       if (pet.getPetType().getId() == null) {
                           pet.setPetType(petTypeService.save(pet.getPetType()));
                       }
                   } else {
                       throw new RuntimeException("Pet Type is required");
                   }

                   if (pet.getId() == null) {
                       Pet savedPet = petService.save(pet);
                       pet.setId(savedPet.getId());
                   }
                });
            }

            return super.save(owner);

        } else {
            return null;
        }
    }

    @Override
    public void delete(Owner owner) {
        super.delete(owner);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }
}
