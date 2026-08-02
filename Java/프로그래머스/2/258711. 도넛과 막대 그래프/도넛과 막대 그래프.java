class Solution {
    public int[] solution(int[][] edges) {
        int maxNode = 0;
        int[] inDegree = new int[1_000_001];
        int[] outDegree = new int[1_000_001];

        for (int[] edge : edges) {
            outDegree[edge[0]]++;
            inDegree[edge[1]]++;
            maxNode = Math.max(maxNode, Math.max(edge[0], edge[1]));
        }

        int createdNode = 0;

        for (int node = 1; node <= maxNode; node++) {
            if (inDegree[node] == 0 && outDegree[node] >= 2) {
                createdNode = node;
                break;
            }
        }

        int[] parent = new int[maxNode + 1];
        int[] vertexCount = new int[maxNode + 1];
        int[] edgeCount = new int[maxNode + 1];
        boolean[] exists = new boolean[maxNode + 1];

        for (int[] edge : edges) {
            exists[edge[0]] = true;
            exists[edge[1]] = true;
        }

        for (int node = 1; node <= maxNode; node++) {
            parent[node] = node;
        }

        for (int[] edge : edges) {
            if (edge[0] == createdNode) {
                continue;
            }

            union(parent, edge[0], edge[1]);
        }

        for (int node = 1; node <= maxNode; node++) {
            if (node == createdNode || !exists[node]) {
                continue;
            }

            vertexCount[find(parent, node)]++;
        }

        for (int[] edge : edges) {
            if (edge[0] == createdNode) {
                continue;
            }

            edgeCount[find(parent, edge[0])]++;
        }

        int donutCount = 0;
        int barCount = 0;
        int eightCount = 0;

        for (int node = 1; node <= maxNode; node++) {
            if (vertexCount[node] == 0) {
                continue;
            }

            if (edgeCount[node] == vertexCount[node]) {
                donutCount++;
            } else if (edgeCount[node] == vertexCount[node] - 1) {
                barCount++;
            } else {
                eightCount++;
            }
        }

        return new int[]{createdNode, donutCount, barCount, eightCount};
    }

    private int find(int[] parent, int node) {
        if (parent[node] == node) {
            return node;
        }

        parent[node] = find(parent, parent[node]);
        return parent[node];
    }

    private void union(int[] parent, int node1, int node2) {
        int root1 = find(parent, node1);
        int root2 = find(parent, node2);

        if (root1 != root2) {
            parent[root2] = root1;
        }
    }
}