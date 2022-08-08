package TreeRepresentationExercise.main.java.implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {

    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    // , Tree<E>... children
    public Tree(E key) {
        this.key = key;
        this.children = new ArrayList<>();
//        this.children.addAll(List.of(children));
//        for (int i = 0; i < children.length; i++) {
//            children[i].setParent(this);
//        }
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return null;
    }

    @Override
    public String getAsString() {
        StringBuilder builder = new StringBuilder();

        traverseTreeWithRecurrence(builder, 0, this);

        return builder.toString().trim();
    }

    private String getPadding(int size) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    private void traverseTreeWithRecurrence(StringBuilder builder,
                                            int indent, Tree<E> eTree) {
        builder.append(this.getPadding(indent))
                .append(eTree.getKey())
                .append(System.lineSeparator());

        for (Tree<E> child : eTree.children) {
            traverseTreeWithRecurrence(builder, indent + 2, child);
        }
    }

    @Override
    public List<E> getLeafKeys() {
        return traverseWithBFS()
                .stream()
                .filter(tree -> tree.children.size() == 0)
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    private List<Tree<E>> traverseWithBFS() {

        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);

        List<Tree<E>> allNodes = new ArrayList<>();
        while (!queue.isEmpty()) {
            Tree<E> tree = queue.poll();
            allNodes.add(tree);
            for (Tree<E> child : tree.children) {
                queue.offer(child);
            }
        }

        return allNodes;
    }

    @Override
    public List<E> getMiddleKeys() {
        List<Tree<E>> allNodes = new ArrayList<>();

        return null;
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        int maxPath = 0;
        Tree<E> deepestLeftMostNode = null;
        List<Tree<E>> trees = this.traverseWithBFS();
        for (Tree<E> tree : trees) {
            int currentPath = getStepsFromLeafToRootCount(tree);
            if (currentPath > maxPath) {
                maxPath = currentPath;
                deepestLeftMostNode = tree;
            }
        }
        return deepestLeftMostNode;
    }

    private int getStepsFromLeafToRootCount(Tree<E> tree) {
        int count = 0;
        Tree<E> current = tree;
        while (current.parent != null) {
            count++;
            current = current.parent;
        }
        return count;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.size() == 0;
    }

    @Override
    public List<E> getLongestPath() {
        return null;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        return null;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        return null;
    }
}



