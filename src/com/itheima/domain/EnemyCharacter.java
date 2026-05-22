package com.itheima.domain;

public class EnemyCharacter extends Character{
    public String skill;
    public boolean defending;

    public EnemyCharacter() {
        super();
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
    }


    //有无减伤BUFF
    @Override
    public void takeDamage(int damage) {
        if(defending){
            damage = damage / 2 > 1? damage / 2 : 1;
            defending = false;
        }
        super.takeDamage(damage);
    }
}
