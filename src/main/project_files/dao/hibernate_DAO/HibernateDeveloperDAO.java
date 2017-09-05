package main.project_files.dao.hibernate_DAO;

import main.project_files.dao.DeveloperDAO;
import main.project_files.models.Developer;
import main.project_files.models.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;

public class HibernateDeveloperDAO implements DeveloperDAO {

    private SessionFactory sessionFactory;

    public HibernateDeveloperDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long save(Developer developer) {
        Long id = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            id = (Long) session.save(developer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public Developer findByID(Long id) {
        Developer developer = createNewEmptyDeveloper();
        developer.setId(id);
        try (Session session = sessionFactory.openSession()) {
            Developer developerDB = session.get(Developer.class, id);
            if (developerDB != null) {
                developer = developerDB;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return developer;
    }

    @Override
    public void update(Developer developer) {
        Developer newDeveloper;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            newDeveloper = session.get(Developer.class, developer.getId());
            if (newDeveloper != null) {
                newDeveloper.setFname(developer.getFname());
                newDeveloper.setLname(developer.getLname());
                newDeveloper.setSalary(developer.getSalary());
                newDeveloper.setCompany(developer.getCompany());
                newDeveloper.setProject(developer.getProject());
                newDeveloper.setSkills(new HashSet<>());
                for (Skill skill : developer.getSkills()) {
                    newDeveloper.addSkill(skill);
                }
                session.clear();
                session.update(newDeveloper);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void delete(Developer developer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Developer developerBD = session.get(Developer.class, developer.getId());
            if (developerBD != null) {
                session.delete(developerBD);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }
    }

    @SuppressWarnings("Unchecked")
    @Override
    public List<Developer> findAll() {
        List<Developer> developers;
        try (Session session = sessionFactory.openSession()) {
            developers = session.createQuery("from Developers").list();
        }
        return developers;
    }

    @Override
    public Developer findByName(String fname) {
        Developer developer = createNewEmptyDeveloper();
        developer.setFname(fname);
        try (Session session = sessionFactory.openSession()) {
            Developer developerDB = (Developer) session.createQuery("from Developer d where d.fname like:=fname")
                    .setParameter("fname", fname).uniqueResult();
            if (developerDB != null) {
                developer = developerDB;
            }
        }
        return developer;
    }

    public Developer findByLname(String lname) {
        Developer developer = createNewEmptyDeveloper();
        developer.setLname(lname);
        try (Session session = sessionFactory.openSession()) {
            Developer developerDB = (Developer) session.createQuery("from Developer d where d.lname like:=lname")
                    .setParameter("lname", lname).uniqueResult();
            if (developerDB != null) {
                developer = developerDB;
            }
        }
        return developer;
    }
}