package pl.lgawin.paypal.ipn.utils;

import java.util.function.Function;

public class SchwartzianTransformItem<T, R> {
    private final T data;
    private final Function<T, R> function;

    public SchwartzianTransformItem(T data, Function<T, R> function) {
        this.data = data;
        this.function = function;
    }

    public T original() {
        return data;
    }

    public R mapped() {
        return function.apply(data);
    }
}
