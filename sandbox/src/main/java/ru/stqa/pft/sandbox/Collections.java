package ru.stqa.pft.sandbox;

import java.util.ArrayList;
import java.util.List;

public class Collections {

  public static void main (String[] args) {
    String[] langs = {"Java", "C#", "Python", "PHP"};

//    String[] langs = new String[4];
//    langs[0] = "Java";
//    langs[1] = "C#";
//    langs[2] = "Python";
//    langs[3] = "PHP";
    
//    for (int i = 0; i < langs.length; i++) { - эквивалетно
    for (String l : langs) {
      System.out.println("Я хочу выучить " + l);

    }
  }
}
