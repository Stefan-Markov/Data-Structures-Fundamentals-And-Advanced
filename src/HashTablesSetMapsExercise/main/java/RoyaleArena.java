package HashTablesSetMapsExercise.main.java;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;


public class RoyaleArena implements IArena {

    private Map<Integer, Battlecard> battleCardById;
    private Map<String, List<Battlecard>> battleCardByName;

    public RoyaleArena() {
        this.battleCardById = new HashMap<>();
        this.battleCardByName = new HashMap<>();
    }

    @Override
    public void add(Battlecard card) {

        this.battleCardById.put(card.getId(), card);
        if (!this.battleCardByName.containsKey(card.getName())) {
            battleCardByName.put(card.getName(), new ArrayList<>());
        }
        battleCardByName.get(card.getName()).add(card);
    }

    @Override
    public boolean contains(Battlecard card) {
        return this.battleCardById.containsKey(card.getId());
    }

    @Override
    public int count() {
        return this.battleCardById.size();
    }

    @Override
    public void changeCardType(int id, CardType type) {
        if (!this.battleCardById.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        this.battleCardById.get(id).setType(type);
    }

    @Override
    public Battlecard getById(int id) {
        if (!this.battleCardById.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        return this.battleCardById.get(id);
    }

    @Override
    public void removeById(int id) {
        if (!this.battleCardById.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        Battlecard remove = this.battleCardById.remove(id);
        this.battleCardByName.get(remove.getName()).remove(remove);

    }

    @Override
    public Iterable<Battlecard> getByCardType(CardType type) {

        List<Battlecard> result = new LinkedList<>();
        for (Battlecard battlecard : this.battleCardById.values()) {
            if (battlecard.getType().equals(type)) {
                result.add(battlecard);
            }
        }
        return result;
    }

    @Override
    public Iterable<Battlecard> getByTypeAndDamageRangeOrderedByDamageThenById(CardType type, int lo, int hi) {
        return this.battleCardById.values().stream()
                .filter(c -> c.getType().equals(type) && c.getDamage() <= hi && c.getDamage() >= lo)
                .sorted(Comparator.comparingDouble(Battlecard::getDamage).reversed().thenComparing(Battlecard::getId))
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Battlecard> getByCardTypeAndMaximumDamage(CardType type, double damage) {
        List<Battlecard> result = this.battleCardById.values().stream()
                .filter(c -> c.getType().equals(type) && c.getDamage() <= damage)
                .sorted(Comparator.comparingDouble(Battlecard::getDamage).reversed().thenComparing(Battlecard::getId))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new UnsupportedOperationException();

        }
        return result;
    }

    @Override
    public Iterable<Battlecard> getByNameOrderedBySwagDescending(String name) {

        List<Battlecard> result = this.battleCardById.values().stream()
                .filter(c -> c.getName().equals(name))
                .sorted(Comparator.comparingDouble(Battlecard::getDamage).reversed().thenComparing(Battlecard::getId))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new UnsupportedOperationException();

        }
        return result;
    }

    @Override
    public Iterable<Battlecard> getByNameAndSwagRange(String name, double lo, double hi) {
        List<Battlecard> result = this.battleCardById.values().stream()
                .filter(c -> c.getName().equals(name) && c.getDamage() <= hi && c.getDamage() >= lo)
                .sorted(Comparator.comparingDouble(Battlecard::getDamage).reversed().thenComparing(Battlecard::getId))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new UnsupportedOperationException();

        }
        return result;
    }

    @Override
    public Iterable<Battlecard> getAllByNameAndSwag() {
        List<Battlecard> result = this.battleCardById.values().stream()
                .sorted(Comparator.comparingDouble(Battlecard::getDamage).reversed().thenComparing(Battlecard::getId))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new UnsupportedOperationException();

        }
        return result;
    }

    @Override
    public Iterable<Battlecard> findFirstLeastSwag(int n) {

        return battleCardById.values()
                .stream()
                .sorted((card1, card2) -> {
                    int compareResult = Double.compare(card2.getSwag(), card1.getSwag());
                    if (compareResult == 0) {
                        return Integer.compare(card1.getId(), card2.getId());
                    }
                    return compareResult;
                })
                .filter(Objects::nonNull)
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getAllInSwagRange(double lo, double hi) {
        return battleCardById.values()
                .stream()
                .filter(card -> card.getSwag() >= lo && card.getSwag() <= hi)
                .sorted(Comparator.comparingDouble(Battlecard::getSwag))
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Battlecard> iterator() {
        return this.battleCardById.values().iterator();
    }
}
