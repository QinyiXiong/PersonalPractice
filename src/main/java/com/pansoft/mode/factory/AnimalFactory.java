package com.pansoft.mode.factory;

public class AnimalFactory {

	public static Animal getAnimal(AnimalTypeEnum animalType){
		
		switch(animalType){
			case CAT: return new Cat();
			case DOG: return new Dog();
		}
		return null;
	}
}
