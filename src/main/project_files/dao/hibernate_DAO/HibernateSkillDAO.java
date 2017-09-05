package main.project_files.dao.hibernate_DAO;

import main.project_files.dao.SkillDAO;
import main.project_files.models.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class HibernateSkillDAO implements SkillDAO {

    private SessionFactory sessionFactory;

    public HibernateSkillDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Long save(Skill skill) {
        Long id;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            id = (Long) session.save(skill);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public Skill findByID(Long id) {
        Skill skill = new Skill(id, "");
        try (Session session = sessionFactory.openSession()) {
            Skill skillDB = session.get(Skill.class, id);
            if (skillDB != null) {
                skill = skillDB;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return skill;
    }

    @Override
    public void update(Skill skill) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Skill skillToUpdate = session.get(Skill.class, skill.getId());
            if (skillToUpdate != null) {
                skillToUpdate.setTitle(skill.getTitle());
                session.update(skillToUpdate);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Skill skill) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Skill skillToDelete = session.get(Skill.class, skill.getId());
            if (skillToDelete != null) {
                session.delete(skillToDelete);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @SuppressWarnings("Unchecked")
    @Override
    public List<Skill> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Skill").list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Skill findByName(String title) {
        Skill skill = new Skill(0, "");
        try (Session session = sessionFactory.openSession()) {
            Skill skillFromDB = (Skill) session.createQuery("from Skill s where s.title like :title")
                    .setParameter("title", title).uniqueResult();
            if (skillFromDB != null) {
                skill = skillFromDB;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return skill;
    }
}
