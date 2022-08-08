package ExamPreparationFundamentals.main.java.core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Loader implements Buffer {

    private final List<Entity> data;

    public Loader() {
        this.data = new ArrayList<>();
    }

    @Override
    public void add(Entity entity) {
        this.data.add(entity);
    }

    @Override
    public Entity extract(int id) {
        Entity result = null;
        for (Entity entity : this.data) {
            if (entity.getId() == id) {
                result = entity;
                break;
            }
        }
        if (result != null) {
            this.data.remove(result);
        }
        return result;
    }

    @Override
    public Entity find(Entity entity) {
        int index = this.data.indexOf(entity);
        if (index < 0) {
            return null;
        }
        return this.data.get(index);
    }

    @Override
    public boolean contains(Entity entity) {
        return this.data.contains(entity);
    }

    @Override
    public int entitiesCount() {
        return this.data.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {
        Entity oldSearch = this.find(oldEntity);
        if (oldSearch == null) {
            throw new IllegalStateException("Not found");
        }
        this.data.remove(oldEntity);
        this.data.add(newEntity);
    }

    @Override
    public void swap(Entity first, Entity second) {
        int i = this.data.indexOf(first);
        int j = this.data.indexOf(second);
        if (i < 0 || j < 0) {
            throw new IllegalStateException("Not valid");
        }
        Collections.swap(this.data, i, j);
    }

    @Override
    public void clear() {
        this.data.clear();
    }

    @Override
    public Entity[] toArray() {
        Entity[] result = new Entity[this.data.size()];

        return this.data.toArray(result);
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        return this.data
                .stream()
                .filter(e -> e.getStatus().ordinal() >= lowerBound.ordinal())
                .filter(e -> e.getStatus().ordinal() <= upperBound.ordinal())
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.data);
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {

        for (Entity entity : this.data) {
            if (entity.getStatus() == oldStatus) {
                entity.setStatus(newStatus);
            }
        }
    }

    @Override
    public void removeSold() {
        this.data.removeIf(e -> e.getStatus() == BaseEntity.Status.SOLD);
    }

    @Override
    public Iterator<Entity> iterator() {
        return this.data.iterator();
    }
}
