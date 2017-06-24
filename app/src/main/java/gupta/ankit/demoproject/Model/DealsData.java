package gupta.ankit.demoproject.Model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

public class DealsData {

    public byte[] imageByteArray;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    /*@SerializedName("fpd_flag")
    @Expose
    private Boolean fpdFlag;
    @SerializedName("off_percent")
    @Expose
    private String offPercent;
    @SerializedName("current_price")
    @Expose
    private Integer currentPrice;
    @SerializedName("original_price")
    @Expose
    private Integer originalPrice;*/
    @SerializedName("image")
    @Expose
    private String image;
   /* @SerializedName("comments_count")
    @Expose
    private Integer commentsCount;
    @SerializedName("all_posts_count")
    @Expose
    private Integer allPostsCount;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("vote_value")
    @Expose
    private Integer voteValue;
    @SerializedName("state")
    @Expose
    private String state;
    */
    @SerializedName("description")
    @Expose
    private String description;
    /*/
    @SerializedName("share_url")
    @Expose
    private String shareUrl;
    @SerializedName("deal_url")
    @Expose
    private String dealUrl;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("vote_down_reason")
    @Expose
    private VoteDownReason voteDownReason;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("fpd_suggestted")
    @Expose
    private Boolean fpdSuggestted;
    @SerializedName("front_page_suggestions_count")
    @Expose
    private Integer frontPageSuggestionsCount;
    *//**//*@SerializedName("merchant")
    @Expose
    private Merchant merchant;
    @SerializedName("user")
    @Expose
    private User user;*/

    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public boolean isFromDatabase() {
        return isFromDatabase;
    }

    public void setFromDatabase(boolean fromDatabase) {
        isFromDatabase = fromDatabase;
    }

    private Bitmap picture;

    private boolean isFromDatabase;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
/*public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getFpdFlag() {
        return fpdFlag;
    }

    public void setFpdFlag(Boolean fpdFlag) {
        this.fpdFlag = fpdFlag;
    }

    public String getOffPercent() {
        return offPercent;
    }

    public void setOffPercent(String offPercent) {
        this.offPercent = offPercent;
    }

    public Integer getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Integer currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Integer getAllPostsCount() {
        return allPostsCount;
    }

    public void setAllPostsCount(Integer allPostsCount) {
        this.allPostsCount = allPostsCount;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getVoteValue() {
        return voteValue;
    }

    public void setVoteValue(Integer voteValue) {
        this.voteValue = voteValue;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    */

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }/*/

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getDealUrl() {
        return dealUrl;
    }

    public void setDealUrl(String dealUrl) {
        this.dealUrl = dealUrl;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public VoteDownReason getVoteDownReason() {
        return voteDownReason;
    }

    public void setVoteDownReason(VoteDownReason voteDownReason) {
        this.voteDownReason = voteDownReason;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getFpdSuggestted() {
        return fpdSuggestted;
    }

    public void setFpdSuggestted(Boolean fpdSuggestted) {
        this.fpdSuggestted = fpdSuggestted;
    }

    public Integer getFrontPageSuggestionsCount() {
        return frontPageSuggestionsCount;
    }

    public void setFrontPageSuggestionsCount(Integer frontPageSuggestionsCount) {
        this.frontPageSuggestionsCount = frontPageSuggestionsCount;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/

    @Override
    public String toString() {
        return "DealsData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public class Deals {

        @SerializedName("total_count")
        @Expose
        private Integer totalCount;
        @SerializedName("data")
        @Expose
        private List<DealsData> data = null;

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public List<DealsData> getData() {
            return data;
        }

        public void setData(List<DealsData> data) {
            this.data = data;
        }

    }

    public class Response {

        /*@SerializedName("seo_setting")
        @Expose
        private SeoSetting seoSetting;*/
        @SerializedName("deals")
        @Expose
        private Deals deals;

        /*public SeoSetting getSeoSetting() {
            return seoSetting;
        }

        public void setSeoSetting(SeoSetting seoSetting) {
            this.seoSetting = seoSetting;
        }*/

        public Deals getDeals() {
            return deals;
        }

        public void setDeals(Deals deals) {
            this.deals = deals;
        }

    }
    /*public class Merchant {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("permalink")
        @Expose
        private String permalink;
        *//*@SerializedName("recommendation")
        @Expose
        private int recommendation;*//*
        @SerializedName("recommendation_flag")
        @Expose
        private Boolean recommendationFlag;
        @SerializedName("average_rating")
        @Expose
        private String averageRating;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPermalink() {
            return permalink;
        }

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }

        *//*public int getRecommendation() {
            return recommendation;
        }

        public void setRecommendation(int recommendation) {
            this.recommendation = recommendation;
        }*//*

        public Boolean getRecommendationFlag() {
            return recommendationFlag;
        }

        public void setRecommendationFlag(Boolean recommendationFlag) {
            this.recommendationFlag = recommendationFlag;
        }

        public String getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(String averageRating) {
            this.averageRating = averageRating;
        }

    }
    public class SeoSetting {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("web_url")
        @Expose
        private String webUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

    }*/
   /* public class User {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("current_dimes")
        @Expose
        private Integer currentDimes;
        @SerializedName("karma")
        @Expose
        private Integer karma;
        @SerializedName("fpd_count")
        @Expose
        private Integer fpdCount;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public Integer getCurrentDimes() {
            return currentDimes;
        }

        public void setCurrentDimes(Integer currentDimes) {
            this.currentDimes = currentDimes;
        }

        public Integer getKarma() {
            return karma;
        }

        public void setKarma(Integer karma) {
            this.karma = karma;
        }

        public Integer getFpdCount() {
            return fpdCount;
        }

        public void setFpdCount(Integer fpdCount) {
            this.fpdCount = fpdCount;
        }

    }
    public class VoteDownReason {

        @SerializedName("Self Promotion")
        @Expose
        private Integer selfPromotion;

        public Integer getSelfPromotion() {
            return selfPromotion;
        }

        public void setSelfPromotion(Integer selfPromotion) {
            this.selfPromotion = selfPromotion;
        }

    }*/
    private void writeObject(ObjectOutputStream out) throws IOException {

        out.writeObject(id);
        out.writeObject(title);
        out.writeObject(description);
        out.writeObject(image);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {

        id = (Integer) in.readObject();
        title = (String) in.readObject();
        description = (String) in.readObject();
        image = (String) in.readObject();

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        picture = BitmapFactory.decodeByteArray(bitmapBytes, 0,
                bitmapBytes.length);
    }

}

