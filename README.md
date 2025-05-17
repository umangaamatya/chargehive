ChargeHive - EV Charging Station Web Platform

🚀 Introduction

ChargeHive is a web-based platform designed to simplify the management and booking of Electric Vehicle (EV) charging stations. It provides users with access to real-time data about station availability, pricing, and allows them to make bookings. Admins can manage users and stations via an intuitive dashboard.

🌐 Technologies Used

- Java (Servlets & JSP): Backend development

- MySQL: Relational database management

- JDBC: Database connectivity

- HTML, CSS, JavaScript: Frontend UI

- Apache Tomcat: Web server (tested on v10)

- phpMyAdmin: Database GUI

- JSTL: Rendering server-side logic in JSP

🧱 How to Run ChargeHive Locally

1. Clone the Repository

git clone https://github.com/your-username/chargehive.git

2. Import into IDE

Use Eclipse, IntelliJ IDEA, or NetBeans.

Make sure to set up as a Dynamic Web Project if using Eclipse.

3. Configure Apache Tomcat

Download and install Apache Tomcat

Configure project build path to target Tomcat

4. Setup MySQL Database

Install MySQL and phpMyAdmin (optional but recommended)

Start MySQL server

5. Create the Database

Open phpMyAdmin or use CLI:

CREATE DATABASE chargehive;
USE chargehive;

6. Create Tables

Execute the following SQL scripts in phpMyAdmin or MySQL CLI:

user Table

CREATE TABLE user (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  user_fullName VARCHAR(100),
  user_email VARCHAR(100),
  user_password TEXT,
  user_contact VARCHAR(20),
  user_address VARCHAR(100),
  user_role VARCHAR(10),
  user_loyaltyPoints INT,
  user_membership VARCHAR(20),
  user_profile_pic VARCHAR(255)
);

station Table

CREATE TABLE station (
  station_id INT AUTO_INCREMENT PRIMARY KEY,
  station_name VARCHAR(100),
  station_location VARCHAR(100),
  station_availability VARCHAR(20),
  station_price DECIMAL(5,2),
  station_ports INT,
  station_type VARCHAR(20)
);

booking Table

CREATE TABLE booking (
  booking_id INT AUTO_INCREMENT PRIMARY KEY,
  booking_date DATE,
  user_id INT,
  station_id INT,
  FOREIGN KEY (user_id) REFERENCES user(user_id),
  FOREIGN KEY (station_id) REFERENCES station(station_id)
);

7. Configure Database Connection in Code

Make sure your JDBC connection URL, username, and password are set correctly in your utility class (example DbConfig.java):

String url = "jdbc:mysql://localhost:3307/chargehive";
String username = "root";
String password = ""; // or your password

8. Build & Deploy

Right-click the project > Run on Server

Navigate to http://localhost:8080/ChargeHive

📊 Key Features

User registration and login with session handling

View all available EV stations with price, port, and type

Book stations with real-time updates

Admin dashboard with CRUD operations for users and stations

Search and filter support

Basic profile picture upload for users


📅 Future Enhancements

- OTP/email verification

- Real-time map integration (Google Maps)

- Payment Integration

- AI-based Recommendation System

🚀 Maintainers

Umanga Amatya (Backend Developer)

✉️ Contact

For issues, reach out to: amatyaumanga@gmail.com