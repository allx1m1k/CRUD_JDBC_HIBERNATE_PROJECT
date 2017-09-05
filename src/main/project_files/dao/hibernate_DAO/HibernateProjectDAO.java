package main.project_files.dao.hibernate_DAO;

import main.project_files.dao.ProjectDAO;
import main.project_files.models.Company;
import main.project_files.models.Customer;
import main.project_files.models.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class HibernateProjectDAO implements ProjectDAO {

    private SessionFactory sessionFactory;


    public HibernateProjectDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long save(Project project) {
        Long id = null;
        try (Session session = sessionFactory.openSession()) {
            id = (Long) session.save(project);
        } catch (Exception e) {
            System.out.println("Exception while trying to save your project " + project);
        }
        return id;
    }

    @Override
    public Project findByID(Long id) {
        Project project =
                new Project(id, "", 0, new Company(0, ""), new Customer(0, ""));
        try (Session session = sessionFactory.openSession()) {
            Project projectFromDB = session.get(Project.class, id);
            if (projectFromDB != null) {
                project = projectFromDB;
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while trying to find project with id: " + id);
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public void update(Project project) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Project projectDB = session.get(Project.class, project.getId());
            if (projectDB != null) {
                return;
            }
            projectDB.setTitle(project.getTitle());
            projectDB.setCost(project.getCost());
            projectDB.setCompany(project.getCompany());
            projectDB.setCustomer(project.getCustomer());
            session.update(projectDB);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception while trying to update your project " + project);
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void delete(Project project) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Project projectDB = session.get(Project.class, project.getId());
            if (projectDB == null) {
                return;
            }
            session.delete(projectDB);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception while trying to delete your project " + project);
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @SuppressWarnings("Unchecked")
    @Override
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            projects = session.createQuery("from Project").list();
        } catch (Exception e) {
            System.out.println("Exception while trying to find all projects");
            e.printStackTrace();
        }
        return projects;
    }

    @SuppressWarnings("Unchecked")
    @Override
    public Project findByName(String title) {
        Project project = new Project(0, title, 0, new Company(0, ""), new Customer(0, ""));
        try (Session session = sessionFactory.openSession()) {
            List<Project> projects = session.createQuery("select c from Project c where c.title like :title")
                    .setParameter("title", title).list();
            if (projects.size() != 0) {
                project = projects.get(0);
            }
        } catch (Exception e) {
            System.out.println("Exception while trying to find your project with title " + title);
            e.printStackTrace();
        }
        return project;
    }
}
