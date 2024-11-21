import java.util.ArrayList;
import java.util.List;

public class CycleDetectionInDirectedGraph {
        private final int vertices;
        private final List<List<Integer>> adjList;

        public CycleDetectionInDirectedGraph(int vertices) {
            this.vertices = vertices;
            adjList = new ArrayList<>();
            for (int i = 0; i < vertices; i++) {
                adjList.add(new ArrayList<>());
            }
        }

        public void addEdge(int source, int destination) {
            adjList.get(source).add(destination);
        }

        public boolean isCyclic() {
            boolean[] visited = new boolean[vertices];
            boolean[] recursionStack = new boolean[vertices];

            for (int i = 0; i < vertices; i++) {
                if (dfs(i, visited, recursionStack)) {
                    return true;
                }
            }
            return false;
        }

        private boolean dfs(int node, boolean[] visited, boolean[] recursionStack) {
            if (recursionStack[node]) {
                return true; // Cycle detected
            }

            if (visited[node]) {
                return false; // Already processed
            }

            visited[node] = true;
            recursionStack[node] = true;

            for (int neighbor : adjList.get(node)) {
                if (dfs(neighbor, visited, recursionStack)) {
                    return true;
                }
            }

            recursionStack[node] = false; // Backtrack
            return false;
        }

        public static void main(String[] args) {
            CycleDetectionInDirectedGraph graph = new CycleDetectionInDirectedGraph(4);

            graph.addEdge(0, 1);
            graph.addEdge(1, 2);
            graph.addEdge(2, 0); // Creates a cycle
            graph.addEdge(3, 1);

            if (graph.isCyclic()) {
                System.out.println("The graph contains a cycle.");
            } else {
                System.out.println("The graph does not contain a cycle.");
            }
        }
    }
