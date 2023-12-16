package com.success.project.kindacoffee.util.frontend.utils;

import java.util.List;

public interface AbstractUnwrapUtil<T, F> {
    T unwrapSingle(F form);

    List<T> unwrap(List<F> forms);

}
