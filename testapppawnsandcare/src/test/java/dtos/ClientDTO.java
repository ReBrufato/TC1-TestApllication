package dtos;

import java.util.Objects;

public class ClientDTO {
    private String name;
    private String doc;
    private String email;
    private String phone;


    public ClientDTO(String name, String doc, String email, String phone) {
        this.name = name;
        this.doc = doc;
        this.email = email;
        this.phone = phone;
    }


    public String getName() {
        return this.name;
    }

    public String getDoc() {
        return this.doc;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ClientDTO)) {
            return false;
        }
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(name, clientDTO.name) && Objects.equals(doc, clientDTO.doc) && Objects.equals(email, clientDTO.email) && Objects.equals(phone, clientDTO.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, doc, email, phone);
    }
    

}