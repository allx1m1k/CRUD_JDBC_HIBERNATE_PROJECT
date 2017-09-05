package main.project_files.models;

import javax.persistence.*;


@Entity
@Table (name = "customers")
public class Customer implements Model {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    public Customer() {

    }

    public Customer(String title) {
        this.title = title;
    }

    public Customer(long id , String title) {
        this.title = title;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != customer.id) return false;
        return title != null ? title.equals(customer.title) : customer.title == null;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
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

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
