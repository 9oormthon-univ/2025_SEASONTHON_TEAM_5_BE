package com.goormthon.whattoeat.config;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenAIConfig {

    @Bean
    @ConfigurationProperties(prefix = "openai")
    public OpenAIProps openAIProps() {
        return new OpenAIProps();
    }

    @Bean
    public OpenAIClient openAIClient(OpenAIProps props) {
        return OpenAIOkHttpClient.builder()
                .apiKey(props.getApiKey())
                .build();
    }

    @Getter
    @Setter
    public static class OpenAIProps {
        private String apiKey;
        private String model = "gpt-5-mini";
        private Double temperature = 0.7;
        private Integer maxTokens = 800;
    }
}