import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GraphOP op = new GraphOP();
        Scanner in = new Scanner(System.in);
        op.InitGraph(in.nextInt());
        int row = in.nextInt();
        for (int i = 0; i < row; i++) {
            op.CreatGraph(in.nextInt(), in.nextInt());
        }

        op.Rsort();
        op.DFSGraph();
        System.out.println();
        op.BFSGraph(0);
//        op.Display();
    }

}
