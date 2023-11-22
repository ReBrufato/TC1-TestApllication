package dtos;

import java.util.Objects;

public class PetDTO {
    private String name;

    private String type;

    private String breed;

    private String owner;

    public PetDTO(String name, String type, String breed) {this(name, type, breed, null);}

    public PetDTO(String name, String type, String breed, String owner) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetDTO petDTO = (PetDTO) o;
        return Objects.equals(name, petDTO.name) && Objects.equals(type, petDTO.type) && Objects.equals(breed, petDTO.breed) && Objects.equals(owner, petDTO.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, breed, owner);
    }
}
