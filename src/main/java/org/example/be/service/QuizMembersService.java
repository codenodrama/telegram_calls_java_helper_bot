package org.example.be.service;

import java.util.List;
import java.util.Random;

public class QuizMembersService {
    public static String randomListString(List<String> listOfUsername) {
        Random random = new Random();
        String reserveString = listOfUsername.get(random.nextInt(listOfUsername.size()));
        String resultString = "";
        int counter = 1;

        while(listOfUsername.size() > 1) {
            int firstIndex = random.nextInt(listOfUsername.size());
            resultString += Integer.toString(counter) + ". " + listOfUsername.get(firstIndex) + " \u21C4 ";
            listOfUsername.remove(firstIndex);
            int secondIndex = random.nextInt(listOfUsername.size());
            resultString += listOfUsername.get(secondIndex) + "\n";
            listOfUsername.remove(secondIndex);
            counter++;
        }

        if(listOfUsername.size() == 1) {
            resultString += Integer.toString(counter) + ". " + listOfUsername.get(0) + " \u21C4 " + reserveString + "\n";
        }

        return resultString;
    }
}
