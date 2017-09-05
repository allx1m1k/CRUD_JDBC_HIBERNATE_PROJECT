select c.title as company, cus.title as customer, min(p.cost) as money from companies c 
join company_projects cp on c.id = cp.company_id 
join projects p on cp.project_id = p.id
join customer_projects cup on cup.project_id = p.id
join customers cus on cus.id = cup.customer_id
group by c.title, cus.title