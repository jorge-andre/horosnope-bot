import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TwitterAutoBot {

    private static final int CHARACTER_LIMIT = 280;
    private static List<Signs> SIGNS = Arrays.asList(Signs.values());
    private static final int HOUR = 1000 * 60 * 60; //1000ms * 60seconds * 60minutes

    public static void main(String[] args) {
        tweetLines();
    }

    private static void tweetLines() {
        try {

            Collections.shuffle(SIGNS);
            //Will be used in the future when there's polls. Currently no use for this
            List<String> signsPool = SIGNS
                    .stream()
                    .limit(4)
                    .map(Signs::label)
                    .collect(Collectors.toList());

            String horoscope = requestHoroscope(signsPool.get(0));

            Status firstTweet = sendTweet(horoscope);
            System.out.println(firstTweet);
            Thread.sleep(4 * HOUR);

            if (firstTweet != null) {
                String tweetUrl = "https://twitter.com/" + firstTweet.getUser().getScreenName() + "/status/" + firstTweet.getId();
                sendTweet("Today's horoscope was: " + horoscope + "! How close was it to your day? Did you get it right?" + tweetUrl);
            }
            Thread.sleep(20 * HOUR);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Status sendTweet(String line) throws TwitterException {
        return TwitterFactory.getSingleton().updateStatus(line);
    }

    private static String requestHoroscope(String sign) throws IOException {
        URL url = new URL("http://ohmanda.com/api/horoscope/" + sign);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        InputStreamReader in = new InputStreamReader(connection.getInputStream());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(in);
        in.close();

        String[] splitHoroscope = json.get("horoscope").asText().split("\\.");
        StringBuilder newHoroscope = new StringBuilder();

        for (String sentence : splitHoroscope) {
            if (newHoroscope.length() + sentence.length() < CHARACTER_LIMIT - 1) {
                newHoroscope.append(sentence);
                newHoroscope.append(".");
            }
        }

        return newHoroscope.toString();
    }

}