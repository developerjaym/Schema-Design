# Schema Design

#### Description
You will interact with the provided database administration tool to craft a schema that matches a given specification

#### Duration
1-2 hours

#### Skills
Relational Database fundamentals such as Tables, Columns, Data Types, Primary & Foreign Keys, Relationships, Multiplicity, Join Tables, Administration Tools

## Concept
The concept of this assignment is to create a database schema that will represent People, their Location, and their Interests. This database is intended to be used to connect individuals who are in the same location and share interests.

## Instructions

* Create the three required tables (**Person**, **Location**, & **Interest**) along with any required *join tables*. Include the following listed columns in the designated tables, *along with any additonal columns required for join statements*

1. Person
  * Id (`Primary Key`)
  * First Name
  * Last Name
  * Age
  
2. Location
  * Id (`Primary Key`)
  * City
  * State
  * Country

3. Interest
  * Id (`Primary Key`)
  * Title

* Set all `Id` fields to be *Auto-Generated Primary Keys*
  
* Add join columns and tables as neccesary to ensure that
  1. A **Person** can have any number of **Interest**s
  2. A **Person** must have one **Location**
  
* Write a SQL query that will find all **Person** entries that share at least one **Interest** and have the same **Location**
  * Create a Stored Procedure based on that SQL query that takes an **Interest** and a **Location** as parameters, and returns a Result Set of all **Person** entries that share that **Interest** and **Location**
  
* Export the schema by using *Tools > Backup...*

* Add the .backup file to your local repository and issue a pull request