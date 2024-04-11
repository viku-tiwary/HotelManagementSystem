# Hotel Management System

This Java application allows users to manage hotel reservations, view existing reservations, update reservation details, and delete reservations. The system is designed to provide a simple and efficient way to handle hotel bookings.

## Features

- Reserve a room: Users can reserve a room by providing customer details such as name, ID, room number, and contact information.
- View reservations: Users can view all existing reservations along with customer details.
- Get room number: Users can search for a reservation by ID and guest name to retrieve the allocated room number.
- Update reservation: Users can update reservation details such as guest name, ID, room number, and contact information.
- Delete reservation: Users can delete a reservation by providing the reservation ID.

## Technologies Used

- Java: Core programming language used for development.
- JDBC: Java Database Connectivity for interacting with the MySQL database.
- MySQL: Database management system used for storing reservation details.

## How to Run

1. Clone the repository to your local machine.
2. Set up a MySQL database and import the provided SQL file (`reservation_details.sql`) to create the necessary tables.
3. Update the database connection details in the `HotelManagementSystem` class (`url`, `user_name`, `password`) according to your MySQL setup.
4. Compile the Java files using a Java compiler.
5. Run the `HotelManagementSystem` class to start the application.

## Usage

Upon running the application, users will be presented with a menu where they can choose from various options such as reserving a room, viewing reservations, updating reservations, and deleting reservations. Follow the on-screen prompts to navigate through the system.

## Contributions

Contributions to the project are welcome! If you find any issues or have suggestions for improvements, feel free to open an issue or create a pull request.

