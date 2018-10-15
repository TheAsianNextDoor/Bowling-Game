package BowlingScoreExercise;

import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The class that holds Game variables and methods
 *
 * @author aaron
 */
public class Game {

    private static final int maxNumberOfFrames = 10; // set number of frames
    private static int frameCounter = 0;

    private Queue<Integer> spareQueue = new LinkedList<>(); // spare frame Queue
    private Queue<Integer> strikeQueue = new LinkedList<>(); //strike frame Queue
    private Frame[] frameArray = new Frame[maxNumberOfFrames]; // array that holds frames

    public static Queue<Integer> holderQueue = new LinkedList<>(); // queue to hold frames
    public static int gameScore = 0;
    public static final int maxPinCount = 10; //set desired number of pins
    public static Random rand = new Random();

    /**
     * Checks to see if game is on last frame
     *
     * @return boolean True if it is on last frame, false if on any other frame
     */
    public static boolean isLastFrame() {
        return (frameCounter == maxNumberOfFrames - 1);
    }

    /**
     * Calculates score of frame that is neither a strike nor spare
     *
     * @param frame The frame that is to be scored
     */
    public void scoreNormalFrame(Frame frame) {
        if (!frame.isSpare() && !frame.isStrike() && !isLastFrame()) {
            gameScore += (frame.getFirstPoint() + frame.getSecondPoint());
        }
    }

    /**
     * Special case method to score last frame. Last frame may have 3 bowls
     *
     * @param frame The frame that is to be scored
     */
    public void scoreLastFrame(Frame frame) {
        if (isLastFrame()) {
            gameScore += (frame.getFirstPoint() + frame.getSecondPoint()
                    + frame.getThirdPoint());
        }
    }

    /**
     * Calculates score of a spare frame. Spare frame can only be scored after
     * the next frame has completed in order to tally multiplier
     *
     * @param frameCounter The frame that was last bowled
     */
    public void scoreSpareFrame(int frameCounter) {
        if (!spareQueue.isEmpty()) {
            int spareFrame = spareQueue.peek();
            if (++spareFrame == frameCounter) { // checks to see if next frame
                gameScore += 10 + frameArray[spareFrame].getFirstPoint();
                spareQueue.remove();
            }
        }
    }

    /**
     * Calculates the score of strike frame. Strike frames are only scored after
     * two bowls are completed. Strike frame may be scored after 1 or 2 frames
     * depending on the situation
     *
     * @param frameCounter The last frame bowled
     * @param frame The current frame we are on
     */
    public void scoreStrikeFrame(int frameCounter, Frame frame) {
        if (!strikeQueue.isEmpty()) {
            int strikeFrame = strikeQueue.peek();
            int nextPoint = 0;
            int nextNextPoint = 0;

            // if next frame isn't a strike
            if (strikeFrame + 1 == frameCounter && !frame.isStrike()) {
                nextPoint = frameArray[++strikeFrame].getFirstPoint();
                nextNextPoint = frameArray[strikeFrame].getSecondPoint();
                gameScore += (10 + nextPoint + nextNextPoint);
                strikeQueue.remove();
            }
            if (strikeFrame + 1 == frameCounter && isLastFrame()) {
                nextPoint = frameArray[++strikeFrame].getFirstPoint();
                nextNextPoint = frameArray[strikeFrame].getSecondPoint();
                gameScore += (10 + nextPoint + nextNextPoint);
                strikeQueue.remove();
            }
            //if next frame is a strike, skip two frames down
            if (strikeFrame + 2 == frameCounter) {
                nextNextPoint = frameArray[strikeFrame + 2].getFirstPoint();
                gameScore += (20 + nextNextPoint);
                strikeQueue.remove();
                scoreStrikeFrame(frameCounter, frame); // recursive call for special case
            }                                          // Strike, strike, not strike
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();

        for (int i = 0; i < maxNumberOfFrames; i++) {
            Frame frame = new Frame();
            Scanner userInput = new Scanner(System.in);
            boolean inputFlag = true;

            while (inputFlag) {
                System.out.println("\nInput 'R' then Enter to bowl Random frame"
                        + "\nInput 'S' then Enter for Score"
                        + "\nInput 'U' then Enter for User input frame");
                String input = userInput.nextLine();

                if (input.equalsIgnoreCase("U")) {
                    boolean loopFlag = true;

                    while (loopFlag) {
                        System.out.println("\nEnter first, second, and bonus third bowl"
                                + " seperated by a comma");
                        String bowls[] = userInput.nextLine().split(","); // read in first bowl
                        System.out.println("");

                        String firstBowl = "";
                        String secondBowl = "";
                        String thirdBowl = "";

                        if (!Game.isLastFrame() && bowls.length == 2) {
                            firstBowl = bowls[0];
                            secondBowl = bowls[1];

                            if (Frame.isNumeric(firstBowl) && Frame.isNumeric(secondBowl)) // if number
                            {
                                //converts string to number and adds to queue
                                holderQueue.add(Integer.parseInt(firstBowl));
                                holderQueue.add(Integer.parseInt(secondBowl));
                                loopFlag = false;
                            } else {
                                System.out.println("Input must be numeric. Re-enter frame.");
                            }
                        } else {
                            System.out.println("Incorect number of inputs. Re-enter frame."
                                    + " Input must be two numbers on a frame that isn't the last");
                            System.out.println("and three numbers on the last frame. If the bonus bowl "
                                    + "isn't awarded, enter a zero as the third number");
                        }

                        if (Game.isLastFrame()) {
                            if (bowls.length == 3) {
                                firstBowl = bowls[0];
                                secondBowl = bowls[1];
                                thirdBowl = bowls[2];

                                if (Frame.isNumeric(firstBowl) && Frame.isNumeric(secondBowl)
                                        && Frame.isNumeric(thirdBowl)) // if number
                                {
                                    //converts string to number and adds to queue
                                    holderQueue.add(Integer.parseInt(firstBowl));
                                    holderQueue.add(Integer.parseInt(secondBowl));
                                    holderQueue.add(Integer.parseInt(thirdBowl));
                                    loopFlag = false;
                                } else {
                                    System.out.println("Input must be numeric. Re-enter frame");
                                }
                            } else {
                                System.out.println("Incorect number of inputs. Re-enter frame."
                                        + " Input must be two numbers on a frame that isn't the last");
                                System.out.println("and three numbers on the last frame. If the bonus bowl "
                                        + "isn't awarded, enter a zero as the third number");
                            }
                        }
                    }

                    int[] holderArray = new int[holderQueue.size()];
                    //transfer queue to array for recordFrame method
                    int k = 0;
                    for (int point : holderQueue) {
                        holderArray[k++] = point;
                    }
                    holderQueue.clear();// clear queue for next pass

                    frame.recordFrame(holderArray);
                    game.frameArray[frameCounter] = frame; // adds frame to frame array 

                    //add strikes and pars to queue
                    if (frame.isStrike()) {
                        game.strikeQueue.add(frameCounter);
                    }
                    if (frame.isSpare()) {
                        game.spareQueue.add(frameCounter);
                    }

                    //scoring methods
                    game.scoreSpareFrame(frameCounter);
                    game.scoreStrikeFrame(frameCounter, frame);
                    game.scoreNormalFrame(frame);
                    game.scoreLastFrame(frame);

                    frameCounter++;
                    inputFlag = false;

                    System.out.printf("\nJust bowled frame: %d", frameCounter);

                } else if (input.equalsIgnoreCase("R")) {
                    frame.randomRecordFrame(); // bowls frame
                    game.frameArray[frameCounter] = frame; // adds frame to frame array 

                    if (frame.isStrike()) {
                        game.strikeQueue.add(frameCounter);
                    }
                    if (frame.isSpare()) {
                        game.spareQueue.add(frameCounter);
                    }

                    //scoring methods
                    game.scoreSpareFrame(frameCounter);
                    game.scoreStrikeFrame(frameCounter, frame);
                    game.scoreNormalFrame(frame);
                    game.scoreLastFrame(frame);

                    frameCounter++;
                    inputFlag = false;

                    System.out.printf("\nJust bowled frame: %d", frameCounter);
                } else if (input.equalsIgnoreCase("S")) {
                    System.out.println("\nYou are to bowl frame " + (frameCounter + 1));
                    frame.displayScore();
                } else {
                    System.out.println("\nIncorrect input\n");
                }
            }
            frame.printFrameScore();
        }

        //printing methods here on out
        System.out.println("");

        int counter = 1;
        for (Frame f : game.frameArray) {
            System.out.print("\nFrame " + counter + " score: " + f.getFirstPoint()
                    + ", " + f.getSecondPoint() + ", " + f.getThirdPoint());
            counter++;
        }

        System.out.println("\nThe game Score is: " + gameScore);
    }
}
