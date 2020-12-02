package mainGroup.projectCore.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee extends Person implements CustomerSupport, Manager{

    @Positive
    private int salary;

    @Enumerated
    private EmployeeRole role;

    @OneToMany(mappedBy = "employee",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    private Set<LanguageSpecialization> specializations = new HashSet<>();

    private static Set<LanguageSpecialization> allSpecializations = new HashSet<>();

    public enum EmployeeRole
    {
        CustomerSupport,
        Manager
    }

    public Employee() {
    }

    public Employee(@NotEmpty String name, @NotEmpty String phone, @Past LocalDate birthday, @Positive int salary, EmployeeRole role) {
        super(name, phone, birthday);
        this.salary = salary;
        this.role = role;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public Set<LanguageSpecialization> getSpecializations() {
        return new HashSet<>(specializations);
    }

    private void setSpecializations(Set<LanguageSpecialization> specializations) {
        this.specializations = specializations;
    }

    public static Set<LanguageSpecialization> getAllSpecializations() {
        return new HashSet<>(allSpecializations);
    }

    private static void setAllSpecializations(Set<LanguageSpecialization> allSpecializations) {
        Employee.allSpecializations = allSpecializations;
    }

    public void addSpecialization(LanguageSpecialization specialization)
    {
        if(specialization == null)
        {
            throw new IllegalArgumentException("Language specialization must not be null");
        }
        if(!specializations.contains(specialization))
        {
            if(specializations.contains(specialization))
            {
                throw new IllegalArgumentException("Language specialization is already part of something");
            }

            specializations.add(specialization);
            allSpecializations.add(specialization);
        }
    }

    public void removeSpecialization(LanguageSpecialization section)
    {
        if(section == null)
        {
            throw new IllegalArgumentException("Language specialization must not be null");
        }
        if(specializations.contains(section))
        {
            if(specializations.size() == 1)
            {
                throw new IllegalArgumentException("Employee cant have 0 specializations");
            }
            specializations.remove(section);
            allSpecializations.remove(section);
            section.removeEmployee();
        }
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + getPersonId() +
                ", salary=" + salary +
                ", role=" + role +
                ", specializations=" + getSpecializations() +
                '}';
    }
}
