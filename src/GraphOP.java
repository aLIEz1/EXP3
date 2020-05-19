import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GraphOP {
    Graph graph = new Graph();

    public void InitGraph(int vertexNum) {
        graph.vertexNum = vertexNum;
        graph.head = new VertxNode[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            graph.head[i] = new VertxNode();
            graph.head[i].data = i;
            graph.head[i].firstArc = null;
        }
    }

    public void CreatGraph(int v1, int v2) {
        Creat(v1 - 1, v2 - 1);
//        Creat(v2 - 1, v1 - 1);
    }

    public void CreatGraph(int v1, int v2, int weight) {
        ArcNode arcNode1 = new ArcNode();
        arcNode1.adjVertx = v2 - 1;
        arcNode1.weight = weight;
        arcNode1.nextArc = null;
        ArcNode arcNode2 = graph.head[v1 - 1].firstArc;
        if (arcNode2 == null) {
            graph.head[v1 - 1].firstArc = arcNode1;
        } else {
            while (arcNode2.nextArc != null) {
                arcNode2 = arcNode2.nextArc;
            }
            arcNode2.nextArc = arcNode1;
        }

    }

    public void Rsort() {
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
        visited[v] = 1;
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

    public void TopOrder() {
        int[] inDegree = new int[graph.vertexNum];
        for (int i = 0; i < graph.vertexNum; i++) {
            inDegree[i] = 0;
        }
        for (int i = 0; i < graph.vertexNum; i++) {
            ArcNode arcNode = graph.head[i].firstArc;
            while (arcNode != null) {
                inDegree[arcNode.adjVertx]++;
                arcNode = arcNode.nextArc;
            }
        }
//        TopoByStack(inDegree);
        ToppByQueue(inDegree);
    }

    private void ToppByQueue(int[] inDegree) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();
        for (int i = 0; i < graph.vertexNum; i++) {
            if (inDegree[i] == 0) {
                priorityQueue.add(i);
            }
        }
        while (!priorityQueue.isEmpty()) {
            int j = priorityQueue.poll();
            System.out.print((j + 1) + " ");
            ArcNode arcNode = graph.head[j].firstArc;
            while (arcNode != null) {
                int k = arcNode.adjVertx;
                if (--inDegree[k] == 0) {
                    priorityQueue.add(k);
                }
                arcNode = arcNode.nextArc;
            }

        }
    }

    private void TopoByStack(int[] inDegree) {
        int top = -1;
        for (int i = 0; i < graph.vertexNum; i++) {
            if (inDegree[i] == 0) {
                inDegree[i] = top;
                top = i;
            }
        }
        for (int i = 0; i < graph.vertexNum; i++) {
            if (top == -1) {
                return;
            }
            int j = top;
            top = inDegree[top];
            System.out.print((j + 1) + " ");
            ArcNode arcNode = graph.head[i].firstArc;
            while (arcNode != null) {
                int k = arcNode.adjVertx;
                if (--inDegree[k] == 0) {
                    inDegree[k] = top;
                    top = k;
                }
                arcNode = arcNode.nextArc;
            }
        }
    }

    public void Dijkstra(int v) {
        int u, k;
        int max = 10000;
        ArcNode arcNode;
        int[] dist = new int[graph.vertexNum];
        int[] visited = new int[graph.vertexNum];
        for (int i = 0; i < graph.vertexNum; i++) {
            dist[i] = max;
            visited[i] = 0;
        }
        dist[v] = 0;
        visited[v] = 1;
        arcNode = graph.head[v].firstArc;
        u = v;
        for (int j = 0; j < graph.vertexNum; j++) {
            while (arcNode != null) {
                k = arcNode.adjVertx;
                if (visited[k] != 1 && dist[u] + arcNode.weight < dist[k]) {
                    dist[k] = dist[u] + arcNode.weight;
                }
                arcNode = arcNode.nextArc;
            }
            int lDist = max;
            for (int i = 0; i < graph.vertexNum; i++) {
                if (dist[i] > 0 && dist[i] < lDist && visited[i] == 0) {
                    lDist = dist[i];
                    u = i;
                }
            }
            visited[u] = 1;
            arcNode = graph.head[u].firstArc;

        }
//        int min = 10000;
//        for (int i = 1; i < graph.vertexNum; i++) {
//            System.out.print(dist[i] + " ");
////            if (dist[i] < min) {
////                min = dist[i];
////            }
//        }
//        System.out.print(min);
        System.out.print(dist[graph.vertexNum - 1]);
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
