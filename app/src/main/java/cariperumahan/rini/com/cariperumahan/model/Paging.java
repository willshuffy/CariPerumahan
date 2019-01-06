
package cariperumahan.rini.com.cariperumahan.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Paging implements Parcelable
{

    private String first;
    private List<Page> pages = null;
    private String last;
    private int total_pages;
    public final static Creator<Paging> CREATOR = new Creator<Paging>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Paging createFromParcel(Parcel in) {
            return new Paging(in);
        }

        public Paging[] newArray(int size) {
            return (new Paging[size]);
        }

    }
    ;

    protected Paging(Parcel in) {
        this.first = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.pages, (Page.class.getClassLoader()));
        this.last = ((String) in.readValue((String.class.getClassLoader())));
        this.total_pages = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Paging() {
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(first);
        dest.writeList(pages);
        dest.writeValue(last);
        dest.writeValue(total_pages);
    }

    public int describeContents() {
        return  0;
    }

}
