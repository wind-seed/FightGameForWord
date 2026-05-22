import com.itheima.ui.Login;
import com.itheima.ui.FightingGame;
public class App {

    public static void main(String[] args) {
        //启动类
        //只负责启动程序
//        Login l = new Login();
//        l.start();
        FightingGame fg = new FightingGame();
        fg.GameStart("黄师兄");

    }
}
