package com.smartchain.experimental;

/* Copyright (C) Smartchain - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Adrian Armaselu <adrian@smartchain.com>, August 2017
 */


public class NumberProgression {

    public static void main(String[] args) {
        fibonaciSequence(50);
        factorialSequence(25);
    }

    public static void fibonaciSequence(int n){
        long first = 1;
        long second = 1;
        for(int i = 0; i < n - 2 ; i++){
            long third = first + second;
            first = second;
            second = third;
            System.out.println("f(" + (i + 3) + "):" + third + " ");
        }
    }

    public static void factorialSequence(int n){
        for(int i = 0 ; i < n ; i++)
            System.out.println(factorial(i));

    }

    public static long factorial(int n){
        long multiplication = 1;
        for(int i = 0; i  < n ; i++)
            multiplication *= n;
        return multiplication;
    }
}
