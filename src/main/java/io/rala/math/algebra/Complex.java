package io.rala.math.algebra;

import io.rala.math.geometry.Vector;
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

    // region absoluteValue, conjugation and reciprocal

    /**
     * @return absolute <i>(modulus)</i> value of complex based on pythagoras
     */
    public double absoluteValue() {
        return Math.sqrt(Math.pow(getRe(), 2) + Math.pow(getIm(), 2));
    }

    /**
     * @return {@link #inverseIm()}
     */
    public Complex conjugation() {
        return inverseIm();
    }

    /**
     * @return new reciprocal of complex
     */
    public Complex reciprocal() {
        double d = Math.pow(getRe(), 2) + Math.pow(getIm(), 2);
        return new Complex(getRe() / d, -getIm() / d);
    }

    // endregion

    // region add and subtract

    /**
     * calls {@link #add(Complex)} with given values
     *
     * @param re re value to add
     * @param im im value to add
     * @return new complex with sum of current and given parameters
     * @see #add(Complex)
     */
    public Complex add(double re, double im) {
        return add(new Complex(re, im));
    }

    /**
     * @param complex complex value to add
     * @return new complex with sum of current and given complex
     * @see #add(double, double)
     */
    public Complex add(Complex complex) {
        return new Complex(getRe() + complex.getRe(), getIm() + complex.getIm());
    }

    /**
     * calls {@link #subtract(Complex)} with given values
     *
     * @param re re value to subtract
     * @param im im value to subtract
     * @return new complex with difference of current and given parameters
     * @see #subtract(Complex)
     */
    public Complex subtract(double re, double im) {
        return subtract(new Complex(re, im));
    }

    /**
     * @param complex complex value to subtract
     * @return new complex with difference of current and given complex
     * @see #subtract(double, double)
     */
    public Complex subtract(Complex complex) {
        return add(complex.inverse());
    }

    // endregion

    // region multiply and divide

    /**
     * @param i value to multiply with re &amp; im
     * @return new complex with multiplied re &amp; im values
     */
    public Complex multiply(double i) {
        return new Complex(getRe() * i, getIm() * i);
    }

    /**
     * @param complex complex value to multiply
     * @return new complex with multiplied re &amp; im values
     */
    public Complex multiply(Complex complex) {
        return new Complex(
            getRe() * complex.getRe() - getIm() * complex.getIm(),
            getRe() * complex.getIm() + getIm() * complex.getRe()
        );
    }

    /**
     * @param i value to divide re &amp; im through
     * @return new complex with divided re &amp; im values
     */
    public Complex divide(double i) {
        return new Complex(getRe() / i, getIm() / i);
    }

    /**
     * @param complex complex value to divide through
     * @return new complex which is quotient of the division
     */
    public Complex divide(Complex complex) {
        return new Complex(
            (getRe() * complex.getRe() + getIm() * complex.getIm()) /
                (Math.pow(complex.getRe(), 2) + Math.pow(complex.getIm(), 2)),
            (complex.getRe() * getIm() - getRe() * complex.getIm()) /
                (Math.pow(complex.getRe(), 2) + Math.pow(complex.getIm(), 2))
        );
    }

    // endregion

    // region inverse

    /**
     * @return new complex which has inverse re &amp; im values
     * @see #inverseRe()
     * @see #inverseIm()
     */
    public Complex inverse() {
        return inverseRe().inverseIm();
    }

    /**
     * @return new complex which has inverse re value
     * @see #inverse()
     * @see #inverseIm()
     */
    public Complex inverseRe() {
        return new Complex(-getRe(), getIm());
    }

    /**
     * @return new complex which has inverse im value
     * @see #inverse()
     * @see #inverseRe()
     */
    public Complex inverseIm() {
        return new Complex(getRe(), -getIm());
    }

    // endregion

    // region asVector, static ofVector

    /**
     * @return new vector representing
     * {@link #getRe()} as <code>x</code> and
     * {@link #getIm()} as <code>y</code>
     */
    public Vector asVector() {
        return new Vector(getRe(), getIm());
    }

    /**
     * @param vector vector to convert to {@link Complex}
     * @return new complex using
     * {@link Vector#getX()} as <code>re</code> and
     * {@link Vector#getY()} as <code>im</code>
     */
    public static Complex ofVector(Vector vector) {
        return new Complex(vector.getX(), vector.getY());
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
        return getRe() + (0 <= getIm() ? "+" : "") + getIm() + "*i";
    }

    @Override
    public int compareTo(Complex o) {
        int compare = Double.compare(getRe(), o.getRe());
        if (compare != 0) return compare;
        return Double.compare(getIm(), o.getIm());
    }

    // endregion
}
