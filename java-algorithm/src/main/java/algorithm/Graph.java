package algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Graph {

    private List<Vertex> vertexList;
    private Map<Vertex, List<Edge>> verEdgeListMap;

    public Graph(List<Vertex> vertexList, Map<Vertex, List<Edge>> verEdgeListMap) {
        super();
        this.vertexList = vertexList;
        this.verEdgeListMap = verEdgeListMap;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }


    public Map<Vertex, List<Edge>> getVerEdgelistMap() {
        return verEdgeListMap;
    }

    public void setVerEdgelistMap(Map<Vertex, List<Edge>> verEdgeListMap) {
        this.verEdgeListMap = verEdgeListMap;
    }


    static class Edge {
        private Vertex startVertex;
        private Vertex endVertex;
        private int weight;

        public Edge(Vertex startVertex, Vertex endVertex, int weight) {
            super();
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.weight = weight;
        }

        public Edge() {
        }

        public Vertex getStartVertex() {
            return startVertex;
        }

        public void setStartVertex(Vertex startVertex) {
            this.startVertex = startVertex;
        }

        public Vertex getEndVertex() {
            return endVertex;
        }

        public void setEndVertex(Vertex endVertex) {
            this.endVertex = endVertex;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

    static class Vertex {
        private final static int infinite_dis = Integer.MAX_VALUE;

        /**
         * node name
         */
        private String name;
        /**
         * before node know
         */
        private boolean known;
        /**
         *  none distance
         */
        private int adjuDist;
        /**
         *  Shortest path of parent node
         */
        private Vertex parent;

        public Vertex() {
            this.known = false;
            this.adjuDist = infinite_dis;
            this.parent = null;
        }

        public Vertex(String name) {
            this.known = false;
            this.adjuDist = infinite_dis;
            this.parent = null;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isKnown() {
            return known;
        }

        public void setKnown(boolean known) {
            this.known = known;
        }

        public int getAdjuDist() {
            return adjuDist;
        }

        public void setAdjuDist(int adjuDist) {
            this.adjuDist = adjuDist;
        }

        public Vertex getParent() {
            return parent;
        }

        public void setParent(Vertex parent) {
            this.parent = parent;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Vertex)) {
                throw new ClassCastException("an object to compare with a Vertext must be Vertex");
            }
            if (this.name == null) {
                throw new NullPointerException("name of Vertex to be compared cannot be null");
            }
            return this.name.equals(((Vertex) obj).name);
        }
    }

    public void setRoot(Vertex v) {
        v.setParent(null);
        v.setAdjuDist(0);
    }


    /**
     * search shortest Length
     * @param startIndex dijkstra start index
     * @param destIndex  dijkstra end index
     */
    public void dijkstraTravasal(int startIndex, int destIndex) {
        Vertex start = vertexList.get(startIndex);
        Vertex destEnd = vertexList.get(destIndex);
        Vertex dest = vertexList.get(destIndex);
        String path = "[" + dest.getName() + "]";

        setRoot(start);
        minChildren(vertexList.get(startIndex));

        int shortestLength = dest.getAdjuDist();

        while ((dest.getParent() != null) && (!dest.equals(start))  ) {
            path = "[" + dest.getParent().getName() + "] --> " + path;
            dest = dest.getParent();
            if (dest.equals(destEnd)) {
                break;
            }
        }

        System.out.println("[" + vertexList.get(startIndex).getName() + "] to [" + vertexList.get(destIndex).getName() + "] dijkstra shortest path :: " + path);
        System.out.println("shortest length::" + shortestLength);
    }

    public void dijkstraTravasal(List<Integer> destIndex) {
        if (destIndex.size() == 0) {
            return ;
        }
        for (int i = 0; (i + 1) < destIndex.size(); i++) {
            int startIndex = destIndex.get(i);
            int nextIndex = destIndex.get(i + 1);
            dijkstraTravasal(startIndex, nextIndex);
        }
    }

    /**
     * straight line match
     * @param destIndex
     */
    public void straightTravasal(List<Integer> destIndex) {
        if (destIndex.size() == 0) {
            return ;
        }
        int dist = 0;
        for (int i = 0; (i + 1) < destIndex.size(); i++) {
            int startIndex = destIndex.get(i);
            int nextIndex = destIndex.get(i + 1);

            Vertex start = vertexList.get(startIndex);
            Vertex dest = vertexList.get(nextIndex);
            List<Edge> list = verEdgeListMap.get(start).stream()
                    .filter(edge -> edge.getStartVertex().equals(start) && edge.getEndVertex().equals(dest))
                    .collect(Collectors.toList());
            if (list.size() == 0) {
                System.out.println("NO SUCH ROUTE");
                return;
            }
            dist += list.get(0).getWeight();
        }
        System.out.println(dist);
    }

    /**
     * count line
     * @param startIndex
     * @param destIndex
     */
    public void countTravasal(int startIndex, int destIndex) {

    }

    /**
     * max count line
     * @param startIndex
     * @param destIndex
     */
    public void maxCountTravasal(int startIndex, int destIndex) {

    }

    /**
     * 从初始节点开始递归更新邻接表
     *
     *  step1 如果子节点之前未知，则把当前子节点假如更新列表
     *  step2 更新每一个子节点
     * @param v
     */
    private void minChildren(Vertex v) {
        if (v == null) {
            return;
        }
        if (verEdgeListMap.get(v) == null || verEdgeListMap.get(v).size() == 0) {
            return;
        }

        List<Vertex> childrenList = new LinkedList<Vertex>();
        for (Edge e : verEdgeListMap.get(v)) {
            Vertex childVertex = e.getEndVertex();

            if (!childVertex.isKnown()) {
                childVertex.setKnown(true);
                childVertex.setAdjuDist(v.getAdjuDist() + e.getWeight());
                childVertex.setParent(v);
                childrenList.add(childVertex);
            }

            int nowDist = v.getAdjuDist() + e.getWeight();
            if (nowDist >= childVertex.getAdjuDist()) {
                continue;
            } else {
                childVertex.setAdjuDist(nowDist);
                childVertex.setParent(v);
            }
        }

        for (Vertex vc : childrenList) {
            minChildren(vc);
        }
    }


    private void clear() {

    }
}
