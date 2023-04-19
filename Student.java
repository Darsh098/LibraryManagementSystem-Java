
class Student extends User {
    String studentId;

    public Student(String fullname, String username, String password, String studentId) {
        super(fullname, username, password);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}