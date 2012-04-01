package com.bennis.minecart.client;

public class FineLineIntersection {

	public static void main(String args[]){
		//Ay = Bx + C
		//Dy = Ex + F
		double A,B,C,D,E,F;
		
		A=1; B=2; C=3; D=4; E=5; F=6;
		
		double X = findX(A,B,C,D,E,F);
		double Y = findY(A,B,C,X);
		
		System.out.println("("+X+","+Y+")");
	}
	
	public static double findX(double A, double B, double C, double D, double E, double F){
		double neg =-1;
		double Q,R,S;
		if (A<0 ^ D<0){
			//If A or D is negative add the values
			neg = 1;
		}
		//Eliminate Y using multiple
		double multiple = neg*(A/D);
		D = D*multiple;
		E = E*multiple;
		F = F*multiple;
		
		//Q will be 0
		Q = A+D;
		R = B+E;
		S = C+F;
		return (-S)/R;
	}
	public static double findY(double A, double B, double C, double X){
		return ((B*X) + C)/A;
	}
}