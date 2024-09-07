# Customer_Reward_System
An application where customers can see their cashback reward for every transaction made or service paid for. 

# Project Structure

```
|--- .mvn
|--- src
|    |--- main
|         |--- java
|             |---Reward_Management_System
|                 |---<feature implementations>
|                 |---Program.java
|             |---utils
|         |--- resources
|             |--- application.properties
|    |--- test
|         |--- java
|             |---Reward_Management_System_tests
|                 |---<implementations tests>
|--- .gitignore
|--- mvnw
|--- mvnw.cmd
|--- README.md
```

## Getting started

Ensure you have your computer setup correctly for development by installing the following

- Java 17 or higher
- Install Java SDK/JDK
- Use your favourite IDE or Text editor

## Contribution Guide

#### If you don't have git on your machine, [install it](https://docs.github.com/en/get-started/quickstart/set-up-git).

## Fork this repository

Fork this repository by clicking on the fork button on the top of this page.
This will create a copy of this repository in your account.

## Clone the repository

<img align="right" width="300" src="https://firstcontributions.github.io/assets/Readme/clone.png" alt="clone this repository" />

Now clone the forked repository to your machine. Go to your GitHub account, open the forked repository, click on the code button and then click the _copy to clipboard_ icon.

Open a terminal and run the following git command:

```bash
git clone "url you just copied"
```

where "url you just copied" (without the quotation marks) is the url to this repository (your fork of this project). See the previous steps to obtain the url.

<img align="right" width="300" src="https://firstcontributions.github.io/assets/Readme/copy-to-clipboard.png" alt="copy URL to clipboard" />

For example:

```bash
git clone git@github.com:this-is-you/customer_reward_system.git
```

where `this-is-you` is your GitHub username. Here you're copying the contents of the first-contributions repository on GitHub to your computer.

## Setup Instructions

### 1. Clone the Repository

First, clone the repository to your local machine using Git.

```sh
git clone https://github.com/your-username/[app-name].git
cd [app-name]
```

### 2. Install Dependencies

Opening the project in your dev environment should automatically restore all your dependencies.
You can also install dependencies by running the following `maven` command in your root folder.

```sh
mvn dependency:resolve
```
### 3. set up database
- Ensure PostgreSQL is installed and running
- Create a new database in the database

 - update the `application.properties` file with your database credentials 
```html
# database configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/db_name
spring.datasource.username= your DB username
spring.datasource.password= your DB password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```

## Database Seeding 
To keep the application simple for the sake of the task, the database is seeded upon running of the application on your local machine,  so there’d be no need for creating new users or trying to sign up.
All you need to do is simply login with these credentials of the mocked users in database

#### User 1
`Username: johndoe@gmail.com`  
`Password: password1`

#### User2
`Username: janesmith@gmail.com`  
`Password: password2`

## Login
To login, ensure your application is running, then open Postman and hit this endpoint `[POST] api/v1/auth/login`  and login using any of the above credentials.

Request Body:

```json
{
    "email": "janesmith@gmail.com",
    "password": "password2"
}
```
Response Body:

```json
{
    "message": "Login Successful! Welcome to Balanceè, where we Redefine your vehicle repairs",
    "status_Code": 200,
    "data": {
        "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5lc21pdGhAZ21haWwuY29tIiwiaWF0IjoxNzI1NzA4MjEwLCJleHAiOjE3MjU3MDgzOTB9.cLTE7ICmiEpthaNT8gSEuY-49YgD8XMQr5TJoMHQCoQ",
        "data": {
            "customer_Id": "c90b08f9-f437-4433-b977-30d2bdfeabea",
            "first_Name": "Jane",
            "last_Name": "Smith",
            "email": "janesmith@gmail.com"
        }
    }
}
```
#### JWT TOKEN
Because the endpoints are protected, copy the Jwt token from the response and open a new request tab on postman by clicking that arrow in the image below then select the [GET] request

![w1](https://github.com/user-attachments/assets/0f1ab425-83d7-4721-b3c9-ba87a385461a)

then click on “Authorization” as shown below

![w2](https://github.com/user-attachments/assets/b7a91ac4-4810-4cc3-b9c3-9c3ecc764b51)

then click on the drop down as shown below and click on “Bearer Token” 

![w3](https://github.com/user-attachments/assets/329978e2-8c32-4998-8b5e-7d9f7bc87973)

then paste the jwt on the right box as shown below

![w4](https://github.com/user-attachments/assets/a7c9ad3b-e996-40ce-9efb-ea904240c299)

### Note: The JWT token only last for 3mins

### Response Cases

Response when you login with wrong username:

```json
{
    "message": "No Account found with Username janes@gmail.com",
    "error": "User Not Found",
    "status_code": 404
}
```

Response when you login with wrong password:

```json
{
    "message": "The password is incorrect",
    "error": "Incorrect Password",
    "status_code": 400
}
```

Response when you login with empty username or empty password:

```json
{
    "status_code": 422,
    "error": "validation error",
    "detail": {
        "email": "Email is required"
    }
}
```
```json
{
    "status_code": 422,
    "error": "validation error",
    "detail": {
        "Password": "Password is required"
    }
}
```

```json
{
    "status_code": 422,
    "error": "validation error",
    "detail": {
        "email": "Email is required",
        "Password": "Password is required"
    }
}
```

Response when you try to access the application with expired token:

```json
{
    "message": "The JWT token has expired",
    "error": "JWT expired at 2024-09-07T15:25:57Z. Current time: 2024-09-07T15:31:23Z, a difference of 326400 milliseconds.  Allowed clock skew: 0 milliseconds.",
    "status_code": 401
}
```

Response when you tamper with the token values:
```json
{
    "message": "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.",
    "error": "JWT token doesn't match the signed token",
    "status_code": 401
}
```

### Testing Endpoints After Login

After Login in successfully and inputting the JWT token, create a new request tab and change the request type to a GET and then test the following endpoints

Endpoint to get current logged in customer balance: ` [GET] api/v1/rewards/balance/{customerId} `

`customerId:` Because you already have customers in the database as a result of the database seeding, just go to your Postgres DB and copy the Id of the currently logged in customer as shown below

![w5](https://github.com/user-attachments/assets/9dcef518-1a32-4d70-b60f-47a9c17e756c)

Replace the Id you copied with the `“customerId”` phrase in the endpoint for accessing customer balance so you get something like this: ` [GET] api/v1/rewards/balance/c90b08f9-f437-4433-b977-30d2bdfeabea`


Response Cases

Response when Successful:

```json
{
    "message": "Success: Hi,Jane here are your available balances",
    "status_Code": 200,
    "data": {
        "customer_Id": "c90b08f9-f437-4433-b977-30d2bdfeabea",
        "cashback": 11000.00,
        "current_Balance": 250000.00
    }
}
```

Response when Customer/User Id is incorrect:

```json
{
    "message": "User with Id: c90b08f9-f437-4433-b977-30d2bdfeabe not found",
    "error": "User Not Found",
    "status_code": 404
}
```

Endpoint to get current logged in customer transaction balance: ` [GET] api/v1/rewards/history/{customerId} `

`customerId:` Because you already have already customers in the database as a result of the database seeding, just go to your Postgres DB and copy the Id of the currently logged in customer as shown below

![w5](https://github.com/user-attachments/assets/9dcef518-1a32-4d70-b60f-47a9c17e756c)

Replace the Id with you copied with the `“customerId”` phrase in the endpoint for accessing customer balance so you get something like this: ` [GET] api/v1/rewards/history/c90b08f9-f437-4433-b977-30d2bdfeabea`

### Response Cases

Response when Successful:

```json
{
    "message": "Success: Hi, Jane here are your available transaction balances",
    "status_Code": 200,
    "transactionData": [
        {
            "id": "3736ea3c-5e26-43b5-95ee-28b5a3b0836f",
            "amountEarned": 1000.00,
            "description": "Exhaust Repair",
            "createdAt": "2024-09-07T11:01:32.046992"
        },
        {
            "id": "2ce0c814-5ba3-4712-b27c-33f2f6948aaf",
            "amountEarned": 570.00,
            "description": "Bonnet Dent Repair",
            "createdAt": "2024-09-07T11:01:32.047992"
        }
    ]
}
```

Response when Customer/User Id is incorrect:

```json
{
    "message": "User with Id: c90b08f9-f437-4433-b977-30d2bdfeabe not found",
    "error": "User Not Found",
    "status_code": 404
}
```
 




