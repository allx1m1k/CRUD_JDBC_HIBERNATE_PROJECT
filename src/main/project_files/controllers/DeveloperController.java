package main.project_files.controllers;

import main.project_files.dao.CompanyDAO;
import main.project_files.dao.GenericDAO;
import main.project_files.dao.ProjectDAO;
import main.project_files.dao.SkillDAO;
import main.project_files.models.Company;
import main.project_files.models.Developer;
import main.project_files.models.Project;
import main.project_files.models.Skill;

import java.util.HashSet;
import java.util.Scanner;

public class DeveloperController extends AbstractModelController<Developer> {

    protected SkillDAO skillDAO;
    protected CompanyDAO companyDAO;
    protected ProjectDAO projectDAO;

    public DeveloperController(GenericDAO<Developer, Long> dao, SkillDAO skillDAO, CompanyDAO companyDAO, ProjectDAO projectDAO) {
        super(dao);
        this.skillDAO = skillDAO;
        this.companyDAO = companyDAO;
        this.projectDAO = projectDAO;
    }


    public DeveloperController(GenericDAO<Developer, Long> dao) {
        super(dao);
    }

    @Override
    protected Developer getNewModel() {
        System.out.print("Input developers first name and last name : ");
        String developerFname = new Scanner(System.in).nextLine();
        String developerLname = new Scanner(System.in).nextLine();
        System.out.println("Input developer salary: ");
        int salary = new Scanner(System.in).nextInt();
        System.out.println("Input company id : ");
        long companyId = new Scanner(System.in).nextLong();
        System.out.println("Input project id : ");
        long projectId = new Scanner(System.in).nextLong();
        System.out.println("Input skill ids: ");
        String skillIdLine = new Scanner(System.in).nextLine().replaceAll(" ", "");
        HashSet<Skill> skills = new HashSet<>();
        for (String skillId : skillIdLine.split(",")) {
            Skill skill = skillDAO.findByID(Long.parseLong(skillId));
            if (skill != null) {
                skills.add(skill);
            }
        }
        Company company = companyDAO.findByID(companyId);
        Project project = projectDAO.findByID(projectId);
        return new Developer(-100 , developerFname , developerLname , salary , company , project , skills);
    }

    @Override
    protected void printMenu() {
        System.out.println();
        System.out.println("ACTIONS WITH DEVELOPERS : ");
        super.printMenu();
    }
}
