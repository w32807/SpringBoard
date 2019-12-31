package com;

import java.util.Scanner;

public class Calculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("계산기 클래스 시작");
        //만들어진 메소드를 메인 메소드 안에서 쓴다!
		// 모든 클래스는 사용을 하려면 인스턴스를 생성해야 한다. -
		Calculator cal = new Calculator();
		// 자기 자신이라도 선언 후 사용할 수 있다.
		//사칙연산 메소드를 사용하기 위해 그 메소드가 들어있는 클래스를 선언한다.
		
		
		Scanner scan = new Scanner(System.in);//입력 받으므로 System.in --- scanner 는 무조건 인스턴스를 선언해야한다!
		
		int a = 0;//지역변수는 무조건 초기화
		int b = 0;
		System.out.println("두개의 값을 입력 해 주세요.");
		
		a = scan.nextInt();// scan이라는 클래스 안에서 입력을 담당하는 메소드.(nextint는 문자를 받아서 숫자로 변환)
		b = scan.nextInt();//연달아 값을 못 받는다.
			                       //키보드로부터 입력 받을 때 쓰는 scanf 라는 것! Scanner도 또한 클래스이다.

		System.out.println("add = " + cal.add(a, b));//선언한 cal이라는 곳 안에 들어있는 add라는 메소드를 쓰겠다.
		
		System.out.println("subtract = " + cal.sub(a, b));
		
		System.out.println("multiply = " + cal.mul(a, b));
		
		System.out.println("divide = " + cal.div(a, b));
		
		
		
		
	}

	public int add(int a, int b) {
		return a + b;
	}

	public int sub(int a, int b) {
		return a - b;
	}

	public int mul(int a, int b) {
		return a * b;
	}

	public int div(int a, int b) {// div라는 애를 불러서 나누기를 시켜라
		return a / b;
	}// 여러 가지 메소드를 선언
}