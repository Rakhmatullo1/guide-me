package uz.guideme.hotelbooking.service.mapper;

import uz.guideme.hotelbooking.entity.BookingEntity;
import uz.guideme.hotelbooking.entity.RoomEntity;
import uz.guideme.hotelbooking.service.dto.BookingDTO;
import uz.guideme.hotelbooking.service.enumeration.Status;
import uz.guideme.hotelbooking.service.utils.DateUtils;

import java.util.Date;

public class BookingMapper {
    public static BookingEntity toEntity(BookingDTO dto, RoomEntity room, String username) {
        BookingEntity entity = new BookingEntity();

        entity.setCreatedAt(new Date(System.currentTimeMillis()));
        entity.setStatus(Status.PENDING);
        entity.setUsername(username);
        entity.setRoom(room);
        entity.setFromDate(DateUtils.getDate(dto.getFromDate()));
        entity.setToDate(DateUtils.getDate(dto.getToDate()));

        return entity;
    }

    public static BookingDTO toDto(BookingEntity entity, String email) {
        BookingDTO dto = new BookingDTO();

        dto.setCreatedAt(entity.getCreatedAt().toString());
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(email);
        dto.setToDate(entity.getToDate().toInstant().toString());
        dto.setFromDate(entity.getFromDate().toString());
        dto.setRoomId(entity.getRoom().getId());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}
