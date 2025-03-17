package learn.vibeur.models;

public class Mood {
    private int moodId;
    private String moodName;

    public Mood(int moodId, String moodName) {
        this.moodId = moodId;
        this.moodName = moodName;
    }

    public Mood() {
    }

    public int getMoodId() {
        return moodId;
    }

    public void setMoodId(int moodId) {
        this.moodId = moodId;
    }

    public String getMoodName() {
        return moodName;
    }

    public void setMoodName(String moodName) {
        this.moodName = moodName;
    }
}

