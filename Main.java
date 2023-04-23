
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final int FULLNAMECOL = 0;
    // private static final int USERNAMECOL = 1;
    // private static final int PASSWORDCOL = 2;
    private static final int STUDENTIDCOL = 3;
    Scanner sc = new Scanner(System.in);
    int choice;
    String fullName, username, password, confirmPassword, studentId;
    String studentFile = "student.csv";
    String adminFile = "Admin.csv";
    String bookFile = "Book.csv";

    void RegisterUser() {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

        System.out.println("\n============");
        System.out.println("Registration");
        System.out.println("============\n");
        System.out.println("1. Admin");
        System.out.println("2. Student");
        System.out.println("0. Home");

        System.out.println("\nEnter Your Choice : ");
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Choice...");
            sc.nextLine();
            RegisterUser();
            return;
        }

        if (choice == 0)
            return;

        if (choice < 0 || choice > 2) {
            RegisterUser();
            return;
        }

        // While Loop For Full Name
        sc.nextLine();
        while (true) {
            System.out.println("\nEnter Your Full Name - ");
            fullName = sc.nextLine();

            if (fullName.length() == 0)
                System.out.println("Name Cannot Be An Empty Field...");
            else if (!Pattern.matches("[a-zA-Z ]{" + fullName.length() + "}", fullName))
                System.out.println("\nName Must Only Contain Characters...");
            else
                break;
        }

        // If Student Then Take Input Of Student Id
        if (choice == 2) {
            while (true) {
                System.out.println("\nEnter Your Student Id - ");
                studentId = sc.nextLine();

                int studIndex[] = CsvFileHandler.findRowColumnNumber(studentFile, studentId, STUDENTIDCOL);

                if (studentId.length() == 0)
                    System.out.println("Student Id Cannot Be An Empty Field...");
                else if (!Pattern.matches("[0-9]{" + studentId.length() + "}", studentId))
                    System.out.println("\nStudent Id Must Only Contain Numbers...");
                else if (choice == 2 && studIndex[0] != -1 && studIndex[1] != -1)
                    System.out.println("This Student ID Is Already In Use.");
                else
                    break;
            }
        }

        // While Loop For Username
        while (true) {
            System.out.println("\nEnter Your Username - ");
            username = sc.nextLine();

            // Validation - Check if username already exists
            if (username.length() == 0)
                System.out.println("Username Cannot Be An Empty Field.");
            // Critical Section - Check This Condition After Running
            else if (choice == 1 && CsvFileHandler.findDataInCsvFile(adminFile, username, 1) != null)
                System.out.println("This Username Is Already In Use.");
            // Critical Section - Check This Condition After Running
            else if (choice == 2 && CsvFileHandler.findDataInCsvFile(studentFile, username, 1) != null)
                System.out.println("This Username Is Already In Use.");
            else
                break;
        }

        // While Loop For Password
        while (true) {
            System.out.print("\nEnter Your Password - ");
            password = new String(System.console().readPassword());
            for (int i = 0; i < password.length(); i++)
                System.out.print("*");

            System.out.println();

            if (Pattern.matches(passwordPattern, password))
                break;
            else {
                System.out.println("\nPassword Must Contain At Least 8 Characters And At Most 20 Characters.");
                System.out.println("Password Must Contain At Least One Digit.");
                System.out.println("Password Must Contain At Least One Uppercase Alphabet.");
                System.out.println("Password Must Contain At Least One Lowercase Alphabet.");
                System.out.println("Password Must Contain At Least One Special Character.");
                System.out.println("Password Cannot Contain Space");
            }
        }

        System.out.print("\nConfirm Password - ");
        confirmPassword = new String(System.console().readPassword());
        for (int i = 0; i < password.length(); i++)
            System.out.print("*");

        if (password.equals(confirmPassword)) {
            if (choice == 1) {
                Admin admin = new Admin(fullName, username, password);
                CsvFileHandler.writeObjectToCsvFile(adminFile, admin);
            } else {
                Student student = new Student(fullName, username, password, studentId);
                CsvFileHandler.writeObjectToCsvFile(studentFile, student);
            }
            System.out.println("\n\nUser Registered Successfully...");
        } else {
            System.out.println("\n\nBoth Password Must Be Same\nTry Again...");
            RegisterUser();
        }
    }

    void SignIn() {
        System.out.println("\n=======");
        System.out.println("Sign In");
        System.out.println("=======\n");
        System.out.println("1. Admin");
        System.out.println("2. Student");
        System.out.println("0. Home");
        System.out.println("\nEnter Your Choice : ");
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Choice...");
            sc.nextLine();
            SignIn();
            return;
        }

        if (choice == 0)
            return;

        if (choice < 0 || choice > 2) {
            SignIn();
            return;
        }

        // While Loop For Username
        sc.nextLine();
        while (true) {
            System.out.println("\nEnter Your Username - ");
            username = sc.nextLine();

            if (username.length() == 0)
                System.out.println("Username Cannot Be An Empty Field...");
            else
                break;
        }

        System.out.print("\nEnter Your Password - ");
        password = new String(System.console().readPassword());
        for (int i = 0; i < password.length(); i++)
            System.out.print("*");

        System.out.println();

        if (choice == 1) {
            // Admin Functionalities after signin
            String adminDetails[] = CsvFileHandler.findDataInCsvFile(adminFile, username, 1);
            if (adminDetails != null && adminDetails[1].equals(username) && adminDetails[2].equals(password)) {
                System.out.println("\nWelcome " + adminDetails[0]);
                adminOperations();
                return;
            }

        } else {
            // Student Functionalities after signin
            String studentDetails[] = CsvFileHandler.findDataInCsvFile(studentFile, username, 1);
            if (studentDetails != null && studentDetails[1].equals(username) && studentDetails[2].equals(password)) {
                System.out.println("\nWelcome " + studentDetails[0]);
                studentOperations(studentDetails[3]);
                return;

            }

        }

        System.out.println("\nIncorrect Password");
        while (true) {
            int newChoice;
            System.out.println("\n1. Try Again");
            System.out.println("2. Forgot Password");
            System.out.println("0. Home Menu");
            System.out.print("\nEnter Your Choice ( 0 - 2 ) : ");
            try {
                newChoice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Choice...");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            if (newChoice == 1) {
                SignIn();
                break;
            } else if (newChoice == 2) {
                ForgetPassword(choice);
                break;
            } else if (newChoice == 0) {
                break;
            } else {
                System.out.println("\nEnter Valid Option...");
            }
        }
    }

    void adminOperations() {
        while (true) {
            System.out.println("\n===============================");
            System.out.println("--------- Admin Panel ---------");
            System.out.println("===============================\n");
            System.out.println("1. Add Books ");
            System.out.println("2. View Books ");
            System.out.println("0. Log Out");
            System.out.println("\nEnter Your Choice: ");
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Choice...");
                sc.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    LateFees();
                    displayAllBooks();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid Selection");
                    break;
            }
        }
    }

    void studentOperations(String studId) {
        while (true) {
            System.out.println("\n===================================");
            System.out.println("---------- Student Panel ----------");
            System.out.println("===================================\n");
            System.out.println("1. Issue Books");
            System.out.println("2. Return Books");
            System.out.println("3. View Issued Books");
            System.out.println("4. Display Profile");
            // System.out.println("4. Update");
            System.out.println("0. Log Out");
            System.out.println("\nEnter Your Choice: ");
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Choice...");
                sc.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    issueBook(studId);
                    break;
                case 2:
                    returnBook(studId);
                    break;
                case 3:
                    LateFees(studId);
                    displayIssuedBooks(studId);
                    break;
                case 4:
                    LateFees(studId);
                    displayProfile(studId);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid Selection");
                    break;
            }
        }
    }

    void ForgetPassword(int userType) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        // If Condition For Admin's Forget Password
        if (userType == 1) {
            while (true) {
                System.out.println("\nEnter Your Full Name - ");
                fullName = sc.nextLine();

                int[] index = CsvFileHandler.findRowColumnNumber(adminFile, fullName, FULLNAMECOL);

                if (index[0] != -1 && index[1] != -1) {
                    while (true) {
                        System.out.print("\nEnter Your New Password - ");
                        password = new String(System.console().readPassword());
                        for (int i = 0; i < password.length(); i++) {
                            System.out.print("*");
                        }
                        System.out.println();

                        if (Pattern.matches(passwordPattern, password)) {
                            break;
                        } else {
                            System.out.println(
                                    "\nPassword Must Contain At Least 8 Characters And At Most 20 Characters.");
                            System.out.println("Password Must Contain At Least One Digit.");
                            System.out.println("Password Must Contain At Least One Uppercase Alphabet.");
                            System.out.println("Password Must Contain At Least One Lowercase Alphabet.");
                            System.out.println("Password Must Contain At Least One Special Character.");
                            System.out.println("Password Cannot Contain Space");
                        }
                    }

                    System.out.print("\nConfirm New Password - ");
                    confirmPassword = new String(System.console().readPassword());
                    for (int i = 0; i < password.length(); i++) {
                        System.out.print("*");
                    }

                    if (password.equals(confirmPassword)) {
                        CsvFileHandler.updateCsvFile(adminFile, index[0], 2, password);
                        System.out.println("\n\nPassword Changed Successfully...");
                        break;
                    } else {
                        System.out.println("\n\nBoth Password Must Be Same\nTry Again...");
                        sc.nextLine();
                    }
                } else {
                    System.out.println("\nUser Not Found...\nTry Again...");
                }
            }
        } else {
            // Else Condition For Student's Forget Password
            while (true) {
                System.out.println("\nEnter Your Full Name - ");
                fullName = sc.nextLine();

                int[] index = CsvFileHandler.findRowColumnNumber(studentFile, fullName, FULLNAMECOL);

                if (index[0] != -1 && index[1] != -1) {
                    while (true) {
                        System.out.print("\nEnter Your New Password - ");
                        password = new String(System.console().readPassword());
                        for (int i = 0; i < password.length(); i++) {
                            System.out.print("*");
                        }
                        System.out.println();

                        if (Pattern.matches(passwordPattern, password)) {
                            break;
                        } else {
                            System.out.println(
                                    "\nPassword Must Contain At Least 8 Characters And At Most 20 Characters.");
                            System.out.println("Password Must Contain At Least One Digit.");
                            System.out.println("Password Must Contain At Least One Uppercase Alphabet.");
                            System.out.println("Password Must Contain At Least One Lowercase Alphabet.");
                            System.out.println("Password Must Contain At Least One Special Character.");
                            System.out.println("Password Cannot Contain Space");
                        }
                    }

                    System.out.print("\nConfirm New Password - ");
                    confirmPassword = new String(System.console().readPassword());
                    for (int i = 0; i < password.length(); i++) {
                        System.out.print("*");
                    }

                    if (password.equals(confirmPassword)) {
                        CsvFileHandler.updateCsvFile(studentFile, index[0], 2, password);
                        System.out.println("\n\nPassword Changed Successfully...");
                        break;
                    } else {
                        System.out.println("\n\nBoth Password Must Be Same\nTry Again...");
                        sc.nextLine();
                    }
                } else {
                    System.out.println("\nUser Not Found...\nTry Again...");
                }
            }
        }
    }

    void addBook() {
        String bookId, bookName, bookAuthorName, bookType;
        Date bookIssuedDate;
        Date bookReturnedDate;
        boolean bookStatus;
        String flag = "1";
        while (!flag.equals("0")) {
            displayAllBooks();
            sc.nextLine();
            System.out.println();

            while (true) {
                System.out.println("Enter Book Id - ");
                bookId = sc.nextLine();
                int bookIndex[] = CsvFileHandler.findRowColumnNumber(bookFile, bookId, 0);

                if (bookId.length() == 0)
                    System.out.println("Book ID Cannot Be An Empty Field...");
                else if (bookIndex[0] != -1 && bookIndex[1] != -1)
                    System.out.println("This Book ID Already Exists...");
                else
                    break;
            }

            while (true) {
                System.out.println("Enter Book Name - ");
                bookName = sc.nextLine();
                if (bookName.length() == 0)
                    System.out.println("Book Name Cannot Be An Empty Field...");
                else if (!Pattern.matches("[a-zA-Z ]{" + bookName.length() + "}", bookName))
                    System.out.println("\nBook Name Must Only Contain Characters...");
                else
                    break;
            }
            while (true) {
                System.out.println("Enter Book's Author Name - ");
                bookAuthorName = sc.nextLine();
                if (bookAuthorName.length() == 0)
                    System.out.println("Book's Author Name Cannot Be An Empty Field...");
                else if (!Pattern.matches("[a-zA-Z ]{" + bookAuthorName.length() + "}", bookAuthorName))
                    System.out.println("\nAuthor Name Must Only Contain Characters...");
                else
                    break;
            }
            while (true) {
                System.out.println("Enter Book Type - ");
                bookType = sc.nextLine();
                if (bookType.length() == 0)
                    System.out.println("Book Type Cannot Be An Empty Field...");
                else if (!Pattern.matches("[a-zA-Z ]{" + bookType.length() + "}", bookType))
                    System.out.println("\nBook Type Must Only Contain Characters...");
                else
                    break;
            }
            bookStatus = true;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                bookReturnedDate = bookIssuedDate = dateFormat.parse("0000-00-00");
                Book newBook = new Book(bookId, bookName, bookAuthorName, bookType, bookIssuedDate, bookReturnedDate,
                        bookStatus);

                CsvFileHandler.writeObjectToCsvFile(bookFile, newBook);
            } catch (ParseException e) {
                System.out.println("Error In Parsing The Date.");
            }

            System.out.print("\nDo You Want To Add More Books...\nPress 0 To Exit...\nEnter Your Choice : ");
            flag = sc.nextLine();
        }
    }

    void displayAllBooks() {
        System.out.println(
                "\n=============================================================================================================");
        System.out.println(
                "---------------------------------------------- Books Catalogue ----------------------------------------------");
        System.out.println(
                "=============================================================================================================\n");
        CsvFileHandler.displayBookCsvFile(bookFile, true);
    }

    void displayAvailableBooks() {
        System.out.println(
                "\n=============================================================================================================");
        System.out.println(
                "----------------------------------------- Available Books Catalogue -----------------------------------------");
        System.out.println(
                "=============================================================================================================\n");
        CsvFileHandler.displayBookCsvFile(bookFile, false);
    }

    void issueBook(String studId) {
        String flag = "1";
        String bookId = null;
        while (!flag.equals("0")) {
            displayAvailableBooks();
            sc.nextLine();
            System.out.println();
            System.out.println("Enter Book Id - ");
            bookId = sc.nextLine();
            int bookIndex[] = CsvFileHandler.findRowColumnNumber(bookFile, bookId, 0);
            if (bookIndex[0] == -1 || bookIndex[1] == -1)
                System.out.println("Book Not Found...");
            else {
                Date issueDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(issueDate);
                calendar.add(Calendar.DATE, 15);
                Date returnDate = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String stringReturnDate = dateFormat.format(returnDate);
                String stringIssueDate = dateFormat.format(issueDate);
                CsvFileHandler.updateCsvFile(bookFile, bookIndex[0], 4, stringIssueDate);
                CsvFileHandler.updateCsvFile(bookFile, bookIndex[0], 5, stringReturnDate);
                CsvFileHandler.updateCsvFile(bookFile, bookIndex[0], 6, Boolean.toString(false));
                CsvFileHandler.updateCsvFile(bookFile, bookIndex[0], 7, studId);
                System.out.print("\nBook Issued Successfully...");
            }
            System.out.print("\nDo You Want To Issue More Books...\nPress 0 To Exit...\nEnter Your Choice : ");
            flag = sc.nextLine();
        }
    }

    void displayIssuedBooks(String studId) {
        System.out.println(
                "\n====================================================================================================================");
        System.out.println(
                "---------------------------------------------- Issued Books Catalogue ----------------------------------------------");
        System.out.println(
                "====================================================================================================================\n");
        CsvFileHandler.displayBookCsvFile(bookFile, studId);
    }

    void LateFees(String studId) {
        CsvFileHandler.UpdateLateFees(bookFile, studId);
    }

    void LateFees() {
        try (BufferedReader reader = new BufferedReader(new FileReader(studentFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > 2) {

                    CsvFileHandler.UpdateLateFees(bookFile, columns[3]);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read CSV file: " + e.getMessage());
        }
    }

    public static long calculateLateFees(String stringReturnDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = new Date();
            Date returnDate = dateFormat.parse(stringReturnDate);
            LocalDate localCurrentDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localReturnDate = returnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long daysdiff = ChronoUnit.DAYS.between(localReturnDate, localCurrentDate);

            return daysdiff;

        } catch (ParseException PE) {
            System.out.println(PE.getMessage());
            return -1;
        }
    }

    void displayProfile(String studId) {
        String str[] = CsvFileHandler.findDataInCsvFile(studentFile, studId, STUDENTIDCOL);

        System.out.println("\n===================================");
        System.out.println("--------- Student Profile ---------");
        System.out.println("===================================\n");

        System.out.println("Full Name : " + str[0]);
        System.out.println("Userame : " + str[1]);
        System.out.println("Student Id : " + str[3]);
        System.out.println();

        displayIssuedBooks(studId);
    }

    void returnBook(String studId) {
        String flag = "1";
        String bookId = null;
        while (!flag.equals("0")) {
            displayIssuedBooks(studId);
            sc.nextLine();
            System.out.println();
            System.out.println("Enter Book Id - ");
            bookId = sc.nextLine();
            int bookIndex[] = CsvFileHandler.findRowColumnNumber(bookFile, bookId, 0);
            if (bookIndex[0] == -1 || bookIndex[1] == -1)
                System.out.println("Book Not Found...");
            else {
                String[] str = CsvFileHandler.findDataInCsvFile(bookFile, bookId, 0);
                if (str != null && str[7].equals(studId)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date newDate = dateFormat.parse("0000-00-00");
                        String stringNewDate = dateFormat.format(newDate);
                        CsvFileHandler.updateCsvFile(bookFile, bookIndex[0], 4, stringNewDate);
                        CsvFileHandler.updateCsvFile(bookFile, bookIndex[0], 5, stringNewDate);
                        CsvFileHandler.updateCsvFile(bookFile, bookIndex[0], 6, Boolean.toString(true));
                        CsvFileHandler.updateCsvFile(bookFile, bookIndex[0], 7, "null");
                        CsvFileHandler.updateCsvFile(bookFile, bookIndex[0], 8, Double.toString(0.0));
                        System.out.print("\nBook Returned Successfully...");
                    } catch (ParseException PE) {
                        System.out.println(PE.getMessage());
                    }
                } else
                    System.out.println("This Book Is Not Issued By You...");
            }
            System.out.print("\nDo You Want To Return More Books...\nPress 0 To Exit...\nEnter Your Choice : ");
            flag = sc.nextLine();
        }
    }

    public static void main(String[] args) {
        int choice;
        Scanner input = new Scanner(System.in);
        Main obj = new Main();
        while (true) {
            System.out.println("\n=============================================");
            System.out.println("--------- Library Management System ---------");
            System.out.println("=============================================\n");
            System.out.println("1. Registration ");
            System.out.println("2. Sign In ");
            System.out.println("0. Exit");
            System.out.println("\nEnter Your Choice: ");
            try {
                choice = input.nextInt();
            } catch (InputMismatchException IME) {
                System.out.println("Invalid Choice...");
                input.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    obj.RegisterUser();
                    break;
                case 2:
                    obj.SignIn();
                    break;
                case 0:
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid Selection");
                    break;
            }
        }
    }
}
// Update Book Functionality
// Exception in all choices InputMisMatchException
// Validations