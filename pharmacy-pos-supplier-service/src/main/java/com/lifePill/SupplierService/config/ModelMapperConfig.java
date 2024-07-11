package com.lifePill.SupplierService.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for ModelMapper.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Configures and initializes the ModelMapper bean.
     *
     * @return Initialized ModelMapper instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

//        // Add explicit mapping for categoryId
//        modelMapper.typeMap(ItemSaveRequestCategoryDTO.class, ItemCategory.class)
//                .addMapping(ItemSaveRequestCategoryDTO::getCategoryId, ItemCategory::setCategoryId);

        return modelMapper;
    }
}
