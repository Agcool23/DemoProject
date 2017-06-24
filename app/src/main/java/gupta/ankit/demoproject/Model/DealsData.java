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
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

        @SerializedName("deals")
        @Expose
        private Deals deals;

        public Deals getDeals() {
            return deals;
        }

        public void setDeals(Deals deals) {
            this.deals = deals;
        }

    }
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

