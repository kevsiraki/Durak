//Durak class.  In charge of the core mechanics and rules of the game.
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
class Durak {
    static void begin() {
   
        Cards AI_playedCard;
        Cards playerPlayedCard;
        Cards playerCard;
        Cards AI_Defense;
        Cards AI_drawnCard;
        Cards playerDrawnCard;
        Cards[] deck = Cards.makeDeck();
        shuffleArray(deck);
        Cards[] playerDeck = deal(deck);
        Cards[] AI_Deck = dealAI(deck);
        boolean checkPlay;
        boolean playerTurn = false;
        int countInvalidPlays = 0;
        String trumpCard = " ";
        int trumpCardValue = 0;
        System.out.println("Your starting deck: " + playerDeck.length + "\n");
        for (int i = 0; i < 6; i++) System.out.println(playerDeck[i].getValue() + " of " + playerDeck[i].getSuit());
        System.out.println("\nAI starting deck: " + AI_Deck.length + "\n");
        for (int i = 0; i < 6; i++) System.out.println(AI_Deck[i].getValue() + " of " + AI_Deck[i].getSuit());
        List deckInitial = Arrays.asList(deck);
        List < Cards > newDeckInitial = new ArrayList < Cards > (deckInitial);
        List playerDeckInitial = Arrays.asList(playerDeck);
        List < Cards > newPlayerDeckInitial = new ArrayList < Cards > (playerDeckInitial);
        List AI_DeckInitial = Arrays.asList(AI_Deck);
        List < Cards > newAIDeckInitial = new ArrayList < Cards > (AI_DeckInitial);
        newDeckInitial.removeAll(newPlayerDeckInitial);
        newDeckInitial.removeAll(newAIDeckInitial);
        Cards[] deckAfterInitialDeal = newDeckInitial.toArray(new Cards[newDeckInitial.size()]);
        System.out.println("\nThe remaining cards in the deck are: " + deckAfterInitialDeal.length + "\n");
        for (int i = 0; i < deckAfterInitialDeal.length; i++) System.out.println(deckAfterInitialDeal[i].getValue() + " of " + deckAfterInitialDeal[i].getSuit());
        trumpCard = determineTrumpCard(newDeckInitial);
        trumpCardValue = determineTrumpCardValue(newDeckInitial);
        while (newPlayerDeckInitial.isEmpty() != true && newAIDeckInitial.isEmpty() != true) {
            if (newDeckInitial.isEmpty() != true) {
                if (newPlayerDeckInitial.size() < 6) {
                    playerDrawnCard = draw(deck, newDeckInitial);
                    newPlayerDeckInitial.add(playerDrawnCard);
                    newDeckInitial.remove(playerDrawnCard);
                }
                if (newAIDeckInitial.size() < 6) {
                    AI_drawnCard = draw(deck, newDeckInitial);
                    newAIDeckInitial.add(AI_drawnCard);
                    newDeckInitial.remove(AI_drawnCard);
                }
            }
            while (playerTurn == true) {
                System.out.println("\nTrump card is: "+trumpCardValue+" of " + trumpCard + ".\n");
                playerCard = playerPlay();
                Cards[] playerDeckAfterPlay = newPlayerDeckInitial.toArray(new Cards[newPlayerDeckInitial.size()]);
                for (int i = 0; i < playerDeckAfterPlay.length; i++)
                    if (playerCard.getValue() == playerDeckAfterPlay[i].getValue() && playerCard.getSuit().equals(playerDeckAfterPlay[i].getSuit())) newPlayerDeckInitial.remove(playerDeckAfterPlay[i]);
                newPlayerDeckInitial.remove(playerCard);
                AI_Defense = AI_PLAY_DEFENSE(newAIDeckInitial, playerCard, trumpCard);
                if (AI_Defense.getValue() == 0) {
                    if (newDeckInitial.isEmpty() != true) {
                        while (newPlayerDeckInitial.size() < 6) {
                            playerDrawnCard = draw(deck, newDeckInitial);
                            newPlayerDeckInitial.add(playerDrawnCard);
                            newDeckInitial.remove(playerDrawnCard);
                        }
                        while (newAIDeckInitial.size() < 6) {
                            AI_drawnCard = draw(deck, newDeckInitial);
                            newAIDeckInitial.add(AI_drawnCard);
                            newDeckInitial.remove(AI_drawnCard);
                        }
                    }
                    newPlayerDeckInitial.remove(playerCard);
                    newAIDeckInitial.add(playerCard);
                    playerTurn = true;
                    Cards[] playerDeckAfterDraw = newPlayerDeckInitial.toArray(new Cards[newPlayerDeckInitial.size()]);
                    System.out.println("Player remaining Cards: " + newPlayerDeckInitial.size());
                    for (int i = 0; i < playerDeckAfterDraw.length; i++) System.out.println(playerDeckAfterDraw[i].getValue() + " of " + playerDeckAfterDraw[i].getSuit());
                    Cards[] AI_deckAfterDraw = newAIDeckInitial.toArray(new Cards[newAIDeckInitial.size()]);
                    System.out.println("AI remaining Cards: " + newAIDeckInitial.size());
                    for (int i = 0; i < AI_deckAfterDraw.length; i++) System.out.println(AI_deckAfterDraw[i].getValue() + " of " + AI_deckAfterDraw[i].getSuit());
                    Cards[] deckAfterDraw = newDeckInitial.toArray(new Cards[newDeckInitial.size()]);
                    System.out.println("Remaining Cards in Deck: " + newDeckInitial.size() + " Player Turn: " + playerTurn);
                    for (int i = 0; i < deckAfterDraw.length; i++) System.out.println(deckAfterDraw[i].getValue() + " of " + deckAfterDraw[i].getSuit());
                    if (newDeckInitial.isEmpty() != true) {
                        while (newPlayerDeckInitial.size() < 6) {
                            playerDrawnCard = draw(deck, newDeckInitial);
                            newPlayerDeckInitial.add(playerDrawnCard);
                            newDeckInitial.remove(playerDrawnCard);
                        }
                        while (newAIDeckInitial.size() < 6) {
                            AI_drawnCard = draw(deck, newDeckInitial);
                            newAIDeckInitial.add(AI_drawnCard);
                            newDeckInitial.remove(AI_drawnCard);
                        }
                    } else if (newDeckInitial.isEmpty() == true) {
                        if (newDeckInitial.isEmpty() == true && newPlayerDeckInitial.isEmpty() == true) {
                            System.out.println("YOU WON!");
                            break;
                        } else if (newDeckInitial.isEmpty() == true && newAIDeckInitial.isEmpty() == true) {
                            System.out.println("YOU LOST. :(");
                            break;
                        }
                        break;
                    }
                } else if (AI_Defense.getValue() > 0) {
                    if (newDeckInitial.isEmpty() != true) {
                        while (newPlayerDeckInitial.size() < 6) {
                            playerDrawnCard = draw(deck, newDeckInitial);
                            newPlayerDeckInitial.add(playerDrawnCard);
                            newDeckInitial.remove(playerDrawnCard);
                        }
                        while (newAIDeckInitial.size() < 6) {
                            AI_drawnCard = draw(deck, newDeckInitial);
                            newAIDeckInitial.add(AI_drawnCard);
                            newDeckInitial.remove(AI_drawnCard);
                        }
                    }
                    newPlayerDeckInitial.remove(playerCard);
                    newAIDeckInitial.remove(AI_PLAY_DEFENSE(newAIDeckInitial, playerCard, trumpCard));
                    playerTurn = false;
                    Cards[] playerDeckAfterDraw = newPlayerDeckInitial.toArray(new Cards[newPlayerDeckInitial.size()]);
                    System.out.println("Player remaining Cards: " + newPlayerDeckInitial.size());
                    for (int i = 0; i < playerDeckAfterDraw.length; i++) System.out.println(playerDeckAfterDraw[i].getValue() + " of " + playerDeckAfterDraw[i].getSuit());
                    Cards[] AI_deckAfterDraw = newAIDeckInitial.toArray(new Cards[newAIDeckInitial.size()]);
                    System.out.println("AI remaining Cards: " + newAIDeckInitial.size());
                    for (int i = 0; i < AI_deckAfterDraw.length; i++) System.out.println(AI_deckAfterDraw[i].getValue() + " of " + AI_deckAfterDraw[i].getSuit());
                    Cards[] deckAfterDraw = newDeckInitial.toArray(new Cards[newDeckInitial.size()]);
                    System.out.println("Remaining Cards in Deck: " + newDeckInitial.size() + " Player Turn: " + playerTurn);
                    for (int i = 0; i < deckAfterDraw.length; i++) System.out.println(deckAfterDraw[i].getValue() + " of " + deckAfterDraw[i].getSuit());
                    if (newDeckInitial.isEmpty() != true) {
                        while (newPlayerDeckInitial.size() < 6) {
                            playerDrawnCard = draw(deck, newDeckInitial);
                            newPlayerDeckInitial.add(playerDrawnCard);
                            newDeckInitial.remove(playerDrawnCard);
                        }
                        while (newAIDeckInitial.size() < 6) {
                            AI_drawnCard = draw(deck, newDeckInitial);
                            newAIDeckInitial.add(AI_drawnCard);
                            newDeckInitial.remove(AI_drawnCard);
                        }
                    } else if (newDeckInitial.isEmpty() == true) {
                        if (newDeckInitial.isEmpty() == true && newPlayerDeckInitial.isEmpty() == true) {
                            System.out.println("YOU WON!");
                            break;
                        } else if (newDeckInitial.isEmpty() == true && newAIDeckInitial.isEmpty() == true) {
                            System.out.println("YOU LOST. :(");
                            break;
                        }
                    }
                }
            }
            while (playerTurn == false) {
                System.out.println("\nTrump card is: "+trumpCardValue+" of " + trumpCard + ".\n");
                AI_playedCard = AI_PLAY(newAIDeckInitial);
                System.out.println("\nAI has played: " + AI_playedCard.getValue() + " of " + AI_playedCard.getSuit());
                newAIDeckInitial.remove(AI_playedCard);
                System.out.println("\n");
                playerCard = playerPlay();
                Cards[] playerDeckAfterPlay = newPlayerDeckInitial.toArray(new Cards[newPlayerDeckInitial.size()]);
                checkPlay = validPlay(playerCard, AI_playedCard, newPlayerDeckInitial, trumpCard);
                if (checkPlay == true) {
                    System.out.println("Valid Play!\n");
                    for (int i = 0; i < playerDeckAfterPlay.length; i++)
                        if (playerCard.getValue() == playerDeckAfterPlay[i].getValue() && playerCard.getSuit().equals(playerDeckAfterPlay[i].getSuit())) newPlayerDeckInitial.remove(playerDeckAfterPlay[i]);
                    playerTurn = true;
                } else if (playerCard.getValue() == 0) {
                    System.out.println("You have taken the card.\n");
                    newPlayerDeckInitial.add(AI_playedCard);
                    playerTurn = false;
                } else {
                    while (checkPlay == false) {
                        System.out.print("Invalid Play! Try Again, or enter 0 as card value to take.\n");
                        playerCard = playerPlay();
                        checkPlay = validPlay(playerCard, AI_playedCard, newPlayerDeckInitial, trumpCard);
                        if (checkPlay == true) {
                            System.out.print("Valid Play!");
                            for (int i = 0; i < playerDeck.length; i++) {
                                if (playerCard.getValue() == playerDeck[i].getValue() && playerCard.getSuit().equals(playerDeck[i].getSuit())) {
                                    newPlayerDeckInitial.remove(playerDeck[i]);
                                }
                            }
                        } else if (playerCard.getValue() == 0) {
                            System.out.println("You have taken the card. ");
                            newPlayerDeckInitial.add(AI_playedCard);
                            playerTurn = false;
                            break;
                        }
                    }
                }
                if (newDeckInitial.isEmpty() != true) {
                    while (newPlayerDeckInitial.size() < 6) {
                        playerDrawnCard = draw(deck, newDeckInitial);
                        newPlayerDeckInitial.add(playerDrawnCard);
                        newDeckInitial.remove(playerDrawnCard);
                    }
                    while (newAIDeckInitial.size() < 6) {
                        AI_drawnCard = draw(deck, newDeckInitial);
                        newAIDeckInitial.add(AI_drawnCard);
                        newDeckInitial.remove(AI_drawnCard);
                    }
                } else if (newDeckInitial.isEmpty() == true) {
                    if (newDeckInitial.isEmpty() == true && newPlayerDeckInitial.isEmpty() == true) {
                        System.out.println("YOU WON!");
                        break;
                    } else if (newDeckInitial.isEmpty() == true && newAIDeckInitial.isEmpty() == true) {
                        System.out.println("YOU LOST. :(");
                        break;
                    }
                }
                Cards[] playerDeckAfterDraw = newPlayerDeckInitial.toArray(new Cards[newPlayerDeckInitial.size()]);
                System.out.println("Player remaining Cards: " + newPlayerDeckInitial.size());
                for (int i = 0; i < playerDeckAfterDraw.length; i++) System.out.println(playerDeckAfterDraw[i].getValue() + " of " + playerDeckAfterDraw[i].getSuit());
                Cards[] AI_deckAfterDraw = newAIDeckInitial.toArray(new Cards[newAIDeckInitial.size()]);
                System.out.println("AI remaining Cards: " + newAIDeckInitial.size());
                for (int i = 0; i < AI_deckAfterDraw.length; i++) System.out.println(AI_deckAfterDraw[i].getValue() + " of " + AI_deckAfterDraw[i].getSuit());
                Cards[] deckAfterDraw = newDeckInitial.toArray(new Cards[newDeckInitial.size()]);
                System.out.println("Remaining Cards in Deck: " + newDeckInitial.size() + " Player Turn: " + playerTurn);
                for (int i = 0; i < deckAfterDraw.length; i++) System.out.println(deckAfterDraw[i].getValue() + " of " + deckAfterDraw[i].getSuit());
            }
        }
    }
    static Cards AI_PLAY(List < Cards > newAIDeckInitial) {
        Cards[] AI_Deck = newAIDeckInitial.toArray(new Cards[newAIDeckInitial.size()]);
        shuffleArray(AI_Deck);
        return AI_Deck[0];
    }
    static String determineTrumpCard(List <Cards> deck) {
        Cards[] newDeck = deck.toArray(new Cards[deck.size()]);
        return newDeck[newDeck.length-1].getSuit();
    }
    static int determineTrumpCardValue(List <Cards> deck) {
        Cards[] newDeck = deck.toArray(new Cards[deck.size()]);
        return newDeck[newDeck.length-1].getValue();
    }
    static Cards AI_PLAY_DEFENSE(List < Cards > newAIDeckInitial, Cards against, String trumpCard) {
        Cards played = new Cards("", 0);
        Cards[] AI_Deck = newAIDeckInitial.toArray(new Cards[newAIDeckInitial.size()]);
        for (int i = 0; i < AI_Deck.length; i++) {
            if (against.getSuit().equals(AI_Deck[i].getSuit()) && against.getValue() < AI_Deck[i].getValue()) {
                played = AI_Deck[i];
                return played;
            } else if (AI_Deck[i].getSuit().equals(trumpCard) && against.getSuit().equals(trumpCard) == false) {
                played = AI_Deck[i];
                return played;
            }
        }
        return played;
    }
    static Cards playerPlay() {
        Scanner s = new Scanner(System.in);
        String cardSuit;
        int cardValue;
        System.out.println("Enter a card value of the same suit to play from your deck or enter 0 to take the card: ");
        cardValue = s.nextInt();
        s.nextLine();
        System.out.println("Enter a card suit to play from your deck or enter 0 to take the card: ");
        cardSuit = s.nextLine();
        Cards card = new Cards(cardSuit, cardValue);
        return card;
    }
    static boolean validPlay(Cards played, Cards against, List < Cards > playerDeck, String trumpCard) {
        boolean flag = false;
        Cards[] newPlayerDeck = playerDeck.toArray(new Cards[playerDeck.size()]);
        for (int i = 0; i < newPlayerDeck.length; i++) {
            if (played.getSuit().equals(newPlayerDeck[i].getSuit()) && played.getValue() == (newPlayerDeck[i].getValue())) {
                flag = true;
                break;
            } else flag = false;
        }
        if (flag == true) {
            if (played.getSuit().equals(against.getSuit()) && played.getValue() > against.getValue()) return true;
            else if (played.getSuit().equals(trumpCard) && (against.getSuit().equals(trumpCard)) == false) return true;
            else return false;
        } else return false;
    }
    static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    static Cards[] deal(Cards[] deck) {
        Cards[] playerDeck = new Cards[6];
        int[] list = rand(0, 14, 6);
        for (int i = 0; i < 6; i++) {
            playerDeck[i] = deck[list[i]];
        }
        return playerDeck;
    }
    static Cards[] dealAI(Cards[] deck) {
        Cards[] playerDeck = new Cards[6];
        int[] list = rand(15, 27, 6);
        for (int i = 0; i < 6; i++) {
            playerDeck[i] = deck[list[i]];
        }
        return playerDeck;
    }
    static int[] rand(int start, int end, int count) {
        Random rng = new Random();
        int[] result = new int[count];
        int cur = 0;
        int remaining = end - start;
        for (int i = start; i < end && count > 0; i++) {
            double probability = rng.nextDouble();
            if (probability < ((double) count) / (double) remaining) {
                count--;
                result[cur++] = i;
            }
            remaining--;
        }
        return result;
    }
    static Cards draw(Cards[] deck, List < Cards > newDeckInitial) {
        Cards[] newPlayerDeckInitial = newDeckInitial.toArray(new Cards[newDeckInitial.size()]);
        if (newDeckInitial.isEmpty() != true) return newPlayerDeckInitial[0];
        else return null;
    }
    static void shuffleArray(Cards[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Cards a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}