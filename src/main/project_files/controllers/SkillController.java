package main.project_files.controllers;

import main.project_files.dao.GenericDAO;
import main.project_files.models.Skill;

import java.util.Scanner;

public class SkillController extends AbstractModelController<Skill> {

    public SkillController(GenericDAO<Skill, Long> dao) {
        super(dao);
    }

    @Override
    protected Skill getNewModel() {
        System.out.print("Input skill name : ");
        String skillName = new Scanner(System.in).nextLine();
        return new Skill(-100 , skillName);
    }

    @Override
    protected void printMenu(){
        System.out.println();
        System.out.println("ACTIONS WITH SKILL : ");
        super.printMenu();
    }
}
