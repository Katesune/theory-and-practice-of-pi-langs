package com.company;


import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

class Compile {
    TreeMap<String, Integer> commands;
    HashMap<Integer, Integer> blocks;

    moo myMoo;

    public moo changePtr(int index) {
        switch (index) {
            case 3:
                this.myMoo.ptr += 1;
                break;
            case 4:
                this.myMoo.ptr -= 1;
                break;
            default:
                break;
        }
        return myMoo;
    }

    public moo changeBlocks(int index) {
        switch (index) {
            case 5:
                if ((int)this.myMoo.buffer[this.myMoo.ptr] != 0) {
                    this.myMoo.i = this.blocks.get(this.myMoo.i)-1;
                }
                break;
            case 6:
                if ((int)this.myMoo.buffer[this.myMoo.ptr] == 0) {
                    this.myMoo.i = this.blocks.get(this.myMoo.i)-1;
                }
                break;
            default:
                break;
        }
        return myMoo;
    }

    public void enterNum() {
        try {
            Scanner sc = new Scanner(System.in);
            this.myMoo.buffer[this.myMoo.ptr] = sc.nextLine().charAt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public void enter(char c) {

        String str = Character.toString(c);

        try {
            byte[] bytes = str.getBytes("ASCII");
            System.out.print(c);
            System.out.print("  ");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public moo changeBuffer(int index) {//изменяет значение ячейки
        switch (index) {
            case 1:
                this.myMoo.buffer[this.myMoo.ptr] += 1;
                break;
            case 2:
                this.myMoo.buffer[this.myMoo.ptr] -= 1;
                break;
            case 7:
                enter(this.myMoo.buffer[myMoo.ptr]);
                break;
            case 9:
                enterNum();
                break;
            case 10:
                if ((int)this.myMoo.buffer[myMoo.ptr] == 0) {
                    enterNum();
                } else enter(this.myMoo.buffer[myMoo.ptr]);
                break;
            case 11:
                this.myMoo.buffer[myMoo.ptr] = '0';
            default:
                break;
        }
        return myMoo;
    }

}

public class moo {
    char[] buffer;
    int i;
    int ptr;

    moo(){i=0;}
}
