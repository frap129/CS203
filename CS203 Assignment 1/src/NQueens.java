public class NQueens {
    public static void main(String [] args) {
        int n = Integer.parseInt(args[0]);
        //NQueensExhaustiveSearch search = new NQueensExhaustiveSearch(n);
        new NQueensIterativeRepair(4);
    }
}
