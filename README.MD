# Bug Tracking and Resolution Management System

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

A comprehensive bug tracking and resolution management system.

## Overview

Bug Tracking is a platform designed to help teams track, manage, and resolve software bugs efficiently. Built with a modern tech stack, Wasfa provides an intuitive interface for documenting issues and their resolutions.

## Features

- Bug tracking and categorization
- Detailed resolution documentation
- User management and role-based access
- Searchable database of past issues and solutions
- Analytics and reporting

## Installation


### Setup Instructions

1. Clone the repository:
```bash
git clone https://github.com/LORDyyyyy/wasfa.git
cd wasfa
```

2. Configure the database connection:
```bash
# create src/main/resources/application.properties
spring.application.name=BugAndResolution
spring.datasource.url=jdbc:mysql://localhost:3306/bug_and_resolution_schema
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.mysql.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
```
3. Build the project:
```bash
./mvnw clean install
```
4. Run the application:
```bash
./mvnw spring-boot:run
```
5. Access the application at `http://localhost:8080`
