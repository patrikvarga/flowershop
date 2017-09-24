package com.patrikvarga.flowershop.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Details of a flower including bundles being sold. Ordering is based on name.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class Flower implements Comparable<Flower> {

    private static final Comparator<String> NULL_SAFE_STRING_COMPARATOR
            = Comparator.nullsFirst(String::compareToIgnoreCase);

    private final String code;
    private final String name;
    private final List<Bundle> bundles;

    private Flower() {
        // for Jackson :(
        this.code = null;
        this.name = null;
        this.bundles = null;
    }

    public Flower(final String code, final String name, final List<Bundle> bundles) {
        this.code = code;
        this.name = name;
        this.bundles = bundles;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public void addBundle(final Bundle bundle) {
        bundles.add(bundle);
        Collections.sort(bundles);
    }

    public boolean removeBundle(final Bundle bundle) {
        return bundles.remove(bundle);
    }

    public List<Bundle> bundles() {
        return new ArrayList<>(bundles);
    }

    @Override
    public String toString() {
        return "Product{" + "code=" + code + ", name=" + name + ", bundles=" + bundles + '}';
    }

    @Override
    public int compareTo(final Flower other) {
        return NULL_SAFE_STRING_COMPARATOR.compare(this.code, other.code);
    }
}
