package algorithm;

import java.util.*;

import algorithm.Graph.*;

/**
 * 测试用main方法
 *
 * @author wuhui.wwh
 */
public class TestGraph {
    public static void main(String[] args) {
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");
        Vertex D = new Vertex("D");
        Vertex E = new Vertex("E");

        List<Vertex> verList = new LinkedList<Vertex>();
        verList.add(A);
        verList.add(B);
        verList.add(C);
        verList.add(D);
        verList.add(E);

        Map<Vertex, List<Edge>> vertexEdgeListMap = new HashMap<Vertex, List<Edge>>();

        List<Edge> AList = new LinkedList<Edge>();
        AList.add(new Edge(A, B, 5));
        AList.add(new Edge(A, D, 5));
        AList.add(new Edge(A, E, 7));


        List<Edge> BList = new LinkedList<Edge>();
        BList.add(new Edge(B, C, 4));

        List<Edge> CList = new LinkedList<Edge>();
        CList.add(new Edge(C, D, 8));
        CList.add(new Edge(C, E, 2));

        List<Edge> DList = new LinkedList<Edge>();
        DList.add(new Edge(D, C, 8));
        DList.add(new Edge(D, E, 6));

        List<Edge> EList = new LinkedList<Edge>();
        EList.add(new Edge(E, B, 3));

        vertexEdgeListMap.put(A, AList);
        vertexEdgeListMap.put(B, BList);
        vertexEdgeListMap.put(C, CList);
        vertexEdgeListMap.put(D, DList);
        vertexEdgeListMap.put(E, EList);

        List<Integer> list = new ArrayList<Integer>();
        Graph g = new Graph(verList, vertexEdgeListMap);

        g.straightTravasal(Arrays.asList(0,1,2));
        g.straightTravasal(Arrays.asList(0,3));
        g.straightTravasal(Arrays.asList(0,3,2));
        g.straightTravasal(Arrays.asList(0,4,1,2,3));
        g.straightTravasal(Arrays.asList(0,4,3));

        g.dijkstraTravasal(0,2);
        g.dijkstraTravasal(1,1);
        g.dijkstraTravasal(1,1);
//        list.add(0);
//        list.add(3);
//        list.add(2);
//        g.dijkstraTravasal(list);
//        list.clear();
//        list.add(0);
//        list.add(4);
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        //A-E-B-C-D
//        g.dijkstraTravasal(list);
//
//        list.clear();
//        list.add(0);
//        list.add(4);
//        list.add(3);
//        g.dijkstraTravasal(list);
    }
}