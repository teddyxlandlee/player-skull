package io.github.teddyxlandlee.mcmod.playerskull;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public class DynamicItemTag implements Tag<Item>, Iterable<Item> {
    private final Set<Item> items;

    protected DynamicItemTag() {
        items = new HashSet<>();
    }

    protected DynamicItemTag(Item... items) {
        this(Arrays.asList(items));
    }

    protected DynamicItemTag(Collection<Item> items) {
        this();
        this.items.addAll(items);
    }

    public static DynamicItemTag of() { return new DynamicItemTag(); }
    public static DynamicItemTag of(Item... items) { return new DynamicItemTag(items); }
    public static DynamicItemTag of(Collection<Item> items) { return new DynamicItemTag(items); }

    public void add(Item item) {
        this.items.add(item);
    }

    /* *-* OVERRIDES *-* */

    @Override
    public boolean contains(Item entry) {
        return items.contains(entry);
    }

    @Override
    public List<Item> values() {
        return ImmutableList.copyOf(items);
    }

    @NotNull @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}
