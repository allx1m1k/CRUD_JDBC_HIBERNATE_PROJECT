create TABLE developers(
id integer  PRIMARY KEY ,
fname text not null,
lname text not null
);
create TABLE skills(
id integer PRIMARY KEY ,
title text  
);
create TABLE projects(
id integer PRIMARY KEY , 
title text not null
);
create TABLE companies(
id integer PRIMARY KEY ,
title text not null
);
create TABLE customers(
id integer PRIMARY KEY , 
title text not null
);
create TABLE developers_skills(
skill_id integer ,
developer_id integer, 
PRIMARY KEY (skill_id , developer_id),
FOREIGN KEY (skill_id ) references skills(id),
FOREIGN KEY (developer_id) references developers(id)
);

create TABLE project_developers(
developer_id integer ,
project_id integer ,
PRIMARY KEY( developer_id, project_id) ,
FOREIGN KEY( developer_id ) references developers(id) ,
FOREIGN KEY( project_id ) references projects(id) 
);

create TABLE company_projects(
project_id integer , 
company_id integer ,
PRIMARY KEY(project_id , company_id) ,
FOREIGN KEY(project_id) references projects(id) , 
FOREIGN KEY(company_id) references companies(id) 
);

create TABLE customer_projects(
project_id integer ,
customer_id integer ,
PRIMARY KEY(project_id ,customer_id) ,
FOREIGN KEY(project_id) references projects(id) ,
FOREIGN KEY(customer_id) references customers(id) 
);
