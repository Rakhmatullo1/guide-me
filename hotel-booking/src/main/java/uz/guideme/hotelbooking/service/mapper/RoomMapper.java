package uz.guideme.hotelbooking.service.mapper;

import uz.guideme.hotelbooking.entity.FacilityEntity;
import uz.guideme.hotelbooking.entity.RoomEntity;
import uz.guideme.hotelbooking.service.dto.HotelDTO.RoomDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomMapper {

    public static RoomDTO toDto(RoomEntity entity, String serverUrl) {
        RoomDTO dto = new RoomDTO();

        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        dto.setHasAC(entity.isHasAC());
        dto.setPrice(entity.getPrice());
        dto.setSize(entity.getSize());
        dto.setBedType(entity.getBedType());
        dto.setHasBreakfast(entity.isHasBreakfast());
        dto.setHasFreeWiFi(entity.isHasFreeWifi());
        dto.setTaxes(entity.getTaxes());
        dto.setBedsCount(entity.getBedsCount());
        dto.setSmoking(entity.isSmoking());
        dto.setMaxPerson(dto.getMaxPerson());

        List<String> images = entity.getImages().stream().map(x->serverUrl+"/"+x.getUrl()).toList();
        List<String> facilities = entity.getFacilities().stream().map(FacilityEntity::getName).toList();

        images = cutList(images);


        dto.setImages(images);
        dto.setRoomFacilities(facilities);

        return dto;
    }

    public static RoomEntity toEntity(RoomDTO dto) {
        RoomEntity entity = new RoomEntity();

        entity.setId(UUID.randomUUID());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setSize(dto.getSize());
        entity.setBedType(dto.getBedType());
        entity.setHasAC(dto.isHasAC());
        entity.setHasBreakfast(dto.isHasBreakfast());
        entity.setSmoking(dto.isSmoking());
        entity.setTaxes(dto.getTaxes());
        entity.setBedsCount(dto.getBedsCount());
        entity.setPrice(dto.getPrice());
        entity.setHasFreeWifi(dto.isHasFreeWiFi());
        entity.setBreakfastPrice(dto.getBreakfastPrice());
        entity.setMaxPerson(dto.getMaxPerson());
        entity.setFacilities(new ArrayList<>());
        entity.setImages(new ArrayList<>());

        return entity;
    }

    private static <T> List<T> cutList(List<T> list) {
        if(list.size()>=3) {
            list = list.subList(0,3);
        }
        return list;
    }
}
