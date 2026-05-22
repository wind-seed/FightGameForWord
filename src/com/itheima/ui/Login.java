package com.itheima.ui;

import com.itheima.domain.User;

import javax.xml.transform.Source;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
    //启动页面
    public void start(){

        ArrayList<User> list = new ArrayList<>();

        //ctrl + alt + t 选择对应语句包裹代码
        while (true) {
            System.out.println("==================================");
            System.out.println("        欢迎来到文字格斗游戏       ");
            System.out.println("==================================");
            System.out.println("请选择你的操作 1.登录  2.注册  3.退出");
            Scanner sc = new Scanner(System.in);
            String choose = sc.next();

            switch(choose){
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> {
                    System.out.println("退出");
                    System.exit(0);
                }
                default -> System.out.println("错误输入");
            }
        }
    }

    public void login(ArrayList<User> list){
        System.out.println("请输入用户名");
        Scanner sc = new Scanner(System.in);
        String username = sc.next();

        if(check_username_only(list, username) == -1){
            System.out.println("用户名不存在，请注册");
            return ;
        }

        int index =check_username_only(list, username);
        User RightUser= list.get(index);

        if(!RightUser.isStatus()){
            System.out.println("用户" + RightUser.getUsername() + "已被锁定");
            return ;
        }

        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码：");
            String password = new Scanner(System.in).next();
            String RightPassword = RightUser.getPassword();

            while(true) {
                String Right_captcha = captcha();
                System.out.println("验证码为" + Right_captcha);
                System.out.println("请输入验证码");
                String cap = new Scanner(System.in).next();
                if (!cap.equalsIgnoreCase(Right_captcha)) {
                    System.out.println("验证码输入错误,重新输入");
                    continue;
                }

                break;
            }
            if(password.equals(RightPassword)){
                System.out.println("密码正确！欢迎游玩");
                FightingGame startGame = new FightingGame();
                startGame.GameStart(username);
                break;
            }

            if(i == 2){
                RightUser.setStatus(false);
                System.out.println("用户锁定");
            }else{
                System.out.println("密码错误，还剩" + (2 - i) + "次机会");
            }
        }

    }

    public void register(ArrayList<User> list){
        User user = new User();
        //判断用户名是否符合要求
        while(true){
            System.out.println("输入用户名");
            Scanner sc1 = new Scanner(System.in);
            String username = sc1.next();
            //长度：3-16
            if(checklen(3,16,username)){
                System.out.println("长度需要在3-16之间");
                continue;
            }
            //格式： 必须有英文  可不包含数字  不可以有其他符号
            if(check_username_format(username)){
                System.out.println("必须有英文  可不包含数字  不可以有其他符号");
                continue;
            }

            //是否唯一
            if(check_username_only(list, username) != -1){
                System.out.println("用户名重复");
                continue;
            }
            user.setUsername(username);
            break;
        }

        //判断密码是否符合需求
        while(true){
            System.out.println("请输入密码");
            Scanner sc2 = new Scanner(System.in);
            String password1 = sc2.next();
            System.out.println("请再次输入密码");
            Scanner sc3 = new Scanner(System.in);
            String password2 = sc3.next();
            if(!password1.equals(password2)){
                System.out.println("两次密码不同");
                continue;
            }
            //长度：3-8
            if(checklen(3, 8, password1)){
                System.out.println("长度需要在3-8之间");
                continue;
            }
            //格式： 必须有英文  可不包含数字  不可以有其他符号
            if(check_password_format(password1)){
                System.out.println("必须有英文  可不包含数字  不可以有其他符号");
                continue;
            }

            user.setPassword(password1);
            break;
        }
        list.add(user);
        System.out.println("注册成功！");
    }

    // 长度在指定范围之内  返回false
    public boolean checklen(int minlen, int maxlen, String str){
        return str.length() < minlen || str.length() > maxlen;
    }


    //用于计算各字符数量
    public int[] getcount(String userinfo){
        int charcount = 0;
        int numcount = 0;
        int othercount = 0;

        for (int i = 0; i < userinfo.length(); i++) {
            char c = userinfo.charAt(i);
            if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'){
                charcount++;
            } else if (c >= '1' && c <= '9') {
                numcount++;
            }else {
                othercount++;
            }
        }
        return new int[]{charcount, numcount, othercount};
    }


    //字母至少一个  数字可以有可以没有   特殊字符不可以有
    //符合返回false  不符合返回ture
    public boolean check_username_format(String username){
        int[] arr = getcount(username);
        return arr[2] >= 1 || arr[0] == 0;
    }

    //字母至少一个  数字至少1个   特殊字符不可以有
    //符合返回false  不符合返回ture
    public boolean check_password_format(String username){
        int[] arr = getcount(username);
        return arr[2] >= 1 || arr[0] == 0 || arr[1] == 0;
    }

    //判断是否唯一
    //唯一返回-1 不唯一返回index
    //另一层含义 用户名不存在返回-1 用户名存在返回index
    public int check_username_only(ArrayList<User> list, String username){
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            if(u.getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }


    //获取验证码
    public String captcha(){
        /*
        长度为5
        由4个大写或小写＋数字，字母可重复
        数字出现在任意位置
         */

        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            char lowercase = (char)('a' + i);
            char upcase = (char)('A' + i);
            list.add(lowercase);
            list.add(upcase);
        }

        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(list.get(r.nextInt(list.size())));
        }

        sb.append(r.nextInt(10));

        char[] arr = sb.toString().toCharArray();

        int arrIndex = r.nextInt(sb.length());
        char temp = arr[arrIndex];
        arr[arrIndex] = arr[sb.length() - 1];
        arr[sb.length() - 1] = temp;

        return new String(arr);
    }
}