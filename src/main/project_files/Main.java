package main.project_files;

import main.project_files.controllers.AbstractController;
import main.project_files.controllers.MainController;
import main.project_files.factories.FactoryController;

import java.sql.SQLException;

public class Main extends AbstractController {
    public static void main(String[] args) {
        try {
            new Main().start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Bye bro");
    }

    @Override
    protected void action(int choice) throws SQLException {
        switch (choice) {
            case 1:
            new MainController(FactoryController.getCompanyController(),
                    FactoryController.getCustomerController(), FactoryController.getDeveloperController(),
                    FactoryController.getProjectController(), FactoryController.getSkillController()).start();
            break;
            case 2:
                System.out.println("go kill yourself , bye:)");
                System.exit(0);
                default:
                    System.out.println("Retard please ENTER VAN OR TY");
        }
    }
    @Override
    protected void printMenu() {
        System.out.println("What you gonna do brother?");
        System.out.println("1 : Lets use JDBC DAO");
        System.out.println("2 : Exit");
    }
}
