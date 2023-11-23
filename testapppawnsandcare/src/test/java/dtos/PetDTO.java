package dtos;

import java.util.Objects;

public class PetDTO {
    private String name;
    private String type;
    private String breed;
    private String ownerName;
    
    public PetDTO(String name, String type, String breed) {this(name, type, breed, null);}

    public PetDTO(String name, String type, String breed, String ownerName) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.ownerName = ownerName;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getBreed() {
        return breed;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetDTO petDTO = (PetDTO) o;
        return Objects.equals(name, petDTO.name) && Objects.equals(type, petDTO.type) && Objects.equals(breed, petDTO.breed) && Objects.equals(ownerName, petDTO.ownerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, breed, ownerName);
    }
}
