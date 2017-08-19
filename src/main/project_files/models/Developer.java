package main.project_files.models;

import java.util.HashSet;
import java.util.Set;

public class Developer implements Model {

    private long id;
    private String fname;
    private String lname;
    private double salary;

    private Company company;
    private Project project;
    private Set<Skill> skills = new HashSet<Skill>();


    public Developer() {
    }

    public Developer(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    public Developer(long id, String fname, String lname, double salary) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
    }

    public Developer(long id, String fname, String lname, double salary, Company company, Project project) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
        this.company = company;
        this.project = project;
    }

    public Developer(long id, String fname, String lname, double salary, Company company, Project project, Set<Skill> skills) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
        this.company = company;
        this.project = project;
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", salary=" + salary +
                ", company=" + company +
                ", project=" + project +
                ", skills=" + skills +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Developer developer = (Developer) o;

        if (id != developer.id) return false;
        if (Double.compare(developer.salary, salary) != 0) return false;
        if (!fname.equals(developer.fname)) return false;
        if (!lname.equals(developer.lname)) return false;
        if (company != null ? !company.equals(developer.company) : developer.company != null) return false;
        if (project != null ? !project.equals(developer.project) : developer.project != null) return false;
        return skills.equals(developer.skills);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + fname.hashCode();
        result = 31 * result + lname.hashCode();
        temp = Double.doubleToLongBits(salary);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + skills.hashCode();
        return result;
    }

    public long getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public boolean addSkill(Skill skill) {
        return (skill != null) && this.skills.add(skill);
    }

    public boolean removeSkill(Skill skill) {
        return (skill != null) && this.skills.remove(skill);
    }
}
