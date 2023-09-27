package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Student {
    private static int nextStudentId = 1;

    private int studentId;
    private String studentName;
    private String birthDay;
    private String address;
    private boolean gender;
    private String phone;

    public Student(String studentName, String birthDateStr, String address, boolean gender, String phone)
            throws ParseException {
        this.studentId = nextStudentId++;
        setStudentName(studentName);
        setBirthDay(birthDateStr);
        setAddress(address);
        setGender(gender);
        setPhone(phone);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static Student inputData() throws ParseException {
        Scanner scanner = new Scanner(System.in);

        String studentName;
        String birthDateStr;
        String address;
        boolean gender;
        String phone;

        do {
            System.out.print("Nhập tên học sinh: ");
            studentName = scanner.nextLine();
            if (studentName.trim().isEmpty()) {
                System.out.println("Tên học sinh không được để trống.");
            }
        } while (studentName.trim().isEmpty());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        Date birthDate;

        do {
            System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
            birthDateStr = scanner.nextLine();
            try {
                birthDate = dateFormat.parse(birthDateStr);
                Date today = new Date();
                if (birthDate.after(today)) {
                    System.out.println("Ngày tháng năm sinh không thể trong tương lai.");
                    continue;
                }
                break;
            } catch (ParseException e) {
                System.out.println("Ngày tháng năm sinh không hợp lệ.");
            }
        } while (true);

        do {
            System.out.print("Nhập địa chỉ: ");
            address = scanner.nextLine();
            if (address.trim().isEmpty()) {
                System.out.println("Địa chỉ không được để trống.");
            }
        } while (address.trim().isEmpty());

        do {
            System.out.print("Nhập giới tính (true - Nam, false - Nữ): ");
            String genderStr = scanner.nextLine();
            if (genderStr.equalsIgnoreCase("true")) {
                gender = true;
                break;
            } else if (genderStr.equalsIgnoreCase("false")) {
                gender = false;
                break;
            } else {
                System.out.println("Giới tính không hợp lệ.");
            }
        } while (true);

        String phonePattern = "0\\d{9,10}";
        do {
            System.out.print("Nhập số điện thoại: ");
            phone = scanner.nextLine();
            if (!Pattern.matches(phonePattern, phone)) {
                System.out.println("Số điện thoại không hợp lệ.");
            }
        } while (!Pattern.matches(phonePattern, phone));

        return new Student(studentName, birthDateStr, address, gender, phone);
    }
    public void displayData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Mã học sinh: " + studentId);
        System.out.println("Tên học sinh: " + studentName);
        System.out.println("Ngày sinh: " + birthDay);
        System.out.println("Địa chỉ: " + address);
        System.out.println("Giới tính: " + (gender ? "Nam" : "Nữ"));
        System.out.println("Số điện thoại: " + phone);
    }
    public static void displayAll(Student[] students, int studentCount) {
        boolean isEmpty = true;

        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null) {
                students[i].displayData();
                System.out.println();
                isEmpty = false;
            }
        }

        if (isEmpty) {
            System.out.println("Danh sách học sinh trống.");
        }
    }

    public static void updateStudentById(Student[] students, int studentCount) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập mã học sinh cần cập nhật: ");
        int studentIdToUpdate = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId() == studentIdToUpdate) {
                System.out.println("Nhập thông tin mới cho học sinh:");

                int originalStudentId = students[i].getStudentId();

                Student updatedStudent = inputData();

                updatedStudent.setStudentId(originalStudentId);

                students[i] = updatedStudent;

                System.out.println("Thông tin học sinh đã được cập nhật.");
                return;
            }
        }
        System.out.println("Không tìm thấy học sinh với mã ID đã nhập.");
    }

    public static void deleteStudentById(Student[] students, int studentCount, Mark[] marks, int markCount) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập mã học sinh cần xóa: ");
        int studentIdToDelete = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId() == studentIdToDelete) {
                boolean hasMarks = false;
                for (int j = 0; j < markCount; j++) {
                    if (marks[j].getStudent().getStudentId() == studentIdToDelete) {
                        hasMarks = true;
                        break;
                    }
                }

                if (hasMarks) {
                    System.out.println("Học sinh có điểm thi, không thể xóa.");
                } else {
                    for (int k = i; k < studentCount - 1; k++) {
                        students[k] = students[k + 1];
                    }
                    students[studentCount - 1] = null;
                    studentCount--;
                    System.out.println("Học sinh đã được xóa.");
                }
                return;
            }
        }
        System.out.println("Không tìm thấy học sinh với mã ID đã nhập.");
    }
}
