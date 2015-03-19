package androidassignment.crossover.com.androidassignment;

/**
 * Created by Sunny on 3/19/2015.
 */
public class AuctionItem {
    String Title;
    String Category;
    String Description;
    String MinBid;
    String EndDate;
    String Location;

    void AuctionItem(String title, String category, String description, String minBid,
                     String endDate, String location, String imageLoc) {
        this.setTitle(title);
        this.setCategory(category);
        this.setDescription(description);
        this.setMinBid(minBid);
        this.setEndDate(endDate);
        this.setLocation(location);
        this.setImageLoc(imageLoc);
    }

    public String getImageLoc() {
        return ImageLoc;
    }

    public void setImageLoc(String imageLoc) {
        ImageLoc = imageLoc;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMinBid() {
        return MinBid;
    }

    public void setMinBid(String minBid) {
        MinBid = minBid;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    String ImageLoc;

}