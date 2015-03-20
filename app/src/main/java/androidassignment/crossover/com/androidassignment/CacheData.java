package androidassignment.crossover.com.androidassignment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 3/20/2015.
 */
public class CacheData {
    public static boolean isWonDataPresent() {
        return WonDataPresent;
    }

    public static void setWonDataPresent(boolean wonDataPresent) {
        WonDataPresent = wonDataPresent;
    }

    public static boolean isLostDataPresent() {
        return LostDataPresent;
    }

    public static void setLostDataPresent(boolean lostDataPresent) {
        LostDataPresent = lostDataPresent;
    }

    public static boolean isActiveDataPresent() {
        return ActiveDataPresent;
    }

    public static void setActiveDataPresent(boolean activeDataPresent) {
        ActiveDataPresent = activeDataPresent;
    }

    private static boolean WonDataPresent, LostDataPresent, ActiveDataPresent;

    public static List<AuctionItem> getActiveAuctions() {
        return ActiveAuctions;
    }

    public static void setActiveAuctions(List<AuctionItem> activeAuctions) {
        ActiveAuctions = activeAuctions;
    }

    public static List<AuctionItem> getLostAuctions() {
        return LostAuctions;
    }

    public static void setLostAuctions(List<AuctionItem> lostAuctions) {
        LostAuctions = lostAuctions;
    }

    public static List<AuctionItem> getWonAuctions() {
        return WonAuctions;
    }

    public static void setWonAuctions(List<AuctionItem> wonAuctions) {
        WonAuctions = wonAuctions;
    }

    private static List<AuctionItem> ActiveAuctions;
    private static List<AuctionItem> LostAuctions;
    private static List<AuctionItem> WonAuctions;

    CacheData() {
        ActiveAuctions = new ArrayList<AuctionItem>();
        LostAuctions = new ArrayList<AuctionItem>();
        WonAuctions = new ArrayList<AuctionItem>();
    }
}
