package bloguelinux.sandmarq.ca.bloguelinux;

/**
 * Created by sandrine on 2014-12-23.
 */
public class ShowsList {
    private String mTitre, mDescription, mLienMp3, mLienOgg;

    public ShowsList(String titre, String description, String lienMp3, String lienOgg) {
        mTitre = titre;
        mDescription = description;
        mLienMp3 = lienMp3;
        mLienOgg = lienOgg;
    }

    public String getTitre() {
        return mTitre;
    }

    public void setTitre(String titre) {
        mTitre = titre;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getLienMp3() {
        return mLienMp3;
    }

    public void setLienMp3(String lienMp3) {
        mLienMp3 = lienMp3;
    }

    public String getLienOgg() {
        return mLienOgg;
    }

    public void setLienOgg(String lienOgg) {
        mLienOgg = lienOgg;
    }
}