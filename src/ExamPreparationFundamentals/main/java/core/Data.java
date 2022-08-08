package ExamPreparationFundamentals.main.java.core;

import interfaces.Entity;
import interfaces.Repository;

import javax.xml.stream.events.EndElement;
import java.util.*;
import java.util.stream.Collectors;

public class Data implements Repository {

    private final Queue<Entity> data;

    public Data() {
        this.data = new PriorityQueue<>();
    }

    public Data(Data other) {
        this.data = new PriorityQueue<>(other.data);
    }

    @Override
    public void add(Entity entity) {

        Entity parent = this.getById(entity.getParentId());
        if (entity.getParentId() > 0) {
            parent.addChild(entity);
            this.data.offer(entity);
        }
    }

    @Override
    public Entity getById(int id) {
        for (Entity entity : this.data) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<Entity> getByParentId(int id) {
        Entity parent = this.getById(id);
        List<Entity> result = new ArrayList<>();
        if (parent != null) {
            result.addAll(parent.getChildren());
        }
        return result;
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.data);
    }

    @Override
    public Repository copy() {
        return new Data(this);
    }

    @Override
    public List<Entity> getAllByType(String type) {
        boolean localModel = type.equals("Invoice") || type.equals("StoreClient")
                || type.equals("User");
        if (!localModel) {
            throw new IllegalArgumentException("Not valid");
        }


        return this.data
                .stream()
                .filter(e -> e.getClass().getSimpleName().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public Entity peekMostRecent() {
        if (this.data.isEmpty()) {
            throw new IllegalStateException("Not Valid");
        }

        return this.data.peek();
    }

    @Override
    public Entity pollMostRecent() {
        if (this.data.isEmpty()) {
            throw new IllegalStateException("Not Valid");
        }
        return this.data.poll();
    }

    @Override
    public int size() {
        return this.data.size();
    }
}
