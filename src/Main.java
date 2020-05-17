import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GraphOP op = new GraphOP();
        Scanner in = new Scanner(System.in);
        op.CreatGraph(in.nextInt(), in.nextInt());
//        op.DFSGraph();
//        System.out.println();
        op.BFSGraph(0);
//        op.Display();
    }

}
