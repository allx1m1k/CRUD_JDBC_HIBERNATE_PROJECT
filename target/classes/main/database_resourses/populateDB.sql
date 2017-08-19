
insert into companies (id , title) VALUES 
(1 , 'Ubisoft'),
(2 , 'Engage - point'),
(3 , 'Microsoft');

insert into developers (id , fname , lname) VALUES
(1 , 'Cartman' , 'Perinno'),
(2 , 'Joseph' , 'Stavinski'),
(3 , 'VItaliy', 'Trinozhenko'),
(4 , 'Vadim' , 'Getman');

insert into customers (id , title) VALUES 
(1 , 'American SoftGear'),
(2 , 'Pinokkio'),
(3 , 'Ryan Soft');

insert into projects (id , title) VALUES 
(1 , 'Back - end of project'),
(2 , 'Front - end of project');
(3 , 'Tests');
(4 , 'Databases');
(5 , 'UML - diagrams');
(6 , 'Management');
(7 , 'Android - development');

insert into skills (id , title) VALUES
(1 , 'Java'), 
(2 , 'C++'),
(3 , 'PHP'), 
(4 , 'Python'),
(5 , 'JavaScript');

insert into developers_skills (skill_id, developer_id) values(1, 1);
insert into developers_skills (skill_id, developer_id) values(3, 1);
insert into developers_skills (skill_id, developer_id) values(5, 1);
insert into developers_skills (skill_id, developer_id) values(2, 2);
insert into developers_skills (skill_id, developer_id) values(3, 2);
insert into developers_skills (skill_id, developer_id) values(4, 2);
insert into developers_skills (skill_id, developer_id) values(1, 3);
insert into developers_skills (skill_id, developer_id) values(3, 3);
insert into developers_skills (skill_id, developer_id) values(4, 3);
insert into developers_skills (skill_id, developer_id) values(5, 4);

insert into company_projects (project_id, company_id) values(1, 1);
insert into company_projects (project_id, company_id) values(1, 2);
insert into company_projects (project_id, company_id) values(2, 3);
insert into company_projects (project_id, company_id) values(2, 2);

insert into customer_projects (project_id, customer_id) values(1, 1);
insert into customer_projects (project_id, customer_id) values(7, 1);
insert into customer_projects (project_id, customer_id) values(2, 1);
insert into customer_projects (project_id, customer_id) values(3, 2);
insert into customer_projects (project_id, customer_id) values(4, 2);
insert into customer_projects (project_id, customer_id) values(4, 3);
insert into customer_projects (project_id, customer_id) values(5, 3);
insert into customer_projects (project_id, customer_id) values(6, 3);

insert into project_developers (project_id, developer_id) values(1, 4);
insert into project_developers (project_id, developer_id) values(1, 2);
insert into project_developers (project_id, developer_id) values(1, 3);
insert into project_developers (project_id, developer_id) values(2, 1);
insert into project_developers (project_id, developer_id) values(2, 2);
insert into project_developers (project_id, developer_id) values(2, 3);
insert into project_developers (project_id, developer_id) values(2, 4);
