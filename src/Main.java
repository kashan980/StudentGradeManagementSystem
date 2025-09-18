import java.util.Scanner;

public class Main {
    // Arrays to store student data
    static String[] names = new String[50];
    static int[] rollNumbers = new int[50];
    static int[][] marks = new int[50][3]; // 3 subjects
    static int studentCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Student Grade Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Update Marks");
            System.out.println("3. Remove Student");
            System.out.println("4. View All Students");
            System.out.println("5. Search Student");
            System.out.println("6. Highest Scorer");
            System.out.println("7. Class Average");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(sc); break;
                case 2: updateMarks(sc); break;
                case 3: removeStudent(sc); break;
                case 4: viewAllStudents(); break;
                case 5: searchStudent(sc); break;
                case 6: highestScorer(); break;
                case 7: classAverage(); break;
                case 8:
                    System.out.println("Exiting... Total Students = " + studentCount);
                    classAverage();
                    break;
                default: System.out.println("Invalid choice. Try again!");
            }
        } while (choice != 8);

        sc.close();
    }

    // ---------------- Methods ----------------

    // 1. Add Student
    static void addStudent(Scanner sc) {
        if (studentCount >= 50) {
            System.out.println("Cannot add more students. Limit reached!");
            return;
        }

        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();

        // Check duplicate roll number
        for (int i = 0; i < studentCount; i++) {
            if (rollNumbers[i] == roll) {
                System.out.println("Roll number already exists! Try again.");
                return;
            }
        }

        sc.nextLine(); // clear buffer
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        int[] m = new int[3];
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter Marks in Subject " + (i + 1) + ": ");
            m[i] = sc.nextInt();
            if (m[i] < 0 || m[i] > 100) {
                System.out.println("Invalid marks! Must be between 0 and 100.");
                i--; // repeat this subject
            }
        }

        rollNumbers[studentCount] = roll;
        names[studentCount] = name;
        for (int i = 0; i < 3; i++) {
            marks[studentCount][i] = m[i];
        }
        studentCount++;

        System.out.println("Student added successfully!");
    }

    // 2. Update Marks
    static void updateMarks(Scanner sc) {
        System.out.print("Enter Roll No to update: ");
        int roll = sc.nextInt();
        int index = findStudentIndex(roll);

        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("Enter new Marks in Subject " + (i + 1) + ": ");
            int newMarks = sc.nextInt();
            if (newMarks < 0 || newMarks > 100) {
                System.out.println("Invalid marks! Must be between 0 and 100.");
                i--;
            } else {
                marks[index][i] = newMarks;
            }
        }
        System.out.println("Marks updated successfully!");
    }

    // 3. Remove Student
    static void removeStudent(Scanner sc) {
        System.out.print("Enter Roll No to remove: ");
        int roll = sc.nextInt();
        int index = findStudentIndex(roll);

        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        // Shift all students up by one
        for (int i = index; i < studentCount - 1; i++) {
            rollNumbers[i] = rollNumbers[i + 1];
            names[i] = names[i + 1];
            for (int j = 0; j < 3; j++) {
                marks[i][j] = marks[i + 1][j];
            }
        }
        studentCount--;
        System.out.println("Student removed successfully!");
    }

    // 4. View All Students
    static void viewAllStudents() {
        if (studentCount == 0) {
            System.out.println("No students available!");
            return;
        }

        System.out.printf("%-10s %-15s %-10s %-10s %-10s %-10s %-10s%n",
                "Roll No", "Name", "Sub1", "Sub2", "Sub3", "Total", "Average");
        for (int i = 0; i < studentCount; i++) {
            int total = marks[i][0] + marks[i][1] + marks[i][2];
            double avg = total / 3.0;
            System.out.printf("%-10d %-15s %-10d %-10d %-10d %-10d %-10.2f%n",
                    rollNumbers[i], names[i], marks[i][0], marks[i][1], marks[i][2], total, avg);
        }
    }

    // 5. Search Student
    static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll No to search: ");
        int roll = sc.nextInt();
        int index = findStudentIndex(roll);

        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        int total = marks[index][0] + marks[index][1] + marks[index][2];
        double avg = total / 3.0;
        System.out.printf("Roll No: %d, Name: %s, Marks: [%d, %d, %d], Total: %d, Average: %.2f%n",
                rollNumbers[index], names[index],
                marks[index][0], marks[index][1], marks[index][2],
                total, avg);
    }

    // 6. Highest Scorer
    static void highestScorer() {
        if (studentCount == 0) {
            System.out.println("No students available!");
            return;
        }

        int maxIndex = 0;
        int maxTotal = marks[0][0] + marks[0][1] + marks[0][2];

        for (int i = 1; i < studentCount; i++) {
            int total = marks[i][0] + marks[i][1] + marks[i][2];
            if (total > maxTotal) {
                maxTotal = total;
                maxIndex = i;
            }
        }

        double avg = maxTotal / 3.0;
        System.out.printf("Highest Scorer -> Roll No: %d, Name: %s, Total: %d, Average: %.2f%n",
                rollNumbers[maxIndex], names[maxIndex], maxTotal, avg);
    }

    // 7. Class Average
    static void classAverage() {
        if (studentCount == 0) {
            System.out.println("No students available!");
            return;
        }

        int totalMarks = 0;
        int totalSubjects = studentCount * 3;

        for (int i = 0; i < studentCount; i++) {
            totalMarks += marks[i][0] + marks[i][1] + marks[i][2];
        }

        double classAvg = totalMarks / (double) totalSubjects;
        System.out.printf("Class Average: %.2f%n", classAvg);
    }

    // Helper: Find Student by Roll No
    static int findStudentIndex(int roll) {
        for (int i = 0; i < studentCount; i++) {
            if (rollNumbers[i] == roll) return i;
        }
        return -1;
    }
}
