package com.pansoft.mode.factory;

public class Test {

	public static void main(String[] args) {
		
		Animal animal1 = AnimalFactory.getAnimal(AnimalTypeEnum.CAT);
		System.out.println(animal1.getName());
		
		Animal animal2 = AnimalFactory.getAnimal(AnimalTypeEnum.DOG);
		System.out.println(animal2.getName());
	}
}
