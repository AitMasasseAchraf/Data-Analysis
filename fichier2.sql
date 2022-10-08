
-- Creating the dwh_customers table
drop table if exists dwh_customers;


create table dwh_customers as
select 
	c1.customer_id,
    c1.first_name,
    c1.last_name,
    c1.full_name,
    c2.country_code
from whiskey_retail__shop.customers as c1
join whiskey_retail__shop.countries as c2
on c1.country_id = c2.country_id
order by customer_id;

-- Setting customer_id as the primary key
alter table dwh_customers
modify column customer_id int not null primary key;



-- Creating the dwh_customers table
drop table if exists dwh_employees;

create table dwh_employees as
select 
	e.employee_id,
    e.first_name,
    e.last_name,
    e.full_name,
    d.department
from whiskey_retail__shop.employees as e
join whiskey_retail__shop.departments as d
on e.department_id = d.department_id
order by employee_id;

-- Setting employee_id as the primary key
alter table dwh_employees
modify column employee_id int not null primary key;

-- Creating the dwh_products table
drop table if exists dwh_products;

create table dwh_products as
select *
from whiskey_retail__shop.products 
order by product_id;

-- Setting product_id as the primary key
alter table dwh_products
modify column product_id int not null primary key;


-- Create a new fact table: dwh_fact
drop table if exists dwh_fact;

CREATE TABLE dwh_fact AS 
SELECT c1.customer_id,
    e1.employee_id,
    p2.product_id,
    p2.Alcohol_Amount,
    p2.Alcohol_Percent,
    p2.Alcohol_Price,
    p2.Product_Name,
    c1.four_digits,
    c2.Country,
    c3.credit_provider,
    d.Date_key,
    p1.date 
FROM
    whiskey_retail__shop.payments AS p1
        JOIN
    whiskey_retail__shop.customers AS c1 ON p1.customer_id = c1.customer_id
        JOIN
    whiskey_retail__shop.employees AS e1 ON p1.employee_id = e1.employee_id
        JOIN
    whiskey_retail__shop.products AS p2 ON p1.product_id = p2.product_id
        JOIN
    whiskey_retail__shop.countries AS c2 ON c1.country_id = c2.country_id
        JOIN
    whiskey_retail__shop.customer_cc AS c3 ON c1.credit_provider_id = c3.credit_provider_id
        JOIN
    dwh_date2 AS d ON p1.date = d.Dates
ORDER BY d.Dates;

-- Setting the foreign keys for each dimension table
alter table dwh_fact
add foreign key (customer_id)  references dwh_customers ( customer_id);

alter table dwh_fact
add foreign key (employee_id)  references dwh_employees ( employee_id);

alter table dwh_fact
add foreign key (product_id)  references dwh_products ( product_id);

alter table dwh_fact
add foreign key (Date_key)  references dwh_date2 ( Date_key);




# Create trigger insert_customer
create trigger insert_customer
after insert on whiskey_retail__shop.customers 
for each row
insert into dwh_whiskey.dwh_customers
select 
	c1.customer_id,
    c1.first_name,
    c1.last_name,
    c1.full_name,
    c2.country_code
from customers as c1
join countries as c2
on c1.country_id = c2.country_id
where c1.customer_id = new.customer_id;


# Create trigger insert_employee
create trigger insert_employee
after insert on whiskey_retail__shop.employees 
for each row
insert into dwh_whiskey.dwh_employees
select 
	e.employee_id,
    e.first_name,
    e.last_name,
    e.full_name,
    d.department
from employees as e
join departments as d
on e.department_id = d.department_id
where e.employee_id = new.employee_id;


create trigger new_payment
after insert on payments
for each row
insert into dwh_whiskey.dwh_fact
select 
	c.customer_id,
    e.employee_id,
    pr.product_id,
    pr.Alcohol_Amount,
    pr.Alcohol_Percent,
    pr.Alcohol_Price,
    pr.Product_Name,
    c.four_digits,
    co.Country,
    cc.credit_provider,
    d.Date_key,
    d.Dates
from payments as p
join customers as c
on p.customer_id = c.customer_id
join countries as co
on c.country_id = co.country_id
join customer_cc cc
on c.credit_provider_id = cc.credit_provider_id
join employees e
on p.employee_id = e.employee_id
join products pr
on p.product_id = pr.product_id
join dwh_whiskey.dwh_date d
on d.Dates = p.date
where p.payment_id = new.payment_id;