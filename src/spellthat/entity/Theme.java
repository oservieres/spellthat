package spellthat.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Theme implements Parcelable {
	
	private String label;
	private int id;
	
	public Theme(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(label);
	}
	
	public static final Parcelable.Creator<Theme> CREATOR = new Parcelable.Creator<Theme>()
			{
			    @Override
			    public Theme createFromParcel(Parcel source)
			    {
			        return new Theme(source);
			    }

			    @Override
			    public Theme[] newArray(int size)
			    {
				return new Theme[size];
			    }
			};

			public Theme(Parcel in) {
				this.id = in.readInt();
				this.label = in.readString();
			}
}
