package edu.escuelaing.arsw;

@FunctionalInterface
public interface NumericProcessor<T extends Number> {
    double process(T input);
}

