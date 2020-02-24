package com.mockapi.mockapi;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args){
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String oldpw = "dQo29p2o";
        String newpw = "$2a$10$1olA0EWPgrBp/sCh4D1wyuJ22sQLzE7IeLMX3s/T1LNNPRuzKSTqC";

        if(encoder.matches(oldpw,newpw)){
            System.out.println(newpw);
            System.out.println("Đúng");
        }else{
            System.out.println("sai");
        }

    }


}
