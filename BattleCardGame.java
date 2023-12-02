import java.util.ArrayList;
import java.util.Scanner;

class Main{
    public static void main(String[] args) {
        Game g = new Game();
        g.start();
        for (int i=1;i<=3;i++){
            g.turn(i);
        }
        g.end();
    }
}


public class Game {
    Scanner scanner = new Scanner(System.in);
    int mana=1;
    Player player = new Player();
    Enemy enemy = new Enemy(20);

    void start(){
        System.out.println("+++++++++++++++++++");
        System.out.println("Начало игры");
    }

    void turn(int num){
        mana=num;
        if(num!=1){
            player.toHandFromDeck(1);
        }
        System.out.println("+++++++++++++++++++");
        while (true){
            System.out.println("Mana:"+mana);
            player.printHand();
            System.out.println("Выбери номер карты или 0 для конца раунда");
            int input = scanner.nextInt();
            if (input!=0){
                if(isManaEnough(player.hand.get(input-1))){
                    Card playerCard = player.hand.remove(input-1);
                    enemy.hit(playerCard);
                    mana-= playerCard.mana;
                }else {
                    System.out.println("Не хватает маны");
                }
            }else {
                mana++;
                break;
            }
        }
    }

    boolean isManaEnough(Card c){
        return c.mana <= mana;
    }

    void end(){
        System.out.println("Игра окончена");
        System.out.println("У противника осталось:"+enemy.hp);
        System.out.println("+++++++++++++++++++");
    }
}

class Enemy{
    int hp;

    Enemy(int hp){
        this.hp=hp;
    }

    void hit(Card c){
        hp -= c.damage;
        System.out.println("Получено урона:"+c.damage);
        System.out.println("Осталось:"+hp);
    }
}

class Player{
    Deck d = new Deck();
    ArrayList<Card> hand = new ArrayList<>(5);

    Player(){
        toHandFromDeck(3);
    }

    void toHandFromDeck(int count){
        for (int i = 0; i < count; i++) {
            hand.add(d.getLast());
        }
    }

    void printHand(){
        String s = "";
        for (int i = 0; i < hand.size(); i++) {
            s += "-----   ";
        }
        s+= "\n";
        for (int i = 0; i < hand.size(); i++) {
            s += "|"+ hand.get(i).mana+"  |   ";
        }
        s+= "\n";
        for (int i = 0; i < hand.size(); i++) {
            s += "| "+ hand.get(i).damage+" |   ";
        }
        s+= "\n";
        for (int i = 0; i < hand.size(); i++) {
            s += "-----   ";
        }
        System.out.println(s);
    }
}

class Deck{
    ArrayList<Card> cards = new ArrayList<>(12);

    Deck() {
        int[] numbers = {1,1,1,1,1,1,2,2,2,2,3,3};
        for (int i = 0; i < numbers.length; i++) {
            int randomIndex = (int) (Math.random()*numbers.length);
            cards.add(new Card(numbers[randomIndex]));
        }
    }

    Card getLast(){
        return cards.remove(cards.size()-1);
    }
}

class Card{
    int mana;
    int damage;

    Card(int mana){
        this.mana = mana;
        int random = (int)(Math.random()*3+1);
        this.damage = mana * random;
    }
}
