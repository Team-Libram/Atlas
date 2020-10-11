# Atlas
Technical University - Varna • FITA faculty • SIT speciality • Project name: "Library"

Written by: Peter Petkov and Miroslav Petrov

# Specification

  Create an informational system representing a library. The program should collect and process records about books and readers. The system should allow simultaneous user access.
  
  The system should support two types of users: Administrators, Operators and Readers (general users). The different types of users should have differentiating roles and access levels to the program's functionality.
  
  General program functionality associated with accounts:
    • Creation of operator accounts by administrators.
    • Creation and unsubscribing of readers by operators.
    • User form, used to create a reader's profile.
    • Renting of books.
    
  General program functionality associated with books:
    • Addition of new books (incl. inventory number, title, author and genre).
    • Retiring of damaged/worn out books.
    • Archival of old books (used only in the reading room).
    • Renting of books (with different security levels based on the book's condition - for takeout or only for the reading room).
    • Returning of rented books.
    
  The system should be able to generate the following reports:
    • Submitted reader forms (incl. date, status and the form details).
    • Books (incl. book condition and information).
    • Users (incl. admission date, rented and/or read books and user information).
    • Reader rating (loyal and non-loyal readers).
    
  Notification system:
    • Notifications about new reader forms (incl. notifying the reader of his form's status, notifying administrators about new forms).
    • Notifications about book archival and/or retirement.
    • Notifications about overdue book rental period. 
    
