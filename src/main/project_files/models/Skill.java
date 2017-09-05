package main.project_files.models;

import javax.persistence.*;

@Entity
@Table(name = "skills")
public class Skill implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    public Skill() {
    }

    public Skill(String title) {
        this.title = title;
    }

    public Skill(long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        if (id != skill.id) return false;
        return title != null ? title.equals(skill.title) : skill.title == null;
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
        this.title = (title != null) ? title : "";
    }
}
