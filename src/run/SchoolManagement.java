package run;

import model.Mark;
import model.Student;
import model.Subject;

import java.text.ParseException;
import java.util.Scanner;

public class SchoolManagement {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        Student[] students = new Student[100];
        Subject[] subjects = new Subject[100];
        Mark[] marks = new Mark[100];
        int studentCount = 0;
        int subjectCount = 0;
        int markCount = 0;

        while (true) {
            System.out.println("************************SCHOOL-MANAGEMENT************************");
            System.out.println("1. Quản lý học sinh");
            System.out.println("2. Quản lý môn học");
            System.out.println("3. Quản lý điểm thi");
            System.out.println("4. Thoát");
            System.out.print("Chọn 1, 2, 3, hoặc 4 để tiếp tục: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    studentManagementMenu(students, studentCount, marks, markCount);
                    break;
                case 2:
                    subjectManagementMenu(subjects, subjectCount, marks, markCount);
                    break;
                case 3:
                    markManagementMenu(students, studentCount, subjects, subjectCount, marks, markCount);
                    break;
                case 4:
                    System.out.println("Kết thúc chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    private static void studentManagementMenu(Student[] students, int studentCount, Mark[] marks, int markCount) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("**********************STUDENT-MANAGEMENT************************");
            System.out.println("1. Thêm mới học sinh");
            System.out.println("2. Hiển thị danh sách tất cả học sinh đã lưu trữ");
            System.out.println("3. Thay đổi thông tin học sinh theo mã id");
            System.out.println("4. Xóa học sinh theo mã id (kiểm tra xem nếu sinh viên có điểm thi thì không xóa được)");
            System.out.println("5. Thoát");
            System.out.print("Chọn 1, 2, 3, 4, hoặc 5 để tiếp tục: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (studentCount < students.length) {
                        students[studentCount] = Student.inputData();
                        studentCount++;
                    } else {
                        System.out.println("Danh sách học sinh đã đầy. Không thể thêm mới.");
                    }
                    break;
                case 2:
                    Student.displayAll(students, studentCount);
                    break;
                case 3:
                    Student.updateStudentById(students, studentCount);
                    break;
                case 4:
                    Student.deleteStudentById(students, studentCount, marks, markCount);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    private static void subjectManagementMenu(Subject[] subjects, int subjectCount, Mark[] marks, int markCount) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("**********************SUBJECT-MANAGEMENT************************");
            System.out.println("1. Thêm mới môn học");
            System.out.println("2. Hiển thị danh sách tất cả môn học đã lưu trữ");
            System.out.println("3. Thay đổi thông tin môn học theo mã id");
            System.out.println("4. Xóa môn học theo mã id (kiểm tra xem nếu môn học có điểm thi thì không xóa được)");
            System.out.println("5. Thoát");
            System.out.print("Chọn 1, 2, 3, 4, hoặc 5 để tiếp tục: ");

            int choice = 0;
            boolean validChoice = false;

            while (!validChoice) {
                try {
                    choice = scanner.nextInt();
                    validChoice = true;
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Lựa chọn phải là một số nguyên. Vui lòng nhập lại.");
                    scanner.next(); // Xóa đầu vào không hợp lệ
                }
            }
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (subjectCount < subjects.length) {
                        subjects[subjectCount] = Subject.inputData(subjects);
                        subjectCount++;
                    } else {
                        System.out.println("Danh sách môn học đã đầy. Không thể thêm mới.");
                    }
                    break;
                case 2:
                    Subject.displayAll(subjects, subjectCount);
                    break;
                case 3:
                    Subject.updateSubjectById(subjects, subjectCount);
                    break;
                case 4:
                    Subject.deleteSubjectById(subjects, subjectCount, marks, markCount);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

    private static void markManagementMenu(
            Student[] students, int studentCount, Subject[] subjects, int subjectCount, Mark[] marks, int markCount) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("**********************MARK-MANAGEMENT************************");
            System.out.println("1. Thêm mới điểm thi cho 1 sinh viên");
            System.out.println("2. Hiển thị danh sách tất cả điểm thi theo thứ tự điểm tăng dần");
            System.out.println("3. Thay đổi điểm theo mã id");
            System.out.println("4. Xóa điểm theo mã id");
            System.out.println("5. Hiển thị danh sách điểm thi theo mã môn học");
            System.out.println("6. Hiển thị đánh giá học lực của từng học sinh theo mã môn học");
            System.out.println("7. Thoát");
            System.out.print("Chọn 1, 2, 3, 4, 5, 6, hoặc 7 để tiếp tục: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (markCount < marks.length) {
                        marks[markCount] = Mark.inputData(students, subjects);
                        markCount++;
                    } else {
                        System.out.println("Danh sách điểm thi đã đầy. Không thể thêm mới.");
                    }
                    break;
                case 2:
                    Mark.displayAll(marks, markCount);
                    break;
                case 3:
                    Mark.updateMarkById(marks, markCount);
                    break;
                case 4:
                    Mark.deleteMarkById(marks, markCount);
                    break;
                case 5:
                    System.out.print("Nhập mã môn học: ");
                    String subjectId = scanner.nextLine();
                    Mark.displayMarksBySubjectId(marks, markCount, subjectId);
                    break;
                case 6:
                    System.out.print("Nhập mã môn học: ");
                    subjectId = scanner.nextLine();
                    Mark.displayStudentPerformanceBySubjectId(marks, markCount, subjectId);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }
}


