package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Main {

    public static ArrayList<String> getComm(String filename) {
        ArrayList<String> commands = new ArrayList<String>();
        String all_line = new String();
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                all_line += line;
                all_line += " ";
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[] line = all_line.split("\\s");

        for (String s:line) {
            if (!s.isEmpty()) {
                commands.add(s.replaceAll("\\s", ""));
            }
        }

        return commands;
    }


    public static TreeMap<String, Integer> setCommands() {
        TreeMap<String, Integer> commands = new TreeMap<>();

        String[] comm ={"MoO","MOo", "moO", "mOo", "moo", "MOO", "OOM", "oom", "mOO", "Moo", "OOO" };
        for (int i=1; i<comm.length+1; i++) {
            commands.put(comm[i-1], i);
        }

        return commands;
    }

    public static int getIndex(String comm) {
        TreeMap<String, Integer> commands = setCommands();
        try {
            int index = commands.get(comm);
            return index;
        } catch (NullPointerException e) {
            //e.printStackTrace();
            return 0;
        }
    }

    public static HashMap<Integer, Integer> getBlocks(ArrayList<String> source) {
        HashMap<Integer, Integer> blocks = new HashMap<>();
        ArrayList<Integer> stack = new ArrayList<>();

        int i = 0;

        for (String s:source) {
            if (getIndex(s)==6) {
                stack.add(i);
        }
            if (getIndex(s)==5) {
                blocks.put(i, stack.get(stack.size()-1));
                blocks.put(stack.remove(stack.size()-1), i);
            }
            i+=1;
        }
        return blocks;
    }

    public static void main(String[] args) {
	// write your code here

        Compile compile = new Compile();
        moo myMoo = new moo();

        compile.myMoo = myMoo;
        ArrayList <String> source = getComm("hello.cow");

        compile.commands = setCommands();
        compile.blocks = getBlocks(source);

        System.out.println(compile.blocks);

        compile.myMoo.buffer = new char[200];

        for (char c:compile.myMoo.buffer) {
            c = '0';
        }

        while (compile.myMoo.i < source.size()) {
            int index = getIndex(source.get(compile.myMoo.i));
            if (index!=0) {
                compile.changePtr(index);
                compile.changeBuffer(index);
                compile.changeBlocks(index);
            }
            compile.myMoo.i += 1;
        }
    }
}
