package main.project_files.controllers;

import main.project_files.dao.GenericDAO;
import main.project_files.models.Company;

import java.util.Scanner;

public class CompanyController extends AbstractModelController<Company> {

    public CompanyController(GenericDAO<Company, Long> dao) {
        super(dao);
    }

    @Override
    protected Company getNewModel() {
        System.out.println("Input company name: ");
        String companyName = new Scanner(System.in).nextLine();
        return new Company(-100 , companyName);
    }
    @Override
    protected void printMenu(){
        System.out.println();
        System.out.println("ACTIONS WITH COMPANIES:");
        super.printMenu();
    }
}
