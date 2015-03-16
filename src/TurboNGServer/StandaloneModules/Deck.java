package TurboNGServer.StandaloneModules;import java.lang.String;import java.lang.System;import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ruijorgeclarateixeira on 10/03/15.
 * Deck Standalone Module.
 */
public class Deck {
    ArrayList<Card> deck;
    public static String[] Suits = {"spades","clubs","diamonds","hearts"};
    Random randomGenerator;

    /**
     * Represents a card in the deck
     */
    public class Card {
        public int number;
        public String suit;

        /**
         * Initializes card
         * @param number Card number
         * @param suit Card suit as a string
         */
        public Card(int number, String suit) {
            this.number = number;
            this.suit = suit;
        }

        /**
         * @return json representation of the card
         */
        public String toJsonString() {
            return "{number:" + this.number + ",suit:" + this.suit + "}";
        }
    }

    /**
     * Random number generator using XORShift Number Generator Algorithms
     * Code from: http://www.javamex.com/tutorials/random_numbers/java_util_random_subclassing.shtml
     */
    public class XORShiftRandom extends Random {
        private long seed = System.nanoTime();

        public XORShiftRandom() {

        }

        protected int next(int nbits) {
            long x = this.seed;
            x ^= (x << 21);
            x ^= (x >>> 35);
            x ^= (x << 4);
            this.seed = x;
            x &= ((1L << nbits) -1);
            return (int) x;
        }
    }

    /**
     *  Creates 52 card deck.
     */
    public Deck () {
        deck = new ArrayList<>(52);
        randomGenerator = new XORShiftRandom();
        for (String suit : Suits) {
            for (int number = 1; number < 14; number++) {
                deck.add(new Card(number, suit));
            }
        }
    }

    /**
     * Remove a random card from the deck and return.
     * @return Card drawn
     */
    public Card draw() {
        if (deck.size() >= 1) {
            return deck.remove(randomGenerator.nextInt(deck.size()));
        }
        else {
            return null;
        }
    }
}
