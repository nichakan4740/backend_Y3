package sit.int221.oasip;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sit.int221.oasip.Utils.ListMapper;
import sit.int221.oasip.property.FileStorageProperties;

@Configuration
@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})

public class ApplicationConfig{


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public ListMapper listMapper() {
        return ListMapper.getInstance();
    }


}
