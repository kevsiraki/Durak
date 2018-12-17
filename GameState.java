//GameState class.  Creates a game loop.
import java.util.Scanner;
class GameState {
    static void startGame(boolean begin) {
        Scanner s = new Scanner(System.in);
        char choice = ' ';
        while (choice != 'n' && begin == true) {
            Durak.begin();
            System.out.println("\nWould you like to play another game or quit?  (Enter 'y' for \"yes\" and 'n' for \"no\")");
            choice = s.next().charAt(0);
            if (choice == 'n') System.out.println("Thanks for playing.  Hajoxutyun Yexo.");
        }
    }
}