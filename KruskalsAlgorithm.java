import java.util.*;

class Edge implements Comparable<Edge> {
        int source, destination, weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight; // Sort edges by weight
        }
    }

    class KruskalsAlgorithm {
        private final int vertices;
        private final List<Edge> edges;

        public KruskalsAlgorithm(int vertices) {
            this.vertices = vertices;
            edges = new ArrayList<>();
        }

        public void addEdge(int source, int destination, int weight) {
            edges.add(new Edge(source, destination, weight));
        }

        private int findParent(int vertex, int[] parent) {
            if (parent[vertex] != vertex) {
                parent[vertex] = findParent(parent[vertex], parent); // Path compression
            }
            return parent[vertex];
        }

        private void union(int u, int v, int[] parent, int[] rank) {
            int rootU = findParent(u, parent);
            int rootV = findParent(v, parent);

            if (rootU != rootV) {
                if (rank[rootU] > rank[rootV]) {
                    parent[rootV] = rootU;
                } else if (rank[rootU] < rank[rootV]) {
                    parent[rootU] = rootV;
                } else {
                    parent[rootV] = rootU;
                    rank[rootU]++;
                }
            }
        }

        public void findMST() {
            Collections.sort(edges); // Sort edges by weight

            int[] parent = new int[vertices];
            int[] rank = new int[vertices];

            for (int i = 0; i < vertices; i++) {
                parent[i] = i; // Initialize each vertex's parent to itself
                rank[i] = 0;
            }

            List<Edge> mst = new ArrayList<>();
            int mstWeight = 0;

            for (Edge edge : edges) {
                int rootU = findParent(edge.source, parent);
                int rootV = findParent(edge.destination, parent);

                if (rootU != rootV) { // If adding this edge doesn't form a cycle
                    mst.add(edge);
                    mstWeight += edge.weight;
                    union(rootU, rootV, parent, rank);
                }
            }

            System.out.println("Minimum Spanning Tree Edges:");
            for (Edge edge : mst) {
                System.out.println("Source: " + edge.source + " - Destination: " + edge.destination + " - Weight: " + edge.weight);
            }
            System.out.println("Total Weight of MST: " + mstWeight);
        }

        public static void main(String[] args) {
            KruskalsAlgorithm graph = new KruskalsAlgorithm(6);

            graph.addEdge(0, 1, 4);
            graph.addEdge(0, 2, 4);
            graph.addEdge(1, 2, 2);
            graph.addEdge(1, 3, 6);
            graph.addEdge(2, 3, 8);
            graph.addEdge(3, 4, 9);
            graph.addEdge(4, 5, 10);
            graph.addEdge(3, 5, 7);

            graph.findMST();
        }
    }
