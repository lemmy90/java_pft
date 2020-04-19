package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceTests {

  @Test // некорректный тест, ожидаемое значение 65 не верно
  public void testPoints(){
    Point a = new Point(0.5, -1.78);
    Point b = new Point(6,4.25);

    Assert.assertEquals(a.distance(b),65);
  }

  @Test // тест который проходит
  public void testPoints1(){
    Point a = new Point(0.7, -10);
    Point b = new Point(0,4.25);

    Assert.assertEquals(a.distance(b),14.26718262306893);
  }


}
