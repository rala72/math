package io.rala.math.algebra;

import io.rala.math.utils.Copyable;

import java.util.Objects;

/**
 * class which holds a real and a imaginary part of a complex number
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Complex implements Copyable<Complex>, Comparable<Complex> {
    // region attributes

    private double re;
    private double im;

    // endregion

    // region constructors

    /**
     * calls {@link #Complex(double, double)} with <code>0</code>
     */
    public Complex() {
        this(0, 0);
    }

    /**
     * creates a complex number with real and imaginary part
     *
     * @param re real part
     * @param im imaginary part
     */
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    // endregion

    // region getter and setter

    /**
     * @return real part of complex number
     */
    public double getRe() {
        return re;
    }

    /**
     * @param re new real part of complex number
     */
    public void setRe(double re) {
        this.re = re;
    }

    /**
     * @return imaginary part of complex number
     */
    public double getIm() {
        return im;
    }

    /**
     * @param im new imaginary part of complex number
     */
    public void setIm(double im) {
        this.im = im;
    }

    // endregion

    // region copy

    @Override
    public Complex copy() {
        return new Complex(getRe(), getIm());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex complex = (Complex) o;
        return Double.compare(complex.getRe(), getRe()) == 0 &&
            Double.compare(complex.getIm(), getIm()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRe(), getIm());
    }

    @Override
    public String toString() {
        return getRe() + (0 < getIm() ? "+" : "") + getIm() + "*i";
    }

    @Override
    public int compareTo(Complex o) {
        int compare = Double.compare(getRe(), o.getRe());
        if (compare != 0) return compare;
        return Double.compare(getIm(), o.getIm());
    }

    // endregion
}
