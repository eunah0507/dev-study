import java.util.*;

class Solution {
    private static final int INF = Integer.MAX_VALUE;

    private static class Point {
        long x;
        long y;

        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Point)) {
                return false;
            }

            Point point = (Point) object;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Node {
        long x;
        long y;
        int limit = INF;
        List<Integer> next = new ArrayList<>();

        Node(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Road {
        long x1;
        long y1;
        long x2;
        long y2;
        int limit;
        List<Integer> points = new ArrayList<>();

        Road(int[] information) {
            x1 = information[0];
            y1 = information[1];
            x2 = information[2];
            y2 = information[3];
            limit = information[4];
        }

        boolean isHorizontal() {
            return y1 == y2;
        }

        boolean contains(long x, long y) {
            return x1 <= x && x <= x2 && y1 <= y && y <= y2
                    && (isHorizontal() ? y == y1 : x == x1);
        }
    }

    private final Map<Point, Integer> nodeMap = new HashMap<>();
    private final List<Node> nodes = new ArrayList<>();

    public int[] solution(int[][] city, int[][] road) {
        Road[] roads = new Road[road.length];

        for (int i = 0; i < road.length; i++) {
            roads[i] = new Road(road[i]);

            addPoint(roads[i], roads[i].x1, roads[i].y1);
            addPoint(roads[i], roads[i].x2, roads[i].y2);

            long cameraX = (roads[i].x1 + roads[i].x2) / 2;
            long cameraY = (roads[i].y1 + roads[i].y2) / 2;
            int cameraNode = addPoint(roads[i], cameraX, cameraY);
            nodes.get(cameraNode).limit = Math.min(nodes.get(cameraNode).limit, roads[i].limit);
        }

        for (int i = 0; i < roads.length; i++) {
            for (int j = i + 1; j < roads.length; j++) {
                addIntersection(roads[i], roads[j]);
            }
        }

        int[] cityNode = new int[city.length];

        for (int i = 0; i < city.length; i++) {
            cityNode[i] = getNode(city[i][0], city[i][1]);

            for (Road currentRoad : roads) {
                if (currentRoad.contains(city[i][0], city[i][1])) {
                    currentRoad.points.add(cityNode[i]);
                }
            }
        }

        for (Road currentRoad : roads) {
            connectRoad(currentRoad);
        }

        int[] maximumSpeed = getMaximumSpeed(cityNode[0]);
        int[] answer = new int[city.length - 1];

        for (int i = 1; i < city.length; i++) {
            answer[i - 1] = maximumSpeed[cityNode[i]] == INF
                    ? 0
                    : maximumSpeed[cityNode[i]];
        }

        return answer;
    }

    private int getNode(long x, long y) {
        Point point = new Point(x, y);
        Integer nodeNumber = nodeMap.get(point);

        if (nodeNumber != null) {
            return nodeNumber;
        }

        int newNodeNumber = nodes.size();
        nodeMap.put(point, newNodeNumber);
        nodes.add(new Node(x, y));

        return newNodeNumber;
    }

    private int addPoint(Road road, long x, long y) {
        int nodeNumber = getNode(x, y);
        road.points.add(nodeNumber);
        return nodeNumber;
    }

    private void addIntersection(Road first, Road second) {
        if (first.isHorizontal() && second.isHorizontal()) {
            if (first.y1 != second.y1) {
                return;
            }

            long left = Math.max(first.x1, second.x1);
            long right = Math.min(first.x2, second.x2);

            if (left == right) {
                int nodeNumber = getNode(left, first.y1);
                first.points.add(nodeNumber);
                second.points.add(nodeNumber);
            }

            return;
        }

        if (!first.isHorizontal() && !second.isHorizontal()) {
            if (first.x1 != second.x1) {
                return;
            }

            long bottom = Math.max(first.y1, second.y1);
            long top = Math.min(first.y2, second.y2);

            if (bottom == top) {
                int nodeNumber = getNode(first.x1, bottom);
                first.points.add(nodeNumber);
                second.points.add(nodeNumber);
            }

            return;
        }

        Road horizontal = first.isHorizontal() ? first : second;
        Road vertical = first.isHorizontal() ? second : first;

        long x = vertical.x1;
        long y = horizontal.y1;

        if (horizontal.x1 <= x && x <= horizontal.x2
                && vertical.y1 <= y && y <= vertical.y2) {
            int nodeNumber = getNode(x, y);
            first.points.add(nodeNumber);
            second.points.add(nodeNumber);
        }
    }

    private void connectRoad(Road road) {
        if (road.isHorizontal()) {
            road.points.sort((first, second) ->
                    Long.compare(nodes.get(first).x, nodes.get(second).x));
        } else {
            road.points.sort((first, second) ->
                    Long.compare(nodes.get(first).y, nodes.get(second).y));
        }

        int previous = -1;

        for (int current : road.points) {
            if (current == previous) {
                continue;
            }

            if (previous != -1) {
                nodes.get(previous).next.add(current);
                nodes.get(current).next.add(previous);
            }

            previous = current;
        }
    }

    private int[] getMaximumSpeed(int start) {
        int[] maximumSpeed = new int[nodes.size()];
        PriorityQueue<int[]> queue = new PriorityQueue<>(
                (first, second) -> Integer.compare(second[1], first[1])
        );

        maximumSpeed[start] = INF;
        queue.offer(new int[]{start, INF});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentNode = current[0];
            int currentSpeed = current[1];

            if (maximumSpeed[currentNode] != currentSpeed) {
                continue;
            }

            for (int nextNode : nodes.get(currentNode).next) {
                int nextSpeed = Math.min(currentSpeed, nodes.get(nextNode).limit);

                if (maximumSpeed[nextNode] < nextSpeed) {
                    maximumSpeed[nextNode] = nextSpeed;
                    queue.offer(new int[]{nextNode, nextSpeed});
                }
            }
        }

        return maximumSpeed;
    }
}