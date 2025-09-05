package com.goormthon.whattoeat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goormthon.whattoeat.dto.RecipeRequest;
import com.goormthon.whattoeat.dto.RecipeResponse;
import com.goormthon.whattoeat.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RecipeService {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final IngredientRepository ingredientRepository;

    public RecipeService(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper, IngredientRepository ingredientRepository) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
        this.ingredientRepository = ingredientRepository;
    }

    public RecipeResponse getRecipe(RecipeRequest request) {
        String template;
        try {
            template = new String(
                    new ClassPathResource("prompts/recipe_prompt.st").getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            ).replace("{", "\\{")
                    .replace("}", "\\}")
                    .replace("\\{ingredients\\}", "{ingredients}")
                    .replace("\\{cook_time_minutes\\}", "{cook_time_minutes}");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "템플릿 로딩 실패", e);
        }
        /*
        Todo
        userId로 재료 목록 가져오기
         */
        String ingredients = "양파 1개, 감자 1개, 소금 20g, 돼지고기 400g, 카레가루 400g, 우유 400ml, 소시지 500g";

        Map<String, Object> vars = new HashMap<>();
        vars.put("ingredients", ingredients);
        vars.put("cook_time_minutes", request.getCookTimeMinutes());

        var prompt = new PromptTemplate(template).create(vars);
        log.info("prompt: {}", prompt);

        String raw;
        try {
            raw = chatClient.prompt(prompt)
                    .call()
                    .content();

            raw = stripCodeFences(raw);
            log.info("OpenAI 답변: ", raw);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "OpenAI 답변 로딩 실패", e);
        }

        RecipeResponse recipeResponse;
        try {
            recipeResponse = objectMapper.readValue(raw, RecipeResponse.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "OpenAI 답변 파싱 실패", e);
        }

        return recipeResponse;

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
