package BowlingScoreExercise;

import static BowlingScoreExercise.Game.rand;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * The class that holds frame variables and methods
 *
 * @author aaron
 */
public class Frame implements IBowlingScore {

    private int[] frameScore = new int[3];
    private int currentPinCount = Game.maxPinCount + 1; // (+1) for random method

    /**
     * This method randomly rolls the ball on a frame by frame interval. It
     * handles normal and bonus frames.
     */
    public void randomRecordFrame() {
        if (!Game.isLastFrame()) { // normal frame logic
            setFirstPoint(rand.nextInt(currentPinCount)); // first bowl of frame
            currentPinCount -= getFirstPoint(); // update the current pin count
            if (!isStrike()) {
                setSecondPoint(rand.nextInt(currentPinCount)); // bowl a second time if not strike
            }
            if (isStrike()) {
                setSecondPoint(0); // sets second bowl to zero
            }
        }
        if (Game.isLastFrame()) { // last frame logic
            setFirstPoint(rand.nextInt(currentPinCount));
            if (isStrike()) {
                setSecondPoint(rand.nextInt(currentPinCount));
                if (getSecondPoint() == Game.maxPinCount) // if second throw is also a strike
                {
                    setThirdPoint(rand.nextInt(currentPinCount));
                }
                if (getSecondPoint() != Game.maxPinCount) { // if second throw isn't stike
                    currentPinCount -= getSecondPoint();
                    setThirdPoint(rand.nextInt(currentPinCount));
                }
            }
            if (!isStrike()) {
                currentPinCount -= getFirstPoint();
                setSecondPoint(rand.nextInt(currentPinCount));
                if (isSpare()) {                             // if second throw is a spare
                    currentPinCount = Game.maxPinCount + 1; //reset pin count for bonus bowl
                    setThirdPoint(rand.nextInt(currentPinCount));
                }
            }
        }
    }

    /**
     * This method handles setting the user inputed frame into the frame object
     *
     * @param pinsKnockedDown An array that contains the number of pins knocked
     * down
     */
    @Override
    public void recordFrame(int[] pinsKnockedDown) {

        setFirstPoint(pinsKnockedDown[0]);
        setSecondPoint(pinsKnockedDown[1]);

        try {
            setThirdPoint(pinsKnockedDown[2]);
        } catch (Exception e) {

        }
    }

    /**
     * This method allows the user to view the score whenever they desire
     *
     * @return the current score
     */
    @Override
    public int displayScore() {
        System.out.println("\nThe Score is: " + Game.gameScore);
        return Game.gameScore;
    }

    /**
     * This method is used to to double check if the String input is only
     * numeric
     *
     * @param str The string that you are testing
     * @return True if the String is numeric and False if not
     */
    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    /**
     * Retrieves the first point of the frame
     *
     * @return the first point
     */
    public int getFirstPoint() {
        return frameScore[0];
    }

    /**
     * Sets the first point of the frame
     *
     * @param num The number to set
     */
    public void setFirstPoint(int num) {
        frameScore[0] = num;
    }

    /**
     * Retrieves the second point of the frame
     *
     * @return the second point of the frame
     */
    public int getSecondPoint() {
        return frameScore[1];
    }

    /**
     * Sets the second point of the frame
     *
     * @param num The number to set
     */
    public void setSecondPoint(int num) {
        frameScore[1] = num;
    }

    /**
     * Retrieves the third point of the frame
     *
     * @return the third point of the frame
     */
    public int getThirdPoint() {
        return frameScore[2];
    }

    /**
     * Sets the third point of the frame
     *
     * @param num The number to set
     */
    public void setThirdPoint(int num) {
        frameScore[2] = num;
    }

    /**
     * Checks to see if the frame is a spare
     *
     * @return True if is a spare and False if not a spare
     */
    public boolean isSpare() {
        return (frameScore[0] != Game.maxPinCount
                && IntStream.of(frameScore).sum() == Game.maxPinCount);
    }

    /**
     * Checks to see if the frame is a strike
     *
     * @return True if is a strike and False if not a strike
     */
    public boolean isStrike() {
        return (frameScore[0] == Game.maxPinCount);
    }

    /**
     * Prints the frame score
     */
    public void printFrameScore() {
        System.out.println("\n" + Arrays.toString(frameScore));
    }
}
