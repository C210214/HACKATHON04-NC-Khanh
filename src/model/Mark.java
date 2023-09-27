package model;

import java.util.Scanner;

public class Mark {
    private static int nextMarkId = 1;

    private int markId;
    private Student student;
    private Subject subject;
    private double point;

    public Mark(int markId, Student student, Subject subject, double point) {
        this.markId = markId;
        this.student = student;
        this.subject = subject;
        this.point = point;
    }

    public int getMarkId() {
        return markId;
    }

    public void setMarkId(int markId) {
        this.markId = markId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public static Mark inputData(Student[] students, Subject[] subjects) {
        Scanner scanner = new Scanner(System.in);

        int markId = nextMarkId++;
        Student selectedStudent = null;
        Subject selectedSubject = null;
        double point = 0.0;

        boolean validInput = false;

        if (students == null || subjects == null) {
            System.out.println("Lỗi: Danh sách học sinh hoặc danh sách môn học chưa được khởi tạo.");
            return null;
        }

        while (!validInput) {
            System.out.println("Danh sách học sinh:");
            for (Student student : students) {
                if (student != null) {
                    System.out.println("ID: " + student.getStudentId() + ", Tên: " + student.getStudentName());
                }
            }

            int studentId = 0;
            boolean validStudentId = false;

            while (!validStudentId) {
                try {
                    System.out.print("Nhập ID của học sinh: ");
                    studentId = scanner.nextInt();
                    validStudentId = true;
                } catch (java.util.InputMismatchException e) {
                    System.out.println("ID của học sinh phải là một số nguyên. Vui lòng nhập lại.");
                    scanner.next(); // Xóa đầu vào không hợp lệ
                }
            }

            for (Student student : students) {
                if (student != null && student.getStudentId() == studentId) {
                    selectedStudent = student;
                    break;
                }
            }

            if (selectedStudent == null) {
                System.out.println("Không tìm thấy học sinh với ID đã nhập. Vui lòng nhập lại.");
                continue; // Yêu cầu người dùng nhập lại
            }

            System.out.println("Danh sách môn học:");
            for (Subject subject : subjects) {
                if (subject != null) {
                    System.out.println("ID: " + subject.getSubjectId() + ", Tên: " + subject.getSubjectName());
                }
            }

            System.out.print("Nhập ID của môn học: ");
            String subjectId = scanner.next();

            for (Subject subject : subjects) {
                if (subject != null && subject.getSubjectId().equals(subjectId)) {
                    selectedSubject = subject;
                    break;
                }
            }

            if (selectedSubject == null) {
                System.out.println("Không tìm thấy môn học với ID đã nhập. Vui lòng nhập lại.");
                continue;
            }

            boolean validPoint = false;

            while (!validPoint) {
                try {
                    System.out.print("Nhập điểm số (từ 0 đến 10): ");
                    point = scanner.nextDouble();

                    if (point < 0 || point > 10) {
                        System.out.println("Điểm số phải nằm trong khoảng từ 0 đến 10. Vui lòng nhập lại.");
                    } else {
                        validPoint = true;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Điểm số phải là một số thực. Vui lòng nhập lại.");
                    scanner.next(); // Xóa đầu vào không hợp lệ
                }
            }

            validInput = true;
        }

        return new Mark(markId, selectedStudent, selectedSubject, point);
    }

    public void displayData() {
        System.out.println("ID Điểm: " + markId);
        System.out.println("Tên học sinh: " + student.getStudentName());
        System.out.println("Tên môn học: " + subject.getSubjectName());
        System.out.println("Điểm số: " + point);
    }

    public static void displayAll(Mark[] marks, int markCount) {
        if (markCount == 0) {
            System.out.println("Danh sách điểm thi trống.");
        } else {
            System.out.println("Danh sách điểm thi:");
            for (int i = 0; i < markCount; i++) {
                if (marks[i] != null) {
                    marks[i].displayData();
                    System.out.println();
                }
            }
        }
    }

    public static void updateMarkById(Mark[] marks, int markCount) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập mã điểm cần cập nhật: ");
        int markIdToUpdate = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < markCount; i++) {
            if (marks[i].getMarkId() == markIdToUpdate) {
                System.out.print("Nhập điểm mới (từ 0 đến 10): ");
                double newPoint = scanner.nextDouble();
                marks[i].setPoint(newPoint);
                System.out.println("Điểm đã được cập nhật.");
                return;
            }
        }
        System.out.println("Không tìm thấy điểm với mã ID đã nhập.");
    }

    public static void deleteMarkById(Mark[] marks, int markCount) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập mã điểm cần xóa: ");
        int markIdToDelete = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng newline

        for (int i = 0; i < markCount; i++) {
            if (marks[i].getMarkId() == markIdToDelete) {
                for (int k = i; k < markCount - 1; k++) {
                    marks[k] = marks[k + 1];
                }
                marks[markCount - 1] = null;
                markCount--;
                System.out.println("Điểm đã được xóa.");
                return;
            }
        }
        System.out.println("Không tìm thấy điểm với mã ID đã nhập.");
    }

    public static void displayMarksBySubjectId(Mark[] marks, int markCount, String subjectId) {
        System.out.println("Danh sách điểm thi cho môn học có mã ID: " + subjectId);
        boolean found = false;

        for (int i = 0; i < markCount; i++) {
            if (marks[i].getSubject().getSubjectId().equals(subjectId)) {
                marks[i].displayData();
                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy điểm cho môn học có mã ID: " + subjectId);
        }
    }

    public static void displayStudentPerformanceBySubjectId(Mark[] marks, int markCount, String subjectId) {
        System.out.println("Đánh giá học lực của học sinh cho môn học có mã ID: " + subjectId);
        boolean found = false;

        for (int i = 0; i < markCount; i++) {
            if (marks[i].getSubject().getSubjectId().equals(subjectId)) {
                double point = marks[i].getPoint();
                String performance = getPerformance(point);

                System.out.println("Học sinh: " + marks[i].getStudent().getStudentName() + ", Điểm: " + point + ", Đánh giá: " + performance);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy điểm cho môn học có mã ID: " + subjectId);
        }
    }

    private static String getPerformance(double point) {
        if (point < 5) {
            return "Yếu";
        } else if (point <= 6.5) {
            return "Trung bình";
        } else if (point <= 8) {
            return "Khá";
        } else if (point <= 9) {
            return "Giỏi";
        } else {
            return "Xuất sắc";
        }
    }
}
