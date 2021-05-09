public class NQueensMain {
    public static void main(String [] args) {
        int n = Integer.parseInt(args[0]);
        if (n < 4) {
            System.out.println("No possible solution for n=" + n);
            return;
        }

        NQueensIterativeRepair repair = new NQueensIterativeRepair(n);
        System.out.print(repair);

        NQueensExhaustiveSearch search = new NQueensExhaustiveSearch(n);
        System.out.println(search);
    }
}
