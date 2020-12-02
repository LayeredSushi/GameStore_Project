package mainGroup.projectCore.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long publisherId;

    @NotBlank(message = "Publisher name must be specified")
    private String name;

    @NotBlank(message = "Publisher phone must be specified")
    private String phone;

    @NotBlank(message = "Publisher address must be specified")
    private String address;

    @OneToMany(mappedBy = "publisher",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    private Set<Contract> contracts = new HashSet<>();


    public Publisher() {
    }

    public Publisher(@NotBlank(message = "Publisher name must be specified") String name, @NotBlank(message = "Publisher phone must be specified") String phone, @NotBlank(message = "Publisher address must be specified") String address) {
        setName(name);
        setPhone(phone);
        setAddress(address);
    }


    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public Set<Contract> getContracts() {
        return new HashSet<>(contracts);
    }

    private void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public void addContract(Contract contract)
    {
        if(contract == null)
        {
            throw new IllegalArgumentException("Product must not be null");
        }
        if(!contracts.contains(contract))
        {
            contracts.add(contract);
        }
    }

    public void removeContract(Contract contract)
    {
        if(contract == null)
        {
            throw new IllegalArgumentException("Contract must not be null");
        }

        if(contracts.contains(contract))
        {
            if(contracts.size() == 1)
            {
                throw new IllegalArgumentException("Contract list should have at least 1 element");
            }
            contracts.remove(contract);
            contract.removeLinks();
        }
    }
}
