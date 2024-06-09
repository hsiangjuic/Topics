package com.ispan.hotel.model;

import java.time.LocalDateTime;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Booking.class)
public class Booking_ {
	
		public static SingularAttribute<Booking, Integer> bid;
		public static SingularAttribute<Booking, Room> room;
		public static SingularAttribute<Booking, String> bookingStatus;
		public static SingularAttribute<Booking, LocalDateTime> beginDate;
		public static SingularAttribute<Booking, LocalDateTime> lastDate;
		
}
