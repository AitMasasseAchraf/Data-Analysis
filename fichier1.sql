-- Creating the Data Warehouse in a different schema 

# Creating a new schema and using it
drop schema if exists dwh_whiskey;

create schema dwh_whiskey;

use dwh_whiskey;

-- 2. Generating the Date Dimension 
# Checking the date of the first transactions
select min(date) as first_transaction
from whiskey_retail_shop.payments;








