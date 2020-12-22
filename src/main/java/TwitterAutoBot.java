import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.*;
import java.nio.charset.Charset;

public class TwitterAutoBot {

    public static void main(String[] args) {
        tweetLines();
    }

    private static void tweetLines() {
        String line;
        InputStream fis;
        InputStreamReader isr;
        BufferedReader br;
        try {
            //fis = new FileInputStream("filepath");
            //isr = new InputStreamReader(fis, Charset.forName("Cp1252"));
            //br = new BufferedReader(isr);

            Status firstTweet = sendTweet("test boop");
            Thread.sleep(10000);

            if (firstTweet != null) {
                String tweetUrl = "https://twitter.com/" + firstTweet.getUser().getScreenName() + "/status/" + firstTweet.getId();
                sendTweet("beep retweet test " + tweetUrl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Status sendTweet(String line) {
        Twitter twitter = TwitterFactory.getSingleton();
        Status status;
        try {
            status = twitter.updateStatus(line);
            System.out.println(status);
            return status;
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }

}