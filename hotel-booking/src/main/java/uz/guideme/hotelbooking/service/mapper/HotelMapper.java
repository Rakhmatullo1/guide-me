package uz.guideme.hotelbooking.service.mapper;

import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.entity.ReviewEntity;
import uz.guideme.hotelbooking.service.dto.HotelDTO;
import uz.guideme.hotelbooking.service.dto.HotelDTO.ReviewDTO;
import uz.guideme.hotelbooking.service.dto.HotelDTO.RoomDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class HotelMapper {


    public static HotelEntity toEntity(HotelDTO hotelDTO, UUID ownerId) {
        HotelEntity entity = toEntity(hotelDTO);
        entity.setId(UUID.randomUUID());
        entity.setOwnerId(ownerId);
        return entity;
    }

    public static HotelEntity toEntity(HotelDTO hotelDTO) {
        HotelEntity entity = new HotelEntity();
        entity.setId(hotelDTO.getId());
        entity.setAddress(hotelDTO.getAddress());
        entity.setName(hotelDTO.getName());
        entity.setLatitude(hotelDTO.getLatitude());
        entity.setLongitude(hotelDTO.getLongitude());
        entity.setDescription(hotelDTO.getDescription());
        entity.setOwnerId(hotelDTO.getOwnerId());
        entity.setType(hotelDTO.getHotelType());
        entity.setReviews(new ArrayList<>());
        entity.setRooms(new ArrayList<>());
        entity.setImages(new ArrayList<>());

        entity.setCity(hotelDTO.getCity());
        entity.setProvince(hotelDTO.getProvince());
        entity.setStar(hotelDTO.getStar());
        entity.setCheckIn(hotelDTO.getCheckIn());
        entity.setCheckOut(hotelDTO.getCheckOut());
        entity.setPhoneNumber(hotelDTO.getPhoneNumber());

        return entity;
    }

    public static HotelDTO toDTO(HotelEntity hotel, String serverUrl) {
        HotelDTO dto = new HotelDTO();

        dto.setAddress(hotel.getAddress());
        dto.setDescription(hotel.getDescription());
        dto.setHotelType(hotel.getType());
        dto.setLatitude(hotel.getLatitude());
        dto.setLongitude(hotel.getLongitude());
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setRank(getAvgRank(hotel.getReviews()));

        dto.setCity(hotel.getCity());
        dto.setProvince(hotel.getProvince());
        dto.setCheckIn(hotel.getCheckIn());
        dto.setCheckOut(hotel.getCheckOut());
        dto.setStar(hotel.getStar());
        dto.setPhoneNumber(hotel.getPhoneNumber());

        List<ReviewDTO> reviews = hotel.getReviews().stream().filter(x->x.getRank()>=4.5).map(ReviewMapper::toDTO).toList();
        List<RoomDTO> rooms  = hotel.getRooms().stream().map(v->RoomMapper.toDto(v, serverUrl)).toList();
        List<String> images = hotel.getImages().stream().map(x->serverUrl+"/"+x.getUrl()).toList();

        reviews = cutList(reviews);
        rooms = cutList(rooms);

        dto.setReviews(reviews);
        dto.setRooms(rooms);
        dto.setImages(images);

        return dto;
    }

    private static <T> List<T> cutList(List<T> list) {
        if(list.size()>=3) {
            list = list.subList(0,3);
        }
        return list;
    }

    private static int getAvgRank(List<ReviewEntity> reviews) {
        if(Objects.isNull(reviews) || reviews.isEmpty()) {
            return 0;
        }

        int overallRank =0;

        for(ReviewEntity review : reviews) {
            overallRank = overallRank + review.getRank();
        }

        return overallRank/reviews.size();
    }
}
