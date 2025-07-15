package com.example.springbootfirst.controllers;

public class Calculator
{
    public int addition(int x, int y)
    {
        int z = x + y;
        return z;
    }

    public int subtraction(int x, int y)
    {
        int z = x - y;
        return z;
    }

    public int multiplication(int x, int y)
    {
        int z = x * y;
        return z;
    }

    public int division(int x, int y)
    {
        if (y == 0) return 0;
        int z = x / y;
        return z;
    }
}