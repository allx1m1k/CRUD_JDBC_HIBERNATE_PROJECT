package main.project_files.controllers;

import main.project_files.dao.CompanyDAO;
import main.project_files.dao.CustomerDAO;
import main.project_files.dao.GenericDAO;
import main.project_files.models.Company;
import main.project_files.models.Customer;
import main.project_files.models.Project;

import java.util.Scanner;

public class ProjectController extends AbstractModelController<Project> {

    protected CompanyDAO companyDAO;
    protected CustomerDAO customerDAO;

    public ProjectController(GenericDAO<Project, Long> dao) {
        super(dao);
        this.companyDAO = companyDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    protected void printMenu() {
        System.out.println();
        System.out.println("ACTIONS WITH PROJECTS : ");
        super.printMenu();
    }

    @Override
    protected Project getNewModel() {
        System.out.print("Input project title : ");
        String projectTitle = new Scanner(System.in).nextLine();
        System.out.print("Input project cost : ");
        int projectCost = new Scanner(System.in).nextInt();
        System.out.print("Input company id : ");
        long companyId = new Scanner(System.in).nextLong();
        System.out.print("Input customer id : ");
        long customerId = new Scanner(System.in).nextLong();
        Company company = companyDAO.findByID(companyId);
        Customer customer = customerDAO.findByID(customerId);
        return new Project(-100 , projectTitle , projectCost , company , customer );
    }
}
