public class BackwardsCount {
    public static void main(String args[]) {
        for (int i = 100; i > 0; i--) {
            if (i % 5 == 0 & i % 3 == 0) {
                System.out.print("\n - Testing");
            } else if (i % 5 == 0) {
                System.out.print("\n - Agile");
            } else if (i % 3 == 0) {
                System.out.print("\n - Software");
            } else {
                System.out.print("\n - Number: " + i);
            }
        }
    }
}