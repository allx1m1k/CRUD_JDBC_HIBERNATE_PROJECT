package main.project_files.controllers;

import java.sql.SQLException;

public class MainController extends AbstractController {

    private CompanyController companyController;
    private CustomerController customerController;
    private DeveloperController developerController;
    private ProjectController projectController;
    private SkillController skillController;

    public MainController(CompanyController companyController,
                          CustomerController customerController,
                          DeveloperController developerController,
                          ProjectController projectController,
                          SkillController skillController) {
        this.companyController = companyController;
        this.customerController = customerController;
        this.developerController = developerController;
        this.projectController = projectController;
        this.skillController = skillController;
    }

    @Override
    protected void action(int choice) throws SQLException {
        switch (choice) {
            case 1:
                companyController.start();
                break;
            case 2:
                customerController.start();
                break;
            case 3:
                developerController.start();
            case 4:
                projectController.start();
            case 5:
                skillController.start();
        }

    }

    @Override
    protected void printMenu() {
        System.out.println();
        System.out.println("MENU");
        System.out.println("1- actions with companies");
        System.out.println("2- actions with customers");
        System.out.println("3- actions with developers");
        System.out.println("4- actions with projects");
        System.out.println("5- actions with skills");
        System.out.println("0- exit");
    }
}
