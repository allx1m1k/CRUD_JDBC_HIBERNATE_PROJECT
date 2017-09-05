select p.title, avg(d.salary), min(p.cost) as mincost 
from developers d
join project_developers pd on d.id = pd.developer_id
join projects p on pd.project_id = p.id
group by p.title
order by mincost limit 1