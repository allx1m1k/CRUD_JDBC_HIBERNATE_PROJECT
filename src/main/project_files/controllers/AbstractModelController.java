package main.project_files.controllers;

import main.project_files.dao.GenericDAO;
import main.project_files.models.Model;

import java.util.Scanner;

public abstract class AbstractModelController<T extends Model> extends AbstractController {

    private final GenericDAO<T, Long> dao;

    public AbstractModelController(GenericDAO<T, Long> dao) {
        this.dao = dao;
    }

    @Override
    protected void printMenu() {
        System.out.println("1 - add new");
        System.out.println("2 - update");
        System.out.println("3 - find by id");
        System.out.println("4 - find by title");
        System.out.println("5 - delete");
        System.out.println("6 - show all");
        System.out.println("0 - go to main menu");
    }

    @Override
    protected void action(int choice) {
        switch (choice) {
            case 1:
                addNew();
                break;
            case 2:
                update();
                break;
            case 3:
                findByIdAndOutput();
                break;
            case 4:
                findByNameAndOutput();
                break;
            case 5:
                deleteById();
                break;
            case 6:
                showAll();
                break;
        }
    }

    private void showAll() {
        dao.findAll().forEach(System.out::println);
    }

    private void deleteById() {
        System.out.println("Delete by id. Input id : ");
        long id = new Scanner(System.in).nextLong();
        T model = dao.findByID(id);
        if (model == null) {
            System.out.println("Model with this id is not found");
            return;
        }
        System.out.println("Delete: " + model);
        dao.delete(model);
    }

    private void addNew() {
        T model = getNewModel();
        System.out.println("Id of new model : " + dao.save(model));
    }

    private void update() {
        System.out.print("Update. Input id : ");
        long id = new Scanner(System.in).nextLong();
        T oldModel = dao.findByID(id);
        if (oldModel == null) {
            System.out.println("Model with this id is not found");
            return;
        }
        System.out.println("Input new information for " + oldModel);
        T model = getNewModel();
        model.setId(id);
        System.out.println("New data : ");
        System.out.println(model);
        dao.update(model);
    }
    private void findByIdAndOutput(){
        System.out.println("Find by id. Input id : ");
        long id = new Scanner(System.in).nextLong();
        T model = dao.findByID(id);
        System.out.println(model);
    }

    private void findByNameAndOutput() {
        System.out.print("Find by name. Input name : ");
        String name = new Scanner(System.in).nextLine();
        T model = dao.findByName(name);
        System.out.println(model);
    }

    protected abstract T getNewModel();
}
