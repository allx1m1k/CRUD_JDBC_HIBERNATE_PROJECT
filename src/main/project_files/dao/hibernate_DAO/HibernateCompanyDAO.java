package main.project_files.dao.hibernate_DAO;

import main.project_files.dao.CompanyDAO;
import main.project_files.models.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class HibernateCompanyDAO implements CompanyDAO {

    private SessionFactory sessionFactory;

    public HibernateCompanyDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long save(Company company) {
        Long id = null;
        try (Session session = sessionFactory.openSession()) {
            id = (Long) session.save(company);
        } catch (Exception e) {
            System.out.println("Exception while trying to save your company + " + company);
        }
        return id;
    }

    @Override
    public Company findByID(Long id) {
        Company company = new Company(id, "");
        try (Session session = sessionFactory.openSession()) {
            Company companyDB = session.get(Company.class, id);
            if (companyDB != null) {
                company = companyDB;
            }
        } catch (Exception e) {
            System.out.println("Exception while trying to find your company with id " + id);
        }
        return company;
    }

    @Override
    public void update(Company company) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Company companyDB = session.get(Company.class, company.getId());
            if (companyDB == null) {
                return;
            }
            companyDB.setTitle(company.getTitle());
            session.update(companyDB);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Exception while trying to update your company " + company);
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void delete(Company company) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            Company companyDB = session.get(Company.class, company.getId());
            if (companyDB == null) {
                return;
            }
        } catch (Exception e) {
            System.out.println("Exception while trying to delete your company " + company);
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @SuppressWarnings("Unchecked")
    @Override
    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            companies = session.createQuery("from Company").list();
        } catch (Exception e) {
            System.out.println("Exception while trying to find all the companies");
            e.printStackTrace();
        }
        return companies;
    }

    @SuppressWarnings("Unchecked")
    @Override
    public Company findByName(String title) {
        Company company = new Company(0, title);
        try (Session session = sessionFactory.openSession()) {
            List<Company> companies = session.createQuery("select c from Company where c.title like :title")
                    .setParameter("title", title).list();
            if(companies.size() !=  0){
                company = companies.get(0);
            }
        } catch (Exception e) {
            System.out.println("Exception while trying find your company by title " + title);
            e.printStackTrace();
        }
        return company;
    }
}
