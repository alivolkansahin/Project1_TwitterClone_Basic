package com.volkans.implementations.repository;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * AdminManagerImpl ve UserManagerImpl için kullanılan scannerda kullanıcıdan 
 * farklı veri tipi değerler almaya yarayacak methodları içeren Utils Sınıfı.
 * Değerleri alırken try-catch ile hataları yakalayacak ve hatasız işlem yapmak için kullanıcıyı zorlayacak.
 * Aynı zamanda renk kodlarını tutan static final fieldlarına sahiptir.
 * 
 * Final Class : Kimse Kalıtım Alamaz
 */
public final class Utils {
	public static final String RESET = "\033[0m";  // RESET COLOR
	public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
	public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // BOLD & BRIGHT RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // BOLD & BRIGHT GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// BOLD & BRIGHT YELLOW
	
	private static int intValue;
	private static long longValue;
	private static String stringValue;
	private static boolean check;
	
	public static String getStringValue(Scanner scanner, String message) {
		System.out.print(message);
		stringValue = scanner.nextLine();
		return stringValue;
	}

	public static long getLongValue(Scanner scanner,String message) { // try-catch denemesi
		while(!check) {
			try {
				System.out.print(message);
				longValue = scanner.nextLong(); scanner.nextLine();
				break;
			} catch (InputMismatchException e) {
				System.out.println(RED + "Çok uzun veya string bir değer girdiniz!" + RESET);
				scanner.nextLine();
				continue;		
			}
		}
		return longValue;
		
	}
	
	public static int getIntegerValue(Scanner scanner,String message) { // try-catch denemesi
		while(!check) {
			try {
				System.out.print(message);
				intValue = scanner.nextInt(); scanner.nextLine();
				break;
			} catch (InputMismatchException e) {
				System.out.println(RED + "Integer değer girilmedi !" + RESET);
				scanner.nextLine();
				continue;
			}
		}
		return intValue;
	}
	
}
