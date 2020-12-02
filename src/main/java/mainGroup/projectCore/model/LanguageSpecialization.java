package mainGroup.projectCore.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class LanguageSpecialization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long languageSpecializationId;

    @NotBlank(message = "Language must be specified")
    private String language;

    @NotBlank(message = "Proficiency must be specified")
    private String proficiency;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "employeeId")
    private Employee employee;

    public LanguageSpecialization() {
    }

    private LanguageSpecialization(@NotBlank(message = "Language must be specified") String language, @NotBlank(message = "Proficiency must be specified") String proficiency, Employee employee) {
        setLanguage(language);
        setProficiency(proficiency);
        setEmployee(employee);
    }

    public static void createLanguageSpecialization(Employee employee, String language, String proficiency)
    {
        if(employee == null)
        {
            throw new IllegalArgumentException("Employee must not be null");
        }

        LanguageSpecialization ls = new LanguageSpecialization(language,proficiency,employee);
        employee.addSpecialization(ls);
    }

    public long getLanguageSpecializationId() {
        return languageSpecializationId;
    }

    public void setLanguageSpecializationId(long languageSpecializationId) {
        this.languageSpecializationId = languageSpecializationId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public Employee getEmployee() {
        return employee;
    }

    private void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void removeEmployee()
    {
        employee.removeSpecialization(this);
        if(employee != null)
        {
            employee = null;
        }
    }
}
