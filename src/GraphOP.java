import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GraphOP {
    Graph graph = new Graph();

    public void CreatGraph(int vertexNum, int arcs) {
        graph.vertexNum = vertexNum;
        graph.head = new VertxNode[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            graph.head[i] = new VertxNode();
            graph.head[i].data = i;
            graph.head[i].firstArc = null;
        }
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < arcs; i++) {
            int v1 = scanner.nextInt() - 1;
            int v2 = scanner.nextInt() - 1;
            Creat(v1, v2);
            Creat(v2, v1);
        }
        for (int i = 0; i < graph.vertexNum; i++) {
            graph.head[i].firstArc = sort(graph.head[i].firstArc);
        }

    }

    private void Creat(int v1, int v2) {
        ArcNode arcNode1 = new ArcNode();
        arcNode1.adjVertx = v2;
        arcNode1.nextArc = null;
        ArcNode arcNode2 = graph.head[v1].firstArc;
        if (arcNode2 == null) {
            graph.head[v1].firstArc = arcNode1;
        } else {
            ArcNode p = new ArcNode();
            while (arcNode2.nextArc != null) {
                arcNode2 = arcNode2.nextArc;
            }
            arcNode2.nextArc = arcNode1;
        }
    }

    private ArcNode sort(ArcNode head) {
        if (head == null || head.nextArc == null) {
            return head;
        }
        ArcNode pre = head;
        ArcNode cur = head.nextArc;
        ArcNode aux = new ArcNode();
        aux.nextArc = head;
        while (cur != null) {
            if (cur.adjVertx < pre.adjVertx) {
                pre.nextArc = cur.nextArc;
                ArcNode node1 = aux;
                ArcNode node2 = aux.nextArc;
                while (cur.adjVertx > node2.adjVertx) {
                    node1 = node2;
                    node2 = node2.nextArc;
                }
                node1.nextArc = cur;
                cur.nextArc = node2;
                cur = pre.nextArc;
            } else {
                pre = cur;
                cur = cur.nextArc;
            }
        }
        return aux.nextArc;
    }

    private int getFirst(int v) {
        if (v == -1) {
            return -1;
        }
        ArcNode arcNode = graph.head[v].firstArc;
        if (arcNode != null) {
            return arcNode.adjVertx;
        } else {
            return -1;
        }
    }

    private int getNext(int v1, int v2) {
        if (v1 == -1 || v2 == -1) {
            return -1;
        }
        ArcNode arcNode = graph.head[v1].firstArc;
        while (arcNode.adjVertx != v2 && arcNode != null) {
            arcNode = arcNode.nextArc;
        }
        if (arcNode == null) {
            return -1;
        }
        arcNode = arcNode.nextArc;
        if (arcNode == null) {
            return -1;
        }
        return arcNode.adjVertx;
    }

    private void RDFSGraph(int v, int[] visited) {
        System.out.print(v + 1 + " ");
        visited[v] = 1;
        int first = getFirst(v);
        while (first != -1) {
            if (visited[first] == 0) {
                RDFSGraph(first, visited);
            }
            first = getNext(v, first);
        }

    }

    public void DFSGraph() {
        int[] visited = new int[graph.vertexNum];
        for (int i = 0; i < graph.vertexNum; i++) {
            visited[i] = 0;
        }
        RDFSGraph(0, visited);
    }

    public void BFSGraph(int v) {
        int[] visited = new int[graph.vertexNum];
        for (int i = 0; i < graph.vertexNum; i++) {
            visited[i] = 0;
        }
        System.out.print((v + 1) + " ");
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(v);
        while (!queue.isEmpty()) {
            int q = queue.remove();
            int first = getFirst(q);
            while (first != -1) {
                if (visited[first] == 0) {
                    System.out.print((first + 1) + " ");
                    visited[first] = 1;
                    queue.add(first);
                }
                first = getNext(q, first);
            }
        }
    }

    public void Display() {
        ArcNode arcNode;
        for (int i = 0; i < graph.vertexNum; i++) {
            System.out.print(graph.head[i].data + 1);
            arcNode = graph.head[i].firstArc;
            while (arcNode != null) {
                System.out.print("->" + (graph.head[arcNode.adjVertx].data + 1));
                arcNode = arcNode.nextArc;
            }
            System.out.println();
        }
    }

}
