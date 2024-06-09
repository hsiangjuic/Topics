package com.ispan.hotel.model;



import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Room.class)
public class Room_ {

	public static SingularAttribute<Room, Integer> rId;
	public static SingularAttribute<Room, String> roomNumber;
	public static SingularAttribute<Room, String> floor;
	public static SingularAttribute<Room, String> roomStatus;
	public static SingularAttribute<Room, RoomType> roomType;
	

}
