import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    Scanner sc = new Scanner(System.in);
    int choice;
    String fullName, username, password, confirmPassword, studentId;
    String studentFile = "user.csv";
    String adminFile = "Admin.csv";

    // Here Username is the keys
    HashMap<String, Student> studentMap = new HashMap<String, Student>();
    HashMap<String, Admin> adminMap = new HashMap<String, Admin>();

    void RegisterUser() {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

        System.out.println("\n============");
        System.out.println("Registration");
        System.out.println("============");
        System.out.println("1. Admin");
        System.out.println("2. Student");
        System.out.println("0. Home");

        System.out.println("Enter Your Choice : ");
        choice = sc.nextInt();

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

                if (studentId.length() == 0)
                    System.out.println("Name Cannot Be An Empty Field...");
                else if (!Pattern.matches("[0-9]{" + studentId.length() + "}", studentId))
                    System.out.println("\nName Must Only Contain Numbers...");
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
            else if (choice == 1 && adminMap.containsKey(username))
                System.out.println("This Username Is Already In Use.");
            // Critical Section - Check This Condition After Running
            else if (choice == 2 && studentMap.containsKey(username))
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
                adminMap.put(username, admin);
            } else {
                Student student = new Student(fullName, username, password, studentId);
                studentMap.put(username, student);
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
        System.out.println("=======");
        System.out.println("1. Admin");
        System.out.println("2. Student");
        System.out.println("0. Home");
        System.out.println("Enter Your Choice : ");
        choice = sc.nextInt();

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
            // Our Code Goes Here For Admin Login
            Admin admin = adminMap.get(username);
            if (admin != null && admin.getUsername().equals(username)
                    && admin.getPassword().equals(password)) {
                System.out.println("\nWelcome " + admin.getFullname());
                // Admin Functionalities after signin
                return;

            }
            // else
            // {
            // System.out.println("\nIncorrect Password");
            // while (true) {
            // System.out.println("\n1. Try Again");
            // System.out.println("2. Forgot Password");
            // System.out.println("0. Home Menu");
            // System.out.print("\nEnter Your Choice ( 0 - 2 ) : ");
            // choice = sc.nextInt();
            // sc.nextLine();

            // if (choice == 1) {
            // SignIn();
            // break;
            // } else if (choice == 2) {
            // ForgetPassword(1);
            // break;
            // } else if (choice == 0) {
            // break;
            // } else {
            // System.out.println("\nEnter Valid Option...");
            // }
            // }
            // }
        } else {
            // Our Code Goes Here Student Login
            // Student student = studentMap.get(username);
            // Student Functionalities after signin
            String studentDetails[] = CsvFileHandler.findDataInCsvFile(studentFile, username);
            if (studentDetails[1].equals(username) && studentDetails[2].equals(password)) {
                System.out.println("\nWelcome " + studentDetails[0]);
                return;
                // if (student != null && student.getUsername().equals(username)
                // && student.getPassword().equals(password)) {
                // System.out.println("\nWelcome " + student.getFullname());

            } else
                System.out.println("Didn't match");
            // else
            // {
            // System.out.println("\nIncorrect Password");
            // while (true) {
            // System.out.println("\n1. Try Again");
            // System.out.println("2. Forgot Password");
            // System.out.println("0. Home Menu");
            // System.out.print("\nEnter Your Choice ( 0 - 2 ) : ");
            // choice = sc.nextInt();
            // sc.nextLine();

            // if (choice == 1) {
            // SignIn();
            // break;
            // } else if (choice == 2) {
            // ForgetPassword(2);
            // break;
            // } else if (choice == 0) {
            // break;
            // } else {
            // System.out.println("\nEnter Valid Option...");
            // }
            // }
            // }
        }

        System.out.println("\nIncorrect Password");
        while (true) {
            System.out.println("\n1. Try Again");
            System.out.println("2. Forgot Password");
            System.out.println("0. Home Menu");
            System.out.print("\nEnter Your Choice ( 0 - 2 ) : ");
            int newChoice = sc.nextInt();
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

    void ForgetPassword(int userType) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        // If Condition For Faculty's Forget Password
        if (userType == 1) {
            // Faculty u = null;
            while (true) {
                // u = null;
                System.out.println("\nEnter Your Full Name - ");
                fullName = sc.nextLine();

                int[] index = CsvFileHandler.findRowColumnNumber(adminFile, fullName, 0);

                // for (Faculty user : facultiesList) {
                // if (fullName.equals(user.getFullName())) {
                // u = user;
                // break;
                // }
                // }

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
                        // u.setPassword(password);
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
            // Student u = null;
            while (true) {
                // u = null;
                System.out.println("\nEnter Your Full Name - ");
                fullName = sc.nextLine();

                int[] index = CsvFileHandler.findRowColumnNumber(studentFile, fullName, 0);

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
                        // u.setPassword(password);
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

    public static void main(String[] args) {
        int choice;
        Scanner input = new Scanner(System.in);
        Main obj = new Main();
        while (true) {
            System.out.println("\n=============================================");
            System.out.println("--------- Library Management System ---------");
            System.out.println("=============================================");
            System.out.println("1. Registration ");
            System.out.println("2. Sign In ");
            System.out.println("0. Exit");
            System.out.println("Enter Your Choice: ");
            choice = input.nextInt();
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
                    System.out.println("Invalid Selection :");
                    break;
            }
        }
    }
}
