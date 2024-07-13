package uz.guideme.bazaar.service.mapper;

import uz.guideme.bazaar.entity.CommentEntity;
import uz.guideme.bazaar.entity.MarketEntity;
import uz.guideme.bazaar.entity.ProductEntity;
import uz.guideme.bazaar.service.dto.MarketDTO;

import java.util.List;
import java.util.Objects;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
public class MarketMapper {

    public static MarketEntity toEntity(MarketDTO marketDTO) {
        MarketEntity entity = new MarketEntity();

        entity.setDescription(marketDTO.getDescription());
        entity.setCity(marketDTO.getCity());
        entity.setAddress(marketDTO.getAddress());
        entity.setLatitude(marketDTO.getLatitude());
        entity.setLongitude(marketDTO.getLongitude());
        entity.setPhoneNumber(marketDTO.getPhoneNumber());
        entity.setName(marketDTO.getName());

        return entity;
    }

    public static MarketDTO toDto(MarketEntity entity) {
        MarketDTO dto = new MarketDTO();

        dto.setId(entity.getId());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setName(entity.getName());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setDescription(entity.getDescription());
        dto.setOverallRanking(calculateOverall(entity.getProducts()));
        dto.setPhoneNumber(entity.getPhoneNumber());

        return dto;
    }

    private static double calculateOverall(List<ProductEntity> comments) {

        if(Objects.isNull(comments) || comments.isEmpty()) {
            return 0;
        }

       int  overall = comments.stream().filter(value->!Objects.isNull(value.getComments())).mapToInt(value -> value.getComments().stream().mapToInt(CommentEntity::getRatings).sum()).sum();
       int size =  comments.stream().filter(value->!Objects.isNull(value.getComments())).mapToInt(value -> value.getComments().size()).sum();

       if(size==0) {
           return 0;
       }

       return overall/size;
    }

}
