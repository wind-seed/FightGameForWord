package com.itheima.ui;

import com.itheima.domain.Character;
import com.itheima.domain.EnemyCharacter;
import com.itheima.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class FightingGame {

    //运行游戏
    public void GameStart(String username){
        System.out.println("||===================================================||");
        System.out.println("         " + username + "欢迎来到文字格斗游戏");
        System.out.println("||===================================================||");

        HeroCharacter hero =  createHeroCharter(username);
        hero.skillList.add("普通攻击");
        hero.skillList.add("强力一击");
        hero.skillList.add("生命汲取");

        System.out.println("初始属性：" + hero.show());
        System.out.println("拥有技能：" + hero.showSkill());

        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("战士", 80 , 15, 10, "猛虎下山"));
        enemyList.add(new EnemyCharacter("刺客", 60 , 20, 5, "一刀秒"));
        enemyList.add(new EnemyCharacter("坦克", 120 , 10, 20, "铁山靠"));
        enemyList.add(new EnemyCharacter("法师", 70 , 25, 0, "消费棍来咯"));

        int enemyCount = 1;
        int winCount = 0;
        while(hero.isAlive(hero.HP)){
            //增强敌人
            if(winCount != 0){
                for (int i = 0; i < enemyList.size(); i++) {
                    enemyList.get(i).maxHP += 10;
                    enemyList.get(i).HP = enemyList.get(i).maxHP;
                    enemyList.get(i).attack += 3;
                    enemyList.get(i).defense += 2;
                    enemyList.get(i).defending = false;
                }
            }

            Random r = new Random();
            EnemyCharacter enemy =  enemyList.get(r.nextInt(enemyList.size()));

            System.out.println("==============⚔ 战斗开始 ============");
            int roundCount = 1;
            while (hero.isAlive(hero.HP)){
                System.out.println("==============第" + roundCount +"回合⚔ 战斗开始 ============");
                System.out.println(bloodStrips(hero.name, hero.HP, hero.maxHP));
                System.out.println(bloodStrips(enemy.name, enemy.HP, enemy.maxHP));
                heroTurn(hero, enemy);
                roundCount += 1;
                if(!enemy.isAlive(enemy.HP)){
                    winCount += 1;
                    break;
                }
                enemyTurn(hero, enemy);
//                System.exit(0);
            }
        }
    }


    //创建角色
    public HeroCharacter createHeroCharter(String username){
        System.out.println("您的角色名为" + username);
        int points = 20;
        int[]  AttributePoints = AssignAttributePoints(points);

        HeroCharacter hero = new HeroCharacter(
                username,
                100 + AttributePoints[0] * 10,
                10 + 2 * AttributePoints[1],
                0 + 1 * AttributePoints[2]
        );

        System.out.println("角色创建成功！");
        return hero;

    }

    //分配属性点
    public int[] AssignAttributePoints(int points){
        System.out.println("请分配属性点（共" + points + "点）");
        System.out.println("1. 生命值（每点 + 10 HP）");
        System.out.println("2. 攻击力（每点 + 2 ATK）");
        System.out.println("3. 防御力（每点 + 1 DEF）");

        String[] Attribute = {"生命值", "攻击力", "防御力"};
        int[] AttributePoints = new int[Attribute.length];

        for (int i = 0; i < Attribute.length; i++) {
            System.out.println("分配点数到" + Attribute[i] +"（剩余点数：" + points +"):");
            Scanner sc = new Scanner(System.in);
            int putin = sc.nextInt();

            if(putin > points){
                putin = points;
                System.out.println("属性点不足，剩余属性点全部分配到：" + Attribute[i]);
            }

            if(putin < 0){
                putin = 0;
                System.out.println("输入错误，分配0点属性点到：" + Attribute[i]);
            }

            AttributePoints[i] = putin;
            points -= putin;
        }

        return AttributePoints;
    }

    //血条显示
    public String bloodStrips(String name, int HP, int maxHP){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(":[");
        int BloodBarGrid = 20;

        int BloodBarGridNow = (int)(HP * 1.0 / maxHP * BloodBarGrid);
        for (int i = 0; i < BloodBarGrid; i++) {
            if(i < BloodBarGridNow){
                sb.append("⬛");
            }else{
                sb.append(" ");
            }
        }
        sb.append("] ").append(HP).append("/").append(maxHP).append(" HP");
        return sb.toString();
    }

    //玩家回合
    public void heroTurn(HeroCharacter hero, EnemyCharacter enemy){
        System.out.println("请选择你的操作：");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击");
        System.out.println("3. 生命汲取");

        Scanner sc = new Scanner(System.in);
        String choose = sc.next();
        System.out.println("===================== 你的回合 ===================");
        int HPbefore = enemy.HP;
        switch (choose){
            default -> {
                System.out.println("错误输入，自动选择普通攻击");
                int damage1 = damageCalculation(enemy.defense, hero.attack);
                enemy.takeDamage(damage1);
                int HPafter = enemy.HP;
                System.out.println(hero.name + "使用普通攻击对" + enemy.name + "造成" + (HPbefore - HPafter) + "点伤害");
            }
            case "1" ->{
                int damage1 = damageCalculation(enemy.defense, hero.attack);
                enemy.takeDamage(damage1);
                int HPafter = enemy.HP;
                System.out.println(hero.name + "使用普通攻击对" + enemy.name + "造成" + (HPbefore - HPafter) + "点伤害");
            }
            case "2" ->{
                System.out.println("选择强力一击");
                if(hero.HP > 10) {
                    int damage2 = damageCalculation(hero.defense, (int) (hero.attack * 1.8));
                    hero.takeDamage(10);
                    enemy.takeDamage(damage2);
                    int HPafter2 = enemy.HP;
                    System.out.println(hero.name + "消耗10点血，" + "使用强力一击对" + enemy.name + "造成" + (HPbefore - HPafter2) + "点伤害");
                }else{
                    System.out.println("血量不足，技能发动失败");
                }
            }
            case "3" ->{
                System.out.println("选择生命汲取");
                if(hero.HP > 10) {
                    hero.takeDamage(10);
                    Random r = new Random();
                    int healGet = r.nextInt(21);
                    hero.RestoreLife(healGet);
                    System.out.println(hero.name + "消耗10点血，" + "使用生命汲取，恢复" + healGet + "点生命值");
                }else{
                    System.out.println("血量不足，技能发动失败");
                }
            }
        }
    }

    //敌人回合
    public void enemyTurn(HeroCharacter hero, EnemyCharacter enemy){
        Random r = new Random();
        int choose = r.nextInt(2);
        System.out.println("===================== 敌人的回合 ===================");
        int HPbefore = hero.HP;
        switch (choose){
            case 0 -> {
                int damage1 = damageCalculation(hero.defense, enemy.attack);
                hero.takeDamage(damage1);
                int HPafter = hero.HP;
                System.out.println(enemy.name + "使用普通攻击对" + hero.name + "造成" + (HPbefore - HPafter) + "点伤害");
            }
            case 1 -> {
                enemySkill(enemy, hero);
            }
        }
    }

    //伤害计算
    public int damageCalculation(int defense, int damage){
        return damage - defense > 0 ? damage -defense : 1;
    }

    //敌人技能回合
    public void enemySkill(EnemyCharacter enemy, HeroCharacter hero){
        switch (enemy.skill){
            case "猛虎下山" -> {
                int damage1 = damageCalculation(hero.defense, (int)(enemy.attack * 1.5));
                hero.takeDamage(damage1);
                System.out.println(enemy.name + "使用猛虎下山对" + hero.name + "造成" + damage1 + "点伤害");
            }
            case "一刀秒" -> {
                int damage1 = damageCalculation(hero.defense, (int)(enemy.attack * 1.8));
                hero.takeDamage(damage1);
                System.out.println(enemy.name + "使用一刀秒对" + hero.name + "造成" + damage1 + "点伤害");
            }
            case "铁山靠" -> {
                enemy.defending = true;
                System.out.println(enemy.name + "使用铁山靠，下回合收到伤害减半");
            }
            case "消费棍来咯" -> {
                int damage1 = damageCalculation(hero.defense, (int)(enemy.attack * 0.5));
                hero.takeDamage(damage1);
                int damage2 = damageCalculation(hero.defense, (int)(enemy.attack * 0.5));
                hero.takeDamage(damage2);
                System.out.println(enemy.name + "使用消费棍来咯对" + hero.name + "两个次攻击共造成" + damage1 + "点伤害");
            }
        }
    }
}

