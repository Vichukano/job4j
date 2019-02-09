package ru.job4j.garbage;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class MemoryUsage {

    public static void main(String[] args) {
        System.out.println("Вес объекта user = " + ObjectSizeCalculator.getObjectSize(new User()));
        info();
        long start = System.currentTimeMillis();
        for (int i = 0; i != 12_000; i++) {
            new User("Test", i);
        }
        System.out.println("Time is: " + (System.currentTimeMillis() - start));
    }

    public static void info() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("---Heap memory statistics---");
        System.out.println("Max memory: " + runtime.maxMemory());
        System.out.println("Available memory: " + runtime.totalMemory());
        System.out.println("Free memory: " + runtime.freeMemory());
        System.out.println("Used memory: " + (runtime.totalMemory() - runtime.freeMemory()));

    }

    private static class User {
        private String name;
        private int age;

        public User() {

        }

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return String.format("User name = %s, age = %d", name, age);
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("Finalize user object.");
        }
    }
}
