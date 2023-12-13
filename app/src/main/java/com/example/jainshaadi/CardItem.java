package com.example.jainshaadi;


import android.util.Log;

public class CardItem {
    private String Account_Managed_for;
    private String Category;
    private String City;
    private String Company;
    private String DateOfBirth;
    private String Description;
    private String Height;
    private String IncomeRange;
    private String IncomeType;
    private String Interest1;
    private String Interest2;
    private String Interest3;
    private String Interest4;
    private String Interest5;
    private String Interest6;
    private String Name;
    private String Subcategory;
    private String Role;
    private String State;
    private String Age;
    private String imageUrl1;
    private String profileId;
    private String profileGender;
    private String FamilyMembers;
    private String Familytype;
    private String FatherName;
    private String FatherOccupation;
    private String MotherName;
    private String ParentCity;
    private String ParentState;
    private String Degree;
    private String College;




    public CardItem() {
        // Default constructor required for Firebase
    }

    public CardItem(String Account_Managed_for, String ProfileGender, String ProfileId, String ImageUrl1, String age, String category, String city, String company, String dateOfBirth, String description, String height, String incomeRange, String incomeType, String interest1, String interest2, String interest3, String interest4, String interest5, String interest6, String name, String subcategory, String role, String state, String familyMembers, String parentCity) {

        Account_Managed_for = Account_Managed_for;
        Category = category;
        City = city;
        Age = age;
        Company = company;
        DateOfBirth = dateOfBirth;
        Description = description;
        Height = height;
        IncomeRange = incomeRange;
        IncomeType = incomeType;
        Interest1 = interest1;
        Interest2 = interest2;
        Interest3 = interest3;
        Interest4 = interest4;
        Interest5 = interest5;
        Interest6 = interest6;
        Name = name;
        Subcategory = subcategory;
        Role = role;
        State = state;
        imageUrl1 = ImageUrl1;
        profileId = ProfileId;
        profileGender = ProfileGender;

        FamilyMembers = familyMembers;
        ParentCity = parentCity;
//        Log.e("ac","account = "+account);
    }

    public String getFamilytype() {
        return Familytype;
    }

    public void setFamilytype(String familytype) {
        Familytype = familytype;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getFatherOccupation() {
        return FatherOccupation;
    }

    public void setFatherOccupation(String fatherOccupation) {
        FatherOccupation = fatherOccupation;
    }

    public String getMotherName() {
        return MotherName;
    }

    public void setMotherName(String motherName) {
        MotherName = motherName;
    }

    public String getParentCity() {
        return ParentCity;
    }

    public void setParentCity(String parentCity) {
        ParentCity = parentCity;
    }

    public String getParentState() {
        return ParentState;
    }

    public void setParentState(String parentState) {
        ParentState = parentState;
    }

    public String getDegree() {
        return Degree;
    }

    public void setDegree(String degree) {
        Degree = degree;
    }

    public String getCollege() {
        return College;
    }

    public void setCollege(String college) {
        College = college;
    }

    public String getFamilyMembers() {
        return FamilyMembers;
    }

    public void setFamilyMembers(String familyMembers) {
        FamilyMembers = familyMembers;
    }

    public String getAccount_Managed_for() {
        if(Account_Managed_for.equals("Myself"))
        return "Managed By Self";
        else if (Account_Managed_for.equals("My Sister") || Account_Managed_for.equals("My Brother" ))
        {
            return "Managed By Their Siblings";
        }
        else if (Account_Managed_for.equals("My Son") || Account_Managed_for.equals("My Daughter"))
        {
            return "Managed By Their Parents";
        }
        else
        {
            return "Managed By Their Relatives";
        }
//        return Account_Managed_for;
    }

    public void setAccount_Manged_for(String Account_Managed_for) {
        Account_Managed_for = Account_Managed_for;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getIncomeRange() {
        return IncomeRange;
    }

    public void setIncomeRange(String incomeRange) {
        IncomeRange = incomeRange;
    }

    public String getIncomeType() {
        return IncomeType;
    }

    public void setIncomeType(String incomeType) {
        IncomeType = incomeType;
    }

    public String getInterest1() {
        return Interest1;
    }

    public void setInterest1(String interest1) {
        Interest1 = interest1;
    }

    public String getInterest2() {
        return Interest2;
    }

    public void setInterest2(String interest2) {
        Interest2 = interest2;
    }

    public String getInterest3() {
        return Interest3;
    }

    public void setInterest3(String interest3) {
        Interest3 = interest3;
    }

    public String getInterest4() {
        return Interest4;
    }

    public void setInterest4(String interest4) {
        Interest4 = interest4;
    }

    public String getInterest5() {
        return Interest5;
    }

    public void setInterest5(String interest5) {
        Interest5 = interest5;
    }

    public String getInterest6() {
        return Interest6;
    }

    public void setInterest6(String interest6) {
        Interest6 = interest6;
    }

    public String getName() {
        if(Name.length() >= 23)
        {
            int firstSpaceIndex = Name.indexOf(" ");
            if (firstSpaceIndex != -1) {
                Name = Name.substring(0, firstSpaceIndex);
            }
        }
        return Name;
    }

    public void setName(String name) {
        if(name.length() >= 23)
        {
            int firstSpaceIndex = name.indexOf(" ");
            if (firstSpaceIndex != -1) {
                Name = name.substring(0, firstSpaceIndex);
            } else {
                Name = name;
            }
        }
    }

    public String getSubcategory() {
        return Subcategory;
    }

    public void setSubcategory(String subcategory) {
        Subcategory = subcategory;
    }

    public String getRole() {

        return Role + " at "+ Company;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileGender() {
        return profileGender;
    }

    public void setProfileGender(String profileGender) {
        this.profileGender = profileGender;
    }
}