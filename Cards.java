//Cards class.  Creates the constructor for creating a card and introduces a method to make a deck of cards from 8-Aces of all 4 suits.
//Jacks equate to 11.
//Queens equate to 12.
//Kings equate to 13.
//Aces equate to 14.
class Cards {
    private String suit;
    private int value;
    Cards(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }
    String getSuit() {
        return suit;
    }
    int getValue() {
        return value;
    }
    static Cards[] makeDeck() {
        Cards[] deck = new Cards[28];
        for (int i = 0; i < 7; i++) deck[i] = new Cards("Clubs", i + 8);
        for (int i = 7; i < 14; i++) deck[i] = new Cards("Diamonds", i + 1);
        for (int i = 14; i < 21; i++) deck[i] = new Cards("Hearts", i - 6);
        for (int i = 21; i <= 27; i++) deck[i] = new Cards("Spades", i - 13);
        return deck;
    }
}