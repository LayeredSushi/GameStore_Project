package mainGroup.projectCore.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Customer extends Person{

    @NotBlank
    private String billingAddress;

    @NotBlank
    @Size(max = 10)
    @Column(unique=true)
    private String username;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

    public enum Region {
        EU,
        CIS,
        NA,
        SA,
        AU,
        ASIA,
        AF,
        OTHER
    }

    @Enumerated
    private Region region;

    @OneToMany(mappedBy = "customer",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    private Set<Purchase> purchases = new HashSet<>();

    public Customer()
    {

    }

    public Customer(@NotEmpty String name, @NotEmpty String phone, @Past LocalDate birthday, @NotBlank String billingAddress, @NotBlank @Size(max = 10) String username, @NotBlank @Size(min = 8, max = 16) String password, Region region) {
        super(name, phone, birthday);
        this.billingAddress = billingAddress;
        this.username = username;
        this.password = password;
        this.region = region;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Set<Purchase> getPurchases() {
        return new HashSet<>(purchases);
    }

    private void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(Purchase purchase)
    {
        if(purchase == null)
        {
            throw new IllegalArgumentException("Purchase must not be null");
        }
        if(!purchases.contains(purchase))
        {
            purchases.add(purchase);
        }
    }

    public void removePurchase(Purchase purchase)
    {
        if(purchase == null)
        {
            throw new IllegalArgumentException("Purchase must not be null");
        }

        if(purchases.contains(purchase))
        {
            if(purchases.size() == 1)
            {
                throw new IllegalArgumentException("Purchase list should have at least 1 element");
            }
            purchases.remove(purchase);
            purchase.removeLinks();
        }
    }

}
