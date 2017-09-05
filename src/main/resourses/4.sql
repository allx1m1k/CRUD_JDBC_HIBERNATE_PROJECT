ALTER TABLE projects ADD cost DOUBLE ;

update projects set cost = 1500 where id = 1;
update projects set cost = 3500 where id = 2;
update projects set cost = 2300 where id = 3;
update projects set cost = 1800 where id = 4;
update projects set cost = 7500 where id = 5;
update projects set cost = 9200 where id = 6;
update projects set cost = 10000 where id = 7;