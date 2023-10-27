package com.example.jainshaadi;



public class CardItem {
    private String postedBy;
    private String profileId;
    private int profileImage;
    private String profileName;
    private String profileWork;
    private String profileBio;
    private String profileBirthDate;
    private String profileCommunity;
    private String profileIncome;
    private String profileLocation;

    public CardItem() {
        // Default constructor required for Firebase
    }

    public CardItem (String postedBy, int profileImage, String profileName, String profileWork, String profileBio, String profileBirthDate, String profileCommunity, String profileIncome, String profileLocation , String profileId) {
        this.postedBy = postedBy;
        this.profileImage = profileImage;
        this.profileName = profileName;
        this.profileWork = profileWork;
        this.profileBio = profileBio;
        this.profileBirthDate = profileBirthDate;
        this.profileCommunity = profileCommunity;
        this.profileIncome = profileIncome;
        this.profileLocation = profileLocation;
        this.profileId = profileId;
    }

    // Getter and Setter methods for all fields
    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getProfileId() {return profileId; }
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileWork() {
        return profileWork;
    }

    public void setProfileWork(String profileWork) {
        this.profileWork = profileWork;
    }

    public String getProfileBio() {
        return profileBio;
    }

    public void setProfileBio(String profileBio) {
        this.profileBio = profileBio;
    }

    public String getProfileBirthDate() {
        return profileBirthDate;
    }

    public void setProfileBirthDate(String profileBirthDate) {
        this.profileBirthDate = profileBirthDate;
    }

    public String getProfileCommunity() {
        return profileCommunity;
    }

    public void setProfileCommunity(String profileCommunity) {
        this.profileCommunity = profileCommunity;
    }

    public String getProfileIncome() {
        return profileIncome;
    }

    public void setProfileIncome(String profileIncome) {
        this.profileIncome = profileIncome;
    }

    public String getProfileLocation() {
        return profileLocation;
    }

    public void setProfileLocation(String profileLocation) {
        this.profileLocation = profileLocation;
    }
}
