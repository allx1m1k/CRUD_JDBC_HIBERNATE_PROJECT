package main.project_files.models;

public class Company implements Model {

    private long id;
    private String title;

    public Company() {
    }

    public Company(String title) {
        this.title = title;
    }

    public Company(long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != company.id) return false;
        return title != null ? title.equals(company.title) : company.title == null;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        } else {
            this.title = "";
        }
    }
}
