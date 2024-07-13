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
            productDTO.setImageUrl(serverUrl + "?filePath="+product.getImages().get(0));
        }

        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(getCategory(product.getCategory()));
        productDTO.setOverallRanking(calculateOverallRanking(product.getComments()));

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
