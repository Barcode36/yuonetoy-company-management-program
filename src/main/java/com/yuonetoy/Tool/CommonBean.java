package com.yuonetoy.Tool;

import com.yuonetoy.DTO.BusinessJournalHistoryDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class CommonBean {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addMappings(new PropertyMap<SalesAccountMachine, SalesAccountMachineDetailDTO>() {
            @Override
            protected void configure() {
                map().setMachineCount(source.getSalesAccountMachineStock().getCount());
                map().setProductCount(source.getSalesAccountProductStock().getCount());
            }
        });

        return modelMapper;
    }
}
