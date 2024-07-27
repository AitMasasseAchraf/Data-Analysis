# Data Engineering Project

## Project Title
**Data Engineering Project**

## Description
In this project, I integrated various data engineering tasks, including extracting, loading, warehousing, and analyzing data.

### Part 1: Data Extraction
- **Source**: [The Whisky Exchange](https://www.thewhiskyexchange.com)
- **Method**: Web scraping using Python

### Part 2: Data Generation and Loading
- **Data Generation**: Used Python to generate random data about various parts of the organization
- **Database Design**: Designed a central RDBMS and applied normalization
- **Data Loading**: Loaded the data into the central RDBMS

### Part 3: Data Warehouse Creation
- **Schema Design**: Created the Data Warehouse in a different schema
- **Date Dimension**: Generated the Date Dimension by checking the date of the first transaction from the central database using `fichier1.sql`
- **Loading**: Completed the creation of the Date Dimension and loaded it into MySQL

### Part 4: Dimension Generation and Automation
- **Schema Warehouse Dimensions**: Generated the dimensions of the warehouse schema
- **Automation**: Created triggers for automating the transfer of data between the source schema and the warehouse schema using `fichier2.sql`

### Part 5: Data Extraction and Analysis
- **Connection**: Connected Python to the MySQL Data Warehouse
- **Data Extraction**: Extracted the Fact Table data
- **Data Analysis**: Analyzed the data

## Required Libraries
- `bs4`
- `pandas`
- `requests`
- `numpy`
- `names`
- `faker`
- `pandasql`
- `random`
- `time`
- `datetime`
- `pymysql`
- `matplotlib.pyplot`
- `seaborn`

## File Order
1. Part 1
2. Part 2
3. `fichier1.sql`
4. Part 3
5. `fichier2.sql`
6. Part 4

---

**Note**: Ensure you have all the required libraries installed before running the project.
