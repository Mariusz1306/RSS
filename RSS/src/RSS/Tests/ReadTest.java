package RSS.Tests;

import RSS.Model.Feed;
import RSS.Model.FeedMessage;
import RSS.Read.RSSFeedParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadTest {
    public static void main(String[] args) {
        ArrayList<String> links = new ArrayList();
        String file = "test.txt";
        Scanner scan = new Scanner(System.in);
        int linkNumber=0;

        //read file and add links to ArrayList links;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                links.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Which feed do you want to read?");
        for (String link : links) {
            System.out.println(++linkNumber + ": " + link);
        }

        int choice = scan.nextInt();
        String link = links.get(--choice);

        RSSFeedParser parser = new RSSFeedParser(link);
        Feed feed = parser.readFeed();
        System.out.println(feed);
        for (FeedMessage message : feed.getMessages()) {
            System.out.println("\n---\nTitle: " + message.getTitle()
                    .replaceAll("\\n", ""));
            System.out.println("-\nDescription: " + message.getDescription().trim()
                    .replaceAll("\\s{2,}", " ") //if there are multiple spaces, replace them with one space
                    .replaceAll("\\. ", "\\.\n")); //Add newline at the end of sentences
            System.out.println("-\nLink: " + message.getLink());
            System.out.println("-\nAuthor: " + message.getAuthor());
            System.out.println("-\nGuid: " + message.getGuid());
        }
    }
}