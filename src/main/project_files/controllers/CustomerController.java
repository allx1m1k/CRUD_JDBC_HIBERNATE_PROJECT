package main.project_files.controllers;

import main.project_files.dao.GenericDAO;
import main.project_files.models.Customer;

import java.util.Scanner;

public class CustomerController extends AbstractModelController<Customer> {

    public CustomerController(GenericDAO<Customer, Long> dao) {
        super(dao);
    }

    @Override
    protected Customer getNewModel() {
        System.out.println("Input customer name: ");
        String customerName = new Scanner(System.in).nextLine();
        return new Customer(-100 , customerName);
    }

    @Override
    protected void printMenu(){
        System.out.println();
        System.out.println("ACTIONS WITH CUSTOMERS : ");
        super.printMenu();
    }
}
