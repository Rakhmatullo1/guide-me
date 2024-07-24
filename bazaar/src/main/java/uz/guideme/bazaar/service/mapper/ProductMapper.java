package uz.guideme.bazaar.service.mapper;

import uz.guideme.bazaar.entity.CommentEntity;
import uz.guideme.bazaar.entity.ProductEntity;
import uz.guideme.bazaar.service.dto.ProductDTO;
import uz.guideme.bazaar.service.enumeration.ProductTypes;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
public class ProductMapper {

    public static ProductEntity toEntity(ProductDTO productDTO) {
        ProductEntity entity = new ProductEntity();

        entity.setDescription(productDTO.getDescription());
        entity.setPrice(productDTO.getPrice());
        entity.setName(productDTO.getName());
        entity.setCategory(productDTO.getCategory().name());
        entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return entity;
    }

    public static ProductDTO toDto(ProductEntity product, String serverUrl) {
        ProductDTO productDTO = new ProductDTO();

        if(Objects.nonNull(product.getImages()) && !product.getImages().isEmpty()) {
            productDTO.setImageUrl(serverUrl + "?filePath="+product.getImages().get(0).getUrl());
        }

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setCreatedAt(product.getCreatedAt().toString());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(getCategory(product.getCategory()));
        productDTO.setOverallRanking(calculateOverallRanking(product.getComments()));

        ProductDTO.MarketDTO marketDTO = new ProductDTO.MarketDTO();

        marketDTO.setAddress(product.getMarket().getAddress());
        marketDTO.setName(product.getMarket().getName());
        marketDTO.setId(product.getMarket().getId());

        if(Objects.nonNull(product.getMarket().getImages()) && !product.getMarket().getImages().isEmpty()) {
            marketDTO.setImageUrl(serverUrl + "?filePath="+product.getMarket().getImages().get(0).getUrl());
        }
        productDTO.setMarket(marketDTO);

        return productDTO;
    }

    private static double calculateOverallRanking(List<CommentEntity> comments){

        if(Objects.isNull(comments) || comments.isEmpty()) {
            return 0;
        }

        int sum = comments.stream().mapToInt(CommentEntity::getRatings).sum();
        int size = comments.size();

        return sum/size;
    }

    private static ProductTypes getCategory(String type) {
        return ProductTypes.valueOf(type);
    }

}
