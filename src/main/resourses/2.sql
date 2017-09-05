select p.title, sum(d.salary) as salary from projects p 
join project_developers pd on pd.project_id = p.id 
join developers d on pd.developer_id = d.id 
group by p.title
order by salary desc limit 1