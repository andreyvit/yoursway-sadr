package com.yoursway.sadr.blocks.integer_literals;

public class Complex {
	public double real;
	public double imag;

	public Complex(double real) {
		this.real = real;
		this.imag = 0;
	}

	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	public Complex add(Complex rhs) {
		return new Complex(this.real + rhs.real, this.imag + rhs.imag);
	}
	
	public Complex subtract(Complex rhs) {
		return new Complex(this.real - rhs.real, this.imag - rhs.imag);
	}
	
	public Complex multiply(Complex rhs) {
		return new Complex(
				this.real * rhs.real - this.imag * rhs.imag, 
				this.imag * rhs.real + this.real * rhs.imag);
	}
	
	public Complex divide(Complex rhs) {
		double abs2 = rhs.abs2();
		return new Complex(
				(this.real * rhs.real + this.imag * rhs.imag)/abs2, 
				(this.imag * rhs.real - this.real * rhs.imag)/abs2);
	}

	public double abs2() {
		return this.real*this.real + this.imag*this.imag;
	}

	public boolean isZero() {
		return real == 0.0 && imag == 0.0;
	}

	public Complex neg() {
		return new Complex(-real, -imag);
	}
	
	@Override
	public String toString() {
		if(real == 0) return printFloat(imag)+"j";
		return "("+printFloat(real)+(imag>=0?"+":"")+printFloat(imag)+"j)";
	}

	private String printFloat(double x) {
		if(x == (int)(x))
			return String.valueOf((int)(x));
		return String.valueOf(x);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imag);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(imag) != Double.doubleToLongBits(other.imag))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}
}
