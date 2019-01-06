
package cariperumahan.rini.com.cariperumahan.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Page implements Parcelable
{

    private int page;
    private String url;
    private String current_page;
    public final static Creator<Page> CREATOR = new Creator<Page>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        public Page[] newArray(int size) {
            return (new Page[size]);
        }

    }
    ;

    protected Page(Parcel in) {
        this.page = ((int) in.readValue((int.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.current_page = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Page() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(url);
        dest.writeValue(current_page);
    }

    public int describeContents() {
        return  0;
    }

}
