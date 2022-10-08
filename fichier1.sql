
# Creating a new schema and using it
drop schema if exists dwh_whiskey;

create schema dwh_whiskey;

use dwh_whiskey;

select min(date) as first_transaction
from whiskey_retail_shop.payments
