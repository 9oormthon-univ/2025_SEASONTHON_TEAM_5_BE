package com.goormthon.whattoeat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.whattoeat.dto.RecipeRequest;
import com.goormthon.whattoeat.dto.RecipeResponse;
import com.goormthon.whattoeat.repository.IngredientRepository;
import io.restassured.internal.common.classpath.ClassPathResolver;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeService {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final IngredientRepository ingredientRepository;

    public RecipeService(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper, IngredientRepository ingredientRepository){
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
        this.ingredientRepository = ingredientRepository;
    }

    public RecipeResponse getRecipe(String ingredients, RecipeRequest request){
        try {
            String template = new String(
                    new ClassPathResource("prompts/recipe_prompt.st").getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            /*
            Todo
            userId로 재료 목록 가져오기
             */

            Map<String, Object> vars = new HashMap<>();
            vars.put("ingredients", ingredients);
            vars.put("cook_time_minutes", request.getCookTimeMinutes());

            var prompt = new PromptTemplate(template).create(vars);

            String raw = chatClient.prompt(prompt)
                    .call()
                    .content();

            raw = stripCodeFences(raw);

            RecipeResponse recipeResponse = objectMapper.readValue(raw, RecipeResponse.class);

            return recipeResponse;
        } catch (Exception e){
            throw new RuntimeException("레시피 JSON 생성 실패");
        }
    }
    private String stripCodeFences(String s) {
        String trimmed = s.trim();
        if (trimmed.startsWith("```")) {
            int idx = trimmed.indexOf('\n');
            if (idx != -1) trimmed = trimmed.substring(idx + 1).trim();
            if (trimmed.endsWith("```")) {
                trimmed = trimmed.substring(0, trimmed.length() - 3).trim();
            }
        }
        return trimmed;
    }
}
