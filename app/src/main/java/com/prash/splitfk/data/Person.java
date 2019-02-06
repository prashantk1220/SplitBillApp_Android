package com.prash.splitfk.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "person_table")
public class Person implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="name")
    String name;

    @ColumnInfo(name="paid")
    double paid;

    @ColumnInfo(name="share")
    double share;

    @ColumnInfo(name="trans")
    double transaction;

    public Person(){

    }

    public Person(String name) {
        this.name = name;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getShare() {
        return share;
    }

    public void setShare(double share) {
        this.share = share;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTransaction() {
        return transaction;
    }

    public void setTransaction(double transaction) {
        this.transaction = transaction;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(name);
//        parcel.writeDouble(transaction);
//        parcel.writeDouble(paid);
//        parcel.writeDouble(share);
//    }
//
//    public Person(Parcel in) {
//        name = in.readString();
//        paid = in.readDouble();
//        share = in.readDouble();
//        transaction = in.readDouble();
//    }
//
//    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
//        public Person createFromParcel(Parcel in) {
//            return new Person(in);
//        }
//
//        public Person[] newArray(int size) {
//            return new Person[size];
//        }
//    };
}
