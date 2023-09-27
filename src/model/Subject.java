package model;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Subject {
    private String subjectId;
    private String subjectName;

    public Subject(String subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public static Subject inputData(Subject[] subjects) {
        Scanner scanner = new Scanner(System.in);

        String subjectId;
        do {
            System.out.print("Nhập mã môn học (MHxxx): ");
            subjectId = scanner.nextLine();
            if (!isValidSubjectId(subjectId)) {
                System.out.println("Mã môn học không hợp lệ. Vui lòng nhập lại.");
            } else if (!isUniqueSubjectId(subjectId, subjects)) {
                System.out.println("Mã môn học đã tồn tại. Vui lòng nhập lại.");
            }
        } while (!isValidSubjectId(subjectId) || !isUniqueSubjectId(subjectId, subjects));

        String subjectName;
        do {
            System.out.print("Nhập tên môn học: ");
            subjectName = scanner.nextLine();
            if (subjectName.trim().isEmpty() || !isUniqueSubjectName(subjectName, subjects)) {
                System.out.println("Tên môn học không hợp lệ hoặc đã tồn tại. Vui lòng nhập lại.");
            }
        } while (subjectName.trim().isEmpty() || !isUniqueSubjectName(subjectName, subjects));

        return new Subject(subjectId, subjectName);
    }

    private static boolean isValidSubjectId(String subjectId) {
        return Pattern.matches("MH\\d{3}", subjectId);
    }

    private static boolean isUniqueSubjectId(String subjectId, Subject[] subjects) {
        for (int i = 0; i < subjects.length; i++) {
            if (subjects[i] != null && subjects[i].getSubjectId().equals(subjectId)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isUniqueSubjectName(String subjectName, Subject[] subjects) {
        for (int i = 0; i < subjects.length; i++) {
            if (subjects[i] != null && subjectName.equals(subjects[i].getSubjectName())) {
                return false;
            }
        }
        return true;
    }

    public void displayData() {
        System.out.println("Mã môn học: " + subjectId);
        System.out.println("Tên môn học: " + subjectName);
    }

    public static void displayAll(Subject[] subjects, int subjectCount) {
        if (subjectCount == 0) {
            System.out.println("Danh sách môn học trống.");
        } else {
            System.out.println("Danh sách môn học:");
            for (int i = 0; i < subjectCount; i++) {
                if (subjects[i] != null) {
                    subjects[i].displayData();
                    System.out.println();
                }
            }
        }
    }

    public static void updateSubjectById(Subject[] subjects, int subjectCount) {
        Scanner scanner = new Scanner(System.in);

        String subjectIdToUpdate;
        boolean found = false;

        do {
            System.out.print("Nhập mã môn học cần cập nhật: ");
            subjectIdToUpdate = scanner.nextLine();

            for (int i = 0; i < subjectCount; i++) {
                if (subjects[i].getSubjectId().equals(subjectIdToUpdate)) {
                    found = true;
                    System.out.println("Nhập thông tin mới cho môn học:");
                    subjects[i] = inputData(subjects);
                    System.out.println("Thông tin môn học đã được cập nhật.");
                    break;
                }
            }

            if (!found) {
                System.out.println("Không tìm thấy môn học với mã ID đã nhập. Vui lòng nhập lại.");
            }
        } while (!found);
    }

    public static void deleteSubjectById(Subject[] subjects, int subjectCount, Mark[] marks, int markCount) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập mã môn học cần xóa: ");
        String subjectIdToDelete = scanner.nextLine();

        for (int i = 0; i < subjectCount; i++) {
            if (subjects[i].getSubjectId().equals(subjectIdToDelete)) {
                boolean hasMarks = false;
                for (int j = 0; j < markCount; j++) {
                    if (marks[j].getSubject().getSubjectId().equals(subjectIdToDelete)) {
                        hasMarks = true;
                        break;
                    }
                }

                if (hasMarks) {
                    System.out.println("Môn học có điểm thi, không thể xóa.");
                } else {
                    for (int k = i; k < subjectCount - 1; k++) {
                        subjects[k] = subjects[k + 1];
                    }
                    subjects[subjectCount - 1] = null;
                    subjectCount--;
                    System.out.println("Môn học đã được xóa.");
                }
                return;
            }
        }
        System.out.println("Không tìm thấy môn học với mã ID đã nhập.");
    }
}
