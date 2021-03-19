package com.daxton.customdisplay;



import com.daxton.customdisplay.api.other.StringFind;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Book {
    private String name;

    public Book(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" + "name=" + name + '}';
    }

    @Override
    public boolean equals(Object obook){
        if(obook instanceof Book == false)
        return false;
        Book book = (Book) obook;
        return book.name.equals(this.name);
    }


}

public class Test {


    public static void main(String[] args) {
        Book b1 = new Book("Java");
        Book b2 = new Book("Kotlin");
        Book b3 = new Book("Python");
        List<Book> books = new ArrayList<>();
        books.add(b1);
        books.add(b2);
        books.add(b3);
        System.out.println(books);
        //books.remove(b2);
        books.remove(new Book("Kotlin"));
        System.out.println(books);

    }





}
