package mainGroup.projectCore.model.misc;

import mainGroup.projectCore.model.Game;

import java.util.HashSet;
import java.util.Set;

public class PaymentInfo {

    private String name;
    private String surname;
    private String credit;
    private String billingAddress;
    private int year;
    private int month;

    private Set<Game> cartGames;

    public PaymentInfo(String name, String surname, String credit, String billingAddress, int year, int month, Set<Game> cartGames) {
        setName(name);
        setSurname(surname);
        setCredit(credit);
        setBillingAddress(billingAddress);
        setYear(year);
        setMonth(month);
        setCartGames(cartGames);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Set<Game> getCartGames() {
        return new HashSet<>(cartGames);
    }

    public void setCartGames(Set<Game> cartGames) {
        this.cartGames = cartGames;
    }
}
