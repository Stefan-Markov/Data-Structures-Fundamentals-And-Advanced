package AVLTrees23Exercise.test.java.test;

import main.Hierarchy;
import main.IHierarchy;

public class HierarchyStructureInitializer {
    
    public static <T> IHierarchy<T> create(T root) {
        return new Hierarchy<>(root);
    }
}
