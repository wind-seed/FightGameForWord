package com.itheima.domain;

public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;

    public Character(){

    }
    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }

    public boolean isAlive(int HP){
        return HP > 0;
    }

    //回血   具体回多少
    public void RestoreLife(int BloodRecoveryValue){
        HP = HP + BloodRecoveryValue > maxHP ? maxHP : HP + BloodRecoveryValue;
    }

    //受伤
    public void takeDamage(int damage){
        HP = HP - damage > 0 ? HP - damage : 0;
    }

    //展示人物属性
    public String show(){
        return name + "[当前生命：" + HP + ", 攻击：" + attack + "， 防御：" + defense + "]";
    }
}
