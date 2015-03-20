package androidassignment.crossover.com.androidassignment;

/**
 * Created by Sunny on 3/19/2015.
 */
public class AuctionItem {

    private String Title;
    private String Category;
    private String Description;
    private String MinBid;
    private String EndDate;
    private String Location;
    private int UserId;

    public Boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    private Boolean isEditable=false;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public int getMaxBid() {
        return MaxBid;
    }

    public void setMaxBid(int maxBid) {
        MaxBid = maxBid;
    }

    private int Count;
    private int MaxBid;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    void AuctionItem(String title, String category, String description, String minBid,
                     String endDate, String location, int imageLoc, int userId) {
        this.setTitle(title);
        this.setCategory(category);
        this.setDescription(description);
        this.setMinBid(minBid);
        this.setEndDate(endDate);
        this.setLocation(location);
        this.setImageLoc(imageLoc);
        this.setUserId(userId);
    }

    public int getImageLoc() {
        return ImageLoc;
    }

    public void setImageLoc(int imageLoc) {
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

    private int ImageLoc;

}
