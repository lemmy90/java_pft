package ru.stqa.pft.sandbox;

public class Task2 {

  public static void main(String[] args) {

    Point a = new Point(5,3);
    Point b = new Point(10,7);

    System.out.println("Расстояние между точками a и b = " + distance(a,b));

  }

  public static double distance(Point a, Point b){
    return Math.sqrt(Math.pow(b.x - a.x, 2) +  Math.pow(b.y - a.y, 2));

  }

}
