package com.patrikvarga.flowershop.catalog;

import java.util.List;

/**
 * Details of a flower including bundles being sold.
 *
 * @author <a href="mailto:varga.patrik@gmail.com">Patrik Varga</a>
 */
public class Flower {

    public final String code;
    public final String name;
    private final List<Bundle> bundles;

    private Flower() {
        this.code = null;
        this.name = null;
        this.bundles = null;
    }

    public Flower(final String code, final String name, final List<Bundle> bundles) {
        // for Jackson :(
        this.code = code;
        this.name = name;
        this.bundles = bundles;
    }

    public void addBundle(final Bundle bundle) {
        bundles.add(bundle);
    }

    public boolean removeBundle(final Bundle bundle) {
        return bundles.remove(bundle);
    }

    @Override
    public String toString() {
        return "Product{" + "code=" + code + ", name=" + name + ", bundles=" + bundles + '}';
    }
}
