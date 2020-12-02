package mainGroup.projectCore.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Table
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long personId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    @Past
    private LocalDate birthday;

    public Person() {
    }

    public Person(@NotEmpty String name, @NotEmpty String phone, @Past LocalDate birthday) {
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
