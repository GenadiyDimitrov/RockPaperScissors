import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
    //region Fields
    public static String[] nameEnum = new String[]{"NONE", "ROCK", " PAPER", " SCISSORS"};
    public static String[] gameStatusEnum = new String[]{"YOU WIN", "YOU LOST", "DRAW", "YOU WIN", "YOU LOST"};
    public static Random rnd;
    public static Scanner sc;
    public static int played = 0;
    public static int won = 0;
    public static int draw = 0;
    public static int statisticsCount = 2;

    //endregion
    //region Main Initialization
    public static void main(String[] args) {
        //initialize scanner
        sc = new Scanner(System.in);
        //initialize random generator
        rnd = new Random();
        logo();
        //Separator
        System.out.printf("%n%n");
        instructions();
        run();
        sc.close();
    }

    public static void instructions() {
        //Instructions how to play
        System.out.println("HOW TO PLAY:");
        System.out.println("To end game please write 'end'");
        System.out.println("For statistics please write 'stat'");
        System.out.println("For settings please write 'settings'");
        System.out.println("For instructions please write 'help' or 'info'");
        System.out.println("To start please chose one of the following:");
        System.out.println("'r' or '1' for rock");
        System.out.println("'p' or '2' for paper");
        System.out.println("'s' or '3' for scissors");
    }

    public static void logo() {
        //Game logo for the start
        System.out.println("Welcome to:");
        System.out.println("    ____                __      ____                               _____        _                               ");
        System.out.println("   / __ \\ ____   _____ / /__   / __ \\ ____ _ ____   ___   _____   / ___/ _____ (_)_____ _____ ____   _____ _____");
        System.out.println("  / /_/ // __ \\ / ___// //_/  / /_/ // __ `// __ \\ / _ \\ / ___/   \\__ \\ / ___// // ___// ___// __ \\ / ___// ___/");
        System.out.println(" / _, _// /_/ // /__ / ,<    / ____// /_/ // /_/ //  __// /      ___/ // /__ / /(__  )(__  )/ /_/ // /   (__  ) ");
        System.out.println("/_/ |_| \\____/ \\___//_/|_|  /_/     \\__,_// .___/ \\___//_/      /____/ \\___//_//____//____/ \\____//_/   /____/  ");
        System.out.println("                                         /_/                                                                    ");
        System.out.println("Game by Genadiy Dimitrov");
    }

    //endregion
    //region Cycle
    public static void run() {
        //get initial command
        String command = sc.nextLine();
        //loop until command = END
        while (!command.equalsIgnoreCase("end")) {
            //default user selection
            int userSelected = 0;
            //switch for functions and indexes
            switch (command.toLowerCase()) {
                case "stat":
                    System.out.println("GAME STATISTICS:");
                    statistics();
                    break;
                case "info":
                case "help":
                    instructions();
                    break;
                case "settings":
                    settings();
                    System.out.println("Continue by: 1.[r]ock 2.[p]aper 3.[s]cissors");
                    System.out.println("Or end by: 'end'");
                    break;
                case "1":
                case "r":
                    //ROCK
                    userSelected = 1;
                    break;
                case "2":
                case "p":
                    //PAPER
                    userSelected = 2;
                    break;
                case "3":
                case "s":
                    //SCISSORS
                    userSelected = 3;
                    break;
                default:
                    System.out.println("Please select valid input.");
                    System.out.println("1.[r]ock 2.[p]aper 3.[s]cissors");
                    System.out.println("'stat' for statistics.");
                    System.out.println("'end' for game end.");
                    break;
            }
            //if valid input
            if (userSelected != 0) {
                //increase played games
                played++;
                //computer selects
                int computerSelected = rnd.nextInt(2) + 1;
                //game status is the difference of indexes + 2
                //smallest difference is 1 - 3 = -2; and we add 2 to start the array from index 0
                int gameStatus = userSelected - computerSelected + 2;
                //print current game result
                System.out.printf("User selected %s, Computer selected %s -> %s%n", nameEnum[userSelected], nameEnum[computerSelected], gameStatusEnum[gameStatus]);
                //depend on game status we increase the appropriate counter
                switch (gameStatus) {
                    case 0:
                    case 3:
                        //WIN
                        won++;
                        break;
                    case 2:
                        //DRAW
                        draw++;
                        break;
                }

                //every X games statistics
                if (statisticsCount > 0) {
                    if (played % statisticsCount == 0) {
                        System.out.printf("Game statistics every %d games:%n", statisticsCount);
                        statistics();
                    }
                }
            }
            //wait for next command
            command = sc.nextLine();
        }
        statistics();
    }

    //endregion
    //region Methods
    public static void statistics() {
        int lost = played - won - draw;
        double winPercent = ((double) won / played) * 100.0;
        double lostPercent = ((double) lost / played) * 100.0;
        double drawPercent = ((double) draw / played) * 100.0;
        System.out.printf("Game played: %d%n", played);
        System.out.printf("W:%d(%.1f%%) ", won, winPercent);
        System.out.printf("L:%d(%.1f%%) ", lost, lostPercent);
        System.out.printf("D:%d(%.1f%%)%n", draw, drawPercent);
    }

    public static void settings() {
        settingsInfo();
        String command = sc.nextLine();
        //loop until command = END
        while (!command.equalsIgnoreCase("exit")) {
            //reset command
            if (command.equalsIgnoreCase("reset")) {
                System.out.println("Are you sure you want to RESET game progress? 'y'-YES / 'n'-NO");
                //confirm reset
                String confirm = sc.nextLine();
                if (confirm.equalsIgnoreCase("y")) {
                    played = 0;
                    won = 0;
                    draw = 0;
                    System.out.println("RESET - Done!");
                }
            } else if (command.toLowerCase().startsWith("info ")) {
                //info command
                String arr = command.substring(5);
                try {
                    int i = Integer.parseInt(arr);
                    if (i < 0) i = 0;
                    statisticsCount = i;
                    if (statisticsCount == 0) {
                        System.out.println("Automatic game info - OFF!");
                    } else System.out.printf("Automatic game info - SET to %d games%n", statisticsCount);
                } catch (Exception e) {
                    System.out.println("Please select valid input: Grater or equals to 0");
                }
            } else {
                System.out.println("Please select valid input.");
                settingsInfo();
            }

            command = sc.nextLine();
        }
    }

    public static void settingsInfo() {
        System.out.println("GAME SETTINGS:");
        System.out.println("To RESET progress type - 'reset'");
        System.out.println("To change automatic statistics info:");
        System.out.println("write 'info X' where X is number of games - 0 stop showing");
        System.out.println("To exit settings type - 'exit'");
    }

    //endregion


}