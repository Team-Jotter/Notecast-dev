package com.example.notecast.utils;

import javafx.scene.Scene;

import java.util.Stack;

public class StateManager {
    static Stack<Scene> stateStack = new Stack<>();

    public static void push(Scene scene) {
        stateStack.push(scene);
    }

    public static void pop() {
        stateStack.pop();
    }

    public static Scene peek() {
        return stateStack.peek();
    }
}
