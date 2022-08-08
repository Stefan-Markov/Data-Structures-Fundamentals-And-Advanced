package AVLTrees23Exercise.main.java.main;

import java.util.*;
import java.util.stream.Collectors;

public class Hierarchy<T> implements IHierarchy<T> {

    private class HierarchyChild<T> {
        private T value;
        private HierarchyChild<T> parent;
        private List<HierarchyChild<T>> children;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public HierarchyChild<T> getParent() {
            return parent;
        }

        public void setParent(HierarchyChild<T> parent) {
            this.parent = parent;
        }

        public List<HierarchyChild<T>> getChildren() {
            return children;
        }

        public void setChildren(List<HierarchyChild<T>> children) {
            this.children = children;
        }

        public HierarchyChild(T value) {
            this.value = value;
            this.parent = null;
            this.children = new ArrayList<>();
        }
    }

    private HierarchyChild<T> root;
    private Map<T, HierarchyChild<T>> nodeMap;

    private int count;

    public Hierarchy(T value) {
        this.root = new HierarchyChild<>(value);
        this.count = 1;
        this.nodeMap = new HashMap<>();
        this.nodeMap.putIfAbsent(value, this.root);
    }

    private HierarchyChild<T> findNode(T value) {
//        if (this.root.getValue().equals(value)) {
//            return this.root;
//        }
//        ArrayDeque<HierarchyChild<T>> queue = new ArrayDeque<>();
//        queue.offer(this.root);
//        while (!queue.isEmpty()) {
//            HierarchyChild<T> current = queue.poll();
//
//            if (current.getValue().equals(value)) {
//                return current;
//            }
//            for (HierarchyChild<T> childNode : current.getChildren()) {
//                queue.offer(childNode);
//            }
//        }
        return this.nodeMap.getOrDefault(value, null);
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public void add(T element, T child) {
        HierarchyChild<T> parent = this.findNode(element);
        if (parent == null || this.findNode(child) != null) {
            throw new IllegalArgumentException();
        }
        HierarchyChild<T> childNode = new HierarchyChild<>(child);
        childNode.setParent(parent);
        parent.getChildren().add(childNode);
        this.nodeMap.putIfAbsent(child, childNode);
        this.count++;

    }

    @Override
    public void remove(T element) {
        if (this.root.getValue().equals(element)) {
            throw new IllegalArgumentException();
        }
        HierarchyChild<T> nodeToRemove = this.findNode(element);

        if (nodeToRemove == null) {
            throw new IllegalArgumentException();
        }

        nodeToRemove.getParent().getChildren().remove(nodeToRemove);
        nodeMap.remove(element);
        for (HierarchyChild<T> child : nodeToRemove.getChildren()) {
            child.setParent(nodeToRemove.getParent());
            nodeToRemove.getParent().getChildren().add(child);
        }
        this.count--;
    }

    @Override
    public Iterable<T> getChildren(T element) {
        return this.findNode(element).getChildren()
                .stream().map(HierarchyChild::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public T getParent(T element) {
        HierarchyChild<T> node = this.findNode(element);

        if (node == null) {
            throw new IllegalArgumentException();
        }
        if (node.getParent() == null) {
            return null;
        }
        return node.getParent().getValue();
    }

    @Override
    public boolean contains(T element) {
        return this.findNode(element) != null;
    }

    @Override
    public Iterable<T> getCommonElements(IHierarchy<T> other) {

        List<T> result = new ArrayList<>();

        for (T value : this) {
            if (other.contains(value)) {
                result.add(value);
            }
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        List<T> result = new ArrayList<>();
        ArrayDeque<HierarchyChild<T>> queue = new ArrayDeque<>();
        queue.offer(this.root);
        while (!queue.isEmpty()) {
            HierarchyChild<T> current = queue.poll();

            result.add(current.getValue());
            for (HierarchyChild<T> childNode : current.getChildren()) {
                queue.offer(childNode);
            }
        }
        return result.iterator();
    }
}
