package main.project_files.models;

public class Project implements Model {

    private long id;
    private String title;
    private int cost;


    private Company company;
    private Customer customer;

    public Project() {
    }

    public Project(String title, int cost, Company company, Customer customer) {
        this.title = title;
        this.cost = cost;
        this.company = company;
        this.customer = customer;
    }

    public Project(long id, String title, int cost, Company company, Customer customer) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.company = company;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", company=" + company +
                ", customer=" + customer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != project.id) return false;
        if (cost != project.cost) return false;
        if (!title.equals(project.title)) return false;
        if (!company.equals(project.company)) return false;
        return customer.equals(project.customer);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + cost;
        result = 31 * result + company.hashCode();
        result = 31 * result + customer.hashCode();
        return result;
    }

    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
